package fr.esgi.polls.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.polls.PollResponse;
import fr.esgi.polls.payload.ChoiceRequest;
import fr.esgi.polls.payload.ChoiceResponse;
import fr.esgi.polls.payload.PagedResponse;
import fr.esgi.polls.payload.PollLength;
import fr.esgi.polls.payload.PollRequest;
import fr.esgi.polls.payload.UserSummary;
import fr.esgi.polls.payload.VoteRequest;
import fr.esgi.polls.repository.PollRepository;
import fr.esgi.polls.repository.UserRepository;
import fr.esgi.polls.repository.VoteRepository;
import fr.esgi.polls.security.UserPrincipal;
import fr.esgi.polls.service.PollService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PollController.class})
@ExtendWith(SpringExtension.class)
public class PollControllerTest {
    @Autowired
    private PollController pollController;

    @MockBean
    private PollRepository pollRepository;

    @MockBean
    private PollService pollService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VoteRepository voteRepository;

    @Test
    public void testCastVote() throws Exception {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setCreatedBy(new UserSummary(123L, "janedoe", "Name"));
        pollResponse.setExpirationDateTime(null);
        pollResponse.setQuestion("Question");
        pollResponse.setTotalVotes(1L);
        pollResponse.setExpired(true);
        pollResponse.setId(123L);
        pollResponse.setChoices(new ArrayList<ChoiceResponse>());
        pollResponse.setCreationDateTime(null);
        pollResponse.setSelectedChoice(1L);
        when(this.pollService.castVoteAndGetUpdatedPoll((Long) any(), (VoteRequest) any(), (UserPrincipal) any()))
                .thenReturn(pollResponse);

        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setChoiceId(123L);
        String content = (new ObjectMapper()).writeValueAsString(voteRequest);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/polls/{pollId}/votes", 123L);
        MockHttpServletRequestBuilder requestBuilder = postResult
                .param("currentUser",
                        String.valueOf(new UserPrincipal(123L, "fr.esgi.polls.security.UserPrincipal", "janedoe",
                                "jane.doe@example.org", "iloveyou", new ArrayList<GrantedAuthority>())))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.pollController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"question\":\"Question\",\"choices\":[],\"createdBy\":{\"id\":123,\"username\":\"janedoe\",\"name\":"
                                        + "\"Name\"},\"creationDateTime\":null,\"expirationDateTime\":null,\"selectedChoice\":1,\"totalVotes\":1,\"expired"
                                        + "\":true}"));
    }

    @Test
    public void testGetPollById() throws Exception {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setCreatedBy(new UserSummary(123L, "janedoe", "Name"));
        pollResponse.setExpirationDateTime(null);
        pollResponse.setQuestion("Question");
        pollResponse.setTotalVotes(1L);
        pollResponse.setExpired(true);
        pollResponse.setId(123L);
        pollResponse.setChoices(new ArrayList<ChoiceResponse>());
        pollResponse.setCreationDateTime(null);
        pollResponse.setSelectedChoice(1L);
        when(this.pollService.getPollById((Long) any(), (UserPrincipal) any())).thenReturn(pollResponse);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/polls/{pollId}", 123L);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser",
                String.valueOf(new UserPrincipal(123L, "fr.esgi.polls.security.UserPrincipal", "janedoe",
                        "jane.doe@example.org", "iloveyou", new ArrayList<GrantedAuthority>())));
        MockMvcBuilders.standaloneSetup(this.pollController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"question\":\"Question\",\"choices\":[],\"createdBy\":{\"id\":123,\"username\":\"janedoe\",\"name\":"
                                        + "\"Name\"},\"creationDateTime\":null,\"expirationDateTime\":null,\"selectedChoice\":1,\"totalVotes\":1,\"expired"
                                        + "\":true}"));
    }

    @Test
    public void testCreatePoll() throws Exception {
        when(this.pollService.getAllPolls((fr.esgi.polls.security.UserPrincipal) any(), anyInt(), anyInt()))
                .thenReturn(new PagedResponse<PollResponse>());

        PollLength pollLength = new PollLength();
        pollLength.setDays(1);
        pollLength.setHours(1);

        PollRequest pollRequest = new PollRequest();
        pollRequest.setQuestion("Question");
        pollRequest.setPollLenght(pollLength);
        pollRequest.setChoices(new ArrayList<ChoiceRequest>());
        String content = (new ObjectMapper()).writeValueAsString(pollRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.pollController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"content\":null,\"page\":0,\"size\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
    }

    @Test
    public void testGetPolls() throws Exception {
        when(this.pollService.getAllPolls((UserPrincipal) any(), anyInt(), anyInt()))
                .thenReturn(new PagedResponse<PollResponse>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/polls");
        MockHttpServletRequestBuilder paramResult = getResult.param("currentUser",
                String.valueOf(new UserPrincipal(123L, "fr.esgi.polls.security.UserPrincipal", "janedoe",
                        "jane.doe@example.org", "iloveyou", new ArrayList<GrantedAuthority>())));
        MockHttpServletRequestBuilder paramResult1 = paramResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult1.param("size", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.pollController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"content\":null,\"page\":0,\"size\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
    }
}

