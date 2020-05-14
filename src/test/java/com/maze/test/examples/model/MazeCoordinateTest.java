package com.maze.test.examples.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MazeCoordinateTest {

    @Test
    public void mazeCoordinateXShouldNotBeNegative() {
        assertThatThrownBy(() ->
                new MazeDirections(-1, 0)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Coordinate should not be negative!");
    }

    @Test
    public void mazeCoordinateYShouldNotBeNegative() {
        assertThatThrownBy(() ->
                new MazeDirections(0, -1)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Coordinate should not be negative!");
    }

    @Test
    public void validCoordinateOrigoShouldBeCreated() {
        MazeDirections coord = new MazeDirections(0, 0);
        assertThat(coord.getX(), is(0));
        assertThat(coord.getY(), is(0));
    }


    @Test
    public void validCoordinateShouldBeCreated() {
        MazeDirections coord = new MazeDirections(11, 12);
        assertThat(coord.getX(), is(11));
        assertThat(coord.getY(), is(12));
    }

    @Test
    public void toTheLeftShouldReturnCoordinateToTheLeft() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.toTheLeft(), is(new MazeDirections(0, 1)));
    }

    @Test
    public void toTheRightShouldReturnCoordinateToTheRight() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.toTheRight(), is(new MazeDirections(2, 1)));
    }

    @Test
    public void aboveShouldReturnCoordinateAbove() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.above(), is(new MazeDirections(1, 0)));
    }

    @Test
    public void belowShouldReturnCoordinateBelow() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.below(), is(new MazeDirections(1, 2)));
    }

    @Test
    public void withXShouldReturnCoordinateWithNewX() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.withX(10), is(new MazeDirections(10, 1)));
    }

    @Test
    public void withYShouldReturnCoordinateWithNewY() {
        MazeDirections coord = new MazeDirections(1, 1);
        assertThat(coord.withY(10), is(new MazeDirections(1, 10)));
    }
}
