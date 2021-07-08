package fr.esgi.polls.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import fr.esgi.polls.PollResponse;
import fr.esgi.polls.model.Role;
import fr.esgi.polls.model.User;
import fr.esgi.polls.payload.PagedResponse;
import fr.esgi.polls.repository.PollRepository;
import fr.esgi.polls.repository.UserRepository;
import fr.esgi.polls.repository.VoteRepository;
import fr.esgi.polls.security.UserPrincipal;
import fr.esgi.polls.service.PollService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @MockBean
    private PollRepository pollRepository;

    @MockBean
    private PollService pollService;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VoteRepository voteRepository;

    @Test
    public void testCheckEmailAvailability() throws Exception {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                .param("email", "une valeur");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    public void testGetPollsCreatedBy() throws Exception {
        when(this.pollService.getPollsCreatedBy(anyString(), (UserPrincipal) any(), anyInt(), anyInt()))
                .thenReturn(new PagedResponse<PollResponse>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users/{username}/polls", "yolantoto");
        MockHttpServletRequestBuilder paramResult = getResult.param("currentUser",
                String.valueOf(new UserPrincipal(123L, "Yolan", "Yolantoto",
                        "yolan@yolan.fr", "testtoto", new ArrayList<GrantedAuthority>())));
        MockHttpServletRequestBuilder paramResult1 = paramResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult1.param("size", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"content\":null,\"page\":0,\"size\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
    }

    @Test
    public void testGetPollsVotedBy() throws Exception {
        when(this.pollService.getPollsVotedBy(anyString(), (UserPrincipal) any(), anyInt(), anyInt()))
                .thenReturn(new PagedResponse<PollResponse>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users/{username}/votes", "yolantoto");
        MockHttpServletRequestBuilder paramResult = getResult.param("currentUser",
                String.valueOf(new UserPrincipal(123L, "Yolan", "Yolantoto",
                        "yolan@yolan.fr", "testtoto", new ArrayList<GrantedAuthority>())));
        MockHttpServletRequestBuilder paramResult1 = paramResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult1.param("size", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"content\":null,\"page\":0,\"size\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
    }

    @Test
    public void testCheckEmailAvailability2() throws Exception {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                .param("email", "une valeur");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":true}"));
    }

    @Test
    public void testCheckUsernameAvailability() throws Exception {
        when(this.userRepository.existsByUsername(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                .param("username", "une valeur");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    public void testCheckUsernameAvailability2() throws Exception {
        when(this.userRepository.existsByUsername(anyString())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                .param("username", "luka");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":true}"));
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser",
                String.valueOf(new UserPrincipal(123L, "Yolan", "Yolantoto",
                        "yolan@yolan.fr", "testtoto", new ArrayList<GrantedAuthority>())));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"username\":null,\"name\":null}"));
    }

    @Test
    public void testGetUserProfile() throws Exception {
        when(this.voteRepository.countByUserId((Long) any())).thenReturn(3L);

        User user = new User();
        user.setCreatedAt(null);
        user.setEmail("luka@luka.fr");
        user.setPassword("testtoto");
        user.setUsername("lukatoto");
        user.setId(123L);
        user.setName("Luka");
        user.setRoles(new HashSet<Role>());
        user.setUpdatedAt(null);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findByUsername(anyString())).thenReturn(ofResult);
        when(this.pollRepository.countByCreatedBy((Long) any())).thenReturn(3L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/{username}", "lukatoto");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"username\":\"lukatoto\",\"name\":\"Luka\",\"joinedAt\":null,\"pollCount\":3,\"voteCount\":3}"));
    }

    @Test
    public void testGetUserProfile2() throws Exception {
        when(this.voteRepository.countByUserId((Long) any())).thenReturn(3L);
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.<User>empty());
        when(this.pollRepository.countByCreatedBy((Long) any())).thenReturn(3L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/{username}", "lukatoto");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

