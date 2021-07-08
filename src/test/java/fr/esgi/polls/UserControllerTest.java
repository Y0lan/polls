package fr.esgi.polls;

import fr.esgi.polls.controller.UserController;
import fr.esgi.polls.payload.UserIdentityAvailability;
import fr.esgi.polls.payload.UserProfile;
import fr.esgi.polls.payload.UserSummary;
import fr.esgi.polls.security.UserPrincipal;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Mock
    UserPrincipal userPrincipal;

    @InjectMocks
    UserController userController;

    @Test
    void getCurrentUserTest(){
        UserSummary response = userController.getCurrentUser(userPrincipal);
        Assert.assertNotNull(response);
    }

    @Test
    void getUserProfile(){
        UserProfile response = userController.getUserProfile("rayann");
        Assert.assertNotNull(response);
    }

    @Test
    void checkUsernameAvailabilityTest(){
        UserIdentityAvailability response = userController.checkUsernameAvailability("rayann");
        Assert.assertNotNull(response);
    }

    @Test
    void checkEmailAvailabilityTest(){
        UserIdentityAvailability response = userController.checkEmailAvailability("toot@toto.fr");
        Assert.assertNotNull(response);
    }

}
