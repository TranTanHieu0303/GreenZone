package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.Object.GenAccessToken;
import com.example.greenzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class LoginActivity extends AppCompatActivity {
    Button btn_DangNhap,btn_DangKy;
    EditText edt_sdt,edt_matkhau;
    FirebaseAuth firebaseAuth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        edt_sdt = findViewById(R.id.edt_Sdt);
        edt_matkhau = findViewById(R.id.edt_MatKhau);
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        btn_DangKy = findViewById(R.id.btn_DangKy);
        if(getIntent()!=null)
        {
            edt_sdt.setText(getIntent().getStringExtra("dangky_sdt"));
            edt_matkhau.setText(getIntent().getStringExtra("dangky_matkhau"));
        }
        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,XacThucActivity.class);
                startActivity(intent);
            }
        });
        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kiemtrataikhoan(edt_sdt.getText().toString(),edt_matkhau.getText().toString());

            }
        });
    }
    private void Kiemtrataikhoan(String sdt,String pass) {
        DatabaseReference datataikhoan = FirebaseDatabase.getInstance().getReference().child("Users");
        datataikhoan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()) {
                    User user = new User();
                    user.setSDT((String) item.child("sdt").getValue());
                    user.setPassword((String) item.child("password").getValue());
                    user.setToken("eyJjdHkiOiJzdHJpbmdlZS1hcGk7dj0xIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJpc3MiOiIwOTgyMzI4MTY0IiwicmVzdF9hcGkiOnRydWUsImV4cCI6MTYzMDE2MzgwNywianRpIjoiU0t5OWZTejRSRFp4NlVEOXFWVldDb0h5UVdKemhubTdJLTE2MzAxMjA2MDc3MzYifQ.54vLz4H_S2GS3jPlmZdOLLC8TOCyse9K0HmF3QAW7Vs");
                    if(sdt.equals(user.getSDT())&&checkPass(pass,user.getPassword()))
                    {
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*datataikhoan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = new User();
                user.setSDT((String) snapshot.child("sdt").getValue());
                user.setPassword((String) snapshot.child("password").getValue());
                user.setToken((String) snapshot.child("token").getValue());
                if(user.getSDT()==null)
                {
                    Toast.makeText(LoginActivity.this,"Tài khoản không tồn tại",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(user.getSDT().equals(sdt)&&user.getPassword().equals(pass))
                {
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Thông tin không chính xác",Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/
    }
    public boolean checkPass(String input,String checkpass)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(input.getBytes());
        byte[] digest = md.digest();
        String passhash =  DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        if(passhash.equals(checkpass))
            return true;
        return false;
    }
}