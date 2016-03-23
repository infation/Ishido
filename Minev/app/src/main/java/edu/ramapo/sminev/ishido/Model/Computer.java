package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/**
 * Created by slavf on 2/27/16.
 */
public class Computer extends Player{

    int cutoff;

    public Computer(){
        super();
        //Initializing the vector with the initial node.
        cutoff=0;
    }


    public void setCutoff(int cutoff){
        this.cutoff=cutoff;
    }

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

    public Location hint(GameModel model,int cutoff, boolean alphaBeta){
        setCutoff(cutoff);
        Location root=new Location();
        if(alphaBeta) {
            root.setLocation(AlphaBetaPruning(0, model.getBoard(), model.getDeck(), root, "Computer"));

        }
        else{
            root.setLocation(MiniMaxAlgorithm(0, model.getBoard(), model.getDeck(), root, "Computer"));
        }
        return root;
        //model.getHuman().addPoints(root.getComputerScore());
        /*model.getBoard().setBlinkableTileAt(root.getRow(), root.getColumn(),
                model.getDeck().getCurrentTile().getColor(), model.getDeck().getCurrentTile().getShape());*/
        //model.getDeck().removeCurrentFromDeck();
    }

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
        //Generate locations
        Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff||generatedLocations.isEmpty()){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore()));
            return parent;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                for (int i = 0; i < generatedLocations.size(); i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i),deck.getTileAt(deckIndex));
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1,board, deck, generatedLocations.get(i), "Human").getHeuristicScore());
                    //Return the state of the board
                    board.undoSimulation(generatedLocations.get(i));
                }
                Location maximizedLocation = new Location();
                maximizedLocation.setLocation(maximize(generatedLocations));
                System.out.println("Exiting from the maximizer: Heuristic value of the location["+maximizedLocation.getRow()+
                        "]["+maximizedLocation.getColumn()+"] with score: "+maximizedLocation.getHumanScore()+" | "+maximizedLocation.getComputerScore()+" | " + maximizedLocation.getHeuristicScore());
                return maximizedLocation;
            }
            //If it's the human's move
            else{
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
                System.out.println("Exiting from the minimizer: Heuristic value of the location["+minimizedLocation.getRow()+
                        "]["+minimizedLocation.getColumn()+"] with score: "+minimizedLocation.getHumanScore()+" | "+minimizedLocation.getComputerScore()+ " | "+minimizedLocation.getHeuristicScore());
                return minimizedLocation;
            }
        }
    }

    //The heuristic function
    public int computeScore(int humanScore, int computerScore){
            return computerScore-humanScore;
    }

    public boolean furtherPruning(Location parent, Location child, String currentTurn){
        if(currentTurn.equals("Human")){
            if(parent.getParentHeuristicScore()>parent.getHeuristicScore()){
                return true;
            }

            if(parent.getParentHeuristicScore()==null){
                parent.setParentHeuristicScore(parent.getHeuristicScore());
                return false;
            }
            return true;
        }
        else{
            if(parent.getParentHeuristicScore()<parent.getHeuristicScore()) {
                return true;
            }

            if(parent.getParentHeuristicScore()==null){
                parent.setParentHeuristicScore(parent.getHeuristicScore());
                return false;
            }
            return true;
        }
    }

    //Alpha-beta Pruning for MiniMax which will make the algorithm more efficient
    public Location AlphaBetaPruning(int deckIndex, GameBoard board,Deck deck, Location parent, String turn){
    //Generate locations
        Vector<Location> generatedLocations=new Vector<>(board.generateAvailableLocations(deck.getTileAt(deckIndex), turn, parent));
        //Set grandparents heuristics
        for(int i=0;i<generatedLocations.size();i++){
            generatedLocations.get(i).setParentHeuristicScore(parent.getHeuristicScore());
        }
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff||generatedLocations.isEmpty()){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore()));
            return parent;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                for (int i = 0; i < generatedLocations.size(); i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i), deck.getTileAt(deckIndex));
                    generatedLocations.get(i).setHeuristicScore(AlphaBetaPruning(deckIndex + 1, board, deck, generatedLocations.get(i), "Human").getHeuristicScore());
                    board.undoSimulation(generatedLocations.get(i));
                    //Setting the parent heuristic if its less than the current heuristic because its maximizer
                    if(parent.getHeuristicScore()<generatedLocations.get(i).getHeuristicScore()){
                        for(int j=i;j<generatedLocations.size();j++){
                            generatedLocations.get(j).setParentHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                        }
                        parent.setHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                    }
                    //System.out.println("The parent/child have heuristic value of : " + parent.getHeuristicScore()
                      //      + "/" + generatedLocations.get(i).getHeuristicScore()+"/"+parent.getParentHeuristicScore());
                    //If the grandparent heuristics and the parent heuristics don't match then prune the other children
                    if(!parent.getIsRoot()){
                            if(parent.getParentHeuristicScore()!=Integer.MAX_VALUE&&
                            parent.getParentHeuristicScore()<generatedLocations.get(i).getHeuristicScore()) {
                                //parent.setParentHeuristicScore(parent.getHeuristicScore());
                                //Return the state of the board
                                Location maximizedLocation = new Location();
                                maximizedLocation.setLocation(maximize(generatedLocations));
                                for (int j = i + 1; j < generatedLocations.size(); j++) {
                                    System.out.println("Pruning minimizer location[" + generatedLocations.get(j).getRow() +
                                            "][" + generatedLocations.get(j).getColumn() + "] with score: " +
                                            generatedLocations.get(j).getHumanScore() + " | " + generatedLocations.get(j).getComputerScore());
                                }
                                //System.out.println("The parent/child have heuristic value of : " + parent.getHeuristicScore() + "/" + generatedLocations.get(i).getHeuristicScore());
                                return maximizedLocation;
                            }
                    }
                }
                Location maximizedLocation = new Location();
                maximizedLocation.setLocation(maximize(generatedLocations));
                return maximizedLocation;
            }
            //Only because one of the children fails the condition that doesnt mean I have to prune the other children
            //If it's the human's move
            else{
                //For every children
                for(int i=0;i<generatedLocations.size();i++) {
                    //Simulate move by putting tile on the board
                    board.simulateMove(generatedLocations.get(i), deck.getTileAt(deckIndex));
                    //Set the next turn to the computer
                    generatedLocations.get(i).setHeuristicScore(AlphaBetaPruning(deckIndex + 1, board, deck, generatedLocations.get(i), "Computer").getHeuristicScore());
                    board.undoSimulation(generatedLocations.get(i));
                    //Setting the parent heuristic if its less than the current heuristic because its maximizer
                    if(parent.getHeuristicScore()>generatedLocations.get(i).getHeuristicScore()){
                        for(int j=i;j<generatedLocations.size();j++){
                            generatedLocations.get(j).setParentHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                        }
                        parent.setHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                    }

                    //If the grandparent heuristics and the parent heuristics don't match then prune the other children
                    if((!parent.getIsRoot())&&parent.getParentHeuristicScore()!=Integer.MIN_VALUE
                            &&parent.getParentHeuristicScore()>generatedLocations.get(i).getHeuristicScore()) {
                        //parent.setParentHeuristicScore(parent.getHeuristicScore());
                        //System.out.println("The parent/child have heuristic value of : " + parent.getHeuristicScore()
                         //       + "/" + generatedLocations.get(i).getHeuristicScore()+"/"+parent.getParentHeuristicScore());
                        //Return the state of the board
                        Location maximizedLocation = new Location();
                        maximizedLocation.setLocation(maximize(generatedLocations));
                        for(int j=i+1;j<generatedLocations.size();j++){
                            System.out.println("Pruning maximizer location[" + generatedLocations.get(j).getRow() +
                                    "][" + generatedLocations.get(j).getColumn() + "] with score: " +
                                    generatedLocations.get(j).getHumanScore() + " | " + generatedLocations.get(j).getComputerScore());
                        }
                        return maximizedLocation;
                    }
                }
                Location minimizedLocation=new Location();
                minimizedLocation.setLocation(minimize(generatedLocations));
                //System.out.println("Exiting from the minimizer: Heuristic value of the location[" + minimizedLocation.getRow() +
                //        "][" + minimizedLocation.getColumn() + "] with score: " + minimizedLocation.getHumanScore() + " | " + minimizedLocation.getComputerScore()+ " | "+minimizedLocation.getHeuristicScore());
                return minimizedLocation;
            }
        }
    }
}
