package com.dcpappsolution.incotest2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dcpappsolution.incotest2.adapters.PopularProductAdapter;
import com.dcpappsolution.incotest2.adapters.PopularRequestAdapter;
import com.dcpappsolution.incotest2.adapters.SellerHomeAdapter;
import com.dcpappsolution.incotest2.models.PopularProductModel;
import com.dcpappsolution.incotest2.models.PopularRequestModel;
import com.dcpappsolution.incotest2.models.SellerHomeCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellerHomeFragment extends Fragment {

    RecyclerView sellerHomeCatRec, sellerPopularRec;
    FirebaseFirestore db;

    // Home Category
    List<SellerHomeCategory> categoryList;
    SellerHomeAdapter sellerHomeAdapter;

    // Popular Requests
    List<PopularRequestModel> popularRequestModelList;
    PopularRequestAdapter popularRequestAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_seller_home, container, false);
        db = FirebaseFirestore.getInstance();

        sellerHomeCatRec = root.findViewById(R.id.explore_rec);
        sellerPopularRec = root.findViewById(R.id.pop_rec_requests);

        // Seller Home Category
        sellerHomeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        sellerHomeAdapter = new SellerHomeAdapter(getActivity(), categoryList);
        sellerHomeCatRec.setAdapter(sellerHomeAdapter);

        db.collection("BuyerHomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                SellerHomeCategory sellerHomeCategory = document.toObject(SellerHomeCategory.class);
                                categoryList.add(sellerHomeCategory);
                                sellerHomeAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // Popular Requests
        sellerPopularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        popularRequestModelList = new ArrayList<>();
        popularRequestAdapter = new PopularRequestAdapter(getActivity(), popularRequestModelList);
        sellerPopularRec.setAdapter(popularRequestAdapter);


        ////////// continue //////////////

        db.collection("PopularRequests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularRequestModel popularRequestModel = document.toObject(PopularRequestModel.class);
                                popularRequestModelList.add(popularRequestModel);
                                popularRequestAdapter.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}