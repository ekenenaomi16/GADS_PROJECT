package com.example.gadsleaderboard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.databinding.RvLearnerItemBinding;
import com.example.gadsleaderboard.models.Learner;

import java.util.List;

public class LeadersAdapter extends RecyclerView.Adapter<LeadersAdapter.MyViewHolder> {

    private List<Learner> itemsList;
    private Context context;
    private boolean isIQLeader;

    LeadersAdapter(Context context, List<Learner> itemsList, boolean isIQLeader) {
        this.itemsList = itemsList;
        this.context = context;
        this.isIQLeader = isIQLeader;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        RvLearnerItemBinding binding;

        MyViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    //--------------------------------
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvLearnerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_learner_item, parent, false);

        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Learner learner = itemsList.get(position);
        holder.binding.setLeader(learner);
        if (isIQLeader){
            holder.binding.tvDesc.setText(
                    context.getString(
                            R.string.score, learner.getScore(), learner.getCountry()
                    )
            );
        }else {
            holder.binding.tvDesc.setText(
                    context.getString(
                            R.string.hours, learner.getHours(), learner.getCountry()
                    )
            );
        }

        Glide.with(holder.binding.imageView)
                .load(learner.getBadgeUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.binding.imageView);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
