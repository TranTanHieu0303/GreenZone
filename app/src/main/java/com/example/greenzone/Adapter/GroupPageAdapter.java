package com.example.greenzone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Activity.ChiTietBaiVietActivity;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.GroupPage;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

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

public class GroupPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_LIST_GROUP_PAGE = 0;
    private static final int TYPE_LIST_BAIVIET = 1;
    Context context;
    User userhost;
    ArrayList<BaiViet> dulieu;
    ListGroupPageAdapter listGroupPageAdapter ;
    ArrayList<GroupPage> groupPages = new ArrayList<>();
    public GroupPageAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<BaiViet> dulieu,User user)
    {
        this.dulieu= dulieu;
        this.userhost = user;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==TYPE_LIST_GROUP_PAGE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_rcv_group_page,null);
            return new viewhodel1(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_baidang,null);
            return new viewhodel2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position==0)
            {
                viewhodel1 viewhodel1 = (GroupPageAdapter.viewhodel1) holder;
                listGroupPageAdapter = new ListGroupPageAdapter(context);
                groupPages.add(new GroupPage("1","Hufi-Conpetion1","","Data/1636345457465.png","","",""));
                groupPages.add(new GroupPage("1","Hufi-Conpetion2","","Data/1636345457465.png","","",""));
                groupPages.add(new GroupPage("1","Hufi-Conpetion3","","Data/1636345457465.png","","",""));
                groupPages.add(new GroupPage("1","Hufi-Conpetion4","","Data/1636345457465.png","","",""));
                listGroupPageAdapter.setData(groupPages);
                viewhodel1.rcv_listgroup.setAdapter(listGroupPageAdapter);
            }
            else
            {
                viewhodel2 viewhodel2 = (GroupPageAdapter.viewhodel2) holder;
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
                                    viewhodel2.tv_thoigian.setText("Lúc " + datetime.toLocalTime().getHour() + ":" + datetime.toLocalTime().getMinute() + " hôm nay");
                                else
                                    viewhodel2.tv_thoigian.setText(LocalTime.now().getMinute() - datetime.toLocalTime().getMinute() + " phút trước");
                            } else {
                                viewhodel2.tv_thoigian.setText(datetime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString() + " lúc " + datetime.toLocalTime().getHour() + ":" + datetime.toLocalTime().getMinute());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (baiViet.getCheDo().equals("Công khai")) {
                            viewhodel2.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cong_khai));
                        } else {
                            if (baiViet.getCheDo().equals("Chỉ mình tôi")) {
                                viewhodel2.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_padlock));
                            } else {
                                viewhodel2.img_chedo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_friends));
                            }
                        }
                        if(baiViet.getNoidung().length()>100)
                        {
                            String noidungmoi = baiViet.getNoidung().substring(0,100)+"...";
                            viewhodel2.tv_noidung.setText(noidungmoi);
                            viewhodel2.tv_xemthem.setVisibility(View.VISIBLE);
                            viewhodel2.tv_xemthem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewhodel2.tv_noidung.setText(baiViet.getNoidung());
                                    viewhodel2.tv_xemthem.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                        else
                        {
                            viewhodel2.tv_noidung.setText(baiViet.getNoidung());
                        }

                        viewhodel2.tv_luotcmt_share.setText(baiViet.getLuotComment().toString() + " bình luận," + baiViet.getLuotShare().toString() + " lượt chia sẽ");
                        viewhodel2.tv_luotlike.setText(baiViet.getLuotLike().toString());
                        viewhodel2.tv_tengroup.setText(user.getFullName());
                        Picasso.with(context).load(Constant.ConnectionString_API + user.getHinhAnh())
                                .into(viewhodel2.img_avatar);
                        if (baiViet.getHinhAnh() != null && !baiViet.getHinhAnh().isEmpty()) {
                            List<String> list = Arrays.asList(baiViet.getHinhAnh().split(",").clone());
                            viewhodel2.viewImageSelectAdapter.setData(list);
                        } else {
                            viewhodel2.viewImageSelectAdapter.setData(null);
                        }
                        viewhodel2.tv_cmt.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return dulieu.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_LIST_GROUP_PAGE;
        else
            return TYPE_LIST_BAIVIET;
    }

    public class viewhodel1 extends RecyclerView.ViewHolder{
        RecyclerView rcv_listgroup;
        public viewhodel1(@NonNull View itemView) {
            super(itemView);
            rcv_listgroup = itemView.findViewById(R.id.layout_rcv_group_page);
            rcv_listgroup.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            rcv_listgroup.setItemAnimator(new DefaultItemAnimator());
        }
    }
    public class viewhodel2 extends RecyclerView.ViewHolder{
        ImageView img_avatar,img_chedo,img_avatar_phu;
        RecyclerView rcv_hinhanh;
        TextView tv_tentk,tv_thoigian,tv_noidung,tv_luotlike,tv_luotcmt_share,tv_like,tv_cmt,tv_share,tv_xemthem,tv_tengroup;
        MediaBangTinAdapter viewImageSelectAdapter ;
        LinearLayout linearLayout;
        public viewhodel2(@NonNull View itemView) {
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
}
