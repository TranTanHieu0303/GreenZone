package com.example.greenzone.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenzone.API.APIservice;
import com.example.greenzone.Adapter.ViewImageSelectAdapter;
import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.FileUpLoad;
import com.example.greenzone.Class.RealPathUtil;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.google.common.base.MoreObjects;
import com.google.firebase.database.annotations.NotNull;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class DangTinActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_tentk;
    ImageView img_avatar;
    EditText edt_noidung;
    Button btn_dangtin,btn_chonhinh;
    User user;
    Spinner spi_chedo;
    final String[] chedo = {new String()};
    RecyclerView rcv_chonhinh;
    ViewImageSelectAdapter viewImageSelectAdapter;
    List<Uri> listuri = new ArrayList<>();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_dang_tin);
        toolbar = findViewById(R.id.dangtin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_tentk = findViewById(R.id.dangtin_tv_tentk);
        img_avatar = findViewById(R.id.dangtin_img_avatar);
        edt_noidung = findViewById(R.id.dangtin_edt_noidung);
        btn_dangtin = findViewById(R.id.dangtin_btn_dangtin);
        spi_chedo = findViewById(R.id.dangtin_snp_chedo);
        rcv_chonhinh =findViewById(R.id.dangtin_rcv_hinhanh);
        btn_chonhinh = findViewById(R.id.dangtin_btn_chonhinh);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Đang tải...");
        viewImageSelectAdapter = new ViewImageSelectAdapter(this);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        rcv_chonhinh.setLayoutManager(gridLayoutManager);
        rcv_chonhinh.setFocusable(false);
        rcv_chonhinh.setAdapter(viewImageSelectAdapter);
        tv_tentk.setText(user.getFullName()+" ơi");
        Picasso.with(DangTinActivity.this)
                .load("http://117.2.159.103:8082/"+user.getHinhAnh())
                .into(img_avatar);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Công khai");
        list.add("Chỉ mình tôi");
        list.add("Bạn bè");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spi_chedo.setAdapter(adapter);
        spi_chedo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chedo[0] = spi_chedo.getSelectedItem().toString();
                Toast.makeText(DangTinActivity.this, spi_chedo.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_dangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangbai();
            }
        });
        btn_chonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtraquyen();
            }
        });
    }

    private void kiemtraquyen() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(DangTinActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                openthechon();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(DangTinActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openthechon() {
        TedBottomPicker.with(DangTinActivity.this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setSelectMaxCount(9)
                .setSelectMaxCountErrorText("Chỉ Chọn tối đa 9 ảnh")
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Chưa có ảnh")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if(uriList!=null && !uriList.isEmpty()) {
                            viewImageSelectAdapter.setData(uriList);
                            listuri.addAll(uriList);
                        }
                    }
                });

    }

    private void dangbai() {
        final FileUpLoad[] fileUpLoad = new FileUpLoad[1];
        String noidung = edt_noidung.getText().toString();
        if(listuri.size()>0)
        {
            List<File> files = new ArrayList<>() ;
            for (Uri item:
                 listuri) {
                String stringPath = RealPathUtil.getRealPath(this,item);
                File file = new File(stringPath);
                files.add(file);
            }
            MultipartBody.Part[] parts = new MultipartBody.Part[listuri.size()];
            for (int i =  0  ; i<listuri.size();i++)
            {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),files.get(i));
                parts[i] = MultipartBody.Part.createFormData("formFiles",files.get(i).getName(),requestBody);
            }

            mProgressDialog.show();
            APIservice.apiService.UploadFile("Data/"+user.getIdUser(),parts).enqueue(new Callback<FileUpLoad>() {
                @Override
                public void onResponse(Call<FileUpLoad> call, Response<FileUpLoad> response) {
                    mProgressDialog.dismiss();
                    fileUpLoad[0] = response.body();
                    BaiViet baiViet = new BaiViet("",user.getIdUser(),noidung,fileUpLoad[0].getFilenames(),"",chedo[0],0,0,0, ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
                    APIservice.apiService.postBaiViet(baiViet).enqueue(new Callback<BaiViet>() {
                        @Override
                        public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
                            Toast.makeText(DangTinActivity.this, "Call api thành công", Toast.LENGTH_SHORT).show();
                            Toast.makeText(DangTinActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangTinActivity.this,HomeActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<BaiViet> call, Throwable t) {
                            Toast.makeText(DangTinActivity.this, "Call api thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<FileUpLoad> call, Throwable t) {
                    Toast.makeText(DangTinActivity.this, "Call api thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            BaiViet baiViet = new BaiViet("",user.getIdUser(),noidung,"","",chedo[0],0,0,0, ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
            APIservice.apiService.postBaiViet(baiViet).enqueue(new Callback<BaiViet>() {
                @Override
                public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
                    Toast.makeText(DangTinActivity.this, "Call api thành công", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DangTinActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangTinActivity.this,HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<BaiViet> call, Throwable t) {
                    Toast.makeText(DangTinActivity.this, "Call api thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }

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