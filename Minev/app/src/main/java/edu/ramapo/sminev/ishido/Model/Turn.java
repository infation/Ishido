package edu.ramapo.sminev.ishido.Model;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 3                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/25/2015                                        *
 ************************************************************/

/* A class which represents who's turn is in the running game.
   	  Contains setters which automatically update the next turn to human/computer */

public class Turn {

    //A string representing who's turn it is.
    private String turn;

    //Default constructor
    public Turn(){
        turn="None";
    }

    public Turn(String turn){
        this.turn=turn;
    }

    //Getter to check who has the current turn
    public String getCurrentTurn(){
        return turn;
    }

    public void setNextTurn(String turn){
        this.turn=turn;
    }

    public void switchTurn(){
        if(turn.equals("Human")){
            turn="Computer";
        }
        else if (turn.equals("Computer")){
            turn="Human";
        }
    }

    //To set the next turn to the human
    public void setNextTurnHuman(){
        turn="Human";
    }

    //To set the next turn to the computer
    public void setNextTurnComputer(){
        turn="Computer";
    }
}
