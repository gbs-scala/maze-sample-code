package com.maze.test.examples.model;

import com.google.common.base.Preconditions;

public final class MazeDirections {
    private final int x;
    private final int y;

    public MazeDirections(int x, int y) {
        Preconditions.checkArgument(x >= 0 && y >= 0, "Coordinate should not be negative!");

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MazeDirections withX(int newX) {
        return new MazeDirections(newX, y);
    }

    public MazeDirections withY(int newY) {
        return new MazeDirections(x, newY);
    }

    public MazeDirections toTheLeft() {
        return new MazeDirections(x - 1, y);
    }

    public MazeDirections toTheRight() {
        return new MazeDirections(x + 1, y);
    }

    public MazeDirections above() {
        return new MazeDirections(x, y - 1);
    }

    public MazeDirections below() {
        return new MazeDirections(x, y + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MazeDirections that = (MazeDirections) o;

        if (x != that.x) return false;
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "MazeDirections{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
