package org.example.rule;

import lombok.Data;
import org.example.model.Trip;

import java.util.Comparator;
import java.util.function.Predicate;

@Data
public class CalculateFare {

    private RuleSituation ruleSituation;

    // Rule Comparator to pick up min fare-amount rule amongst any two rule(s)
    private Comparator<Rule> ruleComparator = (Rule rule1, Rule rule2) -> {
        if (rule1.getAmount() < rule2.getAmount()) {
            return -1;
        } else if (rule1.getAmount() > rule2.getAmount()) {
            return 1;
        } else {
            return 0;
        }
    };

    public Double calculate(Trip trip) {

        Predicate<Rule> rulePredicate = (rule) -> rule.check(trip);

        // Fetch all the rules and pick up all applicable one's. Then pick up the one with lowest rate
        Rule applicable = ruleSituation.getRules().stream().filter(rulePredicate).min(ruleComparator).get();

        return applicable.getAmount();
    }
}
