package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class XacThucActivity extends AppCompatActivity {
    Button btn_goima;
    CountryCodePicker cpp_code;
    EditText edt_sdt,edt_code;
    FirebaseAuth firebaseAuth;
    private String verificationId;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc);
        firebaseAuth =FirebaseAuth.getInstance();
        setContentView(R.layout.activity_xac_thuc);
        btn_goima = findViewById(R.id.xacthuc_btn_goima);
        edt_sdt = findViewById(R.id.xacthuc_edt_sdt);
        cpp_code = findViewById(R.id.xacthuc_ccp_code);
        toolbar = findViewById(R.id.xucthuc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_goima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = cpp_code.getSelectedCountryCodeWithPlus()+edt_sdt.getText().toString().trim();
                sendVerificationCode(sdt);

            }
        });

    }

    private void sendVerificationCode(String sdt) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(sdt)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edt_code.setText(code);
                //verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
            Toast.makeText(XacThucActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            opendialog(Gravity.CENTER);
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(XacThucActivity.this,DangKyActivity.class);
                    intent.putExtra("xacthuc_sdt",edt_sdt.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(XacThucActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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

    void opendialog(int gravity)
    {
        Dialog dialog = new Dialog(XacThucActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_xacthuc);
        Window window = dialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        Button btn_goilaima,btn_xacnhan,btn_dong;
        dialog.setCancelable(false);
        TextView tv_time;
        edt_code = dialog.findViewById(R.id.dl_xacthuc_edt_code);
        btn_goilaima = dialog.findViewById(R.id.dl_xacthuc_btn_goilaima);
        btn_xacnhan = dialog.findViewById(R.id.dl_xacthuc_btn_XacNhan);
        btn_dong = dialog.findViewById(R.id.dl_xacthuc_btn_dong);
        btn_goilaima.setVisibility(View.INVISIBLE);
        btn_goilaima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = cpp_code.getSelectedCountryCodeWithPlus()+edt_sdt.getText().toString().trim();
                sendVerificationCode(sdt);
            }
        });
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(edt_code.getText().toString());
            }
        });
        tv_time = window.findViewById(R.id.dl_xacthuc_tv_time);
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tv_time.setText(millisUntilFinished/1000+"");
            }

            @Override
            public void onFinish() {
                btn_goilaima.setVisibility(View.VISIBLE);
                btn_xacnhan.setVisibility(View.INVISIBLE);
            }
        }.start();
        dialog.show();
    }
}