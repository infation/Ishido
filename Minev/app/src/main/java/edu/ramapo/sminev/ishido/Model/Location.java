package edu.ramapo.sminev.ishido.Model;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/

/* A class that holds an available position on the board, depending on the
   current state of the board. It saves the row/column and the score of that
   available location. It has setters and getters for those attributes a custom constructor
   and setLocationToDefault() which sets the location to default values and setLocation()
   to set all the attributes to a passed Location's attributes */
public class Location {
    //The row of the location on the board
    private Integer row;
    //The column of the location on the board
    private Integer column;
    //The score of the location after being placed on the board
    private int score;

    //The default constructor
    Location(){
        row=null;
        column=null;
        score=0;
    }

    //Custom constructor to set a location
    Location(int i, int j, int s){
        row=i;
        column=j;
        score=s;
    }

    //The location is set to default
    public void setToDefaultLocation(){
        row=null;
        column=null;
        score=0;
    }

    //Getters for the row,column and score of the location
    public Integer getRow() {
        return row;
    }
    public Integer getColumn() {
        return column;
    }
    public int getScore() {
        return score;
    }

    //Setters for the row, column and score
    public void setRow(Integer row) {
        this.row = row;
    }
    public void setColumn(Integer column) {
        this.column = column;
    }
    public void setScore(int score) {
        this.score = score;
    }

    //Sets the location's row, column and score to a passed location's row,column, score
    public void setLocation(Location l){
        setRow(l.getRow());
        setColumn(l.getColumn());
        setScore(l.getScore());
    }

}
