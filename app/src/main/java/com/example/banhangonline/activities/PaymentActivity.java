package com.example.banhangonline.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.banhangonline.R;
import com.example.banhangonline.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView subTotal,discount,shipping,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        toolbar.findViewById(R.id.payment_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        int amount = 5;
//        amount = getIntent().getIntExtra("itemList",5);
//
//        subTotal = findViewById(R.id.sub_total);
//        subTotal.setText(amount+"VND");

    }
}