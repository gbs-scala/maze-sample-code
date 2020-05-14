package com.maze.test.examples.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Breadcrumb {
    private final Optional<HeadingDirectionClockWise> arrivingFrom;
    private final List<HeadingDirectionClockWise> unexploredDirections;

    public Breadcrumb(HeadingDirectionClockWise arrivingFrom, List<HeadingDirectionClockWise> unexploredDirections) {
        this.arrivingFrom = Optional.of(arrivingFrom);
        this.unexploredDirections = new ArrayList<>(unexploredDirections);
        this.unexploredDirections.remove(arrivingFrom);
    }


    public Breadcrumb(List<HeadingDirectionClockWise> unexploredDirections) {
        this.arrivingFrom = Optional.empty();
        this.unexploredDirections = new ArrayList<>(unexploredDirections);
        this.unexploredDirections.remove(arrivingFrom);
    }

    public Optional<HeadingDirectionClockWise> getArrivingFrom() {
        return arrivingFrom;
    }

    public boolean hasUnexploredDirection() {
        return !unexploredDirections.isEmpty();
    }

    public HeadingDirectionClockWise exploreFirstDirection() {
        return unexploredDirections.remove(0);
    }


}
