package com.example.sunimali.petrays;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHoler>{

    private ArrayList<String> petsDPList;
    private ArrayList<String> petsNameList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> petsDPList, ArrayList<String> petsNameList, Context context) {
        this.petsDPList = petsDPList;
        this.petsNameList = petsNameList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pets_profile_cardlayout,viewGroup,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        Glide.with(context).asBitmap().load(petsDPList.get(i)).into(viewHoler.PetsDP);
        viewHoler.name.setText(petsNameList.get(i));

    }

    @Override
    public int getItemCount() {
        return petsNameList.size();
    }

    public  class ViewHoler extends RecyclerView.ViewHolder{

        CircleImageView PetsDP;
        TextView name;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            PetsDP = itemView.findViewById(R.id.PetPic);
            name = itemView.findViewById(R.id.name);
        }
    }
}
