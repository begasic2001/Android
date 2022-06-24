package com.example.banhangonline.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button signIn;
    EditText email , password;
    TextView signUp , forgot;
    FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.login_btn);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        signUp = findViewById(R.id.dangky);
        forgot = findViewById(R.id.forgot);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(toLogin);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRS_pass_account = new Intent(LoginActivity.this,ResetPassActivity.class);
                startActivity(toRS_pass_account);
            }
        });
    }



    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email còn trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Mật khẩu còn trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 6 ){
            Toast.makeText(this,"Mật khẩu có ít nhất 6 ký tự",Toast.LENGTH_SHORT).show();
            return;

        }
        // đăng nhập ng dùng
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            checkMailverfication();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void checkMailverfication() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Vui lòng xác thực email trước", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
    }
}