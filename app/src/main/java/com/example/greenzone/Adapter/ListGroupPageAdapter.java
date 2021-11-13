package com.example.greenzone.Adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenzone.Class.Constant;
import com.example.greenzone.Class.Group;
import com.example.greenzone.Class.GroupPage;
import com.example.greenzone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListGroupPageAdapter extends RecyclerView.Adapter<ListGroupPageAdapter.viewHodel>{
    ArrayList<GroupPage> dulieu;
    Context context;

    public ListGroupPageAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<GroupPage> dulieu)
    {
        this.dulieu = dulieu;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public viewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_group_page,null);
        return new viewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHodel holder, int position) {
        GroupPage groupPage = dulieu.get(position);
        if (groupPage ==null)
            return;
        else
        {
            Picasso.with(context)
                    .load(Constant.ConnectionString_API + groupPage.getAnhDaiDien())
                    .into(holder.img_hinhanh);
            holder.tv_TenGroup.setText(groupPage.getGoupName());
        }
    }

    @Override
    public int getItemCount() {
        if(dulieu ==null)
            return 0;
        else
            return dulieu.size();
    }


    public class viewHodel extends RecyclerView.ViewHolder{
        ImageView img_hinhanh;
        TextView tv_TenGroup;
        View view;
        public viewHodel(@NonNull View itemView) {
            super(itemView);
            img_hinhanh = itemView.findViewById(R.id.item_group_page_img_hinhanh);
            tv_TenGroup = itemView.findViewById(R.id.item_group_page_tv_tengroup);
            view = itemView.findViewById(R.id.item_group_page_view);
        }
    }
}
