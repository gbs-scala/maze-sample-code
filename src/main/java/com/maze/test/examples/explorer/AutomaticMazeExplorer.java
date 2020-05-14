package com.maze.test.examples.explorer;


import com.maze.test.examples.model.Maze;
import com.maze.test.examples.model.MazeDataStruct;
import com.maze.test.examples.model.MazeDirections;

import java.util.*;

public class AutomaticMazeExplorer extends MazeExplorer implements MazeAutomaticExplorer {

    public AutomaticMazeExplorer(Maze maze) {
        super(maze);
        movingToHook(maze.startLocation());
    }

    private final Stack<Breadcrumb> pathFollowed = new Stack<>();

    private final Set<MazeDirections> visitedCoordinates = new HashSet<>();

    @Override
    protected void movingToHook(MazeDirections coordinate) {
        // track visited coordinates in a HashSet for fast constant time lookup
        visitedCoordinates.add(coordinate);
    }

    private boolean findPathTillExit() {
        while (!pathFollowed.isEmpty()) {
            if (pathFollowed.peek().hasUnexploredDirection()) {
                HeadingDirectionClockWise direction = pathFollowed.peek().exploreFirstDirection();

                ExplorerPosition nextPosition = getPosition().withDirection(direction).calculateForwardPositionInMaze(maze);

                if (!visitedCoordinates.contains(nextPosition.getCoordinate())) {
                    moveTo(direction);
                    if (MazeAtMyLocation() == MazeDataStruct.EXIT) {
                        return true;
                    }
                    pathFollowed.add(new Breadcrumb(direction.opposite(), getPossibleDirections()));
                }
            } else {
                moveBackToFirstFieldWithAlternateRoute();
            }
        }
        return false;
    }

    private void moveBackToFirstFieldWithAlternateRoute() {
        Breadcrumb previousBreadcrumb;
        do {
            previousBreadcrumb = pathFollowed.pop();
            if (previousBreadcrumb.getArrivingFrom().isPresent()) {
                moveTo(previousBreadcrumb.getArrivingFrom().get());
            }
        } while (previousBreadcrumb.hasUnexploredDirection());
    }

    @Override
    public synchronized Optional<List<MazeDirections>> searchWayOut() {
        pathFollowed.add(new Breadcrumb(getPossibleDirections()));
        boolean exitReached = findPathTillExit();
        if (exitReached) {
            final Optional<List<MazeDirections>> movement = (Optional<List<MazeDirections>>) Optional.of(getMovement());
            return movement;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void moveForward(int times) {

    }

    @Override
    public void moveTo(HeadingDirectionClockWise direction) {

    }
}

