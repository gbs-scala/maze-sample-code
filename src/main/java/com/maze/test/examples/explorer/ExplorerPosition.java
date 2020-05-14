package com.maze.test.examples.explorer;

import com.maze.test.examples.model.Maze;
import com.maze.test.examples.model.MazeDirections;

public class ExplorerPosition {

    private final MazeDirections coordinate;

    private final HeadingDirectionClockWise direction;

    public ExplorerPosition(MazeDirections location, HeadingDirectionClockWise direction) {
        this.coordinate = location;
        this.direction = direction;
    }

    public MazeDirections getCoordinate() {
        return coordinate;
    }

    public HeadingDirectionClockWise getDirection() {
        return direction;
    }

    public ExplorerPosition withCoordinate(MazeDirections newCoordinate) {
        return new ExplorerPosition(newCoordinate, direction);
    }

    public ExplorerPosition withDirection(HeadingDirectionClockWise newDirection) {
        return new ExplorerPosition(coordinate, newDirection);
    }

    public ExplorerPosition turnLeft() {
        return new ExplorerPosition(coordinate, direction.turnLeft());
    }

    public ExplorerPosition turnRight() {
        return new ExplorerPosition(coordinate, direction.turnRight());
    }

    public ExplorerPosition calculateForwardPositionInMaze(Maze maze) {
        switch (direction) {
            case UP:
                if (coordinate.getY() == 0) throw new FieldIsOutOfMazeBoundsException();
                return withCoordinate(coordinate.above());
            case DOWN:
                if (coordinate.getY() == maze.dimensionY() - 1)
                    throw new FieldIsOutOfMazeBoundsException();
                return withCoordinate(coordinate.below());
            case LEFT:
                if (coordinate.getX() == 0) throw new FieldIsOutOfMazeBoundsException();
                return withCoordinate(coordinate.toTheLeft());
            case RIGHT:
                if (coordinate.getX() == maze.dimensionX() - 1)
                    throw new FieldIsOutOfMazeBoundsException();
                return withCoordinate(coordinate.toTheRight());
        }
        throw new UnsupportedOperationException(String.format("Direction %s not supported!", direction));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplorerPosition that = (ExplorerPosition) o;

        if (!coordinate.equals(that.coordinate)) return false;
        return direction == that.direction;
    }

    @Override
    public int hashCode() {
        int result = coordinate.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExplorerPosition{" +
                "coordinate=" + coordinate +
                ", direction=" + direction +
                '}';
    }
}
