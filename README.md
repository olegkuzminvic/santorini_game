****************************************************************************************************
Santorini (console based simple java game)
*********

Santorini is the strategy-based board game.

Two players pile tiles on a square board alternately, with the goal of building a tower of height 3 to scale it up with their own playing figure. 


*The playing field is square and consists of 5x5 cells.

The game begins when the players place their game figures on different cells. 
Player 1 and Player 2 now play turns alternately. 
It starts player 1. 
A turn consists of three consecutive steps:
1. Selection of a game figure
2. Dragging the selected game figure to an adjacent unoccupied cell. The rules of the dragging must be followed.
3. Build a token on an unoccupied cell adjacent to the target cell. The building rules must be observed.
A player must both draw and build each turn. It must not be suspended.

The player who first climbs a tower with his playing figure wins.

The rules:

A game figure may jump down as many levels as you like while dragging, but only climb up one level at most. A cell with a dome must not be entered. A game figure "occupies" the cell on which it was placed. An occupied cell must not be entered by other players. Components, on the other hand, do not occupy a cell.
A maximum of three cuboids may be stacked on top of each other.
In exactly three cuboids stacked on top of each other.
Let's talk about a tower. On each tower exactly one dome may be built. Above a dome no further components may be placed.


Command line arguments:

At the start of the program, all four game figures are given a name and positioned on the playing field.

Input format <player figure identifier>; <line number>; <column number>
Example: yellow; 1; 1 red; 3; 2 blue; 1; 2 green; 2; 2

-The move command moves an active player's game figure to the specified cell.
Input format : move <game character identifier>; <line number>; <column number>

-The build command creates a specific token at the top of the specified cell.
Input format : build <tile type>; <line number>; <column number>

-The turn command
The turn command completes the active player's turn and then switches the active player

-The cellprint command
The cellprint command gives the list of all game elements placed on a particular cell to the
Console off. The game elements include: game characters, cuboids and domes.

Input format : cellprint <line number>; <column number>

-The print command
The print command prints the game board to the console.

-The quit command
This command terminates the program.
****************************************************************************************************

