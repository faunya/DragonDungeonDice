package edu.neu.madcourse.team20_finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import edu.neu.madcourse.team20_finalproject.achievementRecycler.AchievementAdapter;
import edu.neu.madcourse.team20_finalproject.achievementRecycler.AchievementElement;

public class Achievements extends AppCompatActivity {

    private static final String SETTINGS = "settings";
    private static final String LOG_ON_DAYS = "logOnDays";
    private static final String ROLL_TIMES = "rollingTimes";

    private int logOnDays;
    private int times;

    private TextView totalTimes;
    private TextView totalDays;

    private RecyclerView recyclerView;
    AchievementAdapter adapter;
    ArrayList<AchievementElement> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        totalDays = findViewById(R.id.achieve_day);
        totalTimes = findViewById(R.id.achieve_roll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        totalDays.setText(String.valueOf(logOnDays));
        totalTimes.setText(String.valueOf(times));
        setMyList();
        setAdapter();
    }

    private void setMyList() {
        myList = new ArrayList<>();
        myList.add(new AchievementElement("FIRST LOGIN", true));
        myList.add(new AchievementElement("LOGIN 10 TIMES", logOnDays >= 10));
        myList.add(new AchievementElement("LOGIN 30 TIMES", logOnDays >= 30));
        myList.add(new AchievementElement("FIRST ROLL", times >= 1));
        myList.add(new AchievementElement("ROLLED 10 TIMES", times >= 10));
        myList.add(new AchievementElement("ROLLED 100 TIMES", times >= 100));
        myList.add(new AchievementElement("ROLLED 500 TIMES", times >= 500));
        myList.add(new AchievementElement("UNLOCKED 1 SKIN", logOnDays >= 30));
        myList.add(new AchievementElement("UNLOCKED 3 SKINS", logOnDays >= 90));
        myList.add(new AchievementElement("UNLOCKED ALL SKINS", logOnDays >= 300));
    }

    private void setAdapter() {
        recyclerView = findViewById(R.id.achieve_recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AchievementAdapter(myList, this);
        recyclerView.setAdapter(adapter);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        logOnDays = sharedPreferences.getInt(LOG_ON_DAYS, 1);
        times = sharedPreferences.getInt(ROLL_TIMES, 0);
    }

    public void close(View view) {
        finish();
    }
}