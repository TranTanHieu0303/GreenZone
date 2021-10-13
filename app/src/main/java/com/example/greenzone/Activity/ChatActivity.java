package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.greenzone.Adapter.GroupAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    BottomNavigationView bnv_Chat;
    RecyclerView rcv_listchat;
    User user;
    GroupAdapter adapter;
    ArrayList<Group> groups = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = (User) getIntent().getSerializableExtra("user");
        bnv_Chat = findViewById(R.id.chat_bottomnv);
        bnv_Chat.setItemIconTintList(null);
        rcv_listchat = findViewById(R.id.chat_rcv_listchat);
        LoadChat();
        rcv_listchat.setLayoutManager(new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false));
        rcv_listchat.setItemAnimator(new DefaultItemAnimator());
    }

    private void LoadChat() {
        DatabaseReference datagroup = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getSDT()).child("Groups");
        datagroup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                String a=(String) snapshot.getValue();
                Query dataidgroup = FirebaseDatabase.getInstance().getReference().child("Groups").child((String) snapshot.getValue());
                dataidgroup.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Group group = new Group();
                        group.setIdGroup((String) snapshot.child("idGroup").getValue());
                        group.setTenGroup((String) snapshot.child("tenGroup").getValue());
                        group.setIdMesCuoi((String) snapshot.child("idMesCuoi").getValue());
                        //group.setSothanhVien(Integer.parseInt( (String) snapshot.child("sothanhVien").getValue()));
                        groups.add(group);
                        adapter = new GroupAdapter(groups,ChatActivity.this,user);
                        rcv_listchat.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}