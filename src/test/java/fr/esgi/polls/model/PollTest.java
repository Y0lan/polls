package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PollTest {
    @Test
    public void testAddChoice() {
        Poll poll = new Poll();

        Poll poll1 = new Poll();
        poll1.setUpdatedBy(1L);
        poll1.setCreatedAt(null);
        poll1.setExpirationDateTime(null);
        poll1.setQuestion("Une question");
        poll1.setId(123L);
        poll1.setChoices(new ArrayList<Choice>());
        poll1.setCreatedBy(1L);
        poll1.setUpdatedAt(null);

        Choice choice = new Choice();
        choice.setText("Un choix");
        choice.setPoll(poll1);
        choice.setId(123L);
        poll.addChoice(choice);
        Poll poll2 = choice.getPoll();
        assertSame(poll, poll2);
        assertEquals(1, poll2.getChoices().size());
    }

    @Test
    public void testRemoveChoice() {
        Poll poll = new Poll();

        Poll poll1 = new Poll();
        poll1.setUpdatedBy(1L);
        poll1.setCreatedAt(null);
        poll1.setExpirationDateTime(null);
        poll1.setQuestion("Une question");
        poll1.setId(123L);
        poll1.setChoices(new ArrayList<Choice>());
        poll1.setCreatedBy(1L);
        poll1.setUpdatedAt(null);

        Choice choice = new Choice();
        choice.setText("Un choix");
        choice.setPoll(poll1);
        choice.setId(123L);
        poll.removeChoice(choice);
        assertNull(choice.getPoll());
        assertTrue(poll.getChoices().isEmpty());
    }

    @Test
    public void testConstructor() {
        Poll actualPoll = new Poll();
        ArrayList<Choice> choiceList = new ArrayList<Choice>();
        actualPoll.setChoices(choiceList);
        actualPoll.setExpirationDateTime(null);
        actualPoll.setId(123L);
        actualPoll.setQuestion("Une question");
        assertSame(choiceList, actualPoll.getChoices());
        assertNull(actualPoll.getExpirationDateTime());
        assertEquals(123L, actualPoll.getId().longValue());
        assertEquals("Une question", actualPoll.getQuestion());
    }
}

