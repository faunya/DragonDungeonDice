package edu.neu.madcourse.team20_finalproject.gameRecycler;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.team20_finalproject.game.system.Message;

public class ActLogViewAdapter extends RecyclerView.Adapter{
    private List<Message> log;

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
        return 0;
    }

    private class ActLogViewHolder extends RecyclerView.ViewHolder {

        public ActLogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
