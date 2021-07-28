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

import com.dcpappsolution.incotest2.adapters.SellerAddProductAdapter;
import com.dcpappsolution.incotest2.adapters.SellerHomeAdapter;
import com.dcpappsolution.incotest2.models.SellerAddProductCategory;
import com.dcpappsolution.incotest2.models.SellerHomeCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AddProductFragment extends Fragment {

    RecyclerView sellerAddProductCatRec;
    FirebaseFirestore db;

    // Home Category
    List<SellerAddProductCategory> categoryList;
    SellerAddProductAdapter sellerAddProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_product, container, false);


        db = FirebaseFirestore.getInstance();
        sellerAddProductCatRec = root.findViewById(R.id.explore_rec);

        // Seller Home Category
        sellerAddProductCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        sellerAddProductAdapter = new SellerAddProductAdapter(getActivity(), categoryList);
        sellerAddProductCatRec.setAdapter(sellerAddProductAdapter);

        db.collection("BuyerHomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                SellerAddProductCategory sellerAddProductCategory = document.toObject(SellerAddProductCategory.class);
                                categoryList.add(sellerAddProductCategory);
                                sellerAddProductAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }
}