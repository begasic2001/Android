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
            Toast.makeText(this,"Email c??n tr???ng",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"M???t kh???u c??n tr???ng",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 6 ){
            Toast.makeText(this,"M???t kh???u c?? ??t nh???t 6 k?? t???",Toast.LENGTH_SHORT).show();
            return;

        }
        // ????ng nh???p ng d??ng
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            checkMailverfication();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"????ng nh???p th???t b???i",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void checkMailverfication() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"????ng nh???p th??nh c??ng",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Vui l??ng x??c th???c email tr?????c", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
    }
}