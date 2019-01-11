package com.example.hp.yushannote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messageList;
    Context context;
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView day;
        TextView time;
        TextView title;
        TextView message;


        public ViewHolder(View view)
        {
            super(view);
            date=view.findViewById(R.id.date);
            day=view.findViewById(R.id.day);
            time=view.findViewById(R.id.time);
            title=view.findViewById(R.id.title);
            message=view.findViewById(R.id.message);
        }
    }

    public MessageAdapter(List<Message> messageList, Context context)
    {
        this.messageList=messageList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_item,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                AnimationManager.position=position;
                Message message=messageList.get(position);
                MessageContentActivity.actionStart(context,message.getmId(),message.getYear(),message.getMonth(),message.getDate(),message.getTitle(),message.getMessage());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message message=messageList.get(i);
        viewHolder.date.setText(String.valueOf(message.getDate()));
        viewHolder.day.setText(String.valueOf(message.getDay()));
        viewHolder.time.setText(message.getHour()+":"+message.getMinute());
        viewHolder.title.setText(message.getTitle());
        viewHolder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public List<Message> getMessageList()
    {
        return messageList;
    }

    public void setMessageList(List<Message> messageList)
    {
        this.messageList=messageList;
    }
}
