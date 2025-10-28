package edu.unisabana.tyvs.tdd.domain.service;

import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;
import java.util.HashSet;
import java.util.Set;

public class Registry {

    public static final int MIN_AGE = 0;
    public static final int MIN_VOTING_AGE = 18;
    public static final int MAX_AGE = 120;

    private final Set<Integer> registeredIds = new HashSet<>();

    public RegisterResult registerVoter(Person p) {
        if (p == null) {
            return RegisterResult.INVALID;
        }

        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }

        if (p.getId() <= 0) {
            return RegisterResult.INVALID; 
        }

        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }

        int age = p.getAge();
        if (age < MIN_AGE || age > MAX_AGE) {
            return RegisterResult.INVALID;
        }

        if (age < MIN_VOTING_AGE) {
            return RegisterResult.INVALID; 
        }

        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }

    
    public void clear() {
        registeredIds.clear();
    }
}
