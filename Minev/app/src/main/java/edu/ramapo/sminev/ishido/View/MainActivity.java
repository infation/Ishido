package edu.ramapo.sminev.ishido.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.Time;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Vector;

import edu.ramapo.sminev.ishido.Model.GameModel;
import edu.ramapo.sminev.ishido.Model.Location;
import edu.ramapo.sminev.ishido.Model.Tile;
import edu.ramapo.sminev.ishido.Model.Turn;
import edu.ramapo.sminev.ishido.R;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/


/*
 This is the main activity where the user can use choose between four searches
(DepthFirstSearch, BreadthFirstSearch, BestFirstSearch and BranchAndBound)
Contains a board(8 rows x 12 columns), 1 Spinner for search selection,
1 hidden Spinner to choose the cutoff of the tree in Branch and Bound,
a preview of the current tile in the deck, a next button to show the next node
 visited by the chosen search and a preview for the  current score.

 */

public class MainActivity extends AppCompatActivity {

    //Constants
    public final int MAX_ROWS=8;
    public final int MAX_COLUMNS=12;

    //The views that display different info
    private Button currentTileView;
    private Button humanscoreView;
    private Button computerScoreView;
    private Button nextButton;
    private Button saveGameButton;
    private TextView timeStamp;
    private Button hint;
    private Location suggestedLocation=new Location();
    private boolean suggestionSet=false;

    //The view of the boardView
    private Button[][] boardView=new Button[MAX_ROWS][MAX_COLUMNS];

    //The model of the boardViewView
   // private GameBoard gameBoard=new GameBoard(MAX_ROWS,MAX_COLUMNS);
    public GameModel gameModel=new GameModel();
    private TextView turnView;
    private Spinner cutoffSpinner;

    //Drawer for tiles
    private Vector<String> drawerListViewItems=new Vector<>();
    private ListView drawerListView;
    private CheckBox alphaBeta;
    private String chosenOption;
    //private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();

        //Setting up the grid layout view
        boardView[0][0]=(Button)findViewById(R.id.button00);
        boardView[0][1]=(Button)findViewById(R.id.button01);
        boardView[0][2]=(Button)findViewById(R.id.button02);
        boardView[0][3]=(Button)findViewById(R.id.button03);
        boardView[0][4]=(Button)findViewById(R.id.button04);
        boardView[0][5]=(Button)findViewById(R.id.button05);
        boardView[0][6]=(Button)findViewById(R.id.button06);
        boardView[0][7]=(Button)findViewById(R.id.button07);
        boardView[0][8]=(Button)findViewById(R.id.button08);
        boardView[0][9]=(Button)findViewById(R.id.button09);
        boardView[0][10]=(Button)findViewById(R.id.button010);
        boardView[0][11]=(Button)findViewById(R.id.button011);
        boardView[1][0]=(Button)findViewById(R.id.button10);
        boardView[1][1]=(Button)findViewById(R.id.button11);
        boardView[1][2]=(Button)findViewById(R.id.button12);
        boardView[1][3]=(Button)findViewById(R.id.button13);
        boardView[1][4]=(Button)findViewById(R.id.button14);
        boardView[1][5]=(Button)findViewById(R.id.button15);
        boardView[1][6]=(Button)findViewById(R.id.button16);
        boardView[1][7]=(Button)findViewById(R.id.button17);
        boardView[1][8]=(Button)findViewById(R.id.button18);
        boardView[1][9]=(Button)findViewById(R.id.button19);
        boardView[1][10]=(Button)findViewById(R.id.button110);
        boardView[1][11]=(Button)findViewById(R.id.button111);
        boardView[2][0]=(Button)findViewById(R.id.button20);
        boardView[2][1]=(Button)findViewById(R.id.button21);
        boardView[2][2]=(Button)findViewById(R.id.button22);
        boardView[2][3]=(Button)findViewById(R.id.button23);
        boardView[2][4]=(Button)findViewById(R.id.button24);
        boardView[2][5]=(Button)findViewById(R.id.button25);
        boardView[2][6]=(Button)findViewById(R.id.button26);
        boardView[2][7]=(Button)findViewById(R.id.button27);
        boardView[2][8]=(Button)findViewById(R.id.button28);
        boardView[2][9]=(Button)findViewById(R.id.button29);
        boardView[2][10]=(Button)findViewById(R.id.button210);
        boardView[2][11]=(Button)findViewById(R.id.button211);
        boardView[3][0]=(Button)findViewById(R.id.button30);
        boardView[3][1]=(Button)findViewById(R.id.button31);
        boardView[3][2]=(Button)findViewById(R.id.button32);
        boardView[3][3]=(Button)findViewById(R.id.button33);
        boardView[3][4]=(Button)findViewById(R.id.button34);
        boardView[3][5]=(Button)findViewById(R.id.button35);
        boardView[3][6]=(Button)findViewById(R.id.button36);
        boardView[3][7]=(Button)findViewById(R.id.button37);
        boardView[3][8]=(Button)findViewById(R.id.button38);
        boardView[3][9]=(Button)findViewById(R.id.button39);
        boardView[3][10]=(Button)findViewById(R.id.button310);
        boardView[3][11]=(Button)findViewById(R.id.button311);
        boardView[4][0]=(Button)findViewById(R.id.button40);
        boardView[4][1]=(Button)findViewById(R.id.button41);
        boardView[4][2]=(Button)findViewById(R.id.button42);
        boardView[4][3]=(Button)findViewById(R.id.button43);
        boardView[4][4]=(Button)findViewById(R.id.button44);
        boardView[4][5]=(Button)findViewById(R.id.button45);
        boardView[4][6]=(Button)findViewById(R.id.button46);
        boardView[4][7]=(Button)findViewById(R.id.button47);
        boardView[4][8]=(Button)findViewById(R.id.button48);
        boardView[4][9]=(Button)findViewById(R.id.button49);
        boardView[4][10]=(Button)findViewById(R.id.button410);
        boardView[4][11]=(Button)findViewById(R.id.button411);
        boardView[5][0]=(Button)findViewById(R.id.button50);
        boardView[5][1]=(Button)findViewById(R.id.button51);
        boardView[5][2]=(Button)findViewById(R.id.button52);
        boardView[5][3]=(Button)findViewById(R.id.button53);
        boardView[5][4]=(Button)findViewById(R.id.button54);
        boardView[5][5]=(Button)findViewById(R.id.button55);
        boardView[5][6]=(Button)findViewById(R.id.button56);
        boardView[5][7]=(Button)findViewById(R.id.button57);
        boardView[5][8]=(Button)findViewById(R.id.button58);
        boardView[5][9]=(Button)findViewById(R.id.button59);
        boardView[5][10]=(Button)findViewById(R.id.button510);
        boardView[5][11]=(Button)findViewById(R.id.button511);
        boardView[6][0]=(Button)findViewById(R.id.button60);
        boardView[6][1]=(Button)findViewById(R.id.button61);
        boardView[6][2]=(Button)findViewById(R.id.button62);
        boardView[6][3]=(Button)findViewById(R.id.button63);
        boardView[6][4]=(Button)findViewById(R.id.button64);
        boardView[6][5]=(Button)findViewById(R.id.button65);
        boardView[6][6]=(Button)findViewById(R.id.button66);
        boardView[6][7]=(Button)findViewById(R.id.button67);
        boardView[6][8]=(Button)findViewById(R.id.button68);
        boardView[6][9]=(Button)findViewById(R.id.button69);
        boardView[6][10]=(Button)findViewById(R.id.button610);
        boardView[6][11]=(Button)findViewById(R.id.button611);
        boardView[7][0]=(Button)findViewById(R.id.button70);
        boardView[7][1]=(Button)findViewById(R.id.button71);
        boardView[7][2]=(Button)findViewById(R.id.button72);
        boardView[7][3]=(Button)findViewById(R.id.button73);
        boardView[7][4]=(Button)findViewById(R.id.button74);
        boardView[7][5]=(Button)findViewById(R.id.button75);
        boardView[7][6]=(Button)findViewById(R.id.button76);
        boardView[7][7]=(Button)findViewById(R.id.button77);
        boardView[7][8]=(Button)findViewById(R.id.button78);
        boardView[7][9]=(Button)findViewById(R.id.button79);
        boardView[7][10]=(Button)findViewById(R.id.button710);
        boardView[7][11]=(Button)findViewById(R.id.button711);

        //The score preview
        humanscoreView=(Button)findViewById(R.id.humanscore_preview);
        computerScoreView=(Button)findViewById(R.id.computerscore_preview);
        currentTileView = (Button) findViewById(R.id.current_tile);
        nextButton=(Button)findViewById(R.id.next_button);
        saveGameButton=(Button)findViewById(R.id.save_game);
        turnView=(TextView)findViewById(R.id.turn_view);
        timeStamp=(TextView)findViewById(R.id.time_stamp_after);
        alphaBeta=(CheckBox)findViewById(R.id.alpha_beta);
        hint=(Button)findViewById(R.id.hint_button);

        //Cutoff Spinner initialization
        cutoffSpinner=(Spinner)findViewById(R.id.cutoff_spinner);

        //String whichFile = intent.getStringExtra(Intent.EXTRA_TEXT);
        chosenOption=intent.getStringExtra("ChosenOption");
        //The chosen option from the starting activity
        //Just set the current turn to whoever won the toss and an empty board
        if(chosenOption.equals("1")){
            String turnStr=intent.getStringExtra("Turn");
            gameModel.getDeck().initializeDeck();
            gameModel.getTurn().setNextTurn(turnStr);
            gameModel.getDeck().shuffleDeck();
        }
        //Parse it if resume game
        else{
            String file=intent.getStringExtra("File");
            gameModel.parseFromFile(file);
        }

        setCutoffDropdownValues();

        //On click listeners for the boardView
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                boardView[i][j].setOnClickListener(boardButtonsHandler);
            }
        }

        for(int i=0;i<MAX_ROWS;i++){
            for(int j=0;j<MAX_COLUMNS;j++){
                updateTileView(gameModel.getBoard().getTileAt(i, j).getColor(),
                        gameModel.getBoard().getTileAt(i, j).getShape(), boardView[i][j]);

            }
        }

        //Setting up the views
        updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                gameModel.getDeck().getCurrentTile().getShape(), currentTileView);
        humanscoreView.setText(gameModel.getHuman().getScore().toString());
        computerScoreView.setText(gameModel.getComputer().getScore().toString());
        turnView.append(gameModel.getTurn().getCurrentTurn());
        //Setting up the drawer
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        //drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        // get list items from strings.xml

        setDrawer();

        saveGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gameModel.saveGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cutoffSpinner.getSelectedItemPosition()!=0) {
                    Long timeB = System.currentTimeMillis();
                    suggestedLocation.setLocation(gameModel.getComputer().hint(gameModel, cutoffSpinner.getSelectedItemPosition(),alphaBeta.isChecked()));

                    Long timeA=System.currentTimeMillis();
                    Long time= timeA-timeB;
                    Integer minutes = (int) ((time / (1000*60)) % 60);
                    Integer seconds = (int) (time / 1000) % 60 ;
                    Integer milliSecs= (int)(time - (minutes*1000*60+seconds*1000));

                    timeStamp.setText("Took: " + minutes + " minutes, " + seconds + " seconds, " + milliSecs + " millisecs");
                    updateTileView(gameModel.getDeck().getCurrentTile().getColor(), gameModel.getDeck().getCurrentTile().getShape()
                            , boardView[suggestedLocation.getRow()][suggestedLocation.getColumn()]);
                    gameModel.getHuman().addPoints(suggestedLocation.getComputerScore());
                    humanscoreView.setText(gameModel.getHuman().getScore().toString());
                    Animation mAnimation = new AlphaAnimation(1, 0);
                    mAnimation.setDuration(380);
                    mAnimation.setInterpolator(new LinearInterpolator());
                    mAnimation.setRepeatCount(Animation.INFINITE);
                    mAnimation.setRepeatMode(Animation.REVERSE);
                    boardView[suggestedLocation.getRow()][suggestedLocation.getColumn()].startAnimation(mAnimation);
                    suggestionSet=true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Please select a cutoff value.", Toast.LENGTH_SHORT).show();
                    cutoffSpinner.performClick();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameModel.getTurn().getCurrentTurn().equals("Computer")){
                    if (cutoffSpinner.getSelectedItemPosition() != 0) {
                        Long timeB = System.currentTimeMillis();
                        gameModel.getComputer().minimax(gameModel, cutoffSpinner.getSelectedItemPosition(), alphaBeta.isChecked());

                        Long timeA=System.currentTimeMillis();
                        Long time= timeA-timeB;
                        Integer minutes = (int) ((time / (1000*60)) % 60);
                        Integer seconds = (int) (time / 1000) % 60 ;
                        Integer milliSecs= (int)(time - (minutes*1000*60+seconds*1000));

                        timeStamp.setText("Took: "+ minutes+ " minutes, "+seconds+" seconds, "+milliSecs+" millisecs");

                        for (int i = 0; i < MAX_ROWS; i++) {
                            for (int j = 0; j < MAX_COLUMNS; j++) {
                                updateTileView(gameModel.getBoard().getTileAt(i, j).getColor(),
                                        gameModel.getBoard().getTileAt(i, j).getShape(), boardView[i][j]);
                                if (gameModel.getBoard().getTileAt(i, j).isBlink()) {
                                    Animation mAnimation = new AlphaAnimation(1, 0);
                                    mAnimation.setDuration(380);
                                    mAnimation.setInterpolator(new LinearInterpolator());
                                    mAnimation.setRepeatCount(Animation.INFINITE);
                                    mAnimation.setRepeatMode(Animation.REVERSE);
                                    boardView[i][j].startAnimation(mAnimation);
                                    //gameModel.getBoard().getTileAt(i, j).setBlink(false);
                                } else {
                                    boardView[i][j].clearAnimation();
                                }
                            }
                        }
                        if (gameModel.getDeck().size() == 0) {
                            //Going to the end credits activity
                            Intent endCredits = new Intent(MainActivity.this, EndCreditsActivity.class);
                            String message = "Congratulations!\n" +
                                    "You scored " + gameModel.getHuman().getScore().toString() + " points on Ishido.\n" +
                                    "You used all of the 72 tiles!\n" +
                                    "Thank you for playing Ishido.\n" +
                                    "By Stanislav Minev";
                            endCredits.putExtra(Intent.EXTRA_TEXT, message);
                            startActivity(endCredits);
                            finish();
                        }

                        updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                gameModel.getDeck().getCurrentTile().getShape(), currentTileView);
                        computerScoreView.setText(gameModel.getComputer().getScore().toString());
                        gameModel.getTurn().switchTurn();
                        turnView.setText("Turn: " + gameModel.getTurn().getCurrentTurn());
                        setDrawer();
                    } else {
                        Toast.makeText(MainActivity.this, "Please select a cutoff value.", Toast.LENGTH_SHORT).show();
                        cutoffSpinner.performClick();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "You have the next turn !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setDrawer(){
        //Drawer for tiles
        drawerListViewItems=new Vector<>();
        for(int i=1;i<gameModel.getDeck().size();i++){
            drawerListViewItems.add(gameModel.getDeck().getTileAt(i).getColor()+" "+gameModel.getDeck().getTileAt(i).getShape());
        }
        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));
    }


    public void setCutoffDropdownValues(){
        String[] cutoffValues=new String[gameModel.getDeck().size()+1];
        cutoffValues[0]="Cutoff";
        for(Integer i=1;i<gameModel.getDeck().size();i++){
            cutoffValues[i]=i.toString();
        }
        cutoffValues[gameModel.getDeck().size()]="The whole tree.";

        ArrayAdapter cutoffSpinnerAdapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item, cutoffValues);
        cutoffSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cutoffSpinner.setAdapter(cutoffSpinnerAdapter);
    }

    View.OnClickListener boardButtonsHandler = (new View.OnClickListener() {
        public void onClick(View view) {
            if(gameModel.getTurn().getCurrentTurn().equals("Human")) {
                //Goes through the matrix to find the id, that was clicked
                for (int i = 0; i < MAX_ROWS; i++) {
                    for (int j = 0; j < MAX_COLUMNS; j++) {
                        if (boardView[i][j].getId() == view.getId()) {
                            //If there isn't a tile on the board
                            if (!gameModel.getBoard().getTileAt(i, j).getIsTile()) {
                                //Check if it's a legal move
                                if (gameModel.getBoard().checkIfLegalMove(i, j, gameModel.getDeck().getCurrentTile())) {
                                    if (suggestionSet) {
                                        updateTileView("", "", boardView[suggestedLocation.getRow()][suggestedLocation.getColumn()]);
                                        boardView[suggestedLocation.getRow()][suggestedLocation.getColumn()].clearAnimation();
                                        gameModel.getHuman().setScore(gameModel.getHuman().getScore() - suggestedLocation.getComputerScore());
                                        //humanscoreView.setText(gameModel.getHuman().getScore().toString());
                                        if(i==suggestedLocation.getRow()&&j==suggestedLocation.getColumn()){
                                            gameModel.getBoard().setTileAt(suggestedLocation.getRow(), suggestedLocation.getColumn(),
                                                    gameModel.getDeck().getCurrentTile().getColor(),
                                                    gameModel.getDeck().getCurrentTile().getShape());
                                            gameModel.getTurn().switchTurn();
                                            gameModel.getHuman().addPoints(suggestedLocation.getComputerScore());
                                            updateTileView(gameModel.getDeck().getCurrentTile().getColor(), gameModel.getDeck().getCurrentTile().getShape()
                                                    , boardView[suggestedLocation.getRow()][suggestedLocation.getColumn()]);
                                            ///gameModel.getHuman().addPoints(suggestedLocation.getComputerScore());
                                            humanscoreView.setText(gameModel.getHuman().getScore().toString());
                                            turnView.setText("Turn: " + gameModel.getTurn().getCurrentTurn());
                                            gameModel.getDeck().removeCurrentFromDeck();
                                            updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                                    gameModel.getDeck().getCurrentTile().getShape(), currentTileView);

                                            drawerListViewItems.remove(0);
                                            // Set the adapter for the list view
                                            drawerListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                                    R.layout.drawer_listview_item, drawerListViewItems));
                                            //setCutoffDropdownValues();
                                            suggestionSet = false;
                                        }
                                        else{
                                            suggestionSet=false;
                                            gameModel.getBoard().addPointsToPlayerAfterCheckIfLegal(gameModel.getHuman());
                                            //gameModel.getHuman().setScore(gameModel.getHuman().getScore()-suggestedLocation.getComputerScore());
                                            //Set the tile in the board model
                                            gameModel.getBoard().setTileAt(i, j, gameModel.getDeck().getCurrentTile().getColor(),
                                                    gameModel.getDeck().getCurrentTile().getShape());
                                            //Update the view's background...
                                            updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                                    gameModel.getDeck().getCurrentTile().getShape(), boardView[i][j]);
                                            gameModel.getDeck().removeCurrentFromDeck();
                                            updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                                    gameModel.getDeck().getCurrentTile().getShape(), currentTileView);
                                            //Remove it from the deck
                                            //Update the score view
                                            humanscoreView.setText(gameModel.getHuman().getScore().toString());
                                            gameModel.getTurn().switchTurn();
                                            turnView.setText("Turn: " + gameModel.getTurn().getCurrentTurn());
                                            // get list items from strings.xml
                                            drawerListViewItems.remove(0);
                                            // Set the adapter for the list view
                                            drawerListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                                    R.layout.drawer_listview_item, drawerListViewItems));

                                            //cutoffSpinner.setVisibility(View.VISIBLE);
                                            //setCutoffDropdownValues();
                                        }
                                    }
                                    else{
                                        gameModel.getBoard().addPointsToPlayerAfterCheckIfLegal(gameModel.getHuman());
                                        //Set the tile in the board model
                                        gameModel.getBoard().setTileAt(i, j, gameModel.getDeck().getCurrentTile().getColor(),
                                                gameModel.getDeck().getCurrentTile().getShape());
                                        //Update the view's background...
                                        updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                                gameModel.getDeck().getCurrentTile().getShape(), boardView[i][j]);
                                        //Remove it from the deck
                                        gameModel.getDeck().removeCurrentFromDeck();
                                        if (gameModel.getDeck().size() == 0) {
                                            if (gameModel.getHuman().getScore() > gameModel.getComputer().getScore()) {
                                                //Going to the end credits activity
                                                Intent endCredits = new Intent(MainActivity.this, EndCreditsActivity.class);
                                                String message = "Congratulations YOU WON!\n" +
                                                        "You scored " + gameModel.getHuman().getScore().toString() + " points.\n" +
                                                        "The computer scored " + gameModel.getComputer().getScore().toString() + " points.\n" +
                                                        "Thank you for playing Ishido.\n" +
                                                        "By Stanislav Minev";
                                                endCredits.putExtra(Intent.EXTRA_TEXT, message);
                                                startActivity(endCredits);
                                                finish();
                                            } else {
                                                Intent endCredits = new Intent(MainActivity.this, EndCreditsActivity.class);
                                                String message = "YOU LOST!\n" +
                                                        "You scored " + gameModel.getHuman().getScore().toString() + " points.\n" +
                                                        "The computer scored " + gameModel.getComputer().getScore().toString() + " points.\n" +
                                                        "Thank you for playing Ishido.\n" +
                                                        "By Stanislav Minev";
                                                endCredits.putExtra(Intent.EXTRA_TEXT, message);
                                                startActivity(endCredits);
                                                finish();
                                            }
                                        }
                                        updateTileView(gameModel.getDeck().getCurrentTile().getColor(),
                                                gameModel.getDeck().getCurrentTile().getShape(), currentTileView);
                                        //Update the score view
                                        humanscoreView.setText(gameModel.getHuman().getScore().toString());
                                        gameModel.getTurn().switchTurn();
                                        turnView.setText("Turn: " + gameModel.getTurn().getCurrentTurn());
                                        // get list items from strings.xml
                                        if(gameModel.getDeck().size()!=0) {
                                            drawerListViewItems.remove(0);
                                            // Set the adapter for the list view
                                            drawerListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                                    R.layout.drawer_listview_item, drawerListViewItems));
                                        }
                                        //cutoffSpinner.setVisibility(View.VISIBLE);
                                        //setCutoffDropdownValues();

                                        //Check if the deck is empty, if not continue

                                    }
                                }
                                else{
                                        Toast.makeText(MainActivity.this, "You can't place the tile there.",
                                                Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "You can't place the tile there.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                for(int i=0;i<MAX_ROWS;i++){
                    for(int j=0;j<MAX_COLUMNS;j++){
                        boardView[i][j].clearAnimation();
                        gameModel.getBoard().getTileAt(i,j).setBlink(false);
                    }
                }
            }
            else{
                Toast.makeText(MainActivity.this, "The computer has the next turn. Click next to let it play !", Toast.LENGTH_SHORT).show();
            }
        }
    });

    //Updates the color of the current tile's data and preview
    public void updateTileColor(String color, Button b) {
        switch (color) {
            case "White":
                b.setBackgroundResource(R.drawable.white_tile);
                break;
            case "Blue":
                b.setBackgroundResource(R.drawable.blue_tile);
                break;
            case "Green":
                b.setBackgroundResource(R.drawable.green_tile);
                break;
            case "Yellow":
                b.setBackgroundResource(R.drawable.yellow_tile);
                break;
            case "Orange":
                b.setBackgroundResource(R.drawable.orange_tile);
                break;
            case "Red":
                b.setBackgroundResource(R.drawable.red_tile);
                break;
            default:
                b.setBackgroundResource(R.drawable.button_border);
                break;
        }
    }

    //Updates the shape of the current tile's data and preview
    public void updateTileShape(String shape, Button b){
        switch(shape) {
            case "+":
                b.setText("+");
                break;
            case "*":
                b.setText("*");
                break;
            case "%":
                b.setText("%");
                break;
            case "!":
                b.setText("!");
                break;
            case "@":
                b.setText("@");
                break;
            case "?":
                b.setText("?");
                break;
            default:
                b.setText("");
                break;
        }
    }

    //Update the selected tile data + view
    public void updateTileView(String c, String s, Button b){
        updateTileColor(c,b);
        updateTileShape(s,b);
    }
}