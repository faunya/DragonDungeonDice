package edu.neu.madcourse.team20_finalproject.skinRecycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkinRecyclerAdapter extends RecyclerView.Adapter<SkinViewHolder> {
    private List<WallpaperID> wallpaperIDList;

    @NonNull
    @Override
    public SkinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SkinViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return wallpaperIDList.size();
    }
}
