package com.projecten3.eva.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projecten3.eva.Interfaces.OnCoreButtonCLickedInterface;
import com.projecten3.eva.Model.CoreButtons;
import com.projecten3.eva.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoreAdapter  extends RecyclerView.Adapter<CoreAdapter.CoreViewHolder> {
    private static final String TAG = "CoreAdapter";
    private ArrayList<CoreButtons> buttons;
    private OnCoreButtonCLickedInterface listener;
    public CoreAdapter(ArrayList<CoreButtons> buttons, OnCoreButtonCLickedInterface listener) {
        this.buttons = buttons;
        this.listener = listener;
    }

    /**
     * implement the listener in this method because this only gets called once
     * opposed to the onbindviewholder that gets called every time the card gets visible (thus, would create a new listener for every card
     * on every scroll outside the reach of the view), see: https://stackoverflow.com/a/33845951/5902728
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_core_button,parent,false);
        final CoreViewHolder viewHolder = new CoreViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClickListener(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
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

