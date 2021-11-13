package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    EditText edt_sdt,edt_ho,edt_ten,edt_email,edt_ngaysinh,edt_matkhau,edt_xacnhanmk;
    Button btn_thoat,btn_dangky;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_dang_ky);
        edt_sdt = findViewById(R.id.dangky_edt_sdt);
        edt_ho = findViewById(R.id.dangky_edt_ho);
        edt_ten = findViewById(R.id.dangky_edt_ten);
        edt_email = findViewById(R.id.dangky_edt_email);
        edt_ngaysinh = findViewById(R.id.dangky_edt_ngaysinh);
        edt_matkhau = findViewById(R.id.dangky_edt_matkhau);
        edt_xacnhanmk = findViewById(R.id.dangky_edt_nhaplaimk);
        btn_thoat = findViewById(R.id.dangky_btn_thoat);
        btn_dangky = findViewById(R.id.dangky_btn_dangky);
        toolbar = findViewById(R.id.dangky_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edt_sdt.setText(getIntent().getStringExtra("xacthuc_sdt"));
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLoi();
                DangKy();
                Intent intent = new Intent(DangKyActivity.this,LoginActivity.class);
                intent.putExtra("dangky_sdt",edt_sdt.getText().toString());
                intent.putExtra("dangky_matkhau",edt_matkhau.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    private void CheckLoi() {
    }
    private void DangKy() {
        User user = new User();
        String sdt = edt_sdt.getText().toString().trim();
        user.setSdt(sdt);
        user.setFullName(edt_ho.getText().toString().trim()+" "+edt_ten.getText().toString().trim());
        user.setSoBanBe(0);
        user.setHinhAnh("user.png");
        user.setEmail(edt_email.getText().toString().trim());
        user.setNgaySinh(edt_ngaysinh.getText().toString().trim());
        user.setOnline(true);
        String password = edt_matkhau.getText().toString().trim();
        user.setPassword(password);
        APIservice.apiService.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(DangKyActivity.this,"post thanh cong",Toast.LENGTH_SHORT).show();
                User user1 = response.body();
                FirebaseDatabase.getInstance().getReference().child("Users").child(user1.getIdUser()).setValue(user1);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(DangKyActivity.this,"post không thành công",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String mahoapass(String password)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}