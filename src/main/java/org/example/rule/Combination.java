package org.example.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.Trip;
import org.example.model.Zone;

@Data
@AllArgsConstructor
public class Combination {

    private Zone startZone;
    private Zone endZone;

    public boolean check(Trip trip) {
        return trip.getStart().getZones().contains(startZone) && trip.getEnd().getZones().contains(endZone);
    }
}
