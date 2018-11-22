package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.utls.SPUtils;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

public class ResetPwdActivity extends Activity
{
    private EditText editOldPwd;
    private EditText editPwd;
    private EditText editRePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        editOldPwd = (EditText) findViewById(R.id.editOldPwd);
        editPwd = (EditText) findViewById(R.id.editPwd);
        editRePwd = (EditText) findViewById(R.id.editRePwd);


    }

    /*进行密码更新*/
    public void update(View v)
    {

        String oldPwd = editOldPwd.getText().toString().trim();
        String pwd = editPwd.getText().toString().trim();
        String rePwd = editRePwd.getText().toString().trim();

        if (TextUtils.isEmpty(oldPwd))
        {
            ToastUtils.makeText(ResetPwdActivity.this, "请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(pwd))
        {
            ToastUtils.makeText(ResetPwdActivity.this, "新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(rePwd))
        {
            ToastUtils.makeText(ResetPwdActivity.this, "请确认您的输入");
            return;
        }
        if (!pwd.equals(rePwd))
        {
            ToastUtils.makeText(ResetPwdActivity.this, "两次新密码输入不一致");
            return;
        }


        String upwd = SPUtils.getString("pwd", "", getApplicationContext());
        if (!upwd.equals(oldPwd))
        {
            ToastUtils.makeText(ResetPwdActivity.this, "原密码输入不正确");
            return;
        }
        SPUtils.putString("pwd", pwd, getApplicationContext());

        ToastUtils.makeText(ResetPwdActivity.this, "密码修改成功，下次登陆请使用新密码");
    }
}
