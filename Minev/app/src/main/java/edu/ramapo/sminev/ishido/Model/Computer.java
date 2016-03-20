package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/**
 * Created by slavf on 2/27/16.
 */
public class Computer extends Player{

    Vector<Path> paths;
    Path path;
    int cutoff;
    Vector<Location> locations;

    public Computer(){
        super();
        //Initializing the vector with the initial node.
        paths=new Vector<Path>();
        cutoff=0;
        locations=new Vector<>();
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
                if(bestLoc.getScore() < locations.get(i).getScore()){
                    bestLoc.setLocationWithHeuristicValue(locations.get(i));
                }
            }
            return bestLoc;
        }
        else{
            return locations.get(0);
        }
    }



    //The algorithm with which the computer will play.
    public Vector<Location> MiniMax(int deckIndex, GameModel model) {

        //Base case i.e when the leaf nodes are reached return the locations with the scores of the leaf nodes.
        if(deckIndex==cutoff){
            //Vector<Location> evaluatedLocations=new Vector<>();
            for(int i=0;i<locations.size();i++){
                locations.get(i).setScore(computeScore(model.getHuman().getScore(), model.getComputer().getScore(), model.getTurn().getCurrentTurn()));
            }
            //}
            /*if(model.getTurn().getCurrentTurn().equals("Human")){
                Location location = new Location();
                location.setLocationWithHeuristicValue(mini));
            }
            else{

            }*/
            //Needs to return the difference of the human score and the computer score.
            return locations;
        }
        //Continue expanding the tree
        else{
            Vector<Location> evaluatedLocations=new Vector<>();
            //If its the computer's move
            if(model.getTurn().getCurrentTurn().equals("Computer")) {
                Vector<Location> locations=new Vector<>(model.generateAvailableLocationsWithScore(deckIndex,"Computer"));
                //Vector<Location> evaluatedLocations=new Vector<>();
                //For every children
                for(int i=0;i<locations.size();i++){
                    //Simulate move by putting tile on the board
                    int humanScore=model.getHuman().getScore();
                    int computerScore=model.getComputer().getScore();
                    model.simulateMove(deckIndex, locations.get(i));
                    //model.simulateScore(model.getTurn());
                    model.getTurn().setNextTurnHuman();
                    Location location = new Location();
                    if(deckIndex+1==cutoff){
                        this.locations=new Vector<>(locations);
                    }
                    location.setLocationWithHeuristicValue(maximize(MiniMax(deckIndex+1, model)));
                    evaluatedLocations.add(location);
                    //Return the state of the board
                    model.undoSimulation(locations.get(i), humanScore,computerScore);
                    model.getTurn().setNextTurnComputer();
                }
            }
            //If it's the human's move
            else{
                Vector<Location> locations=new Vector<>(model.generateAvailableLocationsWithScore(deckIndex,"Human"));
                //For every children
                for(int i=0;i<locations.size();i++) {
                    //Simulate move by putting tile on the board
                    int humanScore=model.getHuman().getScore();
                    int computerScore=model.getComputer().getScore();
                    model.simulateMove(deckIndex, locations.get(i));
                    //Set the next turn to the computer
                    model.getTurn().setNextTurnComputer();
                    Location location = new Location();
                    if(deckIndex+1==cutoff){
                        this.locations=new Vector<>(locations);
                    }
                    location.setLocationWithHeuristicValue(minimize(MiniMax(deckIndex+1, model)));
                    evaluatedLocations.add(location);
                    //Return the state of the board
                    model.undoSimulation(locations.get(i),humanScore,computerScore);
                    //Undo the turn to human
                    model.getTurn().setNextTurnHuman();
                }
            }
            return evaluatedLocations;
        }

        //If its a minimizers turn

        //If its the maximizers turn

        //

    }

    public int computeScore(int humanScore, int computerScore, String turn){
        if(turn.equals("Human")){
            return humanScore-computerScore;
        }
        else{
            return computerScore-humanScore;
        }
    }

    //The algorithm with which the computer will play.
    /*public void MiniMaxWoRec(int cutoff, GameModel model){

        Path root=new Path();
        paths.add(root);

        Turn turn;
        turn=model.turn;

        while(paths.get(0).pathSize()!=cutoff){
            //A new vector to save the extended paths and then to be passed into the recursive function
            Vector<Path> extendedPaths=new Vector<>();

            //Until we exhaust all the previous parents
            while(paths.size()!=0) {
                //Initializing a path helper object for each of the old paths and delete
                //the old path until its empty
                Path path = new Path();
                path.changePathTo(paths.get(0));
                paths.remove(0);

                //Places the tiles on the board of the probable best solution
                //So the next tile from the deck considers the legal moves for those tiles as well
                for (int depth = 0; depth < path.pathSize(); depth++) {
                    model.getBoard().setTileAt(path.getLocationAtLevel(depth).getRow(),
                            path.getLocationAtLevel(depth).getColumn(), model.getDeck().getTileAt(depth).getColor(),
                            model.getDeck().getTileAt(depth).getShape());
                }

                //Basically adds the newly extended paths of the old vector-1 to the new one.
                for (int row = 0; row < model.getBoard().getRows(); row++) {
                    for (int column = 0; column < model.getBoard().getColumns(); column++) {
                        //Is the particular button clickable? If not then there is a tile
                        if (!model.getBoard().getTileAt(row, column).getIsTile()) {
                            //The checkIfLegal will update the player score and place a tile at that location
                            //remove the current tile in deck and return true
                            if (model.getBoard().checkIfLegalMove(row, column, model, path.pathSize(), "")) {
                                Path tempPath = new Path();
                                tempPath.changePathTo(path);

                                //System.out.println("Total Score of the path = "+tempPath.getTotalScore());
                                if(turn.getCurrentTurn().equals("Human")){
                                    //Adds a new location to this path with negative value
                                     //Integer minimizer=0
                                    System.out.println("The minimizer scores : "+ -model.getBoard().getTempScoreAfterCheckIfLegal());
                                    Location l = new Location(row, column,-model.getBoard().getTempScoreAfterCheckIfLegal());
                                    tempPath.addLocationToPath(l);
                                }
                                else{
                                    //Adds a new location to this path
                                    Location l = new Location(row, column, model.getBoard().getTempScoreAfterCheckIfLegal());
                                    tempPath.addLocationToPath(l);
                                }

                                //And adds it in the paths vector
                                extendedPaths.add(tempPath);
                            }
                        }
                    }
                }
                //Revert the board to the original state
                for (int depth = 0; depth < path.pathSize(); depth++) {
                    model.getBoard().setTileAtToDefault(path.getLocationAtLevel(depth).getRow(),
                            path.getLocationAtLevel(depth).getColumn());
                }
            }

            //Sticking back those paths in the initial vector for further extension
            while(extendedPaths.size()!=0) {
                    paths.add(extendedPaths.get(0));
                    extendedPaths.remove(0);
            }
            System.out.println("The vector size after extending paths is: "+paths.size());
            turn.switchTurn();
        }

        //Exiting the loops and all extended paths are saved in the vector with the max values
        //Time to find the best one.
        Path bestPath=new Path();
        bestPath.changePathTo(paths.get(0));
        for(int i=1;i<paths.size();i++){
            if(bestPath.getTotalScore()<paths.get(i).getTotalScore()){
                bestPath.changePathTo(paths.get(i));
            }
        }

        paths.removeAllElements();

        //Now set the board,deck,score to the best first location
        model.getBoard().setBlinkableTileAt(bestPath.getLocationAtLevel(0).getRow(),
                bestPath.getLocationAtLevel(0).getColumn(),
                model.getDeck().getCurrentTile().getColor(),
                model.getDeck().getCurrentTile().getShape());
        model.getDeck().removeCurrentFromDeck();
        model.getComputer().addPoints(bestPath.getLocationAtLevel(0).getScore());
        System.out.println("Total Score of the best path = "+bestPath.getTotalScore());
    }*/


    public void getLeafNodes(){

    }


    //For whenever the user has turn, the computer will recalibrate to accordingly to the
    //best turn the user can make
    public void minimize(GameModel model){

    }

    //For whenever the computer has turn, it will try to maximize its move
    public void maximize(GameModel model){

    }

    //Alpha bete prunning for MiniMax which will make the algorithm more efficient
    public void AlphaBetaPrunning(){

    }
}
