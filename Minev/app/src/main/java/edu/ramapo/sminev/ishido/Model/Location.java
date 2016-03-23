package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

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
    //The heuristic score
    private int heuristicscore;
    //The player's scores
    private int humanScore;
    private int computerScore;
    //To represent the parent heuristic value of that location
    private Integer parentHeuristicScore;
    //A bool which represents if that node is a root. Set to false by default.
    private boolean isRoot;

    //The default constructor
    public Location(){
        //parentHeuristicScore=null;
        isRoot=false;
        row=null;
        column=null;
        heuristicscore=0;
        humanScore=0;
        computerScore=0;
    }

    public Location(int i, int j, int hs, int cs){
        row=i;
        column=j;
        humanScore=hs;
        computerScore=cs;
        isRoot=false;
    }

    //Getters for the row,column and score of the location
    public boolean getIsRoot(){
        return isRoot;
    }
    public Integer getRow() {
        return row;
    }
    public Integer getColumn() {
        return column;
    }
    public int getHeuristicScore() {
        return heuristicscore;
    }
    public int getHumanScore(){
        return humanScore;
    }
    public int getComputerScore(){
        return computerScore;
    }
    public Integer getParentHeuristicScore(){return parentHeuristicScore;}

    //Setters for the row, column and score
    public void setIsRoot(boolean value){
        isRoot=value;
    }
    public void setRow(Integer row) {
        this.row = row;
    }
    public void setColumn(Integer column) {
        this.column = column;
    }
    public void setHeuristicScore(int score) {
        heuristicscore = score;
    }
    public void setHumanScore(int humanScore) {
        this.humanScore = humanScore;
    }
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }
    public void setParentHeuristicScore(Integer score){parentHeuristicScore=score;}
    //To a location to another location (without its parent heuristic score)
    public void setLocation(Location l){
        setRow(l.getRow());
        setColumn(l.getColumn());
        setHumanScore(l.getHumanScore());
        setComputerScore(l.getComputerScore());
        setHeuristicScore(l.getHeuristicScore());
    }
}
