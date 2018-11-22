package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.utls.SPUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity
{
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);

        final long startTime = System.currentTimeMillis();
        //拷贝数据库
        copyDb();
        final long endTime = System.currentTimeMillis();


        long used = endTime - startTime;

        long need = 2000;

        if (used < 2000)
        {
            need = 2000 - used;
        }

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                jumpActivity();

                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }, need);



        //渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1);
        animation.setDuration(2000);
        root.startAnimation(animation);

    }

    /*
    *跳转
    * */
    private void jumpActivity()
    {
        String uid = SPUtils.getString("uid", "", SplashActivity.this);
        if (!TextUtils.isEmpty(uid))
        {

            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        } else
        {
            startActivity(new Intent(SplashActivity.this,RegistActivity.class));
            finish();

        }


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();


    }

    //拷贝数据库
    private void copyDb()
    {
        AssetManager manager = getAssets();
        try
        {

            String absolutePath = this.getFilesDir().getAbsolutePath() + "/contract.db";

            File targetDir = new File(absolutePath);


            if (targetDir.exists())
            {
                return;
            }


            InputStream input = manager.open("contract.db");

            FileOutputStream out = new FileOutputStream(targetDir);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = input.read(buffer)) != -1)
            {
                out.write(buffer, 0, len);
            }

            input.close();
            out.close();


        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
