package edu.neu.madcourse.team20_finalproject.gameRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.team20_finalproject.R;
import edu.neu.madcourse.team20_finalproject.game.ingame.ability.Ability;
import edu.neu.madcourse.team20_finalproject.skinRecycler.SkinViewHolder;

import java.util.List;

/**
 *
 */
public class AbilListAdapter extends RecyclerView.Adapter<AbilListAdapter.AbilListViewHolder> {
    private List<Ability> abilities;
    private LayoutInflater inflater;
    private AbilListAdapter.ItemClickListener itemClickListener;

    public AbilListAdapter(Context context, List<Ability> abilities, ItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.abilities = abilities;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AbilListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ability_layout, parent, false);
        return new AbilListViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AbilListViewHolder holder, int position) {
        holder.bindData(abilities.get(position).getName(), abilities.get(position).getRoll());
    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    /**
     *
     */
    public class AbilListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameTV;
        private TextView statTV;
        private ItemClickListener itemClickListener;

        public AbilListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.abilName);
            statTV = itemView.findViewById(R.id.abilRolls);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        public void bindData(String name, String stat) {
            nameTV.setText(name);
            statTV.setText(stat);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     *
     */
    public interface ItemClickListener {
        void onItemClick(int pos);
    }
}
