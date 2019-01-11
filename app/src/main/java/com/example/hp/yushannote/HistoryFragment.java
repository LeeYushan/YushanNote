package com.example.hp.yushannote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private List<Message> messageList;
    private RelativeLayout nonHistory;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.history_content,container,false);
        RecyclerView titleRecyclerView=view.findViewById(R.id.title_recycler_view);
        nonHistory=view.findViewById(R.id.nonhistory);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        titleRecyclerView.setLayoutManager(layoutManager);

        messageList=MessageManager.getAllMessages();
        adapter=new MessageAdapter(messageList,getActivity());
        titleRecyclerView.setAdapter(adapter);


        return view;
    }

    //检测刷新列表
    @Override
    public void onResume() {
        super.onResume();
        AnimationManager.inScanResult=false;
        refresh();
    }

    public void refresh()
    {
        List<Message> newList=MessageManager.getAllMessages();
        messageList.clear();
        messageList.addAll(newList);
        adapter.notifyDataSetChanged();

        if(messageList.size()>0)
        {
            nonHistory.setVisibility(View.INVISIBLE);
        }
        else {
            nonHistory.setVisibility(View.VISIBLE);
        }
    }
}
