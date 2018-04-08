package com.example.cheshta.nirmalhindan.galleryActivities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cheshta.nirmalhindan.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class CustomiseGallery extends RecyclerView.Adapter<CustomiseGallery.myViewHolder> {

    ArrayList<String > userlist=new ArrayList<>();
    ArrayList<POJO> adminlist=new ArrayList<>();
    String[] urls;
    Context context;
    String role;
    Boolean clicked=false;
    DatabaseReference ref;
    FirebaseDatabase database ;
    DatabaseReference ref1;
    int i=0;
    ArrayList<String> key=new ArrayList<>(100);


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
    public CustomiseGallery(ArrayList<POJO> arrayList, Context context, ArrayList<String> stringArrayList, DatabaseReference ref, FirebaseDatabase database)
    {
        Log.e("aswe", "CustomiseGallery: "+arrayList.size() +" s" +stringArrayList.size());
         role= POJO.role;
         this.database=database;
        if (role.equals("admin"))
        {
           this.adminlist=arrayList;
        }
        else
        {
            this.userlist=stringArrayList;
        }
        this.context=context;
        this.ref=ref;
        ref1=database.getReference("imagesUrl").child("user");



    }

    @NonNull
    @Override
    public CustomiseGallery.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;

             v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.admingallerylist, parent, false);



        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomiseGallery.myViewHolder holder, final int position) {


        if (role.equals("user")) {
            Glide.with(context).load(userlist.get(position)).into(holder.image);
        } else {


            final POJO ob = adminlist.get(position);
            holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    String key = holder.key.getText().toString();
                    ref.child(key).removeValue();
                    adminlist.remove(position);
                    notifyItemRangeChanged(position, adminlist.size());
                    return false;
                }
            });
            holder.image.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {

                    DatabaseReference ref=database.getReference("imagesUrl").child("user");

                    if (holder.heart.getVisibility()==View.GONE) {
                        holder.heart.setVisibility(View.VISIBLE);


                        String t=ref1.push().getKey().toString();
                        key.add(position,t);
                        ref1.child(t).setValue(ob.value);
                        clicked = !clicked;
                    } else
                        if (holder.heart.getVisibility()==View.VISIBLE){
                        holder.heart.setVisibility(View.GONE);
                        Log.e("here1","The key "+key.size()+" "+key.get(position));
                        ref1.child(key.get(position)).removeValue();

                    }


                }
            });
            holder.key.setText(ob.key);
            Glide.with(context).load(ob.value).into(holder.image);

        }
    }
    @Override
    public int getItemCount() {
        if (role.equals("user"))
        {
        return userlist.size();
    }
    else return adminlist.size();
    }
}
