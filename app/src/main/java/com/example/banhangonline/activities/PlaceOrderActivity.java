package com.example.banhangonline.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.banhangonline.MainActivity;
import com.example.banhangonline.MyCartsFragment;
import com.example.banhangonline.R;
import com.example.banhangonline.models.AddressModel;
import com.example.banhangonline.models.MyCartModel;
import com.example.banhangonline.models.UserModel;
import com.example.banhangonline.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity {
    AddressModel addressModel = null;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button returnHome ;
    MyCartsFragment myCartsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        returnHome = findViewById(R.id.return_home);
        List<MyCartModel> list =(ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");

//        final Object object2 = getIntent().getSerializableExtra("address");
//        if(object2 instanceof AddressModel){
//            addressModel =(AddressModel) object2;
//        }
//        if(addressModel!=null){
//
//        }
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlaceOrderActivity.this, MainActivity.class));
            }
        });

        if(list != null && list.size() > 0) {
            for(MyCartModel model : list){
//                String address = addressModel.getUserAddress();
                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("productName", model.getProductName());
//                cartMap.put("Address ", address);
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());
//.collection("AddToCart").document(model.getDocumentId())
                firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                        .collection("UserOrders").add(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        String id = model.getDocumentId();
                        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                                .collection("AddToCart").document(id)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(PlaceOrderActivity.this,"Đơn hàng của bạn đã đặt thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PlaceOrderActivity.this,"Khong thành công", Toast.LENGTH_SHORT).show();

                                    }
                                })
                        ;

                    }
                });
            }
        }

    }
}