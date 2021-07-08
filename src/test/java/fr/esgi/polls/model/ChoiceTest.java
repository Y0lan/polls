package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ChoiceTest {
    @Test
    public void testConstructor() {
        Choice actualChoice = new Choice();
        actualChoice.setId(123L);
        Poll poll = new Poll();
        poll.setUpdatedBy(1L);
        poll.setCreatedAt(null);
        poll.setExpirationDateTime(null);
        poll.setQuestion("Une question pour tester");
        poll.setId(123L);
        poll.setChoices(new ArrayList<Choice>());
        poll.setCreatedBy(1L);
        poll.setUpdatedAt(null);
        actualChoice.setPoll(poll);
        actualChoice.setText("Un choix pour tester");
        assertEquals(123L, actualChoice.getId().longValue());
        assertSame(poll, actualChoice.getPoll());
        assertEquals("Un choix pour tester", actualChoice.getText());
    }

    @Test
    public void testConstructor2() {
        Choice actualChoice = new Choice("Un choix");
        actualChoice.setId(123L);
        Poll poll = new Poll();
        poll.setUpdatedBy(1L);
        poll.setCreatedAt(null);
        poll.setExpirationDateTime(null);
        poll.setQuestion("Une question");
        poll.setId(123L);
        poll.setChoices(new ArrayList<Choice>());
        poll.setCreatedBy(1L);
        poll.setUpdatedAt(null);
        actualChoice.setPoll(poll);
        actualChoice.setText("Un choix");
        assertEquals(123L, actualChoice.getId().longValue());
        assertSame(poll, actualChoice.getPoll());
        assertEquals("Un choix", actualChoice.getText());
    }

    @Test
    public void testEquals() {
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
        choice.setText("Un choix");
        choice.setPoll(poll);
        choice.setId(123L);
        assertFalse(choice.equals(null));
    }

    @Test
    public void testEquals2() {
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
        choice.setText("Un choix");
        choice.setPoll(poll);
        choice.setId(123L);
        assertFalse(choice.equals("Different type to Choice"));
    }

    @Test
    public void testEquals3() {
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
        choice.setText("Un choix");
        choice.setPoll(poll);
        choice.setId(123L);
        assertTrue(choice.equals(choice));
        int expectedHashCodeResult = choice.hashCode();
        assertEquals(expectedHashCodeResult, choice.hashCode());
    }

    @Test
    public void testEquals4() {
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
        choice.setText("Un choix");
        choice.setPoll(poll);
        choice.setId(123L);

        Poll poll1 = new Poll();
        poll1.setUpdatedBy(1L);
        poll1.setCreatedAt(null);
        poll1.setExpirationDateTime(null);
        poll1.setQuestion("Une question");
        poll1.setId(123L);
        poll1.setChoices(new ArrayList<Choice>());
        poll1.setCreatedBy(1L);
        poll1.setUpdatedAt(null);

        Choice choice1 = new Choice();
        choice1.setText("Un choix");
        choice1.setPoll(poll1);
        choice1.setId(123L);
        assertTrue(choice.equals(choice1));
        int expectedHashCodeResult = choice.hashCode();
        assertEquals(expectedHashCodeResult, choice1.hashCode());
    }
}

