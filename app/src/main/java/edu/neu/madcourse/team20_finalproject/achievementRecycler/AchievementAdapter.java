package edu.neu.madcourse.team20_finalproject.achievementRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.neu.madcourse.team20_finalproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private final ArrayList<AchievementElement> aList;
    private final Context context;

    public AchievementAdapter(ArrayList<AchievementElement> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AchievementViewHolder(LayoutInflater.from(context).inflate(R.layout.achievement_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        holder.bindThisData(aList.get(position));
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public class AchievementViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgTV;
        public TextView descriptionTV;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgTV = itemView.findViewById(R.id.achievement_status);
            this.descriptionTV = itemView.findViewById(R.id.achievement_description);
        }

        public void bindThisData(AchievementElement element) {
            imgTV.setImageResource(element.getImgId());
            descriptionTV.setText(element.getDescription());
        }
    }
}
