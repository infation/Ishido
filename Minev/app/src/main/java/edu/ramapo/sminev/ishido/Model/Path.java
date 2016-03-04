package edu.ramapo.sminev.ishido.Model;

import java.util.Vector;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/

/* A class that represents a path in a search tree. It has all the locations of that path
  stored in a vector. It provides addLocation() to add a location in the end of a path,
  getLocationAtLevel() to get a certain location at an index and a changePathTo() which
  changes a path object to passed path object.*/
public class Path {
    //A vector to represent a path with an array of locations
    private Vector<Location> path;
    //The total score of all locations, which means the score of the path
    private int totalScore;

    //Default constructor
    public Path(){
        totalScore=0;
        path=new Vector<>();
    }

    //Gets a location at some level of the search tree. Have to pass index
    //so as to be <the path's size and greater or = to 0
    public Location getLocationAtLevel(int index){
        if(index<pathSize()&&index>=0){
            return path.get(index);
        }
        return null;
    }

    //Getter for the total score
    public int getTotalScore(){
        return totalScore;
    }

    //To return the path's size
    public int pathSize(){
        return path.size();
    }

    //To change a path to another path. Have to pass a path object
    public void changePathTo(Path path){
        //Initialize a counter to gets the next level of a path
        int level=0;
        //Remove all of this path's elements and set the total score to 0
        this.path.removeAllElements();
        totalScore=0;
        //Add all the locations to the passed path's locations
        while(level <path.pathSize()){
            addLocationToPath(path.getLocationAtLevel(level));
            level++;
        }
    }

    //Adds a location to a path in the end of the path, also updates the total score
    //by adding the new location's score. Have to pass location object.
    public void addLocationToPath(Location l){
        path.add(l);
        totalScore=totalScore+l.getScore();
    }

}
