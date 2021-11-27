package org.example.rule;

import lombok.AllArgsConstructor;
import org.example.model.Card;
import org.example.model.Trip;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

@AllArgsConstructor
public class CardReader implements Observer {

    private CalculateFare calculateFare;

    private synchronized void transact(Card card, Trip trip) {
        Double maxFare = calculateFare.getRuleSituation().getMaxFare();
        Double fareAmount = Optional.of(calculateFare.calculate(trip)).orElse(maxFare);
        // Fare Amount is either the calculated fareAmount or the max Fare
        card.debit(fareAmount);
    }

    @Override
    public void update(Observable observable, Object argument) {
        Card card = (Card)observable;
        Trip trip = (Trip) argument;
        if(trip.isActive()) {                                      // if trip is complete
            card.credit(calculateFare.getRuleSituation().getMaxFare());  // roll-back the max amount
            transact(card, trip);                                    // charge for the actual amount
        } else {
            card.debit(calculateFare.getRuleSituation().getMaxFare());   // when a trip begins charge for the max amount
        }
    }
}
