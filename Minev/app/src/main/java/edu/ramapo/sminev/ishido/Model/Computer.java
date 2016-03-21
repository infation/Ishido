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


    public void minimax(GameModel model, int cutoff,boolean alphaBeta){
        setCutoff(cutoff);
        Location root=new Location();
        //root.setComputerScore(model.getComputer().getScore());
        //root.setHumanScore(model.getHuman().getScore());
        int initialComputerScore=model.getComputer().getScore();

        if(alphaBeta) {
            root.setLocation(MiniMaxAlgorithm(0, model, root, "Computer"));
        }
        else{
            root.setLocation(AlphaBetaPruning(0,model,root,"Computer"));
        }

        System.out.println("ROOT: Heuristic value of the location[" + root.getRow() + "][" + root.getColumn() + "] with scores: " + root.getHumanScore() + " | " + root.getComputerScore() + " | " + root.getHeuristicScore());
        setScore(initialComputerScore + root.getComputerScore());
        model.getBoard().setBlinkableTileAt(root.getRow(), root.getColumn(),
                model.getDeck().getCurrentTile().getColor(), model.getDeck().getCurrentTile().getShape());
        model.getDeck().removeCurrentFromDeck();
    }


    //The algorithm with which the computer will play.
    public Location MiniMaxAlgorithm(int deckIndex, GameModel model, Location parent, String turn) {
        //Generate locations
        Vector<Location> generatedLocations=new Vector<>(model.generateAvailableLocations(deckIndex, turn, parent));
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff||generatedLocations.isEmpty()){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore(), turn));
            return parent;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                    for (int i = 0; i < generatedLocations.size(); i++) {
                        //Simulate move by putting tile on the board
                        model.simulateMove(deckIndex, generatedLocations.get(i));
                        generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1, model, generatedLocations.get(i), "Human").getHeuristicScore());
                        //Return the state of the board
                        model.undoSimulation(generatedLocations.get(i));
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
                    model.simulateMove(deckIndex, generatedLocations.get(i));
                    //Set the next turn to the computer
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1, model, generatedLocations.get(i), "Computer").getHeuristicScore());
                    //Return the state of the board
                    model.undoSimulation(generatedLocations.get(i));
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
    public int computeScore(int humanScore, int computerScore, String turn){
            return computerScore-humanScore;
    }


    //Alpha-beta Pruning for MiniMax which will make the algorithm more efficient
    public Location AlphaBetaPruning(int deckIndex, GameModel model, Location parent, String turn){
    //Generate locations
        Vector<Location> generatedLocations=new Vector<>(model.generateAvailableLocations(deckIndex, turn, parent));
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff||generatedLocations.isEmpty()){
            //Compute the heuristic value of the leaf node
            parent.setHeuristicScore(computeScore(parent.getHumanScore(), parent.getComputerScore(), turn));
            return parent;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                for (int i = 0; i < generatedLocations.size(); i++) {
                    //Simulate move by putting tile on the board
                    model.simulateMove(deckIndex, generatedLocations.get(i));
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1, model, generatedLocations.get(i), "Human").getHeuristicScore());
                    if(parent.getHeuristicScore()>generatedLocations.get(i).getHeuristicScore()){
                        //Return the state of the board
                        model.undoSimulation(generatedLocations.get(i));
                        Location maximizedLocation = new Location();
                        maximizedLocation.setLocation(maximize(generatedLocations));
                        return maximizedLocation;
                    }
                    else{
                        //Return the state of the board
                        model.undoSimulation(generatedLocations.get(i));
                        parent.setHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                    }
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
                    model.simulateMove(deckIndex, generatedLocations.get(i));
                    //Set the next turn to the computer
                    generatedLocations.get(i).setHeuristicScore(MiniMaxAlgorithm(deckIndex + 1, model, generatedLocations.get(i), "Computer").getHeuristicScore());
                    if(parent.getHeuristicScore()<generatedLocations.get(i).getHeuristicScore()){
                        //Return the state of the board
                        model.undoSimulation(generatedLocations.get(i));
                        Location minimizedLocation=new Location();
                        minimizedLocation.setLocation(minimize(generatedLocations));
                        return minimizedLocation;
                    }
                    else{
                        //Return the state of the board
                        model.undoSimulation(generatedLocations.get(i));
                        parent.setHeuristicScore(generatedLocations.get(i).getHeuristicScore());
                    }
                }
                Location minimizedLocation=new Location();
                minimizedLocation.setLocation(minimize(generatedLocations));
                System.out.println("Exiting from the minimizer: Heuristic value of the location["+minimizedLocation.getRow()+
                        "]["+minimizedLocation.getColumn()+"] with score: "+minimizedLocation.getHumanScore()+" | "+minimizedLocation.getComputerScore()+ " | "+minimizedLocation.getHeuristicScore());
                return minimizedLocation;
            }
        }
    }
}
