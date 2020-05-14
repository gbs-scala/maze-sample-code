package com.maze.test.examples.explorer;


import com.maze.test.examples.model.MazeDirections;

public class MovementBlockedException extends RuntimeException {
    public MovementBlockedException(MazeDirections location) {
        super(String.format("Movement to location %s is blocked!", location));
    }
}
