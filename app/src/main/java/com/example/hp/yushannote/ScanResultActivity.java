package com.example.hp.yushannote;

import android.content.Intent;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ScanResultActivity extends AppCompatActivity {
    private List<Message> messageList=new ArrayList<>();
    private String codeNumber;
    private RelativeLayout nonmessage;
    private ImageButton addMessageBtn;
    private MessageAdapter adapter;
    private MessageManager messageManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        codeNumber=getIntent().getStringExtra("codeNumber");
        nonmessage=findViewById(R.id.nonmessage);
        addMessageBtn =findViewById(R.id.add_message_btn);

        recyclerView=findViewById(R.id.result_recycler_view);
        messageList=MessageManager.getMessages(codeNumber);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MessageAdapter(messageList,this);
        recyclerView.setAdapter(adapter);

        messageManager=new MessageManager(messageList,adapter);

        addMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScanResultActivity.this,AddMessageActivity.class);
                intent.putExtra("codeNumber",codeNumber);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK)
                {
                    Message message=(Message) data.getSerializableExtra("message");
                    int mId=MessageManager.getAllMessages().size()+1;
                    message.setmId(mId);
                    messageManager.insertMessage(message);
                    recyclerView.getLayoutManager().scrollToPosition(0);
                    nonmessage.setVisibility(View.INVISIBLE);
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationManager.inScanResult=true;
        if(AnimationManager.delete)
        {
            messageList.remove(AnimationManager.position);
            adapter.notifyItemRemoved(AnimationManager.position);
            AnimationManager.delete=false;
        }
        if(AnimationManager.update)
        {
            messageList.clear();
            messageList.addAll(MessageManager.getAllMessages());
            adapter.notifyDataSetChanged();
            AnimationManager.update=false;
        }

        if(messageList.size()>0)
        {
            nonmessage.setVisibility(View.INVISIBLE);
        }
        else {
            nonmessage.setVisibility(View.VISIBLE);
        }
    }
}
