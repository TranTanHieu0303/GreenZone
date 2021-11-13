package com.example.greenzone.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAccount  extends Fragment {
    ImageView img_anhbia,img_avatar,img_tb_avatar;
    TextView tv_tentk,tv_tb_tentk;
    RecyclerView rcv_list;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ArrayList<BaiViet> baiViets = new ArrayList<>();
    BangTinAdapter adapter;
    User user;
    public FragmentAccount(User user) {
        this.user = user;
    }
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        anhxaview(view);
        APIservice.apiService.getBaiViet(user.getIdUser()).enqueue(new Callback<ArrayList<BaiViet>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
                baiViets = response.body();
                Collections.sort(baiViets,(o1, o2) -> o2.getIdBaiViet().compareTo(o1.getIdBaiViet()));
                adapter = new BangTinAdapter(getContext());
                adapter.setData(baiViets,user);
                rcv_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void anhxaview(View view) {
        img_anhbia = view.findViewById(R.id.accout_img_anhbia);
        img_avatar = view.findViewById(R.id.accout_img_avatar);

//        tv_tentk = view.findViewById(R.id.accout_tv_tentk);
        rcv_list = view.findViewById(R.id.accout_rcv_list);
        rcv_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_list.setItemAnimator(new DefaultItemAnimator());
        rcv_list.setAdapter(adapter);
        appBarLayout = view.findViewById(R.id.accout_appbar);
        collapsingToolbarLayout = view.findViewById(R.id.accout_collapsing);
        toolbar = view.findViewById(R.id.accout_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Picasso.with(getContext())
                .load(Constant.ConnectionString_API+user.getHinhAnh())
                .into(img_avatar);
//        tv_tentk.setText(user.getFullName());
        collapsingToolbarLayout.setTitle(user.getFullName());
        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout1, int verticalOffset) {
                if(Math.abs(verticalOffset)<550)
                {
                    img_tb_avatar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    img_tb_avatar.setVisibility(View.VISIBLE);
                }
            }
        });
        img_tb_avatar = view.findViewById(R.id.accout_toolbar_img_avatar);
//        tv_tb_tentk = view.findViewById(R.id.accout_toolbar_tv_tentk);
        Picasso.with(getContext())
                .load(Constant.ConnectionString_API+user.getHinhAnh())
                .into(img_tb_avatar);
//        tv_tb_tentk.setText(user.getFullName());

    }
}
