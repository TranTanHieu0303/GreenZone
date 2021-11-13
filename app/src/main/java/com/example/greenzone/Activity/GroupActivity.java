package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenzone.Adapter.ChatAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.Messages;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.call.StringeeCall2;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StringeeConnectionListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    EditText edt_textMes;
    Button btn_goi;
    User id_user;
    Group id_group ;
    ArrayList<Messages> messages =new ArrayList<>();
    ChatAdapter adapter;
    RecyclerView rcv_chat;
    Toolbar toolbar;
    public static StringeeClient stringeeClient;
    String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        id_group = (Group) getIntent().getSerializableExtra("id_group");
        id_user =(User) getIntent().getSerializableExtra("id_user");
        edt_textMes = findViewById(R.id.group_edt_textmess);
        btn_goi = findViewById(R.id.group_btn_goi);
        rcv_chat = findViewById(R.id.group_rcv_noidungchat);
        toolbar = findViewById(R.id.toolbar_group);
        toolbar.setTitle(id_group.getTenGroup());
        setSupportActionBar(toolbar);
        ketnoistring();
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
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDateTime = now.format(format);
                messages.setTime(formatDateTime);
                messages.setIdGroup(id_group.getIdGroup());
                messages.setUsercreate(id_user.getSdt());
                FirebaseDatabase.getInstance().getReference().child("Messages").child(id_group.getIdGroup()).child(messages.getIdMes()).setValue(messages);
                FirebaseDatabase.getInstance().getReference().child("Groups").child(id_group.getIdGroup()).child("idMesCuoi").setValue(messages.getText());
                edt_textMes.setText(null);
                edt_textMes.findFocus();
            }
        });
    }

    private void ketnoistring() {
        token = id_user.getToken();
        stringeeClient = new StringeeClient(GroupActivity.this);
        stringeeClient.setConnectionListener(new StringeeConnectionListener() {
            @Override
            public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GroupActivity.this, "Kết nối thành công",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GroupActivity.this,"không kết nối được",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onIncomingCall(StringeeCall stringeeCall) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_detai,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
            case R.id.mnchat_call_video:
            {
                Intent intent = new Intent(GroupActivity.this,CuocGoiActivity.class);
                intent.putExtra("from", stringeeClient.getUserId());
                intent.putExtra("to", "hieu");
                startActivity(intent);
            }
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
                if(mes.getUsercreate().equals(id_user.getSdt()))
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