package com.projecten3.eva.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projecten3.eva.R;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.Views.Fragments.RestoListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoViewHolder> {

    private Context context;
    private List<Restaurant> restos;

    public RestoAdapter(List<Restaurant> restos, Context context){
        this.restos = restos;
        this.context = context;
    }

    @Override
    public RestoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_resto,viewGroup,false);
        v.setOnClickListener(RestoListFragment.mainOnClickListener);

        return new RestoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RestoViewHolder restoViewHolder, int i){
       /* Glide.with(context)
                .load(restos.get(i).getFoto())
                .centerCrop()
                .into(restoViewHolder.imvResto);*/

        restoViewHolder.restoNaam.setText(restos.get(i).getNaam());
        restoViewHolder.restoOmschrijving.setText(R.string.veganistisch_eten);

    }

    @Override
    public int getItemCount(){
        return restos.size();
    }


    public Restaurant getResto(int index){
        return restos.get(index);
    }

    public void remove(int index){
        restos.remove(index);
        notifyDataSetChanged();
    }

    public static class RestoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.resto_naam)
        TextView restoNaam;
        @BindView(R.id.cardResto)
        CardView cardView;
        @BindView(R.id.imvResto)
        ImageView imvResto;
        @BindView(R.id.txvOmschrijving)
        TextView restoOmschrijving;

        public RestoViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
