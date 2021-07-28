package com.dcpappsolution.incotest2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.activities.ViewAllActivity;
import com.dcpappsolution.incotest2.models.BuyerHomeCategory;
import com.dcpappsolution.incotest2.models.SellerHomeCategory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SellerHomeAdapter extends RecyclerView.Adapter<SellerHomeAdapter.ViewHolder> {

    Context context;
    List<SellerHomeCategory> categoryList;

    public SellerHomeAdapter(Context context, List<SellerHomeCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public SellerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerHomeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerHomeAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(categoryList.get(position).getName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ViewAllActivity.class);
//                intent.putExtra("type", categoryList.get(position).getType());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.home_cat_img);
            name = itemView.findViewById(R.id.cat_home_name);
        }
    }
}
