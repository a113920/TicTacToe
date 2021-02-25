package com.soniya.tacs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplayActivity extends AppCompatActivity {

    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_display );

        Button playAgainBtn = findViewById(R.id.play_again);
        Button homBtn = findViewById(R.id.home);
        TextView playerturn = findViewById(R.id.player_display);

        playAgainBtn.setVisibility(View.GONE);
        homBtn.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        if(playerNames != null){
            playerturn.setText((playerNames[0] + "'s Turn"));
        }

        gameBoard = findViewById(R.id.gameBoard);
        gameBoard.setUpGame(playAgainBtn, homBtn, playerturn, playerNames);
    }

    public void playAgainButton(View view) {
        gameBoard.resetGame();
        gameBoard.invalidate();
    }

    public void homeButton(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}