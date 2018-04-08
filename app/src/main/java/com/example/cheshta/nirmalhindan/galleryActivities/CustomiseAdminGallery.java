package com.example.cheshta.nirmalhindan.galleryActivities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cheshta.nirmalhindan.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CustomiseAdminGallery extends RecyclerView.Adapter<CustomiseAdminGallery.myViewHolder> {

    ArrayList<POJO> arrayList;
    String[] urls;
    Context context;
    DatabaseReference ref;
    Boolean clicked=false;

    public static class myViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        ImageView heart;
        TextView key;
        public myViewHolder(View v)
        {
            super(v);
            image=v.findViewById(R.id.agcimage);
            heart=v.findViewById(R.id.agclike);
            key=v.findViewById(R.id.ackey);

        }
    }
    public CustomiseAdminGallery(ArrayList<POJO> arrayList, Context context, DatabaseReference ref)
    {
        this.context=context;
        this.arrayList=arrayList;
        this.ref=ref;


    }

    @NonNull
    @Override
    public CustomiseAdminGallery.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admingallerylist, parent, false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomiseAdminGallery.myViewHolder holder, final int position) {

        POJO ob=arrayList.get(position);
        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String key=holder.key.getText().toString();

                ref.child(key).removeValue();
                arrayList.remove(position);
                notifyItemRangeChanged(position,arrayList.size());
                return false;
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(clicked==false) {
                        holder.heart.setVisibility(View.VISIBLE);
                        clicked=!clicked;
                    }
                    else{
                        holder.heart.setVisibility(View.GONE);
                        clicked=!clicked;
                    }


            }
        });
     holder.key.setText(ob.key);
        Glide.with(context).load(ob.value).into(holder.image);
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
