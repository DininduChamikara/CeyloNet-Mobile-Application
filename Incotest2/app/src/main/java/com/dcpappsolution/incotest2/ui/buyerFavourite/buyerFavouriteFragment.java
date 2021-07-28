package com.dcpappsolution.incotest2.ui.buyerFavourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.adapters.BuyerFavouriteAdapter;
import com.dcpappsolution.incotest2.models.BuyerFavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class buyerFavouriteFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    BuyerFavouriteAdapter buyerFavouriteAdapter;
    List<BuyerFavouriteModel> buyerFavouriteModelList;

    public buyerFavouriteFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_buyer_favourite, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        buyerFavouriteModelList = new ArrayList<>();
        buyerFavouriteAdapter = new BuyerFavouriteAdapter(getActivity(), buyerFavouriteModelList);
        recyclerView.setAdapter(buyerFavouriteAdapter);

        db.collection("AddToFavourite").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();

                        BuyerFavouriteModel cartModel = documentSnapshot.toObject(BuyerFavouriteModel.class);

                        cartModel.setDocumentId(documentId);

                        buyerFavouriteModelList.add(cartModel);
                        buyerFavouriteAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return root;
    }
}