package edu.ramapo.sminev.ishido.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ramapo.sminev.ishido.R;

/***********************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 1                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/05/2015                                        *
 ************************************************************/
/*
    An activity to end the game.
    To get to this activity the user have to exhaust all the tiles in the deck.
    It shows a preview congratulating the user with his score.
    Contains:   A preview with how many points the player scored.
           Also contains a button("New Game"), which leads the user back to StartingActivity
           where the user can play a new game.

 */

public class EndCreditsActivity extends AppCompatActivity {

    private TextView textView;
    private Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Setting the view and getting the extra information from the previous activity
        Intent intent = getIntent();
        String message 	= intent.getStringExtra(Intent.EXTRA_TEXT);
        setContentView(R.layout.activity_end_credits);
        // intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Setting the text view and the button
        textView = (TextView)findViewById(R.id.end_credits);
        textView.setText(message);
        playAgain=(Button)findViewById(R.id.play_again);


        // setContentView(R.layout.activity_display_message);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToStart=new Intent(EndCreditsActivity.this,StartingActivity.class);
                startActivity(backToStart);
                finish();
            }
        });

    }
}
