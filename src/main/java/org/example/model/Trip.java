package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trip {

    private Station start;
    private Station end;
    private Mode mode;
    private boolean isActive;
}
