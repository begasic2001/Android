package com.example.banhangonline;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.banhangonline.activities.PlaceOrderActivity;
import com.example.banhangonline.adapters.MyCartAdapter;
import com.example.banhangonline.adapters.MyOrderAdapter;
import com.example.banhangonline.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyOdersFragment extends Fragment {
    MyCartsFragment myCartsFragment;
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
//    MyCartAdapter cartAdapter;
    MyOrderAdapter orderAdapter;
    List<MyCartModel> cartModelList;
//    ProgressBar progressBar;

    public MyOdersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_my_oders, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerview);
//        recyclerView.setVisibility(View.GONE);
//        progressBar = root.findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(orderAdapter);
        overTotalAmount = root.findViewById(R.id.textView2);
        showAllOder();
//        LocalBroadcastManager.getInstance(getActivity())
//                .registerReceiver(mMessageReceiver,new IntentFilter("Tổng tiền của tôi"));

        return root;
    }
    public void showAllOder() {

        db.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("UserOrders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                cartModelList.clear();
                if(task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();

                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        orderAdapter.notifyDataSetChanged();

                    }
                }
            }

        });
}}