package com.maze.test.examples.explorer;

import org.junit.Before;
import org.junit.Test;
import org.maze.coding.examples.model.Maze;
import org.maze.coding.examples.model.MazeDataStruct;
import org.maze.coding.examples.model.MazeDirections;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.when;

public class AutomaticExplorerTest {
    private final int mazeDimension = 10;

    private final MazeDirections bottomRightCorner = new MazeDirections(mazeDimension - 1, mazeDimension - 1);
    private final MazeDirections topLeftCorner = new MazeDirections(0, 0);

    private final Maze mazeMock = mock(Maze.class, RETURNS_SMART_NULLS);

    private final MazeDirections startLocation = new MazeDirections(1, 1);


    @Before
    public void Setup() {
        when(mazeMock.startLocation()).thenReturn(startLocation);
        when(mazeMock.dimensionX()).thenReturn(mazeDimension);
        when(mazeMock.dimensionY()).thenReturn(mazeDimension);
    }

    @Test
    public void automaticExplorerShouldReturnEmptyIfThereIsNoExitAvailable() {
        AutomaticExplorer explorer = new AutomaticMazeExplorer(mazeMock);
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.WALL);

        Optional<List<MazeDirections>> movement = explorer.searchWayOut();

        assertThat(movement, is(Optional.empty()));
    }

    @Test
    public void automaticExplorerShouldReturnPathIfExitFound() {
        AutomaticExplorer explorer = new AutomaticMazeExplorer(mazeMock);
        when(mazeMock.MzData(startLocation.above())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.below())).thenReturn(MazeDataStruct.EXIT);
        when(mazeMock.MzData(startLocation.toTheLeft())).thenReturn(MazeDataStruct.WALL);
        when(mazeMock.MzData(startLocation.toTheRight())).thenReturn(MazeDataStruct.WALL);


        Optional<List<MazeDirections>> movement = explorer.searchWayOut();

        assertThat(movement, is(
                Optional.of(Arrays.asList(
                        startLocation,
                        startLocation.below()
                ))
        ));
    }
}
