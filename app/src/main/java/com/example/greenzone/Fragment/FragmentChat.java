package com.example.greenzone.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Activity.HomeActivity;
import com.example.greenzone.Adapter.GroupAdapter;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
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

public class FragmentChat extends Fragment {
    RecyclerView rcv_Groups;
    FirebaseAuth firebaseAuth;
    User user;
    ArrayList<Group> groups = new ArrayList<>();
    GroupAdapter adapter;


    public FragmentChat(User user) {
        this.user = user;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        rcv_Groups = view.findViewById(R.id.frag_home_rcv_groups);
        LoadGroups(user);
        rcv_Groups.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_Groups.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void LoadGroups(User user) {
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
                        adapter = new GroupAdapter(groups,getContext(),user);
                        rcv_Groups.setAdapter(adapter);
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
