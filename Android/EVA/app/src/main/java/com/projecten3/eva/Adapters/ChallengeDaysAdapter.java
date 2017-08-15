package com.projecten3.eva.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChallengeDaysAdapter extends RecyclerView.Adapter<ChallengeDaysAdapter.ChallengeViewHolder> {
    private static final String TAG = "ChallengeDaysAdapter";
    private ArrayList<Day> days;

    public ChallengeDaysAdapter(ArrayList<Day> days) {
        this.days = days;
    }

    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card,parent,false);
        return new ChallengeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, int position) {
        TextView dayOfTheWeek = holder.dayOfTHeWeek;
        TextView nrOfDay = holder.nrOfDay;

        dayOfTheWeek.setText(days.get(position).getDayOfTheWeek());
        nrOfDay.setText(Integer.toString(days.get(position).getWhichDayOfTheChallenge()));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day_of_the_week)
        TextView dayOfTHeWeek;
        @BindView(R.id.number_of_day)
        TextView nrOfDay;

        public ChallengeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
