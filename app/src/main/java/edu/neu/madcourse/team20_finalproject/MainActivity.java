package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void diceRolling(View view) {
        Intent intent = new Intent(MainActivity.this, DiceRolling.class);
        startActivity(intent);
    }

    public void startGame(View view) {

    }
}