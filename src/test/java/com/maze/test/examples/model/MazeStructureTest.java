package com.maze.test.examples.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class MazeStructureTest {
    @Test
    public void mazeStructureCharXShouldBeRecognizesAsWall() {
        assertThat(MazeDataStruct.from('X'), is(MazeDataStruct.WALL));
    }

    @Test
    public void mazeStructureCharSShouldBeRecognizesAsStartingPoint() {
        assertThat(MazeDataStruct.from('S'), is(MazeDataStruct.START));
    }

    @Test
    public void mazeStructureCharFShouldBeRecognizesAsExitPoint() {
        assertThat(MazeDataStruct.from('F'), is(MazeDataStruct.EXIT));
    }

    @Test
    public void mazeStructureCharSpaceShouldBeRecognizesAsSpace() {
        assertThat(MazeDataStruct.from(' '), is(MazeDataStruct.SPACE));
    }

    @Test
    public void unknownStructureCharShouldThrowException() {
        assertThatThrownBy(() -> MazeDataStruct.from('?')
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maze structure not recognised from '?'!");
    }

}
