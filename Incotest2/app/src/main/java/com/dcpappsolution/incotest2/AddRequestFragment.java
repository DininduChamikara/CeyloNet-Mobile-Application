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

import com.dcpappsolution.incotest2.adapters.BuyerAddRequestAdapter;
import com.dcpappsolution.incotest2.adapters.SellerAddProductAdapter;
import com.dcpappsolution.incotest2.models.BuyerAddRequestCategory;
import com.dcpappsolution.incotest2.models.SellerAddProductCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddRequestFragment extends Fragment {

    RecyclerView buyerAddRequestCatRec;
    FirebaseFirestore db;

    // Home Category
    List<BuyerAddRequestCategory> categoryList;
    BuyerAddRequestAdapter buyerAddRequestAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_request, container, false);

        db = FirebaseFirestore.getInstance();
        buyerAddRequestCatRec = root.findViewById(R.id.explore_rec);

        // Seller Home Category
        buyerAddRequestCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        buyerAddRequestAdapter = new BuyerAddRequestAdapter(getActivity(), categoryList);
        buyerAddRequestCatRec.setAdapter(buyerAddRequestAdapter);

        db.collection("BuyerHomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                BuyerAddRequestCategory buyerAddRequestCategory = document.toObject(BuyerAddRequestCategory.class);
                                categoryList.add(buyerAddRequestCategory);
                                buyerAddRequestAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }
}