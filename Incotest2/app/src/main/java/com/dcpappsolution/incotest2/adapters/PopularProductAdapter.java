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
import com.dcpappsolution.incotest2.activities.DetailedActivity;
import com.dcpappsolution.incotest2.activities.ViewAllActivity;
import com.dcpappsolution.incotest2.models.PopularProductModel;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductModel> popularProductModelList;



    public PopularProductAdapter(Context context, List<PopularProductModel> popularModelList) {
        this.context = context;
        this.popularProductModelList = popularModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(popularProductModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularProductModelList.get(position).getName());
        holder.rating.setText(popularProductModelList.get(position).getRating());
        holder.description.setText(popularProductModelList.get(position).getDescription());
  //      holder.discount.setText(popularProductModelList.get(position).getDiscount());
        holder.price.setText(popularProductModelList.get(position).getPrice());


        ////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", popularProductModelList.get(position));
                context.startActivity(intent);


//                Intent intent = new Intent(context, ViewAllActivity.class);
//                intent.putExtra("type", popularProductModelList.get(position).getType());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name, description, rating, discount;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
       //     discount = itemView.findViewById(R.id.pop_discount);
            price = itemView.findViewById(R.id.pop_price);
            rating = itemView.findViewById(R.id.pop_rating);

        }
    }
}

