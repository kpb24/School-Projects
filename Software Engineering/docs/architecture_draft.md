# Hnefatafl: Draft Archtecture
v0.2 by Max Garber <mbg21@pitt.edu> revised 2017-02-13

**Caveats**: This is a rough draft, and the scheme, naming, etc are suggestions, not edicts. We can and should debate, revise, and more or less come to a consensus about this. Moreover, this should not be fixed--we are not developing via Waterfall!

- The design pattern is Model-View-Controller (MVC)
- For which there are three corresponding packages (models, views, controllers)
- The objects are not particularly hierarchical, but are more meant to represent a human perspective of the game play and reflect a simple, straightforward division of roles and information

### hnefatafl.models
- **GameBoard**: represents the board on which the game is played, via a matrix of GameBoardCell instances and serves as the access layer to the board cells

- **GameBoardCell**: represents a single cell of the board, has:
	- *type* (normal, corner, throne, or non-corner-edge)
	- *occupied* (T/F)
	- *piece* (null or \<GamePiece\>)
	- *hostility*
	- *position* (?)
	
- **GamePiece**: represents a piece in the game, has:
	- *color/player*
	- *type* (pawn, king)
	- *containing cell* (null, \<GameBoardCell\>)
	- *position* (?)

- **GamePlayer**: represents one of the two game players, & references their pieces (both in play and eliminated)

- **GameState**: represents a state of game play - can "touch" all dynamic objects in the program needed in order to freese & restore
	- *activePlayer* \<GamePlayer\>
	- *board* \<GameBoard\>

- **GameStateFile**: represents a file used to save a game state
	- TBD, need to research file operations with Java & encoding objects

- **GameMove**: represents a move made by a player with a piece
	- *movedPiece*
	- *initialCell* or position
	- *finalCell* or position
	- *eliminations* ?
	- *inverseMove()* calculated for undo purposes

- **GameHistory**: stores the history of moves made by players
	- List\<GameMove\> moveHistory

- **GameRule**: represents a rule of the game that must be obeyed, e.g. Shield Wall
	- String: description
	- f(): check if obeyed

- **GameArbiter**: stores the rule set, adjudicates the game, i.e. serves as "referee"
	- List\<GameRule\> ruleSet
	- *testMove(\<GameMove\>)()*

### hnefatafl.views
- **GameBoardView**: displays the game board, its cells, and the pieces therein

- **GameStatusView**: displays information about the game itself, e.g. turn, pieces remaining/lost, etc.

- **GamePlayControlsView**: are the buttons & other controls that allow players to reset, save, concede, etc.

### hnefatafl.controllers
- **GameBoardController**: Handles all the interactions with the GameBoardView and GameStatusView view objects

- **GamePlayController**: Handles all the interactions with the GamePlayControls elements

### other objects
- **Hnefatafl**: the top-level class that runs the application, i.e. the class that the JRE/JVM instantiates & on which calls main()
- **GameSettings**: holds the settings (not rules), images, colors, fonts, localizations, etc. for the software
	- this is hidden from the user (unless later we decide to make it accessible/mutable)
