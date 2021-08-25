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

import com.example.greenzone.Adapter.BangTinAdapter;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    RecyclerView rcv_frag_home;
    ArrayList<BaiViet> baiViets = new ArrayList<>();
    BangTinAdapter adapter;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        rcv_frag_home = view.findViewById(R.id.frag_home_rcv_baidang);
        Loadbaiviet();
        rcv_frag_home.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_frag_home.setItemAnimator(new DefaultItemAnimator());
        rcv_frag_home.setAdapter(adapter);
        return view;
    }

    private void Loadbaiviet() {
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        baiViets.add(new BaiViet("1","2","helo","a","","","","",""));
        adapter = new BangTinAdapter(baiViets,getContext());
    }

}
