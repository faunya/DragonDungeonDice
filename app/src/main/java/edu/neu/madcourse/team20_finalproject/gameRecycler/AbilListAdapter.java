package edu.neu.madcourse.team20_finalproject.gameRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.team20_finalproject.R;

import java.util.List;

public class AbilListAdapter extends RecyclerView.Adapter {
    private List<String> abilities;
    private LayoutInflater inflater;

    public AbilListAdapter(Context context, List<String> abilities) {
        this.inflater = LayoutInflater.from(context);
        this.abilities = abilities;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    public class AbilListViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV;
        private TextView statTV;

        public AbilListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.abilName);
            statTV = itemView.findViewById(R.id.abilRolls);
        }

        public void bindData(String name, String stat) {
            nameTV.setText(name);
            statTV.setText(stat);
        }
    }
}
