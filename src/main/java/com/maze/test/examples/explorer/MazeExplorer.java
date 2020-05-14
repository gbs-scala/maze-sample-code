package com.maze.test.examples.explorer;

import com.maze.test.examples.model.Maze;
import com.maze.test.examples.model.MazeDataStruct;
import com.maze.test.examples.model.MazeDirections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Thread safe implementation of a an explorer of the maze
 */
public class MazeExplorer implements Explorer {

    protected final Maze maze;
    private final List<MazeDirections> movement;
    private ExplorerPosition position;

    public MazeExplorer(Maze maze) {
        this(maze, HeadingDirectionClockWise.UP);
    }

    public MazeExplorer(Maze maze, HeadingDirectionClockWise startingDirection) {
        this.maze = maze;
        position = new ExplorerPosition(maze.startLocation(), startingDirection);
        this.movement = new ArrayList<>();
        this.movement.add(maze.startLocation());
    }

    protected void movingToHook(MazeDirections mazeDirections) {
        // do nothing here, provide it for extendability
    }

    @Override
    public synchronized final void moveForward() {
        ExplorerPosition newPosition = position.calculateForwardPositionInMaze(maze);
        if (maze.MzData(newPosition.getCoordinate()).canBeExplored()) {
            movingToHook(newPosition.getCoordinate());
            this.movement.add(newPosition.getCoordinate());
            this.position = newPosition;
        } else {
            throw new MovementBlockedException(newPosition.getCoordinate());
        }
    }

    @Override
    public synchronized final void turnLeft() {
        position = position.turnLeft();
    }

    @Override
    public synchronized final void turnRight() {
        position = position.turnRight();
    }

    @Override
    public synchronized final void turnTo(HeadingDirectionClockWise direction) {
        position = position.withDirection(direction);
    }

    @Override
    public synchronized final List<HeadingDirectionClockWise> getPossibleDirections() {
        return Arrays.stream(HeadingDirectionClockWise.values())
                .filter(canBeExplored())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized final Optional<MazeDataStruct> whatsInFront() {
        return whatsInDirection(position.getDirection());
    }

    @Override
    public synchronized final MazeDataStruct MazeAtMyLocation() {
        return maze.MzData(position.getCoordinate());
    }

    @Override
    public synchronized List<MazeDirections> getMovement() {
        return new ArrayList<>(movement);
    }

    @Override
    public synchronized ExplorerPosition getPosition() {
        return position;
    }

    private Predicate<HeadingDirectionClockWise> canBeExplored() {
        return d -> {
            Optional<MazeDataStruct> ms = whatsInDirection(d);
            return ms.isPresent() && ms.get().canBeExplored();
        };
    }

    private Optional<MazeDataStruct> whatsInDirection(HeadingDirectionClockWise direction) {
        try {
            ExplorerPosition newPosition = position.withDirection(direction).calculateForwardPositionInMaze(maze);
            return Optional.of(maze.MzData(newPosition.getCoordinate()));
        } catch (FieldIsOutOfMazeBoundsException ex) {
            return Optional.empty();
        }
    }

}
