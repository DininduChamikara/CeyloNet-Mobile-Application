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
import com.dcpappsolution.incotest2.models.BuyerFavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuyerFavouriteAdapter extends RecyclerView.Adapter<BuyerFavouriteAdapter.ViewHolder> {

        Context context;
        List<BuyerFavouriteModel> buyerFavouriteModelList;

        FirebaseFirestore firestore;
        FirebaseAuth auth;

public BuyerFavouriteAdapter(Context context, List<BuyerFavouriteModel> buyerFavouriteModelList) {
        this.context = context;
        this.buyerFavouriteModelList = buyerFavouriteModelList;

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_favourite_item, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.name.setText(buyerFavouriteModelList.get(position).getProductName());
    holder.price.setText(buyerFavouriteModelList.get(position).getProductPrice());
    holder.date.setText(buyerFavouriteModelList.get(position).getCurrentDate());
    holder.time.setText(buyerFavouriteModelList.get(position).getCurrentTime());

    holder.deleteItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firestore.collection("AddToFavourite").document(auth.getCurrentUser().getUid())
                    .collection("CurrentUser")
                    .document(buyerFavouriteModelList.get(position).getDocumentId())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                buyerFavouriteModelList.remove(buyerFavouriteModelList.get(position));
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

            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detail", buyerFavouriteModelList.get(position));
            context.startActivity(intent);


        }
    });
}

@Override
public int getItemCount() {
        return buyerFavouriteModelList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, price, date, time;
    ImageView deleteItem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.product_name);
        price = itemView.findViewById(R.id.product_price);
        date = itemView.findViewById(R.id.current_Date);
        time = itemView.findViewById(R.id.current_time);

        deleteItem = itemView.findViewById(R.id.delete);

    }
}
}