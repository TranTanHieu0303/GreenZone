package com.example.greenzone.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.greenzone.Class.User;
import com.example.greenzone.Fragment.FragmentAccount;
import com.example.greenzone.Fragment.FragmentBookphone;
import com.example.greenzone.Fragment.FragmentGroupPage;
import com.example.greenzone.Fragment.FragmentHome;
import com.example.greenzone.Fragment.FragmentNotification;
import com.example.greenzone.Object.GenAccessToken;
import com.example.greenzone.R;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.call.StringeeCall2;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StringeeConnectionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    User user;
//    BottomNavigationView bnv_home;
    FrameLayout layoutfrag;
    Fragment fragmentchon;
    GenAccessToken genAccessToken = new GenAccessToken();
    Toolbar toolbar;
    String token ;
    ActionBar actionBar;
    static StringeeClient stringeeClient;
    public static Map<String, StringeeCall> callMap = new HashMap<>();
    MeowBottomNavigation bnv_home;
    private final int ID_HOME = 1;
    private final int ID_GROUP = 2;
    private final int ID_ACCOUT = 3;
    private final int ID_NOTIFICATION = 4;
    private final int ID_MENU = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (User) getIntent().getSerializableExtra("user");
        token = user.getToken();
        bnv_home = findViewById(R.id.home_bnv_menu);
        bnv_home.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_home));
        bnv_home.add(new MeowBottomNavigation.Model(ID_GROUP,R.drawable.ic_group));
        bnv_home.add(new MeowBottomNavigation.Model(ID_ACCOUT,R.drawable.ic_account));
        bnv_home.add(new MeowBottomNavigation.Model(ID_NOTIFICATION,R.drawable.ic_notification));
        bnv_home.add(new MeowBottomNavigation.Model(ID_MENU,R.drawable.ic_list_24));

        layoutfrag  = findViewById(R.id.home_layout_chinh);
//        toolbar = findViewById(R.id.home_toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
        ketnoistring();
        fragmentchon = new FragmentHome(user);
        Loadlayout(fragmentchon);
//        bnv_home.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                int id = item.getItemId();
//                switch (id)
//                {
//                    case R.id.mnhome_home: {
//                        fragmentchon = new FragmentHome(user);
//                        toolbar.setTitle("Trang Chủ");
//                        break;
//                    }
//                    case R.id.mnhome_Group: {
//                        fragmentchon = new FragmentChat(user);
//                        toolbar.setTitle("Nhóm");
//                        break;
//                    }
//                    case R.id.mnhome_pageaccout: {
//                        fragmentchon = new FragmentBookphone();
//                        toolbar.setTitle("Trang Chủ");
//                        break;
//                    }
//                    case R.id.mnhome_thongbao: {
//                        fragmentchon = new FragmentNotification();
//                        toolbar.setTitle("Thông báo");
//                        break;
//                    }
//                    case R.id.mnhome_menu: {
//                        fragmentchon = new FragmentAccount();
//                        toolbar.setTitle("Menu");
//                        break;
//                    }
//                }
//                Loadlayout(fragmentchon);
//                return true;
//            }
//        });
        bnv_home.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bnv_home.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId())
                {
                    case ID_HOME:
                    {
                        fragmentchon = new FragmentHome(user);
//                        toolbar.setTitle("Trang Chủ");
                        break;
                    }
                    case ID_GROUP:
                    {
                        fragmentchon = new FragmentGroupPage(user);
//                        toolbar.setTitle("Nhóm");
                        break;
                    }
                    case ID_ACCOUT:
                    {
                        fragmentchon = new FragmentAccount(user);
//                        toolbar.setTitle("Trang Chủ");
                        break;
                    }
                    case ID_NOTIFICATION:
                    {
                        fragmentchon = new FragmentNotification();
//                        toolbar.setTitle("Thông báo");
                        break;
                    }
                    case ID_MENU:
                    {
                        fragmentchon = new FragmentAccount(user);
//                        toolbar.setTitle("Menu");
                        break;
                    }
                }
                Loadlayout(fragmentchon);
            }
        });
        bnv_home.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
        bnv_home.show(ID_HOME,true);

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

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mn_home_chat: {
//                Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
//                intent.putExtra("user",user);
//                startActivity(intent);
//                this.finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}