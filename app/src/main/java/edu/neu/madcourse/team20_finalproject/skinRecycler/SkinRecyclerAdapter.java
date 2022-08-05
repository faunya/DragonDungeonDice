package edu.neu.madcourse.team20_finalproject.skinRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.team20_finalproject.R;

public class SkinRecyclerAdapter extends RecyclerView.Adapter<SkinViewHolder> {
    public static SkinViewHolder.ItemClickListener clickListener;
    private List<WallpaperID> wallpaperIDList;
    private final Context context;

    public SkinRecyclerAdapter(List<WallpaperID> wallpaperIDList, Context context, SkinViewHolder.ItemClickListener clickListener) {
        this.wallpaperIDList = wallpaperIDList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SkinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkinViewHolder(LayoutInflater.from(context).inflate(R.layout.wallpaper_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull SkinViewHolder holder, int position) {
        holder.bindThisData(wallpaperIDList.get(position));
    }


    @Override
    public int getItemCount() {
        return wallpaperIDList.size();
    }

}


