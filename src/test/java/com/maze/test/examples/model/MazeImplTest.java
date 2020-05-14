package com.maze.test.examples.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

public class MazeImplTest {

    private final String exampleMaze =
            "XXXXXXXXXXXXXXX\n" +
            "X             X\n" +
            "X XXXXXXXXXXX X\n" +
            "X XS        X X\n" +
            "X XXXXXXXXX X X\n" +
            "X XXXXXXXXX X X\n" +
            "X XXXX      X X\n" +
            "X XXXX XXXX X X\n" +
            "X XXXX XXXX X X\n" +
            "X X    XXXXXX X\n" +
            "X X XXXXXXXXX X\n" +
            "X X XXXXXXXXX X\n" +
            "X X         X X\n" +
            "X XXXXXXXXX   X\n" +
            "XFXXXXXXXXXXXXX";

    @Test
    public void exampleMazeShouldBeInitialized() {
        MazeImpl maze = new MazeImpl(exampleMaze);

        assertThat(maze.dimensionX(), is(15));
        assertThat(maze.dimensionY(), is(15));
    }

    @Test
    public void emptyMazeShouldFailInitialization() {
        String emptyMaze = "";

        assertThatThrownBy(() ->
                new MazeImpl(emptyMaze)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze can not be empty!");

    }

    @Test
    public void mazeWithoutStartingPointShouldFailInitialization() {
        String mazeWithoutStartingPoint =
                "XXXX\n" +
                "X  X\n" +
                "X  X\n" +
                "XXFX\n";

        assertThatThrownBy(() ->
                new MazeImpl(mazeWithoutStartingPoint)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze should have exactly one starting point!");
    }

    @Test
    public void mazeWithoutExitPointShouldFailInitialization() {
        String mazeWithoutExitPoint =
                "XXXX\n" +
                "X  X\n" +
                "XS X\n" +
                "XXXX\n";

        assertThatThrownBy(() ->
                new MazeImpl(mazeWithoutExitPoint)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze should have exactly one exit point!");
    }

    @Test
    public void mazeWithMultipleStartingPointShouldFailInitialization() {
        String mazeWithMultipleStartingPoint =
                "XXXX\n" +
                "X  X\n" +
                "XSSX\n" +
                "XXFX\n";

        assertThatThrownBy(() ->
                new MazeImpl(mazeWithMultipleStartingPoint)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze should have exactly one starting point!");
    }

    @Test
    public void mazeWithMultipleExitPointShouldFailInitialization() {
        String mazeWithMultipleExitPoint =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XFFX\n";

        assertThatThrownBy(() ->
                new MazeImpl(mazeWithMultipleExitPoint)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze should have exactly one exit point!");
    }


    @Test
    public void mazeInitializedWithCorrectNumberOfWallsAndSpaces() {
        String simpleMaze =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XXFX\n";

        MazeImpl maze = new MazeImpl(simpleMaze);

        assertThat(maze.dimensionX(), is(4));
        assertThat(maze.dimensionY(), is(4));
        assertThat(maze.numberOfWalls(), is(11L));
        assertThat(maze.numberOfEmptySpaces(), is(3L));
    }

    @Test
    public void mazeInitializedWithCorrectNumberOfWallsAndSpacesLinebreakWIthCarriageReturn() {
        String simpleMaze =
                "XXXX\r\n" +
                "XS X\r\n" +
                "X  X\r\n" +
                "XXFX";

        MazeImpl maze = new MazeImpl(simpleMaze);

        assertThat(maze.dimensionX(), is(4));
        assertThat(maze.dimensionY(), is(4));
        assertThat(maze.numberOfWalls(), is(11L));
        assertThat(maze.numberOfEmptySpaces(), is(3L));
        assertThat(maze.startLocation(),is(new MazeDirections(1,1)));
    }

    @Test
    public void exampleMazeShouldReturnCorrectNumberOfWallsAndSpaces() {
        MazeImpl maze = new MazeImpl(exampleMaze);

        assertThat(maze.numberOfWalls(), is(149L));
        assertThat(maze.numberOfEmptySpaces(), is(74L));
    }

    @Test
    public void mazeShouldTellWhatIsAtGivenCoordinate() {
        String simpleMaze =
                "XXXX\n" +
                "XS X\n" +
                "X  X\n" +
                "XXFX\n";

        MazeImpl maze = new MazeImpl(simpleMaze);

        assertThat(maze.MzData(new MazeDirections(0, 0)), is(MazeDataStruct.WALL));
        assertThat(maze.MzData(new MazeDirections(3, 3)), is(MazeDataStruct.WALL));
        assertThat(maze.MzData(new MazeDirections(1, 1)), is(MazeDataStruct.START));
        assertThat(maze.MzData(new MazeDirections(2, 3)), is(MazeDataStruct.EXIT));
    }

    @Test
    public void mazeShouldTellWhereStartLocationIs() {
        String simpleMaze =
                "XXXX\n" +
                "X SX\n" +
                "X  X\n" +
                "XXFX\n";

        MazeImpl maze = new MazeImpl(simpleMaze);
        assertThat(maze.startLocation(), is(new MazeDirections(2,1)));
    }

    @Test
    public void mazeWithDifferentRowLengthShouldFailInitialization() {
        String mazeWithDifferentLenghRows =
                "XXFX\n" +
                "X S X\n" +
                "XXXX\n";

        assertThatThrownBy(() ->
                new MazeImpl(mazeWithDifferentLenghRows)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze rows should consist of the same number of blocks!");
    }

    @Test
    public void mazeShouldTellWhereExitLocationIs() {
        String simpleMaze =
                "XXXX\n" +
                "X SX\n" +
                "X  X\n" +
                "XFXX\n";

        MazeImpl maze = new MazeImpl(simpleMaze);
        assertThat(maze.exitLocation(), is(new MazeDirections(1,3)));
    }


    @Test
    public void mazeShouldTellWhereExitLocationIsIfLast() {
        String simpleMaze =
                "XXXXX\n" +
                "X  SX\n" +
                "X   X\n" +
                "X   X\n" +
                "XXXFX\n";

        MazeImpl maze = new MazeImpl(simpleMaze);
        assertThat(maze.exitLocation(), is(new MazeDirections(3,4)));
    }

}
