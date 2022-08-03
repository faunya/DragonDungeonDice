package edu.neu.madcourse.team20_finalproject.skinRecycler;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.team20_finalproject.R;

public class SkinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView wallpaperView;

    public SkinViewHolder(@NonNull View itemView) {
        super(itemView);
        wallpaperView = itemView.findViewById(R.id.wallpapers);
        itemView.setOnClickListener(this);
    }

    public void bindThisData(WallpaperID wallpaper) {
        if (WallpaperID.getWallpaperReference(wallpaper) != 0)
            wallpaperView.setImageResource(WallpaperID.getWallpaperReference(wallpaper));
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        SkinRecyclerAdapter.clickListener.onItemClick(pos);
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
