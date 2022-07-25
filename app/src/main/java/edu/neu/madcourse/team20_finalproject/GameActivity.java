package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.ingame.Room;
import edu.neu.madcourse.team20_finalproject.game.ingame.entity.Entity;
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

        actLog = new ArrayList<Message>();
    }

    public void onAttack(View view) {
        int dmg = 0;
        int ac = curRoom.getNpcList().get(0).getArmorClass();
        //Intent intent = new Intent(this, DiceRollScreen.activity);
        /*
        Sends ac to dice rolling screen
        if roll is equal to or above ac, then it rolls again for dmg
        returns dmg value here
         */
        player.attack(curRoom.getNpcList().get(0), dmg);
        actLog.add(new Message(System.currentTimeMillis(),
                player.getName() + " attacked " + curRoom.getNpcList().get(0).getName()
                        + " for " + String.valueOf(dmg) + "dmg"));
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