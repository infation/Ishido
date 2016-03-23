package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 3                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/28/2015                                        *
 ************************************************************/

/* The class with which is represented. It contains a minimax algorithm
   and alpha-beta pruning for minimax
   and a hint for the human*/
public class Computer extends Player{

    //Cutoff set from the user
    int cutoff;

    public Computer(){
        super();
        //Initializing the vector with the initial node.
        cutoff=0;
    }

    //To set the cutoff
    public void setCutoff(int cutoff){
        this.cutoff=cutoff;
    }

    //The maximize function to pick the best value for the maximizer
    public Location maximize(Vector<Location> locations){
        if(locations.size()>=2) {
            Location bestLoc = new Location();
            bestLoc.setLocation(locations.get(0));
            for (int i = 1; i < locations.size(); i++) {
                if(bestLoc.getHeuristicScore()<locations.get(i).getHeuristicScore()){
                    bestLoc.setLocation(locations.get(i));
                }
            }
            return bestLoc;
        }
        else{
            return locations.get(0);
        }
    }

    //The minimize function to pick the best value for the minimizer
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

    //A hint for the user.
    public Location hint(GameModel model,int cutoff, boolean alphaBeta){
        setCutoff(cutoff);
        Location root=new Location();
        if(alphaBeta) {
            root.setHeuristicScore(Integer.MIN_VALUE);
            root.setIsRoot(true);
            root.setLocation(AlphaBetaPruning(0, model.getBoard(), model.getDeck(), root, "Computer"));
        }
        else{
            root.setLocation(MiniMaxAlgorithm(0, model.getBoard(), model.getDeck(), root, "Computer"));
        }
        return root;
    }

    //A wrapper function for whenever its a computer move
    public void minimax(GameModel model, int cutoff,boolean alphaBeta){
        setCutoff(cutoff);
        Location root=new Location();
        if(alphaBeta) {
            root.setHeuristicScore(Integer.MIN_VALUE);
            root.setIsRoot(true);
            root.setLocation(AlphaBetaPruning(0, model.getBoard(), model.getDeck(), root, "Computer"));
        }
        else{
            root.setLocation(MiniMaxAlgorithm(0, model.getBoard(),model.getDeck(), root, "Computer"));
        }

        System.out.println("ROOT: Heuristic value of the location[" + root.getRow() + "][" + root.getColumn() + "] with scores: " + root.getHumanScore() + " | " + root.getComputerScore() + " | " + root.getHeuristicScore());
        addPoints(root.getComputerScore());
        model.getBoard().setBlinkableTileAt(root.getRow(), root.getColumn(),
                model.getDeck().getCurrentTile().getColor(), model.getDeck().getCurrentTile().getShape());
        model.getDeck().removeCurrentFromDeck();
    }

    //The algorithm with which the computer will play.
    public Location MiniMaxAlgorithm(int deckIndex, GameBoard board, Deck deck, Location parent, String turn) {
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore()));
            return parent;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                //Generate locations
                Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
                for (int i = 0; i < generatedLocations.size(); i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i),deck.getTileAt(deckIndex));
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1,board, deck, generatedLocations.get(i), "Human").getHeuristicScore());
                    //Return the state of the board
                    board.undoSimulation(generatedLocations.get(i));
                }
                Location maximizedLocation = new Location();
                maximizedLocation.setLocation(maximize(generatedLocations));
                //System.out.println("Exiting from the maximizer: Heuristic value of the location["+maximizedLocation.getRow()+
                //        "]["+maximizedLocation.getColumn()+"] with score: "+maximizedLocation.getHumanScore()+" | "+maximizedLocation.getComputerScore()+" | " + maximizedLocation.getHeuristicScore());
                return maximizedLocation;
            }
            //If it's the human's move
            else{
                //Generate locations
                Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
                //For every children
                for(int i=0;i<generatedLocations.size();i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i), deck.getTileAt(deckIndex));
                    //Set the next turn to the computer
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1, board,deck, generatedLocations.get(i), "Computer").getHeuristicScore());
                    //Return the state of the board
                    board.undoSimulation(generatedLocations.get(i));
                }
                Location minimizedLocation=new Location();
                minimizedLocation.setLocation(minimize(generatedLocations));
                //System.out.println("Exiting from the minimizer: Heuristic value of the location["+minimizedLocation.getRow()+
                //        "]["+minimizedLocation.getColumn()+"] with score: "+minimizedLocation.getHumanScore()+" | "+minimizedLocation.getComputerScore()+ " | "+minimizedLocation.getHeuristicScore());
                return minimizedLocation;
            }
        }
    }

    //The heuristic function
    public int computeScore(int humanScore, int computerScore){
            return computerScore-humanScore;
    }

    //Alpha-beta Pruning
    public Location AlphaBetaPruning(int deckIndex, GameBoard board,Deck deck, Location parent, String turn){

        //Base case i.e when the leaf nodes are reached return the heuristic value
        if(deckIndex==cutoff){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore()));
            return parent;
        }
        else{
            //MAXIMIZER
            if(turn.equals("Computer")) {
                //Generate locations
                Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
                for (int i = 0; i < generatedLocations.size(); i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i), deck.getTileAt(deckIndex));
                    //Recursively go to the next level
                    generatedLocations.get(i).setHeuristicScore(AlphaBetaPruning(deckIndex + 1, board, deck, generatedLocations.get(i), "Human").getHeuristicScore());
                    //Undo the simulataion
                    board.undoSimulation(generatedLocations.get(i));

                    //Setting the parent heuristic in all the next generated locations if the current
                    // node has a better heuristic
                    if(generatedLocations.get(i).getParentHeuristicScore()<generatedLocations.get(i).getHeuristicScore()){
                        for(int j=i;j<generatedLocations.size();j++){
                            generatedLocations.get(j).setParentHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                        }
                    }

                    //If the grandparent heuristics and the parent heuristics aren't in the same
                    // range then prune the other generated locations and return the heuristic value to the parent
                    if(!parent.getIsRoot()){
                        if(parent.getParentHeuristicScore()!=Integer.MAX_VALUE&&
                        parent.getParentHeuristicScore()<generatedLocations.get(i).getHeuristicScore()) {
                            Location maximizedLocation = new Location();
                            maximizedLocation.setHeuristicScore(generatedLocations.get(i).getParentHeuristicScore());
                            return maximizedLocation;
                        }
                    }
                }
                Location maximizedLocation = new Location();
                //If the parent is a root and all the children are evaluated then pass the row/column to the root
                if(parent.getIsRoot()) {
                    maximizedLocation.setLocation(maximize(generatedLocations));
                }
                //Else just pass the heuristic score to the parent
                else{
                    maximizedLocation.setHeuristicScore(generatedLocations.get(generatedLocations.size() - 1).getParentHeuristicScore());
                }
                return maximizedLocation;
            }
            //MINIMIZER
            else{
                //Generate locations
                Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
                for(int i=0;i<generatedLocations.size();i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i), deck.getTileAt(deckIndex));
                    //Recursively go to the next level
                    generatedLocations.get(i).setHeuristicScore(AlphaBetaPruning(deckIndex + 1, board, deck, generatedLocations.get(i), "Computer").getHeuristicScore());
                    //Undo simulation
                    board.undoSimulation(generatedLocations.get(i));

                    //Setting the parent heuristic in all the next generated locations if the current
                    // node has a better heuristic
                    if (generatedLocations.get(i).getParentHeuristicScore() > generatedLocations.get(i).getHeuristicScore()) {
                        for (int j = i; j < generatedLocations.size(); j++) {
                            generatedLocations.get(j).setParentHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                        }
                    }

                    //If the grandparent heuristics and the parent heuristics aren't in the same
                    // range then prune the other generated locations and return the heuristic value to the parent
                    if (!parent.getIsRoot()) {
                        if (parent.getParentHeuristicScore() != Integer.MIN_VALUE
                                && parent.getParentHeuristicScore() > generatedLocations.get(i).getHeuristicScore()) {
                            Location maximizedLocation = new Location();
                            maximizedLocation.setHeuristicScore(generatedLocations.get(i).getParentHeuristicScore());
                            return maximizedLocation;
                        }
                    }
                }
                //Just pass the heuristic score to the parent
                Location minimizedLocation=new Location();
                minimizedLocation.setHeuristicScore(generatedLocations.get(generatedLocations.size()-1).getParentHeuristicScore());
                return minimizedLocation;
            }
        }
    }
}
