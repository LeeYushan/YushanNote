package com.example.hp.yushannote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.me_content,container,false);
        RelativeLayout aboutMe=view.findViewById(R.id.about_me_ly);
        RelativeLayout deleteAll=view.findViewById(R.id.delete_all_ly);
        ImageButton editName=view.findViewById(R.id.edit_name);

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AboutMeActivity.class);
                startActivity(intent);
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ChangeNameActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void showDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setMessage("是否拔出我的记忆芯片？");
        normalDialog.setPositiveButton("拔出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MessageManager.deleteAll();
                        MainActivity mainActivity=(MainActivity)getActivity();
                        HistoryFragment historyFragment=(HistoryFragment) mainActivity.getFrag();
                        if(historyFragment!=null)
                        {
                            historyFragment.refresh();
                        }
                    }
                });
        normalDialog.setNegativeButton("不拔出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView userName=view.findViewById(R.id.user_name);
        SharedPreferences pref=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userName.setText(pref.getString("userName","李雨山"));
    }
}
