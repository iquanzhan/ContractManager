package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.dao.CatalogDao;
import com.chengxiaoxiao.com.contractmanager.dao.ContractDao;
import com.chengxiaoxiao.com.contractmanager.domain.Catalog;
import com.chengxiaoxiao.com.contractmanager.domain.Contract;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SettingActivity extends Activity
{
    private ListView listView;
    private String[] functions = {"修改密码", "导出到存储设备", "从存储设备导入", "软件版本", "关于我们"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new MyAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                switch (position)
                {
                    case 0:
                        startActivity(new Intent(SettingActivity.this, ResetPwdActivity.class));
                        break;
                    case 1:
                        //导出到存储目录
                        export();
                        break;

                    case 2:
                        //导入到手机内

                        importContract();
                        break;

                    case 3:
                        builder.setMessage("已经是最新版本了！");
                        builder.setPositiveButton("确定", null);
                        builder.show();
                        break;
                    case 4:
                        builder.setMessage("作者：成笑笑\n\n山东潍坊\n\n2017.06.29");
                        builder.setPositiveButton("确定", null);
                        builder.show();
                        break;

                }
            }
        });

    }

    /*
    * 导入
    * */
    private void importContract()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK) {

            if(data==null)
            {
                return;
            }
            Uri uri = data.getData();
            String path =uri.getPath().toString();
            //Toast.makeText(this, "文件路径："+uri.getPath().toString(), Toast.LENGTH_SHORT).show();
            File file = new File(path);

            try
            {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = null;
                br.readLine();
                while ((line = br.readLine()) != null)
                {
                    // 序号----姓名----性别----手机号码----所属群组

                    String[] lines = line.split("----");

                    int id =CatalogDao.equalName(lines[4],getApplicationContext());

                    Contract  contract = new Contract();
                    contract.setName(lines[1]);
                    contract.setGender(lines[2]);
                    contract.setPhoneNum(lines[3]);
                    contract.setCatalogId(id);

                    ContractDao.add(contract,getApplicationContext());

                    ToastUtils.makeText(getApplicationContext(),"导入成功");
                }







            } catch (Exception e)
            {

            }




        }
    }

    /*
        * 导出到存储目录
        * */
    private void export()
    {
        String status = Environment.getExternalStorageState();

        if (!status.equals(Environment.MEDIA_MOUNTED))
        {
            ToastUtils.makeText(SettingActivity.this, "SD卡未挂载");
            return;
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHssmm");
        String dates = format.format(date);


        File file = new File(Environment.getExternalStorageDirectory(), dates + ".txt");


        //设置listview
        List<Contract> list = ContractDao.get(SettingActivity.this);

        try
        {
            OutputStream out = new FileOutputStream(file);

            StringBuilder sb = new StringBuilder();

            sb.append("序号----姓名----性别----手机号码----所属群组\r\n");

            for (int i = 0; i < list.size(); i++)
            {

                Contract contract = list.get(i);
                Catalog cata = CatalogDao.getItem(contract.getCatalogId(), SettingActivity.this);
                sb.append(contract.getId() + "----" + contract.getName() + "----" + contract.getGender() + "----" + contract.getPhoneNum() + "----" + cata.getcName() + "\n");

            }

            String allaContract = sb.toString();

            out.write(allaContract.getBytes());
            out.close();

            ToastUtils.makeText(SettingActivity.this, "已经导出到SD卡根目录，文件名" + file.getName());


        } catch (Exception e)
        {


        }


    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return functions.length;
        }

        @Override
        public Object getItem(int position)
        {
            return functions[position];
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
            if (convertView == null)
            {
                v = View.inflate(SettingActivity.this, R.layout.list_item_setting, null);
            } else
            {
                v = convertView;
            }

            TextView textName = (TextView) v.findViewById(R.id.textName);

            textName.setText(functions[position]);

            return v;
        }
    }
}
