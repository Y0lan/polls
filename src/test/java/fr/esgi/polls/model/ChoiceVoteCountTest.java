package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ChoiceVoteCountTest {
    @Test
    public void testConstructor() {
        ChoiceVoteCount actualChoiceVoteCount = new ChoiceVoteCount(123L, 3L);
        actualChoiceVoteCount.setChoiceId(123L);
        actualChoiceVoteCount.setVoteCount(3L);
        assertEquals(123L, actualChoiceVoteCount.getChoiceId().longValue());
        assertEquals(3L, actualChoiceVoteCount.getVoteCount().longValue());
    }
}

