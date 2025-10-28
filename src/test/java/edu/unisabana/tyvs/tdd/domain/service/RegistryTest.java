package edu.unisabana.tyvs.tdd.domain.service;

import edu.unisabana.tyvs.tdd.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistryTest {

    private Registry registry;

    @Before
    public void setUp() {
        registry = new Registry();
        registry.clear();
    }

    @Test
    public void shouldReturnInvalidWhenPersonIsNull() {
        RegisterResult result = registry.registerVoter(null);
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldReturnDeadIfNotAlive() {
        Person p = new Person("X", 1, 45, Gender.MALE, false);
        Assert.assertEquals(RegisterResult.DEAD, registry.registerVoter(p));
    }

    @Test
    public void shouldRejectInvalidId() {
        Person p = new Person("Y", 0, 25, Gender.FEMALE, true); // id=0 inválido
        Assert.assertEquals(RegisterResult.INVALID, registry.registerVoter(p));
    }

    @Test
    public void shouldRejectUnderageAt17() {
        Person p = new Person("Z", 2, 17, Gender.FEMALE, true);
        Assert.assertEquals(RegisterResult.INVALID, registry.registerVoter(p)); // o UNDERAGE si cambias enum
    }

    @Test
    public void shouldAcceptAdultAt18() {
        Person p = new Person("Ana", 3, 18, Gender.FEMALE, true);
        Assert.assertEquals(RegisterResult.VALID, registry.registerVoter(p));
    }

    @Test
    public void shouldDetectDuplicate() {
        Person p1 = new Person("A", 5, 30, Gender.MALE, true);
        Person p2 = new Person("B", 5, 40, Gender.FEMALE, true);
        Assert.assertEquals(RegisterResult.VALID, registry.registerVoter(p1));
        Assert.assertEquals(RegisterResult.DUPLICATED, registry.registerVoter(p2));
    }
}
