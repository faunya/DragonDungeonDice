package edu.neu.madcourse.team20_finalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.skinRecycler.SkinRecyclerAdapter;
import edu.neu.madcourse.team20_finalproject.skinRecycler.WallpaperID;

public class SkinActivity extends AppCompatActivity {
    private List<WallpaperID>  wallpaperIDList;
    private SkinRecyclerAdapter skinAdapter;
    private RecyclerView skinRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        wallpaperIDList = new ArrayList<>();
        addImgToList();

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
        wallpaperIDList.add(WallpaperID.B10);
        wallpaperIDList.add(WallpaperID.B11);
        wallpaperIDList.add(WallpaperID.B12);
    }

    private void skinRecyclerSetUp() {

    }

    public void endActivity(View v) {
        finish();
    }
}
