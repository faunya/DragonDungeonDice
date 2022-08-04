package edu.neu.madcourse.team20_finalproject.skinRecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.team20_finalproject.R;
import edu.neu.madcourse.team20_finalproject.SkinActivity;

public class SkinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView wallpaperView;
    private ImageView icon;
    private static final String SETTINGS = "settings";
    private static final String LOG_ON_DAYS = "logOnDays";
    private final static String NUM_OF_DAYS_REQUIRED = "REQUIRED_DAYS";

    public SkinViewHolder(@NonNull View itemView) {
        super(itemView);
        wallpaperView = itemView.findViewById(R.id.wallpapers);
        icon = itemView.findViewById(R.id.lockIcon);
        itemView.setOnClickListener(this);
    }

    public void bindThisData(WallpaperID wallpaper) {
        if (WallpaperID.getWallpaperReference(wallpaper) != 0) {
            wallpaperView.setImageResource(WallpaperID.getWallpaperReference(wallpaper));
            int logOnDays = getLogOnDays(itemView.getContext());
            int numOfDaysRequired = getNumOfDaysRequired(itemView.getContext(), wallpaper);
            System.out.println("LogOnDays:" + logOnDays);
            System.out.println("numOfDays:" + numOfDaysRequired);
            if(logOnDays >= numOfDaysRequired) {
                icon.setVisibility(View.INVISIBLE);
            } else {
                icon.setVisibility(View.VISIBLE);
            }
        }

    }

    public int getNumOfDaysRequired (Context context, WallpaperID wallpaperID) {
        SharedPreferences sharedPref = context.getSharedPreferences(NUM_OF_DAYS_REQUIRED, Context.MODE_PRIVATE);
        int numOfDays = sharedPref.getInt(wallpaperID.getWpString(), 0);
        return numOfDays;
    }
    public int getLogOnDays(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE) ;
        int numOfDays = sharedPreferences.getInt(LOG_ON_DAYS, 0);
        return numOfDays;
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
