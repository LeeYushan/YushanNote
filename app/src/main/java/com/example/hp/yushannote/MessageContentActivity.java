package com.example.hp.yushannote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageContentActivity extends AppCompatActivity {

    //跳转到此活动的时候，向此活动传入message相关信息
    public static void actionStart(Context context,int mid,int year,int month,int date,String title,String message)
    {
        Intent intent=new Intent(context,MessageContentActivity.class);
        intent.putExtra("mid",mid);
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("date",date);
        intent.putExtra("title",title);
        intent.putExtra("message",message);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);

        //获取传入的信息
        int mid=getIntent().getIntExtra("mid",0);
        int year=getIntent().getIntExtra("year",0);
        int month=getIntent().getIntExtra("month",0);
        int date=getIntent().getIntExtra("date",0);
        String title=getIntent().getStringExtra("title");
        String message=getIntent().getStringExtra("message");

        //取得messageContentFragment
        MessageContentFragment messageContentFragment=(MessageContentFragment)getSupportFragmentManager().findFragmentById(R.id.message_content_fragment);
        //刷新fragment中的信息
        messageContentFragment.refresh(mid,year,month,date,title,message);
    }
}
