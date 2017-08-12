package com.projecten3.eva.Adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projecten3.eva.Helpers.CreateCoreButtons;
import com.projecten3.eva.Model.CoreButtons;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoreAdapter  extends RecyclerView.Adapter<CoreAdapter.CoreViewHolder> {
    private static final String TAG = "CoreAdapter";
    private ArrayList<CoreButtons> buttons;

    public CoreAdapter(ArrayList<CoreButtons> buttons) {
        this.buttons = buttons;
    }

    @Override
    public CoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_core_button,parent,false);
        return new CoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CoreViewHolder holder, int position) {
        ImageView icon = holder.buttonIcon;
        TextView title = holder.buttonTitle;

        icon.setImageResource(buttons.get(position).getImage());
        title.setText(buttons.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }



    public static class CoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.core_button_image)
        ImageView buttonIcon;
        @BindView(R.id.core_button_title)
        TextView buttonTitle;
        public View itemView;

        public CoreViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }


    }
}

