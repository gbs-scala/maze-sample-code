package com.maze.test.examples.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public final class MazeImpl implements Maze {

    private final String[] stringMaze;

    private final int dimensionX;

    private final int dimensionY;

    private final long numberOfWalls;
    private final long numberOfEmptySpaces;

    private final MazeDirections startLocation;

    private final MazeDirections exitLocation;

    private final String LINEBREAK = "\\r?\\n";

    public MazeImpl(String mazeStr) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(mazeStr), "Maze can not be empty!");
        Preconditions.checkArgument(countStringContainsOfGivenChar(mazeStr, MazeDataStruct.START.charRepresentation()) == 1, "Maze should have exactly one starting point!");
        Preconditions.checkArgument(countStringContainsOfGivenChar(mazeStr, MazeDataStruct.EXIT.charRepresentation()) == 1, "Maze should have exactly one exit point!");

        this.stringMaze = mazeStr.split(LINEBREAK);

        Preconditions.checkArgument(allRowsHasTheSameLength(this.stringMaze), "Maze rows should consist of the same number of blocks!");

        dimensionX = stringMaze[0].length();
        dimensionY = stringMaze.length;

        startLocation = findLocation(mazeStr, MazeDataStruct.START);
        exitLocation = findLocation(mazeStr, MazeDataStruct.EXIT);

        numberOfWalls = countStringContainsOfGivenChar(mazeStr, MazeDataStruct.WALL.charRepresentation());
        numberOfEmptySpaces = countStringContainsOfGivenChar(mazeStr, MazeDataStruct.SPACE.charRepresentation());
    }

    private final long countStringContainsOfGivenChar(String str, char c) {
        return str.chars().filter(ch -> ch == c).count();
    }

    private final MazeDirections findLocation(String mazeStr, MazeDataStruct mazeDataStruct) {
        int indexOfLocation = mazeStr.replaceAll(LINEBREAK, "").indexOf(mazeDataStruct.charRepresentation());
        return new MazeDirections(indexOfLocation % dimensionX, indexOfLocation / dimensionX);
    }

    private final boolean allRowsHasTheSameLength(String[] stringMaze) {
        for (String mazeRow : stringMaze) {
            if (mazeRow.length() != stringMaze[0].length()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public long numberOfWalls() {
        return numberOfWalls;
    }

    @Override
    public long numberOfEmptySpaces() {
        return numberOfEmptySpaces;
    }

    @Override
    public int dimensionX() {
        return dimensionX;
    }

    @Override
    public int dimensionY() {
        return dimensionY;
    }

    @Override
    public MazeDirections startLocation() {
        return startLocation;
    }

    @Override
    public MazeDirections exitLocation() {
        return exitLocation;
    }

    @Override
    public MazeDataStruct MzData(MazeDirections coord) {
        return MazeDataStruct.from(stringMaze[coord.getY()].charAt(coord.getX()));
    }
}
