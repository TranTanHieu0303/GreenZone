package com.example.greenzone.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewImageSelectAdapter extends RecyclerView.Adapter<ViewImageSelectAdapter.ImageViewHolder>{
    Context context;
    List<Uri> dulieu;

    public ViewImageSelectAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Uri> data)
    {
        this.dulieu = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imege_select,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
                Uri uri = dulieu.get(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
            holder.img_view.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(dulieu== null) {
            return 0;
        }else {
            return dulieu.size();
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView img_view;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_view = itemView.findViewById(R.id.item_img_select);
        }
    }
}
