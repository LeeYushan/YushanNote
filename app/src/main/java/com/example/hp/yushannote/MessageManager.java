package com.example.hp.yushannote;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class MessageManager {
    private MessageAdapter adapter;
    private List<Message> messageList;

    public MessageManager(List<Message> messageList,MessageAdapter messageAdapteradapter )
    {
        this.messageList=messageList;
        adapter=messageAdapteradapter;
    }

    public void insertMessage(Message message)
    {
        messageList.add(0,message);//在最前面添加一个元素
        message.save();//在数据库中添加元素
        adapter.notifyItemInserted(0);
    }

    public void deleteMessage(Message message)
    {
        messageList.remove(message);//从messageList中删除
        message.delete();//在数据库中删除
        adapter.notifyDataSetChanged();
    }

    public static List<Message> getAllMessages()
    {
        List<Message> result= LitePal.order("id desc").find(Message.class);
        return result;
    }

    public static List<Message> getMessages(String codeNumber)
    {
        List<Message> result=LitePal.where("codeNumber=?",codeNumber).order("id desc").find(Message.class);
        return result;
    }

    //更新id为mid的Message
    public static void updateMessage(int mId,String title,String message)
    {
        Message m=new Message();
        m.setTitle(title);
        m.setMessage(message);

        m.updateAll("mId=?",String.valueOf(mId));
        if(AnimationManager.inScanResult)
        {
            AnimationManager.update=true;
        }
    }

    public static void deleteMessage(int mId)
    {
        LitePal.deleteAll(Message.class,"mid=?",String.valueOf(mId));
        if(AnimationManager.inScanResult)
        {
            AnimationManager.delete=true;
        }
    }

    public static void deleteAll()
    {
        LitePal.deleteAll(Message.class);
    }
}
