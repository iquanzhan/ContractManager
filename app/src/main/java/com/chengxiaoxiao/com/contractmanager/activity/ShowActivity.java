package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.dao.CatalogDao;
import com.chengxiaoxiao.com.contractmanager.dao.ContractDao;
import com.chengxiaoxiao.com.contractmanager.domain.Catalog;
import com.chengxiaoxiao.com.contractmanager.domain.Contract;


public class ShowActivity extends Activity
{
    private Contract contract;

    private TextView textName;
    private TextView textNumber;
    private TextView textGender;
    private TextView textCata;
    private TextView textDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        contract = ContractDao.getItem(id, ShowActivity.this);

        Catalog catalog =CatalogDao.getItem(contract.getCatalogId(),ShowActivity.this);

        textName = (TextView) findViewById(R.id.textName);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textGender = (TextView) findViewById(R.id.textGender);
        textCata = (TextView) findViewById(R.id.textCata);
        textDepart = (TextView) findViewById(R.id.textDepart);

        textName.setText(contract.getName());
        textGender.setText(contract.getGender());
        textNumber.setText(contract.getPhoneNum());
        textCata.setText(catalog.getcName());
        textDepart.setText(contract.getDepartment());

    }


}
