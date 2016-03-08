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
        path=new Path();
        paths.add(path);
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

        while(paths.get(0).pathSize()==cutoff){
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
