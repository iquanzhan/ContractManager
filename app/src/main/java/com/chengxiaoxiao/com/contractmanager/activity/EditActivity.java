package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.Intent;
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

public class EditActivity extends Activity
{
    private EditText editName;
    private EditText editNumber;
    private Spinner spinner;
    private EditText editDepart;
    private RadioButton radioBoy;
    private RadioButton radioGril;
    private RadioGroup radioGroup;

    private RadioButton selectedRadio;

    private int selectSpinnerId;
    private int id;

    private List<Catalog> list;
    private Contract contract;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editName = (EditText) findViewById(R.id.editName);
        editNumber = (EditText) findViewById(R.id.editNumber);
        editDepart = (EditText) findViewById(R.id.editDepart);

        spinner = (Spinner) findViewById(R.id.spinner);
        radioBoy = (RadioButton) findViewById(R.id.radioBoy);
        radioGril = (RadioButton) findViewById(R.id.radioGril);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        //初始化数据
        list = CatalogDao.get(EditActivity.this);
        spinner.setAdapter(new SpinnerAdapter());


        Intent intent = getIntent();

        id = intent.getIntExtra("id", 0);

        //初始化数据
        initData(id);

    }

    private void initData(int id)
    {
        contract = ContractDao.getItem(id, EditActivity.this);

        Catalog catalog = CatalogDao.getItem(contract.getCatalogId(), EditActivity.this);

        editName.setText(contract.getName());
        editNumber.setText(contract.getPhoneNum());
        editDepart.setText(contract.getDepartment());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                selectedRadio = (RadioButton) findViewById(checkedId);
            }
        });

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

        //设置Spinner默认选中值
        SpinnerAdapter adapter = (SpinnerAdapter) spinner.getAdapter();

        int k = adapter.getCount();
        for (int i = 0; i < k; i++)
        {
            if (catalog.getcName().equals(adapter.getItem(i).toString()))
            {
                spinner.setSelection(i, true);
                break;
            }
        }

        //设置redio选中状态
        if ("男".equals(contract.getGender()))
        {
            radioBoy.setChecked(true);
        } else
        {
            radioGril.setChecked(true);

        }


    }


    public class SpinnerAdapter extends BaseAdapter
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
            TextView textView = new TextView(EditActivity.this);
            textView.setText(list.get(position).getcName());

            return textView;
        }
    }

    /*
        *返回
        * */
    public void backHome(View v)
    {
        finish();
    }

    /*
    * 确认进行修改
    * */
    public void edit(View v)
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
        contract.setId(id);


        //ToastUtils.makeText(EditActivity.this,"name:"+name+",number="+number+",depart="+depart+",gender="+gender+",cataId="+selectSpinnerId);

        ContractDao.update(contract,EditActivity.this);
        ToastUtils.makeText(EditActivity.this,"修改成功");
        finish();


    }
}
