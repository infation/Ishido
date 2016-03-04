package edu.ramapo.sminev.ishido.Model;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 3                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/25/2015                                        *
 ************************************************************/

public class Human extends Player {

    //To hold the human's deck
    private Deck humanDeck;

    //Constructor which takes a deck and adds the tiles to the human deck.
    public Human(){
        super();
        humanDeck=new Deck();
    }


    public Human(Deck deck){
        super();
        humanDeck=new Deck();
        for(int i=0;i<deck.size();i++){
            humanDeck.addTile(deck.getTileAt(i));
        }
    }

    //Getter for the deck
    public Deck getHumanDeck(){
        return humanDeck;
    }

    //This function to be used if the user needs help from the AI
    public void askAIForHelp(){

    }
}
