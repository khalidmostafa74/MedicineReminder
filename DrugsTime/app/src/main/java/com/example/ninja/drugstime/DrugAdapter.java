package com.example.ninja.drugstime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ninja on 25/04/2018.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugHolder> {
    ArrayList<Drug> drugs ;
    Context ctx;

    public DrugAdapter() {
    }

    public DrugAdapter(ArrayList<Drug> drugs, Context ctx) {
        this.drugs = drugs;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public DrugHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        DrugHolder drugHolder = new DrugHolder(v);
        return drugHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugHolder holder, int position) {
        Drug drug = drugs.get(position);
        byte[] bytes = drug.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
       holder.iv.setImageBitmap(bitmap);
        holder.tv1.setText(drug.getName());
        holder.tv2.setText(drug.getCount()+"");
        holder.tv3.setText(drug.getPeriod()+"");
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    class DrugHolder extends RecyclerView.ViewHolder{
    ImageView iv;
    TextView tv1,tv2,tv3;
     public DrugHolder(View itemView) {
         super(itemView);
         iv= itemView.findViewById(R.id.imageView);
         tv1=itemView.findViewById(R.id.textView);
         tv2=itemView.findViewById(R.id.textView2);
         tv3=itemView.findViewById(R.id.textView3);
     }

    }
    private byte[] intToByteArray ( final int i ) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(i);
        dos.flush();
        return bos.toByteArray();
    }
}
