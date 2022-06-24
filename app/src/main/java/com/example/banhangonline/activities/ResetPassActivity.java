package com.example.banhangonline.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.banhangonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {
    EditText mrsacc__mail;
    Button mrsacc__btn_continue;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        mrsacc__mail = findViewById(R.id.rsacc__mail);
        mrsacc__btn_continue = findViewById(R.id.rsacc__btn_continue);
        firebaseAuth = FirebaseAuth.getInstance();

        mrsacc__btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mrsacc__mail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng điền email", Toast.LENGTH_SHORT).show();
                }
                else{

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Email đã gửi, bạn có thể khôi phục lại mật khẩu", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent toRS_new_pass = new Intent(ResetPassActivity.this,LoginActivity.class);
                                startActivity(toRS_new_pass);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Email sai hoặc tài khoản không có", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

}