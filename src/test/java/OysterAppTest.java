import com.google.common.collect.Sets;
import org.example.model.Card;
import org.example.model.Mode;
import org.example.model.Station;
import org.example.model.Zone;
import org.example.rule.*;
import org.junit.Before;
import org.junit.Test;

public class OysterAppTest {
    private RuleSituation ruleSituation = new RuleSituation();

    private CalculateFare calculateFare = new CalculateFare();

    @Before
    public void setup() {
        ruleSituation.setMaxFare(3.2);

        // Rule 1 -> "Anywhere in zone 1"
        Rule rule1 = new Rule();
        rule1.setAmount(2.5);
        Combination combination = new Combination(Zone.ONE, Zone.ONE); // 1 -> 1
        rule1.addCombination(combination);
        ruleSituation.loadRule(rule1);

        // Rule 2 -> "Any zone outside zone 1"
        Rule rule2 = new Rule();
        rule2.setAmount(2.0);
        rule2.addCombination(new Combination(Zone.TWO, Zone.TWO)); // 2 -> 2
        rule2.addCombination(new Combination(Zone.THREE, Zone.THREE)); // 3 -> 3
        ruleSituation.loadRule(rule2);

        // Rule 3 -> "Any two zones including zone 1"
        Rule rule3 = new Rule();
        rule3.setAmount(3.0);
        rule3.addCombination(new Combination(Zone.ONE, Zone.TWO));
        rule3.addCombination(new Combination(Zone.TWO, Zone.ONE));
        rule3.addCombination(new Combination(Zone.ONE, Zone.THREE));
        rule3.addCombination(new Combination(Zone.THREE, Zone.ONE));
        ruleSituation.loadRule(rule3);

        // Rule 4 -> "Any two zones excluding zone 1"
        Rule rule4 = new Rule();
        rule4.setAmount(2.25);
        rule4.addCombination(new Combination(Zone.TWO, Zone.THREE));
        rule4.addCombination(new Combination(Zone.THREE, Zone.TWO));
        ruleSituation.loadRule(rule4);

        // Rule 5 -> "Any zone max fare"
        Rule rule5 = new Rule();
        rule5.setAmount(3.2);
        ruleSituation.loadRule(rule5);

        // Rule 6 -> "Any journey by Bus"
        Rule rule6 = new Rule();
        rule6.setMode(Mode.Bus);
        rule6.setAmount(1.8);
        ruleSituation.loadRule(rule6);

        calculateFare.setRuleSituation(ruleSituation);
    }

    @Test
    public void testTrip() {
        CardReader cardReader = new CardReader(calculateFare);
        Card card = new Card();
        card.addObserver(cardReader);

        card.credit(30.0);

        card.swipe(new Station("Holborn", Sets.newHashSet(Zone.ONE)), Mode.Tube);
        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), null);
        System.out.println("Card balance after journey-1 is -> " + card.getBalance());

        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), Mode.Tube);
        card.swipe(new Station("HammerSmith", Sets.newHashSet(Zone.TWO)), null);
        System.out.println("Card balance after journey-2 is -> " + card.getBalance());

        card.swipe(new Station("Chelsea", Sets.newHashSet()), Mode.Bus);
        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), null);
        System.out.println("Card balance after journey-3 is -> " + card.getBalance());
    }

//    @Test
//    public void testCardWhenLessMinTrip() {
//        CardReader cardReader = new CardReader(calculateFare);
//        Card card = new Card();
//        card.addObserver(cardReader);
//
//        card.credit(1.4);
//
//        card.swipe(new Station("Holborn", Sets.newHashSet(Zone.ONE)), Mode.Tube);
//        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), null);
//        System.out.println("Card balance after journey-1 is -> " + card.getBalance());
//
//        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), Mode.Tube);
//        card.swipe(new Station("HammerSmith", Sets.newHashSet(Zone.TWO)), null);
//        System.out.println("Card balance after journey-2 is -> " + card.getBalance());
//
//        card.swipe(new Station("Chelsea", Sets.newHashSet()), Mode.Bus);
//        card.swipe(new Station("Earl's Court", Sets.newHashSet(Zone.ONE, Zone.TWO)), null);
//        System.out.println("Card balance after journey-3 is -> " + card.getBalance());
//    }
}
