package com.example.banhangonline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.banhangonline.activities.AddAddressActivity;
import com.example.banhangonline.activities.AddressActivity;
import com.example.banhangonline.activities.PlaceOrderActivity;
import com.example.banhangonline.adapters.MyCartAdapter;
import com.example.banhangonline.models.MyCartModel;
import com.example.banhangonline.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyCartsFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    FirebaseDatabase database;
    List<MyCartModel> cartModelList;
//    ProgressBar progressBar;
    Button buynow;
    public MyCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        buynow = root.findViewById(R.id.buy_now);
        recyclerView = root.findViewById(R.id.recyclerview);
//        recyclerView.setVisibility(View.GONE);
//        progressBar = root.findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);
        overTotalAmount = root.findViewById(R.id.textView2);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver,new IntentFilter("Tổng tiền của tôi"));
        showAllCart();

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartModelList.size() > 0 && cartModelList !=null) {
                    Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
                    intent.putExtra("itemList", (Serializable) cartModelList);
                    startActivity(intent);
                }

//                    Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
//                    intent.putExtra("itemList", (Serializable) cartModelList);
//                    startActivity(intent);
            }
        });
        return root;
    }

    public void showAllCart() {

        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                cartModelList.clear();
                if(task.isSuccessful()) {

                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();

                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();

                    }
//                 calTotalAmount(cartModelList);

                }
            }

        });
//        database.getReference().child("AddToCart").child(auth.getCurrentUser().getUid())
//               .addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//                      for(DataSnapshot dataSnapshot : snapshot.getChildren()){
////                          MyCartModel myCartModel = dataSnapshot.getValue(MyCartModel.class);
////                          cartModelList.add(myCartModel);
//                      }
////                       cartAdapter.notifyDataSetChanged();
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError error) {
//
//                   }
//               });
    }
//    public void calTotalAmount(List<MyCartModel> cartModelList) {
//        int totalAmount = 0;
//        for(MyCartModel myCartModel : cartModelList){
//            totalAmount += myCartModel.getTotalPrice();
//        }
//        overTotalAmount.setText("Tổng Tiền :" + NumberFormat.getNumberInstance(Locale.US).format(totalAmount));
//    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("Tổng tiền",0);
            if(totalBill == 0){
                overTotalAmount.setText("Tổng tiền " + 0 + "VNĐ");
            }else{
                overTotalAmount.setText("Tổng tiền " + NumberFormat.getNumberInstance(Locale.US).format(totalBill) + "VNĐ");
            }
        }
    };

}