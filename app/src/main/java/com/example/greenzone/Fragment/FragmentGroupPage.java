package com.example.greenzone.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Adapter.BangTinAdapter;
import com.example.greenzone.Adapter.GroupAdapter;
import com.example.greenzone.Adapter.GroupPageAdapter;
import com.example.greenzone.Class.BaiViet;
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
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentGroupPage extends Fragment {
    RecyclerView rcv_BaiViets;
    FirebaseAuth firebaseAuth;
    User user;
    ArrayList<BaiViet> baiViets = new ArrayList<>();
    GroupPageAdapter adapter;
    Toolbar toolbar;


    public FragmentGroupPage(User user) {
        this.user = user;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_page,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        APIservice.apiService.getBaiViet(user.getIdUser()).enqueue(new Callback<ArrayList<BaiViet>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
                baiViets = response.body();
                Collections.sort(baiViets,(o1, o2) -> o2.getIdBaiViet().compareTo(o1.getIdBaiViet()));
                adapter = new GroupPageAdapter(getContext());
                adapter.setData(baiViets,user);
                rcv_BaiViets.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rcv_BaiViets = view.findViewById(R.id.frag_home_rcv_groups);
        rcv_BaiViets.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_BaiViets.setItemAnimator(new DefaultItemAnimator());
        rcv_BaiViets.setAdapter(adapter);
        toolbar = view.findViewById(R.id.group_page_toobar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

}
