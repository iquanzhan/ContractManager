package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.utls.SPUtils;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

public class LoginActivity extends Activity
{

    private EditText editPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPwd = (EditText) findViewById(R.id.editPwd);
    }

    public void login(View v)
    {
        String pwd = editPwd.getText().toString().trim();

        if (TextUtils.isEmpty(pwd))
        {
            ToastUtils.makeText(getApplicationContext(), "请输入用户名");
            return;
        }
        String pwdUtils = SPUtils.getString("pwd", "", getApplicationContext());
        if (pwd.equals(pwdUtils))
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else
        {
            ToastUtils.makeText(getApplicationContext(), "密码错误");
        }
    }
}
