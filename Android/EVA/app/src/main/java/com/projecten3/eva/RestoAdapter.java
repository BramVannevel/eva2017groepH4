package com.projecten3.eva;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Desktop Ben on 22/07/2017.
 */

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoViewHolder> {

    private Context context;
    public static class RestoViewHolder extends RecyclerView.ViewHolder{



        //@BindView(R.id.resto_naam)
        public TextView restoNaam;
       // @BindView(R.id.cardResto)
        public CardView cardView;
        public ImageView imvResto;
        public TextView restoOmschrijving;

        public RestoViewHolder(View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardResto);
            restoNaam = (TextView) itemView.findViewById(R.id.resto_naam);
            imvResto = (ImageView) itemView.findViewById(R.id.imvResto);
            restoOmschrijving = (TextView) itemView.findViewById(R.id.txvOmschrijving);
        }
    }

    List<Restaurant> restos;

    RestoAdapter(List<Restaurant> restos, Context context){
        this.restos = restos;
        this.context = context;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RestoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_resto,viewGroup,false);
        v.setOnClickListener(RestoListFragment.mainOnClickListener);
        RestoViewHolder rvh = new RestoViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RestoViewHolder restoViewHolder, int i){
        //restoViewHolder.imvResto.setImageResource(restos.get(i).getFoto());

        Glide.with(context).load(restos.get(i).getFoto()).centerCrop().into(restoViewHolder.imvResto);

        restoViewHolder.restoNaam.setText(restos.get(i).getNaam());
        restoViewHolder.restoOmschrijving.setText(restos.get(i).getOmschrijving());

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

}
