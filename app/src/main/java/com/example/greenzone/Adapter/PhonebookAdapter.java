package com.example.greenzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Class.User;
import com.example.greenzone.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhonebookAdapter extends RecyclerView.Adapter<PhonebookAdapter.Viewhoder1>{
    ArrayList<User> users;
    Context context;

    public PhonebookAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Viewhoder1 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_danhba,parent,false);
        return new Viewhoder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewhoder1 holder, int position) {
        User user = users.get(position);
        holder.tv_tentk.setText(user.getFullName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Viewhoder1 extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        TextView tv_tentk;
        public Viewhoder1(@NonNull @NotNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.layout_item_danhba_img_avartar);
            tv_tentk = itemView.findViewById(R.id.layout_item_danhba_tv_tentk);
        }
    }
}
