package edu.neu.madcourse.team20_finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TestDiceForGame extends AppCompatActivity {

    TextView result;
    EditText type;
    EditText ac;
    int myType;
    int myAC;
    String info;
    ActivityResultLauncher rollResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dice_for_game);
        result = findViewById(R.id.test);
        type = findViewById(R.id.type);
        ac = findViewById(R.id.ac);
        info = "start";

        rollResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int res = data.getIntExtra("roll", 1);
                        info = String.valueOf(res);
                    } else {
                        info = "not found";
                    }
                });
    }

    public void submit(View view) {
        myType = Integer.parseInt(type.getText().toString());
        myAC = Integer.parseInt(ac.getText().toString());
        Intent intent = new Intent(this, DiceForGame.class);
        intent.putExtra("type", myType);
        intent.putExtra("ac", myAC);
        rollResultLauncher.launch(intent);
    }

    public void display(View view) {
        result.setText(info);
    }
}