package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.dao.CatalogDao;
import com.chengxiaoxiao.com.contractmanager.dao.ContractDao;
import com.chengxiaoxiao.com.contractmanager.domain.Catalog;
import com.chengxiaoxiao.com.contractmanager.domain.Contract;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

import java.util.List;

public class AddActivity extends Activity
{
    private Spinner spinner;
    private EditText editName;
    private EditText editNumber;
    private EditText editDepart;
    private RadioGroup radioGroup;

    private List<Catalog> list;

    //获取Spinner和Radio的选中
    private RadioButton selectedRadio;
    private int selectSpinnerId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //初始化数据
        list = CatalogDao.get(AddActivity.this);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectSpinnerId = list.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        spinner.setAdapter(new SpinnerAdapter());

        initView();
    }

    /*
    * 初始化数据
    * */
    private void initView()
    {
        editName = (EditText) findViewById(R.id.editName);
        editNumber = (EditText) findViewById(R.id.editNumber);
        editDepart = (EditText) findViewById(R.id.editDepart);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                selectedRadio = (RadioButton)findViewById(checkedId);

            }
        });



    }

    /*
    *
    * */
    class SpinnerAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            View v;
            if(convertView==null)
            {
                v =View.inflate(getApplicationContext(),R.layout.spinner_catalog_item,null);
            }
            else
            {
                v = convertView;
            }

            TextView textCatalog = (TextView) v.findViewById(R.id.textCatalog);

            textCatalog.setText(list.get(position).getcName());

            return v;
        }
    }

    /*
    * 取消按钮
    * */
    public void backHome(View v)
    {
        finish();

    }

    /*
    * 添加到通讯录
    * */
    public void add(View v)
    {
        String name = editName.getText().toString().trim();
        String number = editNumber.getText().toString().trim();
        String depart = editDepart.getText().toString().trim();
        String gender = selectedRadio.getText().toString().trim();
        Contract contract = new Contract();

        contract.setDepartment(depart);
        contract.setGender(gender);
        contract.setCatalogId(selectSpinnerId);
        contract.setName(name);
        contract.setPhoneNum(number);

        //ToastUtils.makeText(AddActivity.this,"name="+name+",number="+number+",depart="+depart+",gender="+gender+"selectSpinnerId="+selectSpinnerId);

        ContractDao.add(contract,AddActivity.this);

        ToastUtils.makeText(AddActivity.this,"添加成功");

        finish();

    }


}
