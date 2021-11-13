package com.example.greenzone.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Activity.ChatActivity;
import com.example.greenzone.Activity.DangTinActivity;
import com.example.greenzone.Adapter.BangTinAdapter;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {
    RecyclerView rcv_frag_home;
    ArrayList<BaiViet> baiViets = new ArrayList<>();
    BangTinAdapter adapter;
    User user;
    CardView cv_search,cv_chat;
    TextView tv_coutchat;
    Toolbar toolbar;

    public FragmentHome(User user) {
        this.user = user;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        APIservice.apiService.getBaiViet(user.getIdUser()).enqueue(new Callback<ArrayList<BaiViet>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
                baiViets = response.body();
                Collections.sort(baiViets,(o1, o2) -> o2.getIdBaiViet().compareTo(o1.getIdBaiViet()));
                adapter = new BangTinAdapter(getContext());
                adapter.setData(baiViets,user);
                rcv_frag_home.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        edt_noidung = view.findViewById(R.id.fmhome_edt_noidung);
//        img_avatar = view.findViewById(R.id.fmhome_img_avatar);
//        edt_noidung.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Intent intent = new Intent(getContext(), DangTinActivity.class);
//                intent.putExtra("user",user);
//                getContext().startActivity(intent);
//            }
//        });
//        edt_noidung.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), DangTinActivity.class);
//                intent.putExtra("user",user);
//                getContext().startActivity(intent);
//            }
//        });
//        Picasso.with(getContext())
//                .load(Constant.ConnectionString_API+user.getHinhAnh())
//                .into(img_avatar);
        rcv_frag_home = view.findViewById(R.id.frag_home_rcv_baidang);
        rcv_frag_home.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_frag_home.setItemAnimator(new DefaultItemAnimator());
        rcv_frag_home.setAdapter(adapter);
        toolbar = view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        cv_search = view.findViewById(R.id.fm_home_cv_search);
        cv_chat = view.findViewById(R.id.fm_home_cv_chat);
        tv_coutchat = view.findViewById(R.id.fm_home_tv_coutchat);
        cv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        return view;
    }

//    private void Loadbaiviet() {
//        APIservice.apiService.getBaiViet("1634553675470").enqueue(new Callback<ArrayList<BaiViet>>() {
//            @Override
//            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
//                baiViets = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        adapter = new BangTinAdapter(baiViets,getContext());
////        for (int i = 0;i<10;i++ ) {
////            APIservice.apiService.getBaiVietid("1634389005180").enqueue(new Callback<BaiViet>() {
////                @Override
////                public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
////                    BaiViet bv = response.body();
////                }
////
////                @Override
////                public void onFailure(Call<BaiViet> call, Throwable t) {
////                    BaiViet bv = new BaiViet();
////                }
////            });
////            baiViets.add(new BaiViet("1", "2", "helo", "a", "", "", 0, 0, 0, LocalDateTime.now().toString()));
////        }
//
//    }

}
