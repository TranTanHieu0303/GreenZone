package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.greenzone.Adapter.GroupAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.Fragment.FragmentAccount;
import com.example.greenzone.Fragment.FragmentBookphone;
import com.example.greenzone.Fragment.FragmentChat;
import com.example.greenzone.Fragment.FragmentHome;
import com.example.greenzone.Fragment.FragmentNotification;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    User user;
    BottomNavigationView bnv_home;
    FrameLayout layoutfrag;
    Fragment fragmentchon;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (User) getIntent().getSerializableExtra("user");
        bnv_home = findViewById(R.id.home_bnv_menu);
        bnv_home.setItemIconTintList(null);
        layoutfrag  = findViewById(R.id.home_layout_chinh);

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
                        break;
                    }
                    case R.id.mnhome_chat: {
                        fragmentchon = new FragmentChat(user);
                        break;
                    }
                    case R.id.mnhome_danhba: {
                        fragmentchon = new FragmentBookphone();
                        break;
                    }
                    case R.id.mnhome_thongbao: {
                        fragmentchon = new FragmentNotification();
                        break;
                    }
                    case R.id.mnhome_taiKhoan: {
                        fragmentchon = new FragmentAccount();
                        break;
                    }
                }
                Loadlayout(fragmentchon);
                return true;
            }
        });

    }

    private void Loadlayout(Fragment fragmentchon) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_layout_chinh, fragmentchon);
        transaction.commit();
    }

}