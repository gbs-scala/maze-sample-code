package com.maze.test.examples.explorer;

import org.junit.Before;
import org.junit.Test;
import org.maze.coding.examples.model.Maze;
import org.maze.coding.examples.model.MazeDataStruct;
import org.maze.coding.examples.model.MazeDirections;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MazeExplorerTest {

    private final int mazeDimension = 10;

    private final MazeDirections bottomRightCorner = new MazeDirections(mazeDimension - 1, mazeDimension - 1);
    private final MazeDirections topLeftCorner = new MazeDirections(0, 0);

    private final Maze mazeMock = mock(Maze.class);

    private final MazeDirections startLocation = new MazeDirections(1, 1);


    @Before
    public void Setup() {
        when(mazeMock.startLocation()).thenReturn(startLocation);
        when(mazeMock.dimensionX()).thenReturn(mazeDimension);
        when(mazeMock.dimensionY()).thenReturn(mazeDimension);
    }

    @Test
    public void shouldInitializeInStartLocationTest() {
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        ExplorerPosition loc = explorer.getPosition();
        assertThat(loc.getDirection(), is(HeadingDirectionClockWise.UP));
        assertThat(loc.getCoordinate(), is(startLocation));
    }

    @Test
    public void shouldMoveForwardToUpIfFieldIsSpace() {
        shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeDataStruct.SPACE);
    }

    @Test
    public void shouldMoveForwardToUpIfFieldIsExit() {
        shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeDataStruct.EXIT);
    }

    @Test
    public void shouldMoveForwardToUpIfFieldIsStart() {
        shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeDataStruct.START);
    }

    @Test
    public void shouldThrowExceptionWhenMoveForwardAndFieldIsWall() {
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.WALL);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThatThrownBy(() ->
                explorer.moveForward()
        ).isInstanceOf(MovementBlockedException.class)
                .hasMessageContaining("Movement to location MazeCoordinate{x=1, y=0} is blocked!");
    }

    @Test
    public void shouldThrowExceptionWhenMoveToUpAndFieldIsOutOfBounds() {
        when(mazeMock.startLocation()).thenReturn(topLeftCorner);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThatThrownBy(() ->
                explorer.moveForward()
        ).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
                .hasMessageContaining("Field is out of the maze!");

    }

    @Test
    public void shouldThrowExceptionWhenMoveToLeftAndFieldIsOutOfBounds() {
        when(mazeMock.startLocation()).thenReturn(topLeftCorner);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

        assertThatThrownBy(() ->
                explorer.moveForward()
        ).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
                .hasMessageContaining("Field is out of the maze!");
    }

    @Test
    public void shouldThrowExceptionWhenMoveToDownAndFieldIsOutOfBounds() {
        when(mazeMock.startLocation()).thenReturn(bottomRightCorner);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

        assertThatThrownBy(() ->
                explorer.moveForward()
        ).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
                .hasMessageContaining("Field is out of the maze!");

    }

    @Test
    public void shouldThrowExceptionWhenMoveToRightAndFieldIsOutOfBounds() {
        when(mazeMock.startLocation()).thenReturn(bottomRightCorner);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

        assertThatThrownBy(() ->
                explorer.moveForward()
        ).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
                .hasMessageContaining("Field is out of the maze!");
    }

    @Test
    public void shouldMoveToRightIfFieldIsSpace() {
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.SPACE);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

        explorer.moveForward();

        ExplorerPosition loc = explorer.getPosition();
        assertThat(loc.getDirection(), is(HeadingDirectionClockWise.RIGHT));
        assertThat(loc.getCoordinate(), is(startLocation.toTheRight()));
    }

    @Test
    public void shouldMoveToLeftIfFieldIsSpace() {
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.SPACE);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

        explorer.moveForward();

        ExplorerPosition loc = explorer.getPosition();
        assertThat(loc.getDirection(), is(HeadingDirectionClockWise.LEFT));
        assertThat(loc.getCoordinate(), is(startLocation.toTheLeft()));
    }

    @Test
    public void shouldMoveDownIfFieldIsSpace() {
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.SPACE);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

        explorer.moveForward();

        ExplorerPosition loc = explorer.getPosition();
        assertThat(loc.getDirection(), is(HeadingDirectionClockWise.DOWN));
        assertThat(loc.getCoordinate(), is(startLocation.below()));
    }

    @Test
    public void whatsInFrontShouldReturnWallIfAbove() {
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.WALL);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThat(explorer.whatsInFront(), is(Optional.of(MazeDataStruct.WALL)));
    }

    @Test
    public void whatsInFrontShouldReturnWallIfLeft() {
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.WALL);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

        assertThat(explorer.whatsInFront(), is(Optional.of(MazeDataStruct.WALL)));
    }

    @Test
    public void whatsInFrontShouldReturnWallIfRight() {
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.WALL);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

        assertThat(explorer.whatsInFront(), is(Optional.of(MazeDataStruct.WALL)));
    }

    @Test
    public void whatsInFrontShouldReturnWallIfDown() {
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.WALL);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

        assertThat(explorer.whatsInFront(), is(Optional.of(MazeDataStruct.WALL)));
    }

    @Test
    public void whatsInFrontShouldReturnNoneIfOutOfBounds() {
        when(mazeMock.startLocation()).thenReturn(topLeftCorner);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThat(explorer.whatsInFront(), is(Optional.empty()));
    }

    @Test
    public void MazeAtMyLocationReturnTheCurrentLocationType() {
        when(mazeMock.MzData(startLocation)).thenReturn(MazeDataStruct.EXIT);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThat(explorer.MazeAtMyLocation(), is(MazeDataStruct.EXIT));
    }

    @Test
    public void getPossibleDirectionsShouldReturnAllTheDirectionsIfExplorerCanMoveThere() {
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.SPACE);
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.SPACE);
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.SPACE);
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.SPACE);

        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThat(explorer.getPossibleDirections(), is(Arrays.asList(HeadingDirectionClockWise.values())));
    }

    @Test
    public void getPossibleDirectionsShouldReturnNoTheDirectionsIfExplorerCannotMoveAnywhere() {
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.WALL);

        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

        assertThat(explorer.getPossibleDirections(), is(Arrays.asList(new HeadingDirectionClockWise[]{})));
    }

    @Test
    public void movementShouldBeTracked() {
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.SPACE);
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);
        explorer.moveForward();
        assertThat(explorer.getPosition(), is(new ExplorerPosition(new MazeDirections(1, 0), HeadingDirectionClockWise.UP)));
        explorer.turnLeft();
        when(mazeMock.MzData(new MazeDirections(1, 0).toTheLeft())).thenReturn(MazeDataStruct.SPACE);
        explorer.moveForward();
        assertThat(explorer.getPosition(), is(new ExplorerPosition(new MazeDirections(0, 0), HeadingDirectionClockWise.LEFT)));

        assertThat(explorer.getMovement(), is(Arrays.asList(
                new MazeDirections[]{
                        new MazeDirections(1, 1),
                        new MazeDirections(1, 0),
                        new MazeDirections(0, 0)
                }
        )));
    }

    @Test
    public void turnToShouldSetDirection() {
        MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);
        assertThat(explorer.getPosition().getDirection(), is(HeadingDirectionClockWise.UP));

        explorer.turnTo(HeadingDirectionClockWise.DOWN);

        assertThat(explorer.getPosition().getDirection(), is(HeadingDirectionClockWise.DOWN));

    }

    private void shouldMoveUpWhenFieldIs(MazeExplorer explorer, MazeDataStruct field) {
        when(mazeMock.MzData(startLocation.above())).thenReturn(field);
        explorer.moveForward();

        ExplorerPosition loc = explorer.getPosition();
        assertThat(loc.getDirection(), is(HeadingDirectionClockWise.UP));
        assertThat(loc.getCoordinate(), is(startLocation.above()));
    }


}
