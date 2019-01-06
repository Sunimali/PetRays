package com.example.sunimali.petrays;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterH extends RecyclerView.Adapter<RecyclerViewAdapterH.ViewHoler>{

    private ArrayList<String> petsDPList;
    private ArrayList<String> petsNameList;
    private Context context;

    public RecyclerViewAdapterH(ArrayList<String> petsDPList, ArrayList<String> petsNameList, Context context) {
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
    public void onBindViewHolder(@NonNull final ViewHoler viewHoler, final int i) {
        Uri uri= Uri.fromFile(new File(petsDPList.get(i)));
       // bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

        Glide.with(context)
                .load(new File(uri.getPath())) // Uri of the picture
                .into(viewHoler.PetsDP);

       // Glide.with(context).asBitmap().load(uri).into(viewHoler.PetsDP);
        viewHoler.name.setText(petsNameList.get(i));


        viewHoler.PetsDP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, deleteViewEditPetProfileActivity.class);
                intent.putExtra("image", petsDPList.get(i));
                intent.putExtra("name", petsNameList.get(i));
                context.startActivity(intent);

            }
        });

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
