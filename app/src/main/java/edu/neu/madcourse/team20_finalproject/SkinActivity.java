package edu.neu.madcourse.team20_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
    }

    private void skinRecyclerSetUp() {
        skinAdapter = new SkinRecyclerAdapter(wallpaperIDList, this, this);
        skinRecycler = findViewById(R.id.skin_recycler);
        skinRecycler.setHasFixedSize(true);
        skinRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        skinRecycler.setAdapter(skinAdapter);
    }

    public void endActivity(View v) {
        finish();
    }

    @Override
    public void onItemClick(int position) {
        System.out.println("hahahah");
        int backgroundId = WallpaperID.getWallpaperReference(wallpaperIDList.get(position));
        Intent intent = new Intent(this, DiceRolling.class);
        intent.putExtra("backgroundId", backgroundId);
        startActivity(intent);
    }
}
