package edu.ramapo.sminev.ishido.Model;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/

/*
GameModel: The main model class of the program. It contains all other model classes -
Player, Deck, GameBoard as attributes. It has getter functions for the attributes -
getPlayer(), getDeck(), getBoard(). It also contains a parseFromFile(), which parses
a particular file and updates the mentioned models with the updateFromFile(),
which translates the characters into strings.
 */

public class GameModel {

    //Private objects
    private Turn turn;
    private Human human;
    private Computer computer;
    private Deck deck;
    private GameBoard board;

    public GameModel(){
        human=new Human();
        computer=new Computer();
        deck=new Deck();
        board=new GameBoard();
        turn=new Turn();
    }

    //Getter for the different models
    public Human getHuman(){
        return human;
    }
    public Computer getComputer(){
        return computer;
    }
    public Turn getTurn(){
        return turn;
    }
    public Deck getDeck() {
        return deck;
    }
    public GameBoard getBoard() {
        return board;
    }

    //Parsing the file and updating the data in the models. The file is chosen depending
    //on which file the user specified in StartingActivity.
    public void parseFromFile(String whichFile) {

        //Finding the sdcard path on the tablet
        File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
        System.out.println(sdcard);
        //Get the text file, depending on whichFile the user chose.
        File textFile = new File(sdcard, whichFile);
        //Initialize string builder
        StringBuilder boardState = new StringBuilder();

        //Try if the text file will be opened
        try {
            //If yes initialize buffer reader and a new line which will get the buffers next line
            BufferedReader br = new BufferedReader(new FileReader(textFile));
            String line;

            //Initializing the row
            int row = 0;
            //The first character will always be the charAt(0) and second one will be charAt(1)
            //Because of the structure of the serialized files
            int firstChar = 0;
            int secondChar = 1;

            //Also initialize a previous line which will get a value of "Layout:", "Stock:" or "Score:"
            //and so as to know when parsing which model should be updated
            String prevLine=null;

            try {
                //While the end of the file read next line
                while ((line = br.readLine()) != null) {

                    //If the line is equal to one of those make the previous line to be one of those
                    if (line.equals("Layout:") || line.equals("Stock:") || line.equals("Score:")||
                            line.equals("Human Score:")||line.equals("Computer Score:")||line.equals("Next Player:")) {
                        prevLine = line;
                        //If the line is empty string just ignore it
                    } else if (line.equals("")) {

                        //If it's not the begin the real parsing
                    } else {
                        //Append the line to the string builder.
                        boardState.append(line);
                        boardState.append('\n');

                        //A switch depending on the prevLine which has all the info which model should be updated
                        switch (prevLine) {
                            case "Layout:":
                                //The column is initialized here, every time i'm grabbing a new line, but the row
                                //is updated only once after the parsing of the first one
                                for (int column = 0; column < board.getColumns(); column++) {
                                    //Update the board
                                    updateFromFile(board.getTileAt(row, column), boardState.charAt(firstChar), boardState.charAt(secondChar));
                                    //Then the color and shape chars will be 3 characters ahead
                                    firstChar = firstChar + 3;
                                    secondChar = secondChar + 3;
                                }
                                //Update the row, so the next time i'm updating the next row
                                row++;
                                break;
                            case "Stock:":
                                //Create temp tile object and a count to know when I have to stop updating the deck
                                Tile tempTile;
                                int count=0;
                                //Until the end is reached
                                while(count<boardState.length()-1) {
                                    //Adding a new tile to the deck
                                    tempTile = new Tile();
                                    updateFromFile(tempTile, boardState.charAt(firstChar), boardState.charAt(secondChar));
                                    deck.addTile(tempTile);
                                    //Then the color and shape chars will be 3 characters ahead and so is the count.
                                    firstChar = firstChar + 3;
                                    secondChar = secondChar + 3;
                                    count = count + 3;
                                }
                                break;
                            case "Human Score:":
                                //Just parse the character to int
                                human.setScore(Integer.parseInt(line));
                                break;
                            case "Computer Score:":
                                //Just parse the character to int
                                computer.setScore(Integer.parseInt(line));
                                break;
                            case "Next Player:":
                                //Just parse the character to int
                                turn.setNextTurn(line);
                                break;
                            default:
                                break;
                        }

                        //Make buffer and characters ready for the next grab of characters
                        boardState.delete(0, boardState.length());
                        firstChar = 0;
                        secondChar = 1;
                    }
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Updates a color/shape of a tile depending on the character
    //in the text file
    public void updateFromFile(Tile t, char c, char s){
        switch(c){
            case '1': t.setColor("White");
                t.setIsTile(true);
                break;
            case '2': t.setColor("Blue");
                t.setIsTile(true);
                break;
            case '3': t.setColor("Green");
                t.setIsTile(true);
                break;
            case '4': t.setColor("Yellow");
                t.setIsTile(true);
                break;
            case '5': t.setColor("Orange");
                t.setIsTile(true);
                break;
            case '6': t.setColor("Red");
                t.setIsTile(true);
                break;
            default:
                t.setColor("");
        }

        switch(s){
            case '1': t.setShape("+");
                break;
            case '2': t.setShape("*");
                break;
            case '3': t.setShape("%");
                break;
            case '4': t.setShape("!");
                break;
            case '5': t.setShape("@");
                break;
            case '6': t.setShape("?");
                break;
            default:
                t.setShape("");
        }
    }

    //To parse the strings as chars from 0-6
    public void parseInFile(Tile t, BufferedWriter wr){
        try {
            switch (t.getColor()) {
                case "White":
                    wr.write('1');
                    break;
                case "Blue":
                    wr.write('2');
                    break;
                case "Green":
                    wr.write('3');
                    break;
                case "Yellow":
                    wr.write('4');
                    break;
                case "Orange":
                    wr.write('5');
                    break;
                case "Red":
                    wr.write('6');
                    break;
                default:
                    wr.write('0');
            }

            switch (t.getShape()) {
                case "+":
                    wr.write('1');
                    break;
                case "*":
                    wr.write('2');
                    break;
                case "%":
                    wr.write('3');
                    break;
                case "!":
                    wr.write('4');
                    break;
                case "@":
                    wr.write('5');
                    break;
                case "?":
                    wr.write('6');
                    break;
                default:
                    wr.write('0');
            }
            wr.write(' ');
        }
        catch (IOException e){

        }
    }

    //To save the game into a text file.
    public void saveGame() throws IOException {
        String fileName="SavedGame.txt";
        String filePath=Environment.getExternalStorageDirectory().toString();
        String line="Layout:";

        File file= new File(filePath,fileName);

        if(!file.exists()) file.delete();

        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file));
            bufferWriter.write(line + "\n");
            for(int i=0;i<board.getRows();i++){
                for(int j=0;j<board.getColumns();j++) {
                    parseInFile(board.getTileAt(i,j),bufferWriter);
                }
                bufferWriter.write("\n");
            }
            bufferWriter.write("\n"+"\n"+"Stock:"+"\n");
            for(int i=0;i<deck.size();i++){
                parseInFile(deck.getTileAt(i),bufferWriter);
            }
            bufferWriter.write("\n"+"\n"+"Human Score:"+"\n"+human.getScore().toString());
            bufferWriter.write("\n"+"\n"+"Computer Score:"+"\n"+computer.getScore().toString());
            bufferWriter.write("\n"+"\n"+"Next Player:"+"\n"+turn.getCurrentTurn());
            bufferWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
