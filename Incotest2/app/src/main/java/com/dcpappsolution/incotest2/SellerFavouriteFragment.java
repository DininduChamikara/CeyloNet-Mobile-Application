package com.dcpappsolution.incotest2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcpappsolution.incotest2.adapters.BuyerFavouriteAdapter;
import com.dcpappsolution.incotest2.adapters.SellerFavouriteAdapter;
import com.dcpappsolution.incotest2.models.BuyerFavouriteModel;
import com.dcpappsolution.incotest2.models.SellerFavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellerFavouriteFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    SellerFavouriteAdapter sellerFavouriteAdapter;
    List<SellerFavouriteModel> sellerFavouriteModelList;

    public SellerFavouriteFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_seller_favourite, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.favourite_request_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sellerFavouriteModelList = new ArrayList<>();
        sellerFavouriteAdapter = new SellerFavouriteAdapter(getActivity(), sellerFavouriteModelList);
        recyclerView.setAdapter(sellerFavouriteAdapter);

        db.collection("AddToFavouriteRequest").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();

                        SellerFavouriteModel cartModel = documentSnapshot.toObject(SellerFavouriteModel.class);

                        cartModel.setDocumentId(documentId);

                        sellerFavouriteModelList.add(cartModel);
                        sellerFavouriteAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return root;
    }
}