package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class VoteTest {
    @Test
    public void testConstructor() {
        Vote actualVote = new Vote();
        Poll poll = new Poll();
        poll.setUpdatedBy(1L);
        poll.setCreatedAt(null);
        poll.setExpirationDateTime(null);
        poll.setQuestion("Une question");
        poll.setId(123L);
        poll.setChoices(new ArrayList<Choice>());
        poll.setCreatedBy(1L);
        poll.setUpdatedAt(null);
        Choice choice = new Choice();
        choice.setText("Un texte");
        choice.setPoll(poll);
        choice.setId(123L);
        actualVote.setChoice(choice);
        actualVote.setId(123L);
        Poll poll1 = new Poll();
        poll1.setUpdatedBy(1L);
        poll1.setCreatedAt(null);
        poll1.setExpirationDateTime(null);
        poll1.setQuestion("Une question");
        poll1.setId(123L);
        poll1.setChoices(new ArrayList<Choice>());
        poll1.setCreatedBy(1L);
        poll1.setUpdatedAt(null);
        actualVote.setPoll(poll1);
        User user = new User();
        user.setCreatedAt(null);
        user.setEmail("luka@luka.fr");
        user.setPassword("testtoto");
        user.setUsername("lukatoto");
        user.setId(123L);
        user.setName("Luka");
        user.setRoles(new HashSet<Role>());
        user.setUpdatedAt(null);
        actualVote.setUser(user);
        assertSame(choice, actualVote.getChoice());
        assertEquals(123L, actualVote.getId().longValue());
        assertSame(poll1, actualVote.getPoll());
        assertSame(user, actualVote.getUser());
    }
}

