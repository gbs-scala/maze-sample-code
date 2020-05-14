package com.maze.test.examples.model;

import java.util.Arrays;
import java.util.Optional;

public enum MazeDataStruct {
    WALL('X'),
    SPACE(' '),
    START('S'),
    EXIT('F');

    private final char charRepresentation;

    MazeDataStruct(char charRepresentation) {
        this.charRepresentation = charRepresentation;
    }

    public char charRepresentation() {
        return charRepresentation;
    }

    public static MazeDataStruct from(char ch) {
        Optional<MazeDataStruct> structureFromChar = Arrays.stream(MazeDataStruct.values()).filter(ms -> ms.charRepresentation == ch).findFirst();
        return structureFromChar.orElseThrow(() -> new IllegalArgumentException(String.format("Maze structure not recognised from '%s'!", ch)));
    }

    public boolean canBeExplored() {
        return this != WALL;
    }

}