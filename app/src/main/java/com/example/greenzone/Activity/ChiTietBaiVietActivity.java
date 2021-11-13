package com.example.greenzone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Adapter.CommentAdapter;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Comment;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietBaiVietActivity extends AppCompatActivity {
    Toolbar toolbar;
    User user;
    BaiViet baiViet;
    EditText edt_cmt;
    ImageView img_avatar,img_chedo,img_send;
    TextView tv_tentk,tv_thoigian,tv_noidung,tv_luotlike,tv_cmt_share;
    RecyclerView rcv_danhsachimg,rcv_danhsachcmt;
    ArrayList<Comment> dulieu;
    CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_viet);
        user = (User) getIntent().getSerializableExtra("user");
        baiViet = (BaiViet) getIntent().getSerializableExtra("baiviet");
        anhxa();
        gangiatri();
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = edt_cmt.getText().toString();
                if(!cmt.isEmpty())
                {
                    Comment comment = new Comment();
                    comment.setIdBaiViet(baiViet.getIdBaiViet());
                    comment.setIdUser(user.getIdUser());
                    comment.setNoiDung(cmt);
                    String Ngay = ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
                    comment.setThoiGian(Ngay);
                    APIservice.apiService.postComment(comment).enqueue(new Callback<Comment>() {
                        @Override
                        public void onResponse(Call<Comment> call, Response<Comment> response) {
//                            Comment comment1 = response.body();
//                            dulieu.add(comment1);
//                            commentAdapter.setData(dulieu);
//                            rcv_danhsachcmt.setAdapter(commentAdapter);
//                            edt_cmt.setText(null);
                            gangiatri();
                            edt_cmt.setText(null);
                        }
                        @Override
                        public void onFailure(Call<Comment> call, Throwable t) {
                            Toast.makeText(ChiTietBaiVietActivity.this,"Call api thất bại",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void gangiatri() {
        APIservice.apiService.getBaiVietid(baiViet.getIdBaiViet()).enqueue(new Callback<BaiViet>() {
            @Override
            public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
                baiViet = response.body();
                APIservice.apiService.getUser(baiViet.getIdUser()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user1 = response.body();
                        SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        try {
                            Date date = formatter4.parse(baiViet.getNgayDang());
                            LocalDateTime datetime = date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            if(datetime.toLocalDate().equals(LocalDate.now()))
                            {
                                int time = (LocalTime.now().getHour()-datetime.toLocalTime().getHour());
                                if(time>=1)
                                    tv_thoigian.setText("Lúc "+datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute()+" hôm nay");
                                else
                                    tv_thoigian.setText(LocalTime.now().getMinute()-datetime.toLocalTime().getMinute()+" phút trước");
                            }else
                            {
                                tv_thoigian.setText(datetime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()+" lúc "+ datetime.toLocalTime().getHour()+":"+datetime.toLocalTime().getMinute());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(baiViet.getCheDo().equals("Công khai"))
                        {
                            img_chedo.setImageDrawable(getResources().getDrawable(R.drawable.ic_cong_khai));
                        }
                        else
                        {
                            if(baiViet.getCheDo().equals("Chỉ mình tôi"))
                            {
                                img_chedo.setImageDrawable(getResources().getDrawable(R.drawable.ic_padlock));
                            }
                            else
                            {
                                img_chedo.setImageDrawable(getResources().getDrawable(R.drawable.ic_friends));
                            }
                        }
                        tv_noidung.setText(baiViet.getNoidung());
                        tv_cmt_share.setText(baiViet.getLuotComment().toString()+" bình luận,"+baiViet.getLuotShare().toString()+" lượt chia sẽ");
                        tv_luotlike.setText(baiViet.getLuotLike().toString());
                        tv_tentk.setText(user1.getFullName());
                        Picasso.with(ChiTietBaiVietActivity.this).load(Constant.ConnectionString_API+user1.getHinhAnh())
                                .into(img_avatar);
                        loadCmt();
                        commentAdapter = new CommentAdapter(ChiTietBaiVietActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChiTietBaiVietActivity.this, LinearLayoutManager.VERTICAL, false);
                        rcv_danhsachcmt.setLayoutManager(linearLayoutManager);
                        rcv_danhsachcmt.setAdapter(commentAdapter);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<BaiViet> call, Throwable t) {
                Toast.makeText(ChiTietBaiVietActivity.this,"Call api thất bại",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadCmt() {
        APIservice.apiService.getComment(baiViet.getIdBaiViet()).enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                dulieu = response.body();
                commentAdapter.setData(dulieu);
                rcv_danhsachcmt.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                Toast.makeText(ChiTietBaiVietActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.CT_BaiViet_Toobar);
        img_avatar = findViewById(R.id.CT_BaiViet_img_avatar);
        img_chedo = findViewById(R.id.CT_BaiViet_img_chedo);
        tv_tentk = findViewById(R.id.CT_BaiViet_tv_tentk);
        tv_thoigian = findViewById(R.id.CT_BaiViet_tv_thoigian);
        tv_noidung = findViewById(R.id.CT_BaiViet_tv_noidung);
        tv_luotlike = findViewById(R.id.CT_BaiViet_tv_luotlike);
        tv_cmt_share = findViewById(R.id.CT_BaiViet_tv_luotcmt_share);
        rcv_danhsachcmt = findViewById(R.id.CT_BaiViet_rcv_Cmt);
        img_send = findViewById(R.id.Ct_BaiViet_img_send);
        rcv_danhsachimg = findViewById(R.id.CT_BaiViet_rcv_hinhanh);
        edt_cmt = findViewById(R.id.CT_BaiViet_edt_cmt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}