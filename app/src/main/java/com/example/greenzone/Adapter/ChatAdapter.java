package com.example.greenzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Class.Messages;
import com.example.greenzone.R;
import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Messages> messages;
    Context context;
    private static int TYPE_NHAN = 0;
    private  static  int TYPE_GOI = 1;

    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_GOI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemmess_goi, parent, false);
            return new ViewHode(view);
        }else
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemmess_nhan, parent, false);
                return new Viewhoder2(view);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Messages messages1 = messages.get(position);

            if(messages1==null)
                return;


            if(holder.getItemViewType()==TYPE_GOI)
            {
                ViewHode viewHode = (ViewHode) holder;
                ((ViewHode) holder).tv_text.setText(messages1.getText());
                SimpleDateFormat formatter4 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    Date date = formatter4.parse(messages1.getTime().toString());
                    ((ViewHode) holder).tv_tg.setText(date.getHours()+":"+date.getMinutes());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
                else
            {
                Viewhoder2 viewHode = (Viewhoder2) holder;
                ((Viewhoder2) holder).tv_textnhan.setText(messages1.getText());
                SimpleDateFormat formatter4 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    Date date = formatter4.parse(messages1.getTime().toString());

                    ((Viewhoder2) holder).tv_tgnhan.setText(date.getHours()+":"+date.getMinutes());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


    }

    @Override
    public int getItemViewType(int position) {
        Messages messages1 = messages.get(position);
        if(messages1.isIsuer())
            return TYPE_GOI;
        else
            return TYPE_NHAN;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHode extends RecyclerView.ViewHolder{

        TextView tv_text,tv_tg;
        public ViewHode(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.layoutitemchat_tv_text);
            tv_tg = itemView.findViewById(R.id.layoutitemchat_tv_tg);
            tv_tg.setVisibility(View.INVISIBLE);
            tv_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv_tg.getVisibility() == View.INVISIBLE)
                        tv_tg.setVisibility(View.VISIBLE);
                    else
                        tv_tg.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
    public class Viewhoder2 extends RecyclerView.ViewHolder{
        TextView tv_textnhan,tv_tgnhan;
        ImageView img_avatar;
        public Viewhoder2(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_textnhan = itemView.findViewById(R.id.layoutitemchat_tv_textnhan);
            img_avatar = itemView.findViewById(R.id.layoutitemnhan_img_avatar);
            tv_tgnhan = itemView.findViewById(R.id.layoutchat_tv_thg_nhan);
            tv_tgnhan.setVisibility(View.INVISIBLE);
            tv_textnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv_tgnhan.getVisibility() == View.INVISIBLE)
                        tv_tgnhan.setVisibility(View.VISIBLE);
                    else
                        tv_tgnhan.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
