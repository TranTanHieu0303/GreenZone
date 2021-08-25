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

import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity {
    EditText edt_sdt,edt_ho,edt_ten,edt_matkhau,edt_xacnhanmk;
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
        user.setSDT(edt_sdt.getText().toString().trim());
        user.setHo(edt_ho.getText().toString().trim());
        user.setTen(edt_ten.getText().toString().trim());
        user.setPassword(edt_matkhau.getText().toString().trim());
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getSDT()).setValue(user);
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