package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Station {

    private String stationName;
    private Set<Zone> zones;
}
