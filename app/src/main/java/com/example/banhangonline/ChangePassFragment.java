package com.example.banhangonline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banhangonline.activities.PlaceOrderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassFragment extends DialogFragment {
    EditText rePassword, reEmail , newPass;
    Button btnChangePass;
    FirebaseAuth auth;
    FirebaseUser user;
    public ChangePassFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_change_pass, container, false);
        rePassword = root.findViewById(R.id.re_pass);
        reEmail = root.findViewById(R.id.re_email);
        newPass = root.findViewById(R.id.newPass);
        btnChangePass = root.findViewById(R.id.btn_continue);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePass();
            }
        });
        return root;
    }

    private void onClickChangePass() {
        String RePassword = rePassword.getText().toString().trim();
        String ReEmail = reEmail.getText().toString().trim();
        String newPassword = newPass.getText().toString().trim();
        if(TextUtils.isEmpty(ReEmail)){
            Toast.makeText(getActivity(),"Email còn trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(RePassword)){
            Toast.makeText(getActivity(),"Vui lòng nhập mật khẩu củ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            Toast.makeText(getActivity(),"Vui lòng nhập mật khẩu mới",Toast.LENGTH_SHORT).show();
            return;
        }
        reauthenticateUser(ReEmail,RePassword,newPassword);
    }
    private void reauthenticateUser(String email,String password,String newPassword){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
        firebaseUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(),"Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"Thay đổi không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        } else {
                            Toast.makeText(getActivity(),"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}