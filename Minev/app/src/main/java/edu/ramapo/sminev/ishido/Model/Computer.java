package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/**
 * Created by slavf on 2/27/16.
 */
public class Computer extends Player{

    Vector<Path> paths;
    Path path;


    public Computer(){
        super();
        //Initializing the vector with the initial node.
        paths=new Vector<Path>();
    }

    //The algorithm with which the computer will play.
    /*public void MiniMax(int cutoff, GameModel model, Vector<Path> paths){



        //Base case for the recursive algorithm, when we reached the leaf nodes.
        if(paths.get(0).pathSize()==cutoff){
            Vector<Path> newPaths=new Vector<Path>();

            while(paths.size()!=0) {
                Path path = new Path();
                path.changePathTo(paths.get(0));
                path.removeLastLocation();
                while (paths.get(0).pathSize()) {

                }
            }

        }
        //Else extend all the paths of the tree until we reach the leaf nodes.
        else{
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
                            if (model.getBoard().checkIfLegalMove(row, column, model, paths.get(0).pathSize(), "")) {
                                Path tempPath = new Path();
                                tempPath.changePathTo(path);
                                Location l = new Location(row, column,model.getBoard().getTempScoreAfterCheckIfLegal());
                                //Adds a new location to this path
                                tempPath.addLocationToPath(l);
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
            MiniMax(cutoff,model,extendedPaths);
        }


        Turn turn=new Turn();
        turn.equals(model.turn);
        //while(cutoff!=0) {
            switch (turn.toString()) {
                case "Human":
                    minimize(model);
                    break;
                case "Computer":
                    maximize(model);
                    break;
            }
        //}
    }*/

    //The algorithm with which the computer will play.
    public void MiniMaxWoRec(int cutoff, GameModel model){

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
    }


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
