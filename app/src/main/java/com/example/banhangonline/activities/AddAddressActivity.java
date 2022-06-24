package com.example.banhangonline.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banhangonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
EditText name , address , phone;
Toolbar toolbar;
Button addAddressBtn;
FirebaseFirestore firestore;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        phone = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userAddress = address.getText().toString();
                String userPhone = phone.getText().toString();
                String final_address = "";

                if(!userName.isEmpty()){
                    final_address+=userName;
                }
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if(!userPhone.isEmpty()){
                    final_address+=userPhone;
                }
                if (!userName.isEmpty() && !userAddress.isEmpty() &&!userPhone.isEmpty() ){
                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this,"Thêm địa chỉ thành công",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),AddressActivity.class));
                            }
                        }
                    });

                }else{
                    Toast.makeText(AddAddressActivity.this,"Ô nhập còn trống",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}