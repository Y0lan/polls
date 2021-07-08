package fr.esgi.polls;

import fr.esgi.polls.controller.PollController;
import fr.esgi.polls.payload.ChoiceResponse;
import fr.esgi.polls.payload.PagedResponse;
import fr.esgi.polls.payload.PollLength;
import fr.esgi.polls.payload.PollRequest;
import fr.esgi.polls.security.CurrentUser;
import fr.esgi.polls.security.UserPrincipal;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PollsControllerTest {

    @Mock
    UserPrincipal userPrincipal;

    @InjectMocks
    PollController pollController;

    @Test
    public void createPollTest()
    {
        PollResponse pollResponse = new PollResponse();
        ChoiceResponse choiceResponse = new ChoiceResponse();
        choiceResponse.setText("toto");
        choiceResponse.setId(1);
        List<ChoiceResponse> list = new ArrayList<ChoiceResponse>() {};
        list.add(choiceResponse);
        pollResponse.setChoices(list);
        //Length
        PollLength pollLength = new PollLength();
        pollLength.setDays(1);
        pollLength.setHours(1);
        //Request
        PollRequest pollRequest = new PollRequest();
        pollRequest.setQuestion("Ceci est une question de test");
        pollRequest.setPollLenght(pollLength);

        ResponseEntity responseEntity = pollController.createPoll(pollRequest);
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assert.assertEquals(20d,responseEntity.getBody());
    }

    @Test
    public void getPollByIdTest()
    {
        PollResponse response = pollController.getPollById(userPrincipal, Long.parseLong("12124"));
        Assert.assertNotNull(response);
    }

    @Test
    public void getPollsTest(){
        PagedResponse response = pollController.getPolls(userPrincipal, 1, 3);
        Assert.assertNotNull(response);
    }
}
