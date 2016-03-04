package edu.ramapo.sminev.ishido.Model;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/


/*
    A class to represent a tile. It has 3 variables :
        Color - has the color as a string. The default value is an empty string.
        Shape - has the shape as a string. The default value is an empty string.
        isSet - a flag to represent if the tile is set or not. The default value is false;
        blink - a flag to represent if the tile is supposed to blink in the view
 */

public class Tile {

    //The color and the shape of the tile saved as strings
    private String color;
    private String shape;
    //If there is a tile(used from the GameBoard class)
    private boolean isTile;
    //If the tile is supposed to blink
    private boolean blink;

    //Default constructor
    public Tile(){
        color="";
        shape="";
        isTile=false;
        blink=false;
    }

    //Custom constructor
    public Tile(String c, String s){
        color=c;
        shape=s;
        isTile=false;
        blink=false;
    }

    //Getters for all the attributes
    public String getShape() {
        return shape;
    }
    public boolean getIsTile() {
        return isTile;
    }
    public String getColor(){
        return color;
    }
    public boolean isBlink() {
        return blink;
    }

    //Setters
    public void setColor(String color) {
        this.color = color;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }
    public void setIsTile(boolean isTile) {
        this.isTile = isTile;
    }
    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    //A simple setter to set up the whole tile's data
    public void setTile(String c, String s, boolean isTile, boolean blink){
        color=c;
        shape=s;
        this.isTile=isTile;
        this.blink=blink;
    }
}