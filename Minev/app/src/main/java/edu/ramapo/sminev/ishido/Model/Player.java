package edu.ramapo.sminev.ishido.Model;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/


/* A class to hold the current score of a player.
    Contains addPoints feature, getter and setter
    for the score.
 */

public class Player {

    //The data type is a wrapper class so it can be used in TextViews
    private Integer score;

    //Constructor
    public Player(){
        score=0;
    }

    //Add points to the current score
    public void addPoints(Integer points){
        score=score+points;
    }

    //Getter
    public Integer getScore(){
        return score;
    }

    //Setter
    public void setScore(int s){
        score=s;
    }
}
