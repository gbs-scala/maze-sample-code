package com.maze.test.examples.explorer;

import com.maze.test.examples.model.MazeDataStruct;
import com.maze.test.examples.model.MazeDirections;

import java.util.List;
import java.util.Optional;

public interface Explorer {

    void moveForward();
    default void moveForward(int times) {
        for (int i = 0; i < times; i++) moveForward();
    }
    void turnLeft();
    void turnRight();
    void turnTo(HeadingDirectionClockWise direction);

    default void moveTo(HeadingDirectionClockWise direction) {
        turnTo(direction);
        moveForward();
    }

    List<HeadingDirectionClockWise> getPossibleDirections();
    Optional<MazeDataStruct> whatsInFront();
    MazeDataStruct MazeAtMyLocation();
    List<MazeDirections> getMovement();
    ExplorerPosition getPosition();
}
