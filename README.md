# Maze Test

## User Story 1

As a world famous explorer of Mazes I would like a maze to exist so that I can explore it

Acceptance Criteria:

* A Maze (as illustrated in ExampleMaze.txt) consists of walls 'X', Empty spaces ' ', one and only one Start point 'S' and one and only one exit 'F'.

* After a maze has been created the number of walls and empty spaces should be available to me.

* After a maze has been created I should be able to put in a coordinate and know what exists at that point.


## User Story 2

As a world famous explorer of Mazes I would like to exist in a maze and be able to navigate it so that I can explore it

Acceptance Criteria:

* Given a maze the explorer should be able to drop in to the Start point.

* An explorer in a maze must be able to:
    Move forward;
    Turn left and right;
    Understand what is in front of them;
    Understand all movement options from their given location;
    Have a record of where they have been.


## UserStory 3
* An explorer must be able to automatically explore a maze and find the exit, and on exit they must be able to state the route they took in an understandable fashion.


## Assumptions, comments:
- upgraded to java 1.8 to be able to use the latest features
- maze dimensions can be different (number of rows and columns should not be equal)
- maze format check is not comprehensive due to time restrictions and to keep things simple
- logging framework is not used for now (for production code it would be considered based on debugging / operation needs)
- `main` method is not provided, acceptance tests reading in the input file and verify the criteria based on it
 
## Testing
- `mvn test` to run unit and acceptance tests

Thanks for the review!
