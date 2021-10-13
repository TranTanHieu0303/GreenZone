package com.example.greenzone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.greenzone.R;
import com.stringee.call.StringeeCall;
import com.stringee.common.StringeeConstant;

import org.json.JSONObject;

public class NhanCuocGoiActivity extends AppCompatActivity {
    private StringeeCall stringeeCall;
    private StringeeCall.SignalingState state;

    private FrameLayout vd_Local;
    private FrameLayout vd_Remote;
    Button btn_chapnhan,btn_tuchoi;
    String callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_cuoc_goi);
        vd_Local = findViewById(R.id.nhancuocgoi_frame_userlocal);
        vd_Remote = findViewById(R.id.nhancuocgoi_frame_usernhan);
        btn_chapnhan = findViewById(R.id.nhancuocgoi_btn_chapnhan);
        btn_tuchoi = findViewById(R.id.nhancuocgoai_btn_tuchoi);
        btn_chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stringeeCall != null) {
                    stringeeCall.answer();

                }
            }
        });
        btn_tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stringeeCall != null) {
                    stringeeCall.hangup();
                    finish();
                }
            }
        });
        initAnswert();
    }

    private void initAnswert() {
        callId = getIntent().getStringExtra("call_id");
        stringeeCall = HomeActivity.callMap.get(callId);
        // true if call is videoCall, false if call is not videoCall
        stringeeCall.enableVideo(true);

        //set quality of video : QUALITY_NORMAL, QUALITY_HD, QUALITY_FULLHD
        stringeeCall.setQuality(StringeeConstant.QUALITY_FULLHD);
        stringeeCall.setCallListener(new StringeeCall.StringeeCallListener() {
            @Override
            public void onSignalingStateChange(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s, int i, String s1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        state = signalingState;
                        if (state == StringeeCall.SignalingState.ENDED) {
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onError(StringeeCall stringeeCall, int i, String s) {
                Toast.makeText(NhanCuocGoiActivity.this,"Lỗi",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onHandledOnAnotherDevice(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s) {
                Toast.makeText(NhanCuocGoiActivity.this,"onHandleOnAnother",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMediaStateChange(StringeeCall stringeeCall, StringeeCall.MediaState mediaState) {
                Toast.makeText(NhanCuocGoiActivity.this,"onMediStateChange",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLocalStream(StringeeCall stringeeCall) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vd_Local.addView(stringeeCall.getLocalView());
                        stringeeCall.renderLocalView(true);
                    }
                });
            }

            @Override
            public void onRemoteStream(StringeeCall stringeeCall) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vd_Remote.addView(stringeeCall.getRemoteView());
                        stringeeCall.renderRemoteView(true);
                    }
                });

            }

            @Override
            public void onCallInfo(StringeeCall stringeeCall, JSONObject jsonObject) {
                Toast.makeText(NhanCuocGoiActivity.this,"onCallInfo",Toast.LENGTH_LONG).show();
            }
        });
    }
}