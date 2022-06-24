package com.example.banhangonline.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banhangonline.R;
import com.example.banhangonline.activities.DetailActivity;
import com.example.banhangonline.models.ViewAllModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolders> {
    Context context;
    List<ViewAllModel> list;

    public ViewAllAdapter(Context context, List<ViewAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.price.setText(NumberFormat.getNumberInstance(Locale.US).format(list.get(position).getPrice())+ "VND");

//        if(list.get(position).getType().equals("egg")){
//            holder.price.setText(list.get(position).getPrice()+ "VND");
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name , description , price,rating;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_description);
            price = itemView.findViewById(R.id.view_price);
            rating = itemView.findViewById(R.id.view_rating);
        }
    }
}
