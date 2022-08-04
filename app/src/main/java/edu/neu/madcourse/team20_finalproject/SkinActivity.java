package edu.neu.madcourse.team20_finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.skinRecycler.SkinRecyclerAdapter;
import edu.neu.madcourse.team20_finalproject.skinRecycler.SkinViewHolder;
import edu.neu.madcourse.team20_finalproject.skinRecycler.WallpaperID;

public class SkinActivity extends AppCompatActivity implements SkinViewHolder.ItemClickListener {
    private List<WallpaperID>  wallpaperIDList;
    private SkinRecyclerAdapter skinAdapter;
    private RecyclerView skinRecycler;
    private Context context;
    private SharedPreferences sharedPref;
    private final static String NUM_OF_DAYS_REQUIRED = "REQUIRED_DAYS";
    private final static Integer NUM_OF_WALLPAPERS = 10;
    private final static Integer NUM_OF_DAYS_PER_SKIN = 30;

    private static final String SETTINGS = "settings";
    private static final String LOG_ON_DAYS = "logOnDays";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        wallpaperIDList = new ArrayList<>();
        addImgToList();
        setSharedPreferenceInt();
        skinRecyclerSetUp();
    }

    private void addImgToList() {
        wallpaperIDList.add(WallpaperID.B1);
        wallpaperIDList.add(WallpaperID.B2);
        wallpaperIDList.add(WallpaperID.B3);
        wallpaperIDList.add(WallpaperID.B4);
        wallpaperIDList.add(WallpaperID.B5);
        wallpaperIDList.add(WallpaperID.B6);
        wallpaperIDList.add(WallpaperID.B7);
        wallpaperIDList.add(WallpaperID.B8);
        wallpaperIDList.add(WallpaperID.B9);
    }

    private void setSharedPreferenceInt() {
        context = SkinActivity.this;
        sharedPref = context.getSharedPreferences(NUM_OF_DAYS_REQUIRED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 1; i <= NUM_OF_WALLPAPERS; i++) {
            String key = "b" + i;
            int value = i * NUM_OF_DAYS_PER_SKIN;
            editor.putInt(key, value);
            editor.apply();
        }
    }

    private void skinRecyclerSetUp() {
        skinAdapter = new SkinRecyclerAdapter(wallpaperIDList, this, this);
        skinRecycler = findViewById(R.id.skin_recycler);
        skinRecycler.setHasFixedSize(true);
        skinRecycler.setLayoutManager(new LinearLayoutManager(this));
        skinRecycler.setAdapter(skinAdapter);
    }

    public void endActivity(View v) {
        finish();
    }

    @Override
    public void onItemClick(int position) {
        int backgroundId = WallpaperID.getWallpaperReference(wallpaperIDList.get(position));
        int logOnDays = getLogOnDays();
        int daysRequiredToUnlock = sharedPref.getInt(wallpaperIDList
                .get(position).getWpString(), 0);
        System.out.println("logOnDays:" + logOnDays);
        if(logOnDays >= daysRequiredToUnlock) {
            Intent intent = new Intent(this, DiceRolling.class);
            intent.putExtra("backgroundId", backgroundId);
            startActivity(intent);
        } else {
            int daysGap = daysRequiredToUnlock - logOnDays;
            String toastString = "Keep on logging into your account everyday!! " +
                    "You will be able to unlock this wallpaper in " + daysGap + " days!!";
            Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
        }
    }

    public int getLogOnDays() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS,MODE_PRIVATE);
        int numOfDays = sharedPreferences.getInt(LOG_ON_DAYS, 0);
        return numOfDays;
    }



}
