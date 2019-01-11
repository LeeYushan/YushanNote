package com.example.hp.yushannote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private TextView txt_scan;
    private TextView txt_history;
    private TextView txt_me;
    private FrameLayout ly_content;

    private TextView topbarTitle;

    private ScanFragment scanFragment;
    private HistoryFragment historyFragment;
    private MeFragment meFragment;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        txt_scan=findViewById(R.id.txt_scan);
        txt_history=findViewById(R.id.txt_history);
        txt_me=findViewById(R.id.txt_me);
        ly_content=findViewById(R.id.ly_content);
        topbarTitle=findViewById(R.id.txt_topbar);

        LitePal.getDatabase();//创建\获取数据库
        //initPref();

        txt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                hideAllFragment(transaction);
                setSelected();
                txt_scan.setSelected(true);
                if(scanFragment==null)
                {
                    scanFragment=new ScanFragment();
                    transaction.add(R.id.ly_content,scanFragment);
                }
                else {
                    transaction.show(scanFragment);
                }
                transaction.commit();
                setTopbarTitle("YUSHAN NOTE");
            }
        });

        txt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                hideAllFragment(transaction);
                setSelected();
                txt_history.setSelected(true);
                if(historyFragment==null)
                {
                    historyFragment=new HistoryFragment();
                    transaction.add(R.id.ly_content,historyFragment);
                }
                else {
                    transaction.show(historyFragment);
                }
                transaction.commit();
                setTopbarTitle("HISTORY");
            }
        });

        txt_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                hideAllFragment(transaction);
                setSelected();
                txt_me.setSelected(true);
                if(meFragment==null)
                {
                    meFragment=new MeFragment();
                    transaction.add(R.id.ly_content,meFragment);
                }
                else {
                    transaction.show(meFragment);
                }
                transaction.commit();
                setTopbarTitle("ME");
            }
        });

        txt_scan.performClick();
    }

    private void setSelected()
    {
        txt_scan.setSelected(false);
        txt_history.setSelected(false);
        txt_me.setSelected(false);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction)
    {
        if(scanFragment!=null)
        {
            fragmentTransaction.hide(scanFragment);
        }

        if(historyFragment!=null)
        {
            fragmentTransaction.hide(historyFragment);
        }

        if(meFragment!=null)
        {
            fragmentTransaction.hide(meFragment);
        }
    }

    public void goScan(){
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫码
                    goScan();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //int codeNumber=Integer.parseInt(content);
                Log.d(content,"MainActivity");
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                Intent intent=new Intent(MainActivity.this,ScanResultActivity.class);
                intent.putExtra("codeNumber",content);
                startActivity(intent);
            }
        }
    }

    public void setTopbarTitle(String newTitle)
    {
        topbarTitle.setText(newTitle);
    }

    public Fragment getFrag()
    {
        return historyFragment;
    }

    public void initPref()
    {
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("userName","李雨山");
        editor.apply();
    }
}


