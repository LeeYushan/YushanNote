package com.example.hp.yushannote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageContentFragment extends Fragment {
    private View view;
    private int mid;
    private ImageButton editBtn;
    private ImageButton delBtn;
    private ImageButton saveBtn;
    private EditText titleTxt;
    private EditText messageTxt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.message_content_frag,container,false);
        editBtn=view.findViewById(R.id.edit_btn);
        delBtn=view.findViewById(R.id.delete_btn);
        saveBtn=view.findViewById(R.id.message_content_save);
        titleTxt=view.findViewById(R.id.message_content_title);
        messageTxt=view.findViewById(R.id.message_content_message);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             titleTxt.setFocusable(true);
             titleTxt.setCursorVisible(true);
             titleTxt.setFocusableInTouchMode(true);
             titleTxt.requestFocus();

             messageTxt.setFocusable(true);
             messageTxt.setCursorVisible(true);
             messageTxt.setFocusableInTouchMode(true);
             messageTxt.requestFocus();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageManager.updateMessage(mid,titleTxt.getText().toString(),messageTxt.getText().toString());
                getActivity().finish();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    //点击item之后刷新content页数据
    public void refresh(int mid,int year,int month,int date,String title,String message)
    {
        this.mid=mid;

        TextView dateTxt=view.findViewById(R.id.message_content_date);

        ImageButton back=view.findViewById(R.id.message_content_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        dateTxt.setText(year+"-"+month+"-"+date);
        titleTxt.setText(title);
        messageTxt.setText(message);
    }

    private void showDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setMessage("确认要删除这条记忆嘛？");
        normalDialog.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MessageManager.deleteMessage(mid);
                        getActivity().finish();
                    }
                });
        normalDialog.setNegativeButton("算了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }
}
