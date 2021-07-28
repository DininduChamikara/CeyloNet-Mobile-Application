package com.dcpappsolution.incotest2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.activities.RequestsDetailedActivity;
import com.dcpappsolution.incotest2.models.PopularRequestModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularRequestAdapter extends RecyclerView.Adapter<PopularRequestAdapter.ViewHolder> {

    private Context context;
    private List<PopularRequestModel> popularRequestModelList;



    public PopularRequestAdapter(Context context, List<PopularRequestModel> popularModelList) {
        this.context = context;
        this.popularRequestModelList = popularModelList;
    }


    @NonNull
    @Override
    public PopularRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularRequestAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_requests, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRequestAdapter.ViewHolder holder, int position) {

       // Glide.with(context).load(popularRequestModelList.get(position).getImg_url()).into(holder.popImg);
        holder.titleNeed.setText(popularRequestModelList.get(position).getTitleNeed());
        holder.rating.setText(popularRequestModelList.get(position).getRating());
        holder.description.setText(popularRequestModelList.get(position).getDescription());

        holder.price.setText("Rs."+popularRequestModelList.get(position).getPrice() + "/-");


        ////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RequestsDetailedActivity.class);
                intent.putExtra("detail", popularRequestModelList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return popularRequestModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleNeed, description, rating;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            titleNeed = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            price = itemView.findViewById(R.id.pop_price);
            rating = itemView.findViewById(R.id.pop_rating);

        }
    }
}
