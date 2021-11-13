package com.example.greenzone.API;

import com.example.greenzone.Class.BaiViet;
import com.example.greenzone.Class.Comment;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.FileUpLoad;
import com.example.greenzone.Class.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIservice {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    APIservice apiService = new Retrofit.Builder()
            .baseUrl(Constant.ConnectionString_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIservice.class);

    @GET("api/Users/{id}")
    Call<User> getUser(@Path("id") String id);
    //https://117.2.159.103:8082/api/Users/GetLogin?sdt=0397330562&pass=123
    @GET("api/Users/GetLogin")
    Call<User> getLogin(@Query("sdt") String sdt,@Query("pass") String pass);
    //https://117.2.159.103:8082/api/Users/CheckSDT?sdt=0397330562
    @GET("api/Users/CheckSDT")
    Call<Boolean> getCheckPhone(@Query("sdt") String sdt);

    @POST("api/Users")
    Call<User> postUser(@Body User user);

    //https://117.2.159.103:8082/api/BaiViets/1634389005180
    @GET("api/BaiViets/{id}")
    Call<BaiViet> getBaiVietid(@Path("id") String id);

    //https://117.2.159.103:8082/api/BaiViets/GetBaiVietTheoUser?iduser=1634553675470
    @GET("api/BaiViets/GetBaiVietTheoUser")
    Call<ArrayList<BaiViet>> getBaiViet(@Query("iduser") String iduser);

    @POST("api/BaiViets")
    Call<BaiViet> postBaiViet(@Body BaiViet baiViet);

    //https://117.2.159.103:8082/api/Home/Upload?subDirectory=Data
    @Multipart
    @POST("api/Home/Upload")
    Call<FileUpLoad> UploadFile(@Query("subDirectory") String subDirectory,@Part MultipartBody.Part[] formFiles);

    //Like
    @POST("api/BaiViets")
    Call<BaiViet> postLike(@Body BaiViet baiViet);

    //Comment
    //http://117.2.159.103:8082/api/Comments/GetCommentTheoBaiViet?id=1634389005180
    @GET("api/Comments/GetCommentTheoBaiViet")
    Call<ArrayList<Comment>> getComment(@Query("id") String id);

    //http://117.2.159.103:8082/api/Comments
    @POST("api/Comments")
    Call<Comment> postComment(@Body Comment comment);
}
