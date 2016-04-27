Kabasuji Readme -- Team Imbrius

Builder milestones:
	-Launch the MainBuilder.java in the app package, this will launch the builder
	-At the top you will see a scollpane that contains all of the current levels on disk that may be edited
	-To add a new level click one of the three large buttons half way down the window (For this readme, we'll assume you clicked Puzzle, but they all work).
	-To make the board, simply click on a square on the board to turn it into a board tile (white = playable space, grey = not playable space)
	-To set the number of moves, look to the right of the window for the scroller next to "Move Count"
	-The bullpen is the scollable area to the upper right. To add a piece to the bullpen for use simply increment the scroller next to it.
	-To place the piece onto the board in the builder follow these steps:
		1:click the "place piece" button directly above the board
		2:click the piece you want to place on the board in the bullpen (needs a count of non zero)
		3:the piece is now in the "selected piece area", so you may flip it or rotate it freely
		4:when you want to place the piece simply move your mouse onto the board
		5:if the piece is red it cannot be placed at that location, if green it may be placed
		6:once it is at a valid location simply click the mouse and the piece will be placed.
	-Once satisfied with piece placement you can click the "save" button in the lower right corner of the window (and it will remove pieces on board, before writing the level to disk).
	-Once you have closed the builder and re-launched it, to get to the level you just created scroll all the way to the right on the upper scroll bar, it should be level 16
	-To edit that level simply click the button with the picture and level label
	-For the lightning level follow the same process as you did for the puzzle, except now instead of setting the moves set the time in the same way you did moves.
		-> At any time during editing of a level you can toggle the other buttons next to place piece (place piece must be toggled on for those to work)
		    and be able to place a piece to create board tiles or place a piece in the form of a hint.
		-> To remove a hint or release tile from the board, just click it. 
		-> To make a release tile in the release mode, a special palette of toggle buttons appears on the right. Toggle the one you 
		     would like to make "On" and then press the board (you'll need to toggle it off again after to stop placing release tiles).


Player milestones:
	-Launch the MainGame.java in the app package, this will launch the game
	-Click the button labeled "Level 1", that will launch level one for play.
	!-Quick discrepency, our level here is different from the one in the milestone, but it is perfectly fine and will show all the functionality-!
	!-The yellow tiles are hint tiles, feel free to take advantage of them by placing the relevant piece on top of them-!
	-To place a piece on the board click the piece you would like to place in the bullpen
	-The piece will now appear in the selected piece area, you are free to use the flip and rotate buttons for the piece
	-Once you are satisfied with the rotation/flipping on the piece move your mouse to the board
	-Same exact functionality as the builder now, green = can be placed, red = cannot be placed
	-Continue this until you have earned three stars, once three stars are earned the game will automatically end and you will see the end game screen, click the return to level select button and you will go back to the level select screen
		-> (Or leave the level once you have earned a star and the next will be available).
	-Lightning level is exactly the same as the puzzle except you have to be quick, if a tile is claimed in lightning it will turn blue, as is specified in the rules of the game. Pieces may still be placed on top of these blue pieces, so feel free to place on top of them all you want.
		- Pieces are replenished in the bullpen as you place pieces (they aren't always unique, a piece already in there may increment its count!)