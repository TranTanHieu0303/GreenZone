package com.example.greenzone.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Class.Constant;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MediaBangTinAdapter extends RecyclerView.Adapter<MediaBangTinAdapter.ImageViewHodel> {
    Context context;
    List<String> dulieu;

    public MediaBangTinAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<String> data)
    {
        this.dulieu = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ImageViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imege_select,parent,false);
        return new ImageViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHodel holder, int position) {
        String url = dulieu.get(position);
        Picasso.with(context)
                .load(Constant.ConnectionString_API +url)
                .placeholder(R.drawable.img_loadding)
                .into(holder.img_hinhanh);

    }

    @Override
    public int getItemCount() {
        if(dulieu== null) {
            return 0;
        }else {
            return dulieu.size();
        }
    }

    public class ImageViewHodel extends RecyclerView.ViewHolder{
        ImageView img_hinhanh;
        public ImageViewHodel(@NonNull View itemView) {
            super(itemView);
            img_hinhanh = itemView.findViewById(R.id.item_img_select);
        }
    }
}
