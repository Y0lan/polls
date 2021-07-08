package fr.esgi.polls.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testConstructor() {
        User actualUser = new User();
        actualUser.setEmail("rayann@rayann.fr");
        actualUser.setId(123L);
        actualUser.setName("Rayann");
        actualUser.setPassword("testtoto");
        HashSet<Role> roleSet = new HashSet<Role>();
        actualUser.setRoles(roleSet);
        actualUser.setUsername("rayanntoto");
        assertEquals("rayann@rayann.fr", actualUser.getEmail());
        assertEquals(123L, actualUser.getId().longValue());
        assertEquals("Rayann", actualUser.getName());
        assertEquals("testtoto", actualUser.getPassword());
        assertSame(roleSet, actualUser.getRoles());
        assertEquals("rayanntoto", actualUser.getUsername());
    }

    @Test
    public void testConstructor2() {
        User actualUser = new User("Luka", "lukatoto", "luka@luka.fr", "testtoto");
        actualUser.setEmail("luka@luka.fr");
        actualUser.setId(123L);
        actualUser.setName("Luka");
        actualUser.setPassword("testtoto");
        HashSet<Role> roleSet = new HashSet<Role>();
        actualUser.setRoles(roleSet);
        actualUser.setUsername("lukatoto");
        assertEquals("luka@luka.fr", actualUser.getEmail());
        assertEquals(123L, actualUser.getId().longValue());
        assertEquals("Luka", actualUser.getName());
        assertEquals("testtoto", actualUser.getPassword());
        assertSame(roleSet, actualUser.getRoles());
        assertEquals("lukatoto", actualUser.getUsername());
    }
}

