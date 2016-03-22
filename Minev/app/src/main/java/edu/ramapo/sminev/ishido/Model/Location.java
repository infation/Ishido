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
    //The score of the location after being placed on the board
    private int heuristicscore;
    private int humanScore;
    private int computerScore;
    private Integer parentHeuristicScore;
    private boolean isRoot=false;

    //The default constructor
    public Location(){
        //parentHeuristicScore=null;
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
    }

    public boolean getIsRoot(){
        return isRoot;
    }

    public void setIsRoot(boolean value){
        isRoot=value;
    }

    //Getters for the row,column and score of the location
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

    public void initializeParentHerScore(String turn){
        //parentHeuristicScore=null;
        if(turn.equals("Human")){
            parentHeuristicScore=Integer.MAX_VALUE;
        }
        else{
            parentHeuristicScore=Integer.MIN_VALUE;
        }
    }

    public void setLocation(Location l){
        setRow(l.getRow());
        setColumn(l.getColumn());
        setHumanScore(l.getHumanScore());
        setComputerScore(l.getComputerScore());
        setHeuristicScore(l.getHeuristicScore());
    }

    public Location minimize(Vector<Location> locations){
        if(locations.size()>=2) {
            Location bestLoc = new Location();
            bestLoc.setLocation(locations.get(0));
            for (int i = 1; i < locations.size(); i++) {
                if(bestLoc.getHeuristicScore() > locations.get(i).getHeuristicScore()){
                    bestLoc.setLocation(locations.get(i));
                }
            }
            return bestLoc;
        }
        else{
            return locations.get(0);
        }
    }

}
