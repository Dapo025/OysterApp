package org.example.rule;

import lombok.Data;
import org.example.model.Mode;
import org.example.model.Trip;

import java.util.HashSet;
import java.util.Set;

@Data
public class Rule {

    private Mode mode;

    private Double amount;

    private final Set<Combination> combinations = new HashSet<>();

    // check if it's a general rule which is applicable to any one or
    // check if the mode is similar to the specified mode (if any mode based
    // check is specified) or check whether any combination satisfies
    public boolean check(Trip trip) {
        return combinations.stream().anyMatch((combination) -> combination.check(trip))
                || (trip.getMode().equals(this.getMode()) && combinations.isEmpty());
    }

    public synchronized void addCombination(Combination combination) {
        combinations.add(combination);
    }
}
