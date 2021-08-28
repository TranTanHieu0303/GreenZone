package com.example.greenzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BangTinAdapter extends RecyclerView.Adapter<BangTinAdapter.ViewHoder1> {
    ArrayList<BaiViet> dulieu;
    Context context;

    public BangTinAdapter(ArrayList<BaiViet> dulieu, Context context) {
        this.dulieu = dulieu;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHoder1 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_baidang,null);
        return new ViewHoder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHoder1 holder, int position) {
        BaiViet baiViet = dulieu.get(position);

    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class ViewHoder1 extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        RecyclerView rcv_hinhanh;
        TextView tv_tentk,tv_thoigian,tv_noidung,tv_luotlike,tv_luotcmt_share,tv_like,tv_cmt,tv_share;

        public ViewHoder1(@NonNull @NotNull View itemView) {
            super(itemView);
            img_avatar =itemView.findViewById(R.id.it_baidang_img_avatar);
            //rcv_hinhanh =itemView.findViewById(R.id.it_baidang_rcv_hinhanh);
            tv_tentk =itemView.findViewById(R.id.it_baidang_tv_tentk);
            tv_thoigian =itemView.findViewById(R.id.it_baidang_tv_thoigian);
            ///tv_noidung =itemView.findViewById(R.id.it_baidang_tv_noidung);
            //tv_luotlike =itemView.findViewById(R.id.it_baidang_tv_luotlike);
            //tv_luotcmt_share =itemView.findViewById(R.id.it_baidang_tv_luotcmt_share);
            //tv_like =itemView.findViewById(R.id.it_baidang_tv_like);
            //tv_cmt =itemView.findViewById(R.id.it_baidang_tv_cmt);
           // tv_share =itemView.findViewById(R.id.it_baidang_tv_share);

        }
    }
}
