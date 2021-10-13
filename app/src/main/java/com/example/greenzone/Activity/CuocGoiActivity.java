package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.greenzone.R;
import com.stringee.call.StringeeCall;

import org.json.JSONObject;

import java.util.Locale;

public class CuocGoiActivity extends AppCompatActivity {
    private StringeeCall stringeeCall;
    private StringeeCall.SignalingState state;
    FrameLayout vd_loacol,vd_remoter;
    Button btn_kethtuc;
    String form,to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoc_goi);
        vd_loacol = findViewById(R.id.CuocGoi_frame_userlocal);
        vd_remoter = findViewById(R.id.CuocGoi_frame_usernhan);
        btn_kethtuc = findViewById(R.id.CuocGoi_btn_ketthuc);
        form = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        btn_kethtuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stringeeCall != null) {
                    stringeeCall.hangup();
                    finish();
                }
            }
        });
        xacnhanquyen();
        callMake();
    }

    private void callMake() {
        stringeeCall = new StringeeCall(GroupActivity.stringeeClient,form,to);
        stringeeCall.setVideoCall(true);
        stringeeCall.setCallListener(new StringeeCall.StringeeCallListener() {
            @Override
            public void onSignalingStateChange(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s, int i, String s1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        state = signalingState;
                        if(state ==  StringeeCall.SignalingState.ENDED)
                            finish();
                    }
                });
            }

            @Override
            public void onError(StringeeCall stringeeCall, int i, String s) {

            }

            @Override
            public void onHandledOnAnotherDevice(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s) {

            }

            @Override
            public void onMediaStateChange(StringeeCall stringeeCall, StringeeCall.MediaState mediaState) {

            }

            @Override
            public void onLocalStream(StringeeCall stringeeCall) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vd_loacol.addView(stringeeCall.getLocalView());
                        stringeeCall.renderLocalView(true);
                    }
                });
            }
            @Override
            public void onRemoteStream(StringeeCall stringeeCall) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vd_remoter.addView(stringeeCall.getRemoteView());
                        stringeeCall.renderRemoteView(true);
                    }
                });
            }

            @Override
            public void onCallInfo(StringeeCall stringeeCall, JSONObject jsonObject) {

            }
        });
        stringeeCall.makeCall();
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