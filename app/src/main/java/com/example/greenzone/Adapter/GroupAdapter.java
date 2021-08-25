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
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Activity.GroupActivity;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupAdapter  extends RecyclerView.Adapter<GroupAdapter.KHUNGNHIN> {
    ArrayList<Group> groups;
    Context context;
    User user;

    public GroupAdapter(ArrayList<Group> groups, Context context,User user) {
        this.groups = groups;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @NotNull
    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_itemgroup,null);

        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull KHUNGNHIN holder, int position) {
        Group group =groups.get(position);
        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/greenzone-a8ef0.appspot.com/o/Avatar_Nu.jpg?alt=media&token=7fe0d85d-abcc-40dd-bb3b-f2c577cd1fb8")
                .into(holder.img_avatar);
        holder.tv_name.setText(group.getTenGroup());
        holder.tv_noidung.setText(group.getIdMesCuoi());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupActivity.class);
                intent.putExtra("id_group", (Serializable) group);
                intent.putExtra("id_user",user.getSDT());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder{
LinearLayout layout;
    ImageView img_avatar;
    TextView tv_name,tv_noidung;
    public KHUNGNHIN(@NonNull @NotNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.itemgr_avatar);
        tv_name = itemView.findViewById(R.id.itemgr_tv_name);
        tv_noidung = itemView.findViewById(R.id.itemgr_tv_noidung);
        layout = itemView.findViewById(R.id.layout_itemgroup);
    }
}
}
