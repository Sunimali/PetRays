package com.example.sunimali.petrays.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sunimali.petrays.R;

import java.util.ArrayList;

public class RecyclerViewAdapterTreatments extends RecyclerView.Adapter<RecyclerViewAdapterTreatments.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mdecrpition = new ArrayList<>();
    private ArrayList<String> mmedicine = new ArrayList<>();
    private Context mContext;

    //get arryas.........................
    public RecyclerViewAdapterTreatments(Context context, ArrayList<String> decrpition, ArrayList<String> medicine ) {
        mdecrpition = decrpition;
        mmedicine = medicine;
        mContext = context;
    }

    //crete view holder.............................
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_treatment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //get treatment details and set them............
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
;

        holder.description.setText("Description: "+mdecrpition.get(position));
        holder.medicine.setText("Medicines"+mmedicine.get(position));

    }

    @Override
    public int getItemCount() {
        return mdecrpition.size();
    }

    //viewholder class intialize view objects.............................
    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView description;
        TextView medicine;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            medicine = itemView.findViewById(R.id.medicines);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
