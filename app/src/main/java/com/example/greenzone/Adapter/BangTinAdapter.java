package com.example.greenzone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenzone.API.APIservice;
import com.example.greenzone.Activity.ChiTietBaiVietActivity;
import com.example.greenzone.Activity.DangTinActivity;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_POSTBAI = 0 ;
    private final int TYPE_LISTBAI = 1 ;
    ArrayList<BaiViet> dulieu;
    Context context;
    User userhost;

    public BangTinAdapter(Context context) {

        this.context = context;

    }
    public void setData(ArrayList<BaiViet> dulieu,User user)
    {
        this.dulieu = dulieu;
        this.userhost=  user;
        notifyDataSetChanged();
    }

//    @NonNull
//    @NotNull
//    @Override
//    public ViewHoder1 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_baidang,null);
//        return new ViewHoder1(view);
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_POSTBAI==viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_dangbai, null);
            return new ViewHodel2(view);
        }else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_baidang,null);
            return new ViewHoder1(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(dulieu==null||TYPE_POSTBAI==holder.getItemViewType())
        {
            ViewHodel2 viewHodel2 =   (ViewHodel2) holder;
            Glide.with(context)
                    .load(Constant.ConnectionString_API+userhost.getHinhAnh())
                    .into(viewHodel2.img_avatar);
            viewHodel2.edt_postbai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DangTinActivity.class);
                    intent.putExtra("user",userhost);
                    context.startActivity(intent);
                }
            });
            viewHodel2.edt_postbai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Intent intent = new Intent(context, DangTinActivity.class);
                    intent.putExtra("user",userhost);
                    context.startActivity(intent);
                }
            });

        }else
        {
            ViewHoder1 viewHoder =   (ViewHoder1) holder;
            BaiViet baiViet = dulieu.get(position-1);
            APIservice.apiService.getUser(baiViet.getIdUser()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                try {
                    Date date = formatter4.parse(baiViet.getNgayDang());
                    LocalDateTime datetime = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    if (datetime.toLocalDate().equals(LocalDate.now())) {
                        int time = (LocalTime.now().getHour() - datetime.toLocalTime().getHour());
                        if (time >= 1)
                            viewHoder.tv_thoigian.setText("Lúc " + datetime.toLocalTime().getHour() + ":" + datetime.toLocalTime().getMinute() + " hôm nay");
                        else
                            viewHoder.tv_thoigian.setText(LocalTime.now().getMinute() - datetime.toLocalTime().getMinute() + " phút trước");
                    } else {
                        viewHoder.tv_thoigian.setText(datetime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString() + " lúc " + datetime.toLocalTime().getHour() + ":" + datetime.toLocalTime().getMinute());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (baiViet.getCheDo().equals("Công khai")) {
                    viewHoder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cong_khai));
                } else {
                    if (baiViet.getCheDo().equals("Chỉ mình tôi")) {
                        viewHoder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_24));
                    } else {
                        viewHoder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_friends));
                    }
                }
                if(baiViet.getNoidung().length()>100)
                {
                    String noidungmoi = baiViet.getNoidung().substring(0,100)+"...";
                    viewHoder.tv_noidung.setText(noidungmoi);
                    viewHoder.tv_xemthem.setVisibility(View.VISIBLE);
                    viewHoder.tv_xemthem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewHoder.tv_noidung.setText(baiViet.getNoidung());
                            viewHoder.tv_xemthem.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else
                {
                    viewHoder.tv_noidung.setText(baiViet.getNoidung());
                    viewHoder.tv_xemthem.setVisibility(View.INVISIBLE);
                }

                viewHoder.tv_luotcmt_share.setText(baiViet.getLuotComment().toString() + " bình luận," + baiViet.getLuotShare().toString() + " lượt chia sẽ");
                viewHoder.tv_luotlike.setText(baiViet.getLuotLike().toString());
                viewHoder.tv_tengroup.setText(user.getFullName());
                Picasso.with(context).load(Constant.ConnectionString_API + user.getHinhAnh())
                        .placeholder(R.drawable.img_loadding)
                        .into(viewHoder.img_avatar);
                if (baiViet.getHinhAnh() != null && !baiViet.getHinhAnh().isEmpty()) {
                    List<String> list = Arrays.asList(baiViet.getHinhAnh().split(",").clone());
                    viewHoder.viewImageSelectAdapter.setData(list);
                } else {
                    viewHoder.viewImageSelectAdapter.setData(null);
                }
                viewHoder.tv_cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChiTietBaiVietActivity.class);
                        intent.putExtra("baiviet", baiViet);
                        intent.putExtra("user", userhost);
                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        }



    }
    private int dpToPx(int a)
    {
        float px = a * context.getResources().getDisplayMetrics().density;
        return (int)px;
    }
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull ViewHoder1 holder, int position) {
//        BaiViet baiViet = dulieu.get(position);
//        APIservice.apiService.getUser(baiViet.getIdUser()).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//                try {
//                    Date date = formatter4.parse(baiViet.getNgayDang());
//                    LocalDateTime datetime = date.toInstant()
//                            .atZone(ZoneId.systemDefault())
//                            .toLocalDateTime();
//                    if(datetime.toLocalDate().equals(LocalDate.now()))
//                    {
//                        int time = (LocalTime.now().getHour()-datetime.toLocalTime().getHour());
//                        if(time>=1)
//                            holder.tv_thoigian.setText("Lúc "+datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute()+" hôm nay");
//                        else
//                            holder.tv_thoigian.setText(LocalTime.now().getMinute()-datetime.toLocalTime().getMinute()+" phút trước");
//                    }else
//                    {
//                        holder.tv_thoigian.setText(datetime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()+" lúc "+ datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute());
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                if(baiViet.getCheDo().equals("Công khai"))
//                {
//                    holder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cong_khai));
//                }
//                else
//                {
//                    if(baiViet.getCheDo().equals("Chỉ mình tôi"))
//                    {
//                        holder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_padlock));
//                    }
//                    else
//                    {
//                        holder.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_friends));
//                    }
//                }
//                holder.tv_noidung.setText(baiViet.getNoidung());
//                holder.tv_luotcmt_share.setText(baiViet.getLuotComment().toString()+" bình luận,"+baiViet.getLuotShare().toString()+" lượt chia sẽ");
//                holder.tv_luotlike.setText(baiViet.getLuotLike().toString());
//                holder.tv_tentk.setText(user.getFullName());
//                Picasso.with(context).load(Constant.ConnectionString_API +user.getHinhAnh())
//                        .into(holder.img_avatar);
//                if(baiViet.getHinhAnh()!=null&&!baiViet.getHinhAnh().isEmpty()) {
//                    List<String> list = Arrays.asList(baiViet.getHinhAnh().split(",").clone());
//                    holder.viewImageSelectAdapter.setData(list);
//                }
//                else
//                {
//                    holder.viewImageSelectAdapter.setData(null);
//                }
//                holder.tv_cmt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(context, ChiTietBaiVietActivity.class);
//                        intent.putExtra("baiviet", baiViet);
//                        intent.putExtra("user",userhost);
//                        context.startActivity(intent);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return dulieu.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_POSTBAI;
        else
            return TYPE_LISTBAI;
    }

    public class ViewHoder1 extends RecyclerView.ViewHolder{
        ImageView img_avatar,img_chedo,img_avatar_phu;
        RecyclerView rcv_hinhanh;
        TextView tv_tentk,tv_thoigian,tv_noidung,tv_luotlike,tv_luotcmt_share,tv_like,tv_cmt,tv_share,tv_xemthem,tv_tengroup;
        MediaBangTinAdapter viewImageSelectAdapter ;
        LinearLayout linearLayout;

        public ViewHoder1(@NonNull @NotNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.it_baidang_layuottong);
            img_avatar =itemView.findViewById(R.id.it_baidang_img_avatar);
            img_chedo = itemView.findViewById(R.id.it_baidang_img_chedo);
            img_avatar_phu = itemView.findViewById(R.id.it_baidang_img_avatar_phu);
            rcv_hinhanh =itemView.findViewById(R.id.it_baidang_rcv_hinhanh);
            tv_tengroup = itemView.findViewById(R.id.it_baidang_tv_tengroup);
            tv_xemthem = itemView.findViewById(R.id.it_baidang_tv_xemthem);
            tv_tentk =itemView.findViewById(R.id.it_baidang_tv_tentk);
            tv_thoigian =itemView.findViewById(R.id.it_baidang_tv_thoigian);
            tv_noidung =itemView.findViewById(R.id.it_baidang_tv_noidung);
            tv_luotlike =itemView.findViewById(R.id.it_baidang_tv_luotlike);
            tv_luotcmt_share =itemView.findViewById(R.id.it_baidang_tv_luotcmt_share);
            tv_like =itemView.findViewById(R.id.it_baidang_tv_like);
            tv_cmt =itemView.findViewById(R.id.it_baidang_tv_cmt);
            tv_share =itemView.findViewById(R.id.it_baidang_tv_share);
            viewImageSelectAdapter = new MediaBangTinAdapter(context);
            GridLayoutManager gridLayoutManager =new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false);
            rcv_hinhanh.setLayoutManager(gridLayoutManager);
            rcv_hinhanh.setFocusable(false);
            rcv_hinhanh.setAdapter(viewImageSelectAdapter);
            img_avatar_phu.setVisibility(View.INVISIBLE);
            tv_tentk.setVisibility(View.GONE);
            tv_xemthem.setVisibility(View.INVISIBLE);
        }
    }
    public class ViewHodel2 extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        EditText edt_postbai;
        LinearLayout post_hinhanh,post_video,post_camxuc;
        public ViewHodel2(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.fmhome_img_avatar);
            edt_postbai = itemView.findViewById(R.id.fmhome_edt_noidung);
            post_hinhanh = itemView.findViewById(R.id.home_posthinhanh);
            post_video = itemView.findViewById(R.id.home_postvideo);
            post_camxuc = itemView.findViewById(R.id.home_postcamxuc);
        }
    }
}
