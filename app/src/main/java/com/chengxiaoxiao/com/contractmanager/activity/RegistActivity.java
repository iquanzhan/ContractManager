package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.utls.SPUtils;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

public class RegistActivity extends Activity
{

    private EditText editUid;
    private EditText editPwd;
    private EditText editRePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


        editUid = (EditText) findViewById(R.id.editUid);
        editPwd = (EditText) findViewById(R.id.editPwd);
        editRePwd = (EditText) findViewById(R.id.editRePwd);


    }

    public void register(View v)
    {
        String uid = editUid.getText().toString();

        String pwd = editPwd.getText().toString();
        String rePwd = editRePwd.getText().toString();

        if (TextUtils.isEmpty(uid))
        {
            ToastUtils.makeText(RegistActivity.this, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd))
        {
            ToastUtils.makeText(RegistActivity.this, "请输入密码");
            return;
        }
        if (TextUtils.isEmpty(rePwd))
        {
            ToastUtils.makeText(RegistActivity.this, "请输入确认密码");
            return;
        }

        if (!pwd.equals(rePwd))
        {
            ToastUtils.makeText(RegistActivity.this, "两次密码输入不一致");
            return;
        }

        SPUtils.putString("uid", uid, RegistActivity.this);
        SPUtils.putString("pwd", pwd, RegistActivity.this);


        ToastUtils.makeText(RegistActivity.this, "用户设置成功");

        startActivity(new Intent(RegistActivity.this, MainActivity.class));
        finish();
    }
}
