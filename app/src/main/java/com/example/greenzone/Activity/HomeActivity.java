package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.greenzone.Adapter.GroupAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.Fragment.FragmentAccount;
import com.example.greenzone.Fragment.FragmentBookphone;
import com.example.greenzone.Fragment.FragmentChat;
import com.example.greenzone.Fragment.FragmentHome;
import com.example.greenzone.Fragment.FragmentNotification;
import com.example.greenzone.Object.GenAccessToken;
import com.example.greenzone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.call.StringeeCall2;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StringeeConnectionListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    User user;
    BottomNavigationView bnv_home;
    FrameLayout layoutfrag;
    Fragment fragmentchon;
    GenAccessToken genAccessToken = new GenAccessToken();
    Toolbar toolbar;
    String token ;
    ActionBar actionBar;
    static StringeeClient stringeeClient;
    public static Map<String, StringeeCall> callMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (User) getIntent().getSerializableExtra("user");
        token = user.getToken();
        bnv_home = findViewById(R.id.home_bnv_menu);
        bnv_home.setItemIconTintList(null);
        layoutfrag  = findViewById(R.id.home_layout_chinh);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        ketnoistring();
        fragmentchon = new FragmentHome();
        Loadlayout(fragmentchon);
        bnv_home.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.mnhome_home: {
                        fragmentchon = new FragmentHome();
                        toolbar.setTitle("Trang Chủ");
                        break;
                    }
                    case R.id.mnhome_Group: {
                        fragmentchon = new FragmentChat(user);
                        toolbar.setTitle("Nhóm");
                        break;
                    }
                    case R.id.mnhome_pageaccout: {
                        fragmentchon = new FragmentBookphone();
                        toolbar.setTitle("Trang Chủ");
                        break;
                    }
                    case R.id.mnhome_thongbao: {
                        fragmentchon = new FragmentNotification();
                        toolbar.setTitle("Thông báo");
                        break;
                    }
                    case R.id.mnhome_menu: {
                        fragmentchon = new FragmentAccount();
                        toolbar.setTitle("Menu");
                        break;
                    }
                }
                Loadlayout(fragmentchon);
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void ketnoistring() {
        stringeeClient = new StringeeClient(HomeActivity.this);
        stringeeClient.setConnectionListener(new StringeeConnectionListener() {
            @Override
            public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this,stringeeClient.getUserId(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this,"không kết nối được",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onIncomingCall(StringeeCall stringeeCall) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callMap.put(stringeeCall.getCallId(),stringeeCall);
                        Intent intent = new Intent(HomeActivity.this,NhanCuocGoiActivity.class);
                        intent.putExtra("call_id", stringeeCall.getCallId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onIncomingCall2(StringeeCall2 stringeeCall2) {

            }

            @Override
            public void onConnectionError(StringeeClient stringeeClient, StringeeError stringeeError) {

            }

            @Override
            public void onRequestNewToken(StringeeClient stringeeClient) {

            }

            @Override
            public void onCustomMessage(String s, JSONObject jsonObject) {

            }

            @Override
            public void onTopicMessage(String s, JSONObject jsonObject) {

            }
        });
        stringeeClient.connect(token);
    }

    private void Loadlayout(Fragment fragmentchon) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_layout_chinh, fragmentchon);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_home_chat: {
                Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}