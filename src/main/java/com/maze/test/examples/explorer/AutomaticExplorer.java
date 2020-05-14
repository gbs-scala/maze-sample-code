package com.maze.test.examples.explorer;

import com.maze.test.examples.model.MazeDirections;

import java.util.List;
import java.util.Optional;

public interface AutomaticExplorer {

    Optional<List<MazeDirections>> searchWayOut();
}
