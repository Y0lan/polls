package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void testConstructor() {
        Role actualRole = new Role();
        actualRole.setId(123L);
        actualRole.setName(RoleName.ROLE_USER);
        assertEquals(123L, actualRole.getId().longValue());
        assertEquals(RoleName.ROLE_USER, actualRole.getName());
    }

    @Test
    public void testConstructor2() {
        Role actualRole = new Role(RoleName.ROLE_USER);
        actualRole.setId(123L);
        actualRole.setName(RoleName.ROLE_USER);
        assertEquals(123L, actualRole.getId().longValue());
        assertEquals(RoleName.ROLE_USER, actualRole.getName());
    }
}

