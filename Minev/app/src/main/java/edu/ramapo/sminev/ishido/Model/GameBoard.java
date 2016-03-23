package edu.ramapo.sminev.ishido.Model;


import java.util.Vector;

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
    private final int COLUMNS=12;
    private Tile[][] board;
    //This is a score just to keep track of the points after the user got
    // after using checkIfLegalMove()
    private int scoreAfterCheckIfLegal;

    //Constructor.. Need to pass row and column
    public GameBoard(){
        //Initialize the board and the tempScore
        board=new Tile[ROWS][COLUMNS];
        for(int row=0;row<ROWS;row++) {
            for (int column = 0; column < COLUMNS; column++) {
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
        return COLUMNS;
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
    public boolean checkTopAdjacent(int row, int column, Tile tile){
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
                if (board[row - 1][column].getColor().equals(tile.getColor())
                        || board[row - 1][column].getShape().equals(tile.getShape())) {
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
    public boolean checkLeftAdjacent(int row, int column, Tile tile){
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
                if (board[row][column - 1].getColor().equals(tile.getColor())
                        || board[row][column - 1].getShape().equals(tile.getShape())) {
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
    public boolean checkBottomAdjacent(int row, int column, Tile tile) {
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
                if (board[row + 1][column].getColor().equals(tile.getColor())
                        || board[row + 1][column].getShape().equals(tile.getShape())) {
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
    public boolean checkRightAdjacent(int row, int column, Tile tile) {
        boolean right = false;

        if (column + 1 == COLUMNS) {
            right = true;
            return right;
        }
        //If it's not near the right border, check the right square
        if (!right) {
            //Check if there is a tile on the right?
            if (board[row][column + 1].getIsTile()) {
                //If the color or the shape matches, then set right to true and add a point to the score
                if (board[row][column + 1].getColor().equals(tile.getColor())
                        || board[row][column + 1].getShape().equals(tile.getShape())) {
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
    It has to pass a tile from the deck.*/
    public boolean checkIfLegalMove(int row, int column, Tile tile) {

        scoreAfterCheckIfLegal=0;
        //Check if all of the adjacent squares are okay for the next tile to be placed
        if (checkTopAdjacent(row, column, tile)
                && checkBottomAdjacent(row, column, tile)
                && checkLeftAdjacent(row, column, tile)
                && checkRightAdjacent(row, column, tile)) {
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

    //To simulate a move
    public void simulateMove(Location location, Tile tile){
        setTileAt(location.getRow(), location.getColumn(),
                tile.getColor(), tile.getShape());
    }

    //To undo the simulation
    public void undoSimulation(Location location){
        setTileAtToDefault(location.getRow(), location.getColumn());
    }

    //To generate the available locations for specific tile from the deck.
    public Vector<Location> generateAvailableLocations(Tile tile, String turn, Location parent){
        Vector<Location> locations=new Vector<>();
        //Basically adds the newly extended paths of the old vector-1 to the new one.
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                //Is the particular button clickable? If not then there is a tile
                if (!getTileAt(row, column).getIsTile()) {
                    //The checkIfLegal will update the player score and place a tile at that location
                    //remove the current tile in deck and return true
                    if (checkIfLegalMove(row, column,tile)) {
                        if(turn.equals("Human")){
                            Location newLocation=new Location(row, column,parent.getHumanScore()+getTempScoreAfterCheckIfLegal(),parent.getComputerScore());
                            newLocation.setHeuristicScore(Integer.MIN_VALUE);
                            newLocation.setParentHeuristicScore(Integer.MAX_VALUE);
                            //newLocation.setParentHeuristicScore(Integer.MIN_VALUE);
                            locations.add(newLocation);
                        }
                        else{
                            Location newLocation=new Location(row, column, parent.getHumanScore(),parent.getComputerScore()+getTempScoreAfterCheckIfLegal());
                            newLocation.setHeuristicScore(Integer.MAX_VALUE);
                            newLocation.setParentHeuristicScore(Integer.MIN_VALUE);
                            //newLocation.setParentHeuristicScore(Integer.MAX_VALUE);
                            locations.add(newLocation);
                        }
                    }
                }
            }
        }
        return locations;
    }
}
