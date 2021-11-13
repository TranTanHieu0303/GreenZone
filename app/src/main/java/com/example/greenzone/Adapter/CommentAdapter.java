package com.example.greenzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenzone.API.APIservice;
import com.example.greenzone.Class.Comment;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CmtViewHodel> {
    ArrayList<Comment> dulieu;
    Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Comment> dulieu)
    {
        this.dulieu = dulieu;
    }
    @NonNull
    @Override
    public CmtViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_comment,null);
        return new CmtViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CmtViewHodel holder, int position) {
        Comment comment = dulieu.get(position);
        APIservice.apiService.getUser(comment.getIdUser()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Glide.with(context)
                        .load(Constant.ConnectionString_API+user.getHinhAnh())
                        .into(holder.img_avatar);
                holder.tv_tentk.setText(user.getFullName());
                holder.tv_noidung.setText(comment.getNoiDung());
                SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                try {
                    Date date = formatter4.parse(comment.getThoiGian());
                    LocalDateTime datetime = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    if(datetime.toLocalDate().equals(LocalDate.now()))
                    {
                        int time = (LocalTime.now().getHour()-datetime.toLocalTime().getHour());
                        if(time>=1)
                            holder.tv_thoigian.setText("Lúc "+datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute()+" hôm nay");
                        else
                            holder.tv_thoigian.setText(LocalTime.now().getMinute()-datetime.toLocalTime().getMinute()+" phút trước");
                    }else
                    {
                        holder.tv_thoigian.setText(datetime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()+" lúc "+ datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context,"Call api fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dulieu!=null)
        return dulieu.size();
        else return 0;
    }

    public class CmtViewHodel extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        TextView tv_tentk,tv_noidung,tv_thoigian;
        public CmtViewHodel(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.it_comment_img_avatar);
            tv_noidung = itemView.findViewById(R.id.it_comment_tv_noidung);
            tv_tentk = itemView.findViewById(R.id.it_comment_tv_tentk);
            tv_thoigian = itemView.findViewById(R.id.it_comment_tv_thoigian);
        }
    }
}
