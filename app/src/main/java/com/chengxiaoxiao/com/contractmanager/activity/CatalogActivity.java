package com.chengxiaoxiao.com.contractmanager.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chengxiaoxiao.com.contractmanager.R;
import com.chengxiaoxiao.com.contractmanager.dao.CatalogDao;
import com.chengxiaoxiao.com.contractmanager.domain.Catalog;

import java.util.List;

public class CatalogActivity extends Activity
{

    private ListView listView;

    private List<Catalog> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        list = CatalogDao.get(CatalogActivity.this);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new MyAdapter());

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(CatalogActivity.this);

                builder.setTitle("重要操作");
                builder.setMessage("确定要删除本群组吗？");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Catalog cata =list.get(position);

                        CatalogDao.updateCatalogByDelete(cata.getId(),CatalogActivity.this);


                        list = CatalogDao.get(CatalogActivity.this);

                        listView.setAdapter(new MyAdapter());

                    }
                });
                builder.setNegativeButton("取消",null);

                builder.show();



                return false;
            }
        });

    }

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
                v = View.inflate(CatalogActivity.this, R.layout.list_item_catalog, null);
            }
            else
            {
                v = convertView;
            }
            TextView editName = (TextView) v.findViewById(R.id.editName);

            editName.setText(list.get(position).getcName());


            return v;
        }
    }


    /*
    * 添加群组
    * */
    public void addCatalog(View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CatalogActivity.this);

        View v = View.inflate(CatalogActivity.this, R.layout.activity_catalog_add, null);

        final EditText editName = (EditText) v.findViewById(R.id.editName);

        builder.setTitle("添加群组");

        builder.setNegativeButton("取消", null);

        builder.setPositiveButton("添加", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Catalog catalog = new Catalog();
                catalog.setcName(editName.getText().toString().trim());
                CatalogDao.add(catalog, CatalogActivity.this);

                list = CatalogDao.get(CatalogActivity.this);
                listView.setAdapter(new MyAdapter());
            }
        });


        builder.setView(v);

        builder.show();
    }
}
