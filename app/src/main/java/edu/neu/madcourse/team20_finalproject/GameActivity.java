package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.Room;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Player;
import edu.neu.madcourse.team20_finalproject.game.system.Message;

public class GameActivity extends AppCompatActivity {
    private List<Message> actLog;
    private Player player;
    private Room curRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void onAttack(View view) {
        //player.attack();
    }

    public void onBlock(View view) {
    }

    public void onItem(View view) {

    }

    public void onRun(View view) {

    }

    public void onRest(View view) {

    }
}