package org.example.model;

import java.util.Observable;

public class Card extends Observable {
    private Double balance = 0.0;

    public synchronized void credit(Double amount) {
        balance += amount;
    }

    public synchronized void debit(Double amount) {
        balance -= amount;
    }

    public Double getBalance() {
        return balance;
    }

    private Trip tripIsActive;

    public Card() {}

    public void swipe(Station station, Mode mode) {
        if (null != tripIsActive) {            // in case the journey is in progress
            tripIsActive.setEnd(station);      // set the end station
            tripIsActive.setActive(true);    // mark the journey as complete
            this.setChanged();                      // without this statement observers aren't notified
            notifyObservers(tripIsActive);     // notify the observer
            tripIsActive = null;               // once a journey is complete set journeyInProgress to null
        } else {
            // prepare a journey and set the starting station and mode of transport
            Trip journey  = Trip.builder().start(station).mode(mode).isActive(false).build();
            tripIsActive = journey;            // set the journeyInProgress with the instance
            this.setChanged();                      // without this statement observers aren't notified
            notifyObservers(tripIsActive);     // notify the observer
        }
    }
}
