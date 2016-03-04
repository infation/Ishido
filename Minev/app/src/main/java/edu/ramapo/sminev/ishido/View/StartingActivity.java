package edu.ramapo.sminev.ishido.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import edu.ramapo.sminev.ishido.Model.Turn;
import edu.ramapo.sminev.ishido.R;

/************************************************************
 * Name:  Stanislav Minev                                   *
 * Project:  Project 2                                      *
 * Class:  CMPS 331 Artificial Intelligence I               *
 * Date:  02/23/2015                                        *
 ************************************************************/

/*An activity which displays a colorful view. It's main function is to
           familiarize the user with the rules of Ishido and how to play.
 Contains: Preview for the rules of the game and a button("Play Game") which leads to the
 main activity and starts the game. Also contains a spinner to select a file from
 the given cases (1,2,3 and 4). It passes the selected option as an extra in  MainActivity.
*/

public class StartingActivity extends AppCompatActivity {

    private Button newGame;
    private Button resumeGame;
    private Spinner filesSpinner;
    private ImageView coinView;
    private Button toss;
    private Spinner tossSpinner;
    private String turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        toss=(Button)findViewById(R.id.toss_coin);
        coinView=(ImageView)findViewById(R.id.coin_preview);
        newGame=(Button)findViewById(R.id.new_game);
        resumeGame=(Button)findViewById(R.id.resume_game);
        tossSpinner=(Spinner)findViewById(R.id.coin_spinner);


        filesSpinner=(Spinner)findViewById(R.id.files_spinner);
        filesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Intent goToMain = new Intent(StartingActivity.this, MainActivity.class);
                    String message = filesSpinner.getItemAtPosition(position).toString();
                    goToMain.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(goToMain);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filesSpinner.setVisibility(View.INVISIBLE);
                tossSpinner.setVisibility(View.VISIBLE);
                coinView.setVisibility(View.VISIBLE);
            }
        });

        tossSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 2) {
                    toss.setVisibility(View.VISIBLE);
                } else {
                    //Toast.makeText(StartingActivity.this, "Please select side.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        toss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resumeGame.setEnabled(false);
                //resumeGame.setText("");
                tossSpinner.setEnabled(false);
                int usersChoice = tossSpinner.getSelectedItemPosition();
                final Random ranNum = new Random();
                int nextNum ;
                /*
                for (int i = 0; i < 20000; i++) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            nextNum = ranNum.nextInt(2);
                            System.out.println("Number- " + nextNum);
                            switch(nextNum) {
                                case 0:
                                    coinView.setImageResource(R.mipmap.coin_heads);
                                    break;
                                case 1:
                                    coinView.setImageResource(R.mipmap.coin_tails);
                                    break;
                            }
                            numPicked=nextNum;
                        }
                    },100);
                }
            }*/
                for (int i = 0; i < 1000; i++) {
                    new CountDownTimer(10000, 1000) {
                        public void onFinish() {
                            // When timer is finished
                            // Execute your code here

                        }

                        public void onTick(long millisUntilFinished) {
                            coinView.setImageResource(R.mipmap.coin_heads);
                            // millisUntilFinished    The amount of time until finished.
                        }
                    }.start();


                    new CountDownTimer(10000, 1000) {
                        public void onFinish() {
                            // When timer is finished
                            // Execute your code here

                        }

                        public void onTick(long millisUntilFinished) {
                            coinView.setImageResource(R.mipmap.coin_tails);
                            // millisUntilFinished    The amount of time until finished.
                        }
                    }.start();

                }
                nextNum = ranNum.nextInt(2);
                switch (nextNum) {
                    case 0:
                        coinView.setImageResource(R.mipmap.coin_heads);
                        break;
                    case 1:
                        coinView.setImageResource(R.mipmap.coin_tails);
                        break;
                }

                if (usersChoice - 1 == nextNum) {
                    turn = "Human";
                } else {
                    turn = "Computer";
                }


                new CountDownTimer(10000, 1000) {
                    public void onFinish() {
                        Toast.makeText(StartingActivity.this, "The "+turn+" has the first move.", Toast.LENGTH_LONG).show();
                    }
                    public void onTick(long millisUntilFinished) {
                        Toast.makeText(StartingActivity.this, "FLIPPING THE COIN", Toast.LENGTH_SHORT).show();
                    }
                }.start();

                new CountDownTimer(20000, 2000) {
                    public void onFinish() {
                        Intent goToMain = new Intent(StartingActivity.this, MainActivity.class);
                        goToMain.putExtra("Turn", turn);
                        startActivity(goToMain);
                    }

                    public void onTick(long millisUntilFinished) {
                        Toast.makeText(StartingActivity.this, "FLIPPING THE COIN", Toast.LENGTH_SHORT).show();
                    }
                }.start();

            }
        });

        resumeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newGame.setEnabled(false);
                //newGame.setText("");
                tossSpinner.setVisibility(View.INVISIBLE);
                coinView.setVisibility(View.INVISIBLE);
                filesSpinner.setVisibility(View.VISIBLE);
            }
        });

    }
}
