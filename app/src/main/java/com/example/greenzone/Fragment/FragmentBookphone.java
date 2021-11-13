package com.example.greenzone.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenzone.Adapter.PhonebookAdapter;
import com.example.greenzone.Class.User;
import com.example.greenzone.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentBookphone extends Fragment {
    RecyclerView rcv_phonebook;
    ArrayList<User> users = new ArrayList<>();
    PhonebookAdapter adapter;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookphone,container,false);
        rcv_phonebook = view.findViewById(R.id.phonebook_rcv);
        Loaddanhsach();
        rcv_phonebook.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_phonebook.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void Loaddanhsach() {
        for (int i = 0 ; i<10; i ++)
        {
            users.add(new User(""+i,"Trần Tấn","Hiếu","","","","","","","","",true,0));

        }
        adapter = new PhonebookAdapter(users,getContext());
        rcv_phonebook.setAdapter(adapter);
    }
}
