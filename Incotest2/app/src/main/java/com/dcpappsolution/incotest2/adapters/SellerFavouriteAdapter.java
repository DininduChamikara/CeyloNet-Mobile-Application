package com.dcpappsolution.incotest2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.activities.DetailedActivity;
import com.dcpappsolution.incotest2.activities.RequestsDetailedActivity;
import com.dcpappsolution.incotest2.models.BuyerFavouriteModel;
import com.dcpappsolution.incotest2.models.SellerFavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SellerFavouriteAdapter extends RecyclerView.Adapter<SellerFavouriteAdapter.ViewHolder> {

    Context context;
    List<SellerFavouriteModel> sellerFavouriteModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public SellerFavouriteAdapter(Context context, List<SellerFavouriteModel> sellerFavouriteModelList) {
        this.context = context;
        this.sellerFavouriteModelList = sellerFavouriteModelList;

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SellerFavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerFavouriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_favourite_request, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerFavouriteAdapter.ViewHolder holder, int position) {

        holder.titleNeed.setText(sellerFavouriteModelList.get(position).getTitleNeed());
        holder.price.setText(sellerFavouriteModelList.get(position).getPrice());
//        holder.date.setText(sellerFavouriteModelList.get(position).getCurrentDate());
//        holder.time.setText(sellerFavouriteModelList.get(position).getCurrentTime());
        holder.rating.setText(sellerFavouriteModelList.get(position).getRating());
        holder.description.setText(sellerFavouriteModelList.get(position).getDescription());

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("AddToFavouriteRequest").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser")
                        .document(sellerFavouriteModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    sellerFavouriteModelList.remove(sellerFavouriteModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        ///////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RequestsDetailedActivity.class);
                intent.putExtra("detail", sellerFavouriteModelList.get(position));
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return sellerFavouriteModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleNeed, price, description, rating, date, time;
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleNeed = itemView.findViewById(R.id.seller_pop_name);
            price = itemView.findViewById(R.id.seller_pop_price);
            description = itemView.findViewById(R.id.seller_pop_des);
            rating = itemView.findViewById(R.id.seller_pop_rating);

            deleteItem = itemView.findViewById(R.id.delete_fav_request);

//            date = itemView.findViewById(R.id.current_Date);
//            time = itemView.findViewById(R.id.current_time);

        }
    }
}