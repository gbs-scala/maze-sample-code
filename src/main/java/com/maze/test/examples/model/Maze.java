package com.maze.test.examples.model;

public interface Maze {

    long numberOfWalls();

    long numberOfEmptySpaces();

    int dimensionX();

    int dimensionY();

    MazeDirections startLocation();
    MazeDirections exitLocation();
    MazeDataStruct MzData(MazeDirections dir);
}
