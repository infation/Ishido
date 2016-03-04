package edu.ramapo.sminev.ishido.Model;


/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/

/* The board model class of the game, it contains a 2D array of Tile objects. Has the
 constants ROWS and MAX_COLUMNS set to 8, 12.  Also contains a method checkIfLegalMove()
which is used to check if the move at (i,j) position in the matrix depending on the deckIndex and
is legal and updates the player score if the caller wants to. Provides a method getTempScoreAfterCheckIfLegal()
to get the points after a move was check, so as not to update the player model every time.
Has setTileAt() which sets a tile at some i,j and setBlinkableTileAt() which works the same way,
but sets the tile’s blink attribute to blinkable and setTileAtToDefault().
which just sets the the square to an empty square
 */

public class GameBoard {
    //The board
    private final int ROWS=8;
    private final int MAX_COLUMNS=12;
    private Tile[][] board;
    //This is a score just to keep track of the points after the user got
    // after using checkIfLegalMove()
    private int scoreAfterCheckIfLegal;

    //Constructor.. Need to pass row and column
    public GameBoard(){
        //Initialize the board and the tempScore
        board=new Tile[ROWS][MAX_COLUMNS];
        for(int row=0;row<ROWS;row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                board[row][column] = new Tile();
            }
        }
        scoreAfterCheckIfLegal=0;
    }

    //Getter to return the tile at a particular location
    public Tile getTileAt(int row, int column){
        return board[row][column];
    }

    //Setter to set a tile at a given row/column to default
    public void setTileAtToDefault(int row, int column){
        board[row][column].setTile("","",false,false);
    }

    //Getters for the board dimensions
    public int getRows(){
        return ROWS;
    }
    public int getColumns(){
        return MAX_COLUMNS;
    }

    //To set a tile at a particular location
    public void setTileAt(int row, int column, String c, String s){
        board[row][column].setTile(c, s, true, false);
    }

    //Setter to set a tile at a given row/column blinkable
    public void setBlinkableTileAt(int row, int column, String c, String s){
        board[row][column].setTile(c,s,true, true);
    }


    //To check the top adjacent tile.
    public boolean checkTopAdjacent(int row, int column, Deck deck, int deckIndex){
        boolean top = false;
        //For top corner
        if (row - 1 < 0) {
            top=true;
            return top;
        }
        //If it's not a corner
        if (!top) {
            //Check if there is a tile on the top?
            if (board[row - 1][column].getIsTile()) {
                //If the color or the shape matches, then set top to true and add a point to the score
                if (board[row - 1][column].getColor().equals(deck.getTileAt(deckIndex).getColor())
                        || board[row - 1][column].getShape().equals(deck.getTileAt(deckIndex).getShape())) {
                    top = true;
                    scoreAfterCheckIfLegal++;
                    return top;
                    //The test fails and the tile can't be placed there
                } else return top;
                //If there isn't a tile then top is good to go, but the player doesn't get any points
            } else {
                top=true;
                return top;
            }
        }
        return false;
    }

    //To check the left adjacent tile
    public boolean checkLeftAdjacent(int row, int column, Deck deck, int deckIndex){
        boolean left = false;
        //For left corner
        if (column - 1 < 0) {
            left = true;
            return left;
        }
        //If it's not near the left border, check the left square
        if (!left) {
            //Check if there is a tile on the left?
            if (board[row][column - 1].getIsTile()) {
                //If the color or the shape matches, then set left to true and add a point to the score
                if (board[row][column - 1].getColor().equals(deck.getTileAt(deckIndex).getColor())
                        || board[row][column - 1].getShape().equals(deck.getTileAt(deckIndex).getShape())) {
                    left = true;
                    scoreAfterCheckIfLegal++;
                    return left;
                    //The test fails and the tile can't be placed there
                } else return left;
                //If there isn't a tile then left is good to go, but the player doesn't get any points
            } else {
                left = true;
                return left;
            }
        }
        return false;
    }

    //To check the bottom adjacent tile
    public boolean checkBottomAdjacent(int row, int column, Deck deck, int deckIndex) {
        boolean bottom = false;

        //For bottom corner
        if (row + 1 == ROWS) {
            bottom = true;
            return bottom;
        }
        //If it's not near the bottom border, check the bottom square
        if (!bottom) {
            //Check if there is a tile on the bottom?
            if (board[row + 1][column].getIsTile()) {
                //If the color or the shape matches, then set bottom to true and add a point to the score
                if (board[row + 1][column].getColor().equals(deck.getTileAt(deckIndex).getColor())
                        || board[row + 1][column].getShape().equals(deck.getTileAt(deckIndex).getShape())) {
                    bottom = true;
                    scoreAfterCheckIfLegal++;
                    return bottom;
                    //The test fails and the tile can't be placed there
                } else return bottom;
                //If there isn't a tile then bottom is good to go, but the player doesn't get any points
            } else {
                bottom = true;
                return bottom;
            }
        }
        return false;
    }

    //To check the right adjacent tile
    public boolean checkRightAdjacent(int row, int column, Deck deck, int deckIndex) {
        boolean right = false;

        if (column + 1 == MAX_COLUMNS) {
            right = true;
            return right;
        }
        //If it's not near the right border, check the right square
        if (!right) {
            //Check if there is a tile on the right?
            if (board[row][column + 1].getIsTile()) {
                //If the color or the shape matches, then set right to true and add a point to the score
                if (board[row][column + 1].getColor().equals(deck.getTileAt(deckIndex).getColor())
                        || board[row][column + 1].getShape().equals(deck.getTileAt(deckIndex).getShape())) {
                    right = true;
                    scoreAfterCheckIfLegal++;
                    return right;
                    //The test fails and the tile can't be placed there
                } else return right;
                //If there isn't a tile then right is good to go, but the player doesn't get any points
            } else {
                right = true;
                return right;
            }
        }
        return false;
    }

    /*To check if the tile can be placed on a particular square
    Has to pass the indexes of the square that's being checked.
    It has to pass the whole model, the deckIndex, since it's checking for a move
    depending on the deck and updates the score if the caller wants to add points to the
    player.*/

    public boolean checkIfLegalMove(int row, int column, GameModel model, int deckIndex, String player) {

        //setting the temp score to 0 before checking for a legal move
        scoreAfterCheckIfLegal=0;

        //Check if all of the adjacent squares are okay for the next tile to be placed
        if (checkTopAdjacent(row, column, model.getDeck(), deckIndex)
                && checkBottomAdjacent(row, column, model.getDeck(), deckIndex)
                && checkLeftAdjacent(row, column, model.getDeck(), deckIndex)
                && checkRightAdjacent(row, column, model.getDeck(), deckIndex)) {
            //If the caller wanted to update the player model...
            if(player.equals("Human")){
                addPointsToPlayerAfterCheckIfLegal(model.getHuman());
            }
            else if(player.equals("Computer")){
                addPointsToPlayerAfterCheckIfLegal(model.getComputer());
            }
            return true;
        } else return false;
    }


    //Add points to the player's model. Just pass the player model
    public void addPointsToPlayerAfterCheckIfLegal(Player player){
        player.addPoints(scoreAfterCheckIfLegal);
    }

    //Returns the points that the user will get for this move.
    public int getTempScoreAfterCheckIfLegal(){
        return scoreAfterCheckIfLegal;
    }


}
