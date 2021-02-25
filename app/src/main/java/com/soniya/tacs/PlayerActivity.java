package com.soniya.tacs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayerActivity extends AppCompatActivity {

    private EditText player1;
    private EditText player2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player );

         player1 = (EditText) findViewById(R.id.player1name);
         player2 = (EditText) findViewById(R.id.player2name);



    }

    public void submitButtonClick(View view) {
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();

        Intent intent = new Intent(this,GameDisplayActivity.class);
        intent.putExtra("PLAYER_NAMES", new String[]{player1Name,player2Name});
        startActivity(intent);
    }
}