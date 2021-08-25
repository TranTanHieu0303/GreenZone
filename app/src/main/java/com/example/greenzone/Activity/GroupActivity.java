package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.greenzone.Adapter.ChatAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.Messages;
import com.example.greenzone.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    EditText edt_textMes;
    Button btn_goi;
    String id_user;
    Group id_group ;
    ArrayList<Messages> messages =new ArrayList<>();
    ChatAdapter adapter;
    RecyclerView rcv_chat;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        id_group = (Group) getIntent().getSerializableExtra("id_group");
        id_user = getIntent().getStringExtra("id_user");
        edt_textMes = findViewById(R.id.group_edt_textmess);
        btn_goi = findViewById(R.id.group_btn_goi);
        rcv_chat = findViewById(R.id.group_rcv_noidungchat);
        toolbar = findViewById(R.id.toolbar_group);
        toolbar.setTitle(id_group.getTenGroup());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Loadchat();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupActivity.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        rcv_chat.setLayoutManager(linearLayoutManager);
        rcv_chat.setItemAnimator(new DefaultItemAnimator());
        rcv_chat.setAdapter(adapter);
        btn_goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messages messages = new Messages();
                messages.setIdMes(FirebaseDatabase.getInstance().getReference().child("Messages").child(id_group.getIdGroup()).push().getKey());
                messages.setText(edt_textMes.getText().toString());
                messages.setTime(LocalDateTime.now().toString());
                messages.setIdGroup(id_group.getIdGroup());
                messages.setUsercreate(id_user);
                FirebaseDatabase.getInstance().getReference().child("Messages").child(id_group.getIdGroup()).child(messages.getIdMes()).setValue(messages);
                FirebaseDatabase.getInstance().getReference().child("Groups").child(id_group.getIdGroup()).child("idMesCuoi").setValue(messages.getText());
                edt_textMes.setText(null);
                edt_textMes.findFocus();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
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

    private void Loadchat() {
        DatabaseReference datachat = FirebaseDatabase.getInstance().getReference().child("Messages").child(id_group.getIdGroup());
        datachat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Messages mes = new Messages();
                mes.setIdMes((String) snapshot.child("idMes").getValue());
                mes.setText((String) snapshot.child("text").getValue());
                mes.setTime((String) snapshot.child("time").getValue());
                mes.setUsercreate((String) snapshot.child("usercreate").getValue());
                if(mes.getUsercreate().equals(id_user))
                    mes.setIsuer(true);
                else
                    mes.setIsuer(false);
                messages.add(mes);
                adapter = new ChatAdapter(messages,GroupActivity.this);
                rcv_chat.setAdapter(adapter);
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