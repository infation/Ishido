package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/**
 * Created by slavf on 2/27/16.
 */
public class Computer extends Player{

    Vector<Path> paths;
    Path path;
    int cutoff;
    int initialComputerScore=0;
    int initialHumanScore=0;
    //Vector<Location> locations;

    public Computer(){
        super();
        //Initializing the vector with the initial node.
        paths=new Vector<Path>();
        cutoff=0;
        //locations=new Vector<>();
    }

   /* public Vector<Location> generateAvailableLocations(){
        Vector<Location> locations=new Vector<>();
        //Basically adds the newly extended paths of the old vector-1 to the new one.
        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                //Is the particular button clickable? If not then there is a tile
                if (!board.getTileAt(row, column).getIsTile()) {
                    //The checkIfLegal will update the player score and place a tile at that location
                    //remove the current tile in deck and return true
                    if (board.checkIfLegalMove(row, column, model, path.pathSize(), "")) {
                        Location newLocation=new Location(row, column,board.getTempScoreAfterCheckIfLegal());
                        locations.add(newLocation);
                    }
                }
            }
        }
    }*/

    public void setCutoff(int cutoff){
        this.cutoff=cutoff;
    }

    public Location maximize(Vector<Location> locations){
        if(locations.size()>=2) {
            Location bestLoc = new Location(locations.get(0).getRow(),locations.get(0).getColumn(),locations.get(0).getScore());
            for (int i = 1; i < locations.size(); i++) {
                if(bestLoc.getScore()<locations.get(i).getScore()){
                    bestLoc.setLocationWithHeuristicValue(locations.get(i));
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
            Location bestLoc = new Location(locations.get(0).getRow(), locations.get(0).getColumn(), locations.get(0).getScore());
            for (int i = 1; i < locations.size(); i++) {
                if(bestLoc.getScore() > locations.get(i).getScore()){
                    bestLoc.setLocationWithHeuristicValue(locations.get(i));
                }
            }
            return bestLoc;
        }
        else{
            return locations.get(0);
        }
    }

    public void setInitialScores(int humanScore,int computerScore){
        initialComputerScore=computerScore;
        initialHumanScore=humanScore;
    }


    //The algorithm with which the computer will play.
    public Location MiniMax(int deckIndex, GameModel model, Location l, String turn) {
        Vector<Location> locations=new Vector<>(model.generateAvailableLocationsWithScore(deckIndex,turn,initialHumanScore,initialComputerScore));
        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff||locations.isEmpty()){
            //Compute the heuristic value of the leaf node
            l.setScore(computeScore(l.getHumanScore(),l.getComputerScore(),turn));
            return l;
        }
        else{
            //If its the computer's move
            if(turn.equals("Computer")) {
                    for (int i = 0; i < locations.size(); i++) {
                        //Simulate move by putting tile on the board
                        int humanScore = model.getHuman().getScore();
                        int computerScore = model.getComputer().getScore();
                        model.simulateMove(deckIndex, locations.get(i));
                        locations.get(i).setScore(MiniMax(deckIndex + 1, model, locations.get(i), "Human").getScore());
                        //Return the state of the board
                        model.undoSimulation(locations.get(i), humanScore, computerScore);
                    }
                    Location maximizedLocation = new Location();
                    maximizedLocation.setLocationWithHeuristicValue(maximize(locations));
                    System.out.println("Exiting from the maximizer: Heuristic value of the location["+maximizedLocation.getRow()+
                            "]["+maximizedLocation.getColumn()+"] with score: "+ maximizedLocation.getScore());
                    return maximizedLocation;
            }
            //If it's the human's move
            else{
                //For every children
                for(int i=0;i<locations.size();i++) {
                    //Simulate move by putting tile on the board
                    int humanScore=model.getHuman().getScore();
                    int computerScore=model.getComputer().getScore();
                    model.simulateMove(deckIndex, locations.get(i));
                    //Set the next turn to the computer
                    locations.get(i).setScore(MiniMax(deckIndex + 1, model, locations.get(i), "Computer").getScore());
                    //Return the state of the board
                    model.undoSimulation(locations.get(i), humanScore, computerScore);
                }
                Location minimizedLocation=new Location();
                minimizedLocation.setLocationWithHeuristicValue(minimize(locations));
                System.out.println("Exiting from the minimizer: Heuristic value of the location["+minimizedLocation.getRow()+
                        "]["+minimizedLocation.getColumn()+"] with score: "+ minimizedLocation.getScore());
                return minimizedLocation;
            }
        }
    }

    public int computeScore(int humanScore, int computerScore, String turn){
            return computerScore-humanScore;
    }

    //Alpha bete prunning for MiniMax which will make the algorithm more efficient
    public void AlphaBetaPrunning(){

    }
}
