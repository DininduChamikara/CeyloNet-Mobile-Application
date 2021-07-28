package com.dcpappsolution.incotest2.ui.buyerHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.adapters.BuyerHomeAdapter;
import com.dcpappsolution.incotest2.adapters.PopularProductAdapter;
import com.dcpappsolution.incotest2.models.BuyerHomeCategory;
import com.dcpappsolution.incotest2.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class buyerHomeFragment extends Fragment {


    RecyclerView popularRec,  homeCatRec;
    FirebaseFirestore db;

    // Popular Products
    List<PopularProductModel> popularProductModelList;
    PopularProductAdapter popularProductAdapters;

    // Home Category
    List<BuyerHomeCategory> categoryList;
    BuyerHomeAdapter buyerHomeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_buyer_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec_requests);
        homeCatRec = root.findViewById(R.id.explore_rec);

        // Popular items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        popularProductModelList = new ArrayList<>();
        popularProductAdapters = new PopularProductAdapter(getActivity(), popularProductModelList);
        popularRec.setAdapter(popularProductAdapters);

        ///////////// PopularProducts --> AllProducts
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                                popularProductModelList.add(popularProductModel);
                                popularProductAdapters.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Buyer Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        buyerHomeAdapter = new BuyerHomeAdapter(getActivity(), categoryList);
        homeCatRec.setAdapter(buyerHomeAdapter);

        db.collection("BuyerHomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                BuyerHomeCategory buyerHomeCategory = document.toObject(BuyerHomeCategory.class);
                                categoryList.add(buyerHomeCategory);
                                buyerHomeAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}