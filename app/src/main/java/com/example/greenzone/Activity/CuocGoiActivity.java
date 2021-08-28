package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.greenzone.R;

public class CuocGoiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoc_goi);
        xacnhanquyen();
    }

    private void xacnhanquyen() {
        ActivityCompat.requestPermissions(CuocGoiActivity.this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        },1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==1)
        {
            if(grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }else
            {
                Toast.makeText(CuocGoiActivity.this,"Quyền bị từ chối",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}