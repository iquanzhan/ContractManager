package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.dao.ContractDao;
import com.chengxiaoxiao.com.contractmanager.domain.Contract;
import com.chengxiaoxiao.com.contractmanager.utls.ToastUtils;

import java.util.List;

public class MainActivity extends Activity
{
    private EditText editSearch;
    private ListView listContract;
    private LinearLayout lineCata;

    private List<Contract> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listContract = (ListView) findViewById(R.id.listView);
        lineCata = (LinearLayout) findViewById(R.id.lineCata);


        //我的群组打开
        lineCata.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, CatalogActivity.class));
            }
        });

        editSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                list = ContractDao.getContract(editSearch.getText().toString(), MainActivity.this);
                listContract.setAdapter(new MyAdapter());

            }
        });


        //设置listview
        list = ContractDao.get(MainActivity.this);

        listContract.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);


            }
        });

        listContract.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {


                        switch (item.getItemId())
                        {
                            case R.id.action_open:
                                //打电话

                                String number = list.get(position).getPhoneNum();

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + number);
                                intent.setData(data);

                                startActivity(intent);
                                break;
                            case R.id.action_edit:
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("id", list.get(position).getId());
                                startActivity(intent1);
                                break;
                            case R.id.action_del:
                                //删除

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("重要操作");
                                builder.setMessage("确定要删除吗？");
                                builder.setNegativeButton("取消", null);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        int id = list.get(position).getId();
                                        ContractDao.delete(id, MainActivity.this);
                                        ToastUtils.makeText(MainActivity.this, "删除成功");

                                        //设置listview
                                        list = ContractDao.get(MainActivity.this);
                                        listContract.setAdapter(new MyAdapter());

                                    }
                                });


                                builder.show();


                                break;
                            case R.id.action_send:
                                //发送到桌面
                                break;
                        }


                        return false;
                    }
                });


                popupMenu.show();


                return true;
            }
        });

        listContract.setAdapter(new MyAdapter());


    }

    @Override
    protected void onRestart()
    {
        super.onRestart();


        //设置listview
        list = ContractDao.get(MainActivity.this);
        listContract.setAdapter(new MyAdapter());


    }

    /*
        *
        * 联系人的Adapter
        *
        * */
    class MyAdapter extends BaseAdapter
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
            if (convertView == null)
            {
                v = View.inflate(MainActivity.this, R.layout.list_item_contract, null);
            } else
            {
                v = convertView;
            }

            TextView textName = (TextView) v.findViewById(R.id.textName);
            TextView textDepart = (TextView) v.findViewById(R.id.textDepart);
            TextView textNumber = (TextView) v.findViewById(R.id.textNumber);

            Contract contract = list.get(position);
            textName.setText(contract.getName());
            textDepart.setText(contract.getDepartment());
            textNumber.setText(contract.getPhoneNum());

            return v;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode ==KeyEvent.KEYCODE_MENU)
        {
            startActivity(new Intent(MainActivity.this,SettingActivity.class));

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
        * 添加
        * */
    public void addContract(View v)
    {

        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }
}
