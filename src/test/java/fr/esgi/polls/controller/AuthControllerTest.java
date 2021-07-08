package fr.esgi.polls.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.polls.model.Role;
import fr.esgi.polls.model.RoleName;
import fr.esgi.polls.model.User;
import fr.esgi.polls.payload.LoginRequest;
import fr.esgi.polls.payload.SignUpRequest;
import fr.esgi.polls.repository.RoleRepository;
import fr.esgi.polls.repository.UserRepository;
import fr.esgi.polls.security.JwtTokenProvider;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAuthenticateUser() throws Exception {
        when(this.jwtTokenProvider.generateToken((org.springframework.security.core.Authentication) any()))
                .thenReturn("foo");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("rayanntoto");
        loginRequest.setPassword("testtoto");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"accessToken\":\"foo\",\"tokenType\":\"Bearer\"}"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(this.userRepository.existsByUsername(anyString())).thenReturn(true);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("rayann@rayann.fr");
        signUpRequest.setPassword("testtoto");
        signUpRequest.setUsername("rayanntoto");
        signUpRequest.setName("Rayann");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":false,\"message\":\"Username is already taken!\"}"));
    }

    @Test
    public void testRegisterUser2() throws Exception {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(true);
        when(this.userRepository.existsByUsername(anyString())).thenReturn(false);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("rayann@rayann.fr");
        signUpRequest.setPassword("testtoto");
        signUpRequest.setUsername("rayanntoto");
        signUpRequest.setName("Rayann");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"success\":false,\"message\":\"Email Address already in use!\"}"));
    }

    @Test
    public void testRegisterUser3() throws Exception {
        User user = new User();
        user.setCreatedAt(null);
        user.setEmail("rayann@rayann.fr");
        user.setPassword("testtoto");
        user.setUsername("rayanntoto");
        user.setId(123L);
        user.setName("Rayann");
        user.setRoles(new HashSet<Role>());
        user.setUpdatedAt(null);
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.userRepository.existsByUsername(anyString())).thenReturn(false);

        Role role = new Role();
        role.setId(123L);
        role.setName(RoleName.ROLE_USER);
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((RoleName) any())).thenReturn(ofResult);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("rayann@rayann.fr");
        signUpRequest.setPassword("testtoto");
        signUpRequest.setUsername("rayanntoto");
        signUpRequest.setName("Rayann");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"User registered successfully\"}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/api/users/rayanntoto"));
    }

    @Test
    public void testRegisterUser4() throws Exception {
        User user = new User();
        user.setCreatedAt(null);
        user.setEmail("rayann@rayann.fr");
        user.setPassword("testtoto");
        user.setUsername("rayanntoto");
        user.setId(123L);
        user.setName("Rayann");
        user.setRoles(new HashSet<Role>());
        user.setUpdatedAt(null);
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.userRepository.existsByUsername(anyString())).thenReturn(false);
        when(this.roleRepository.findByName((fr.esgi.polls.model.RoleName) any())).thenReturn(Optional.<Role>empty());
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("rayann@rayann.fr");
        signUpRequest.setPassword("testtoto");
        signUpRequest.setUsername("rayanntoto");
        signUpRequest.setName("Rayann");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }
}

