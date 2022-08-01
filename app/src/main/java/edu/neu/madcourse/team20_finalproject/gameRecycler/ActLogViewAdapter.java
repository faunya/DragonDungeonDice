package edu.neu.madcourse.team20_finalproject.gameRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.neu.madcourse.team20_finalproject.R;
import edu.neu.madcourse.team20_finalproject.game.system.Message;

public class ActLogViewAdapter extends RecyclerView.Adapter<ActLogViewAdapter.ActLogViewHolder> {
    private List<Message> log;
    private LayoutInflater inflater;

    public ActLogViewAdapter(Context context, List<Message> msgList) {
        this.inflater = LayoutInflater.from(context);
        this.log = msgList;
    }

    @NonNull
    @Override
    public ActLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.act_log_layout, parent, false);
        return new ActLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActLogViewHolder holder, int position) {
        Message msg = log.get(position);
        holder.bindData(msg.getTimestamp(), msg.getContent());
    }

    @Override
    public int getItemCount() {
        return log.size();
    }

    public class ActLogViewHolder extends RecyclerView.ViewHolder {
        private TextView timestampTV;
        private TextView actionTV;

        public ActLogViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTV = itemView.findViewById(R.id.timestamp);
            actionTV = itemView.findViewById(R.id.action);
        }

        public void bindData(long time, String action) {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            timestampTV.setText(format.format(new Date(time)));
            actionTV.setText(action);
        }
    }
}
