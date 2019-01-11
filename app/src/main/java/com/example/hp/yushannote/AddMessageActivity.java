package com.example.hp.yushannote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;

import java.util.Calendar;

public class AddMessageActivity extends AppCompatActivity {

    private ImageButton returnBtn;
    private ImageButton saveBtn;
    private EditText editText;
    private EditText title;
    private String messageTxt;
    private String titleTxt;
    private String codeNumber;
    private TextView title_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        returnBtn=findViewById(R.id.add_message_return);
        saveBtn=findViewById(R.id.add_message_save);
        editText=findViewById(R.id.editText);
        title=findViewById(R.id.add_message_title);
        title_date=findViewById(R.id.add_message_date);

        codeNumber=getIntent().getStringExtra("codeNumber");
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int date=calendar.get(Calendar.DATE);
        title_date.setText(year+"-"+month+"-"+date);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageTxt=editText.getText().toString();
                titleTxt=title.getText().toString();

                if(messageTxt==null||messageTxt.equals(""))
                {
                    Toast.makeText(AddMessageActivity.this,"哼，不写点什么就别想保存啦~",Toast.LENGTH_SHORT).show();
                }
                else if(titleTxt==null||titleTxt.equals(""))
                {
                    Toast.makeText(AddMessageActivity.this,"起个标题再走呗~",Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar=Calendar.getInstance();
                    Message message=new Message();
                    message.setCodeNumber(codeNumber);
                    message.setYear(calendar.get(Calendar.YEAR));
                    message.setMonth(calendar.get(Calendar.MONTH)+1);
                    message.setDate(calendar.get(Calendar.DATE));
                    message.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                    message.setMinute(calendar.get(Calendar.MINUTE));
                    message.setTitle(titleTxt);
                    message.setMessage(messageTxt);

                    int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
                    String day;
                    switch (dayOfWeek)
                    {
                        case 1:
                            day="Sunday";
                        case 2:
                            day="Monday";
                            break;
                        case 3:
                            day="Tuesday";
                            break;
                        case 4:
                            day="Wednesday";
                            break;
                        case 5:
                            day="Thursday";
                            break;
                        case 6:
                            day="Friday";
                            break;
                        case 7:
                            day="Saturday";
                            break;
                        default:
                            day="Something Wrong";
                            break;
                    }
                    message.setDay(day);
                    Intent intent=new Intent();
                    intent.putExtra("message",message);
                    setResult(RESULT_OK,intent);
                    finish();
                    Log.d("用传递message","MainActivity");
                }
            }

        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
