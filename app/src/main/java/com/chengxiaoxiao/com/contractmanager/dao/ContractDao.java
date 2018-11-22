package com.chengxiaoxiao.com.contractmanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.chengxiaoxiao.com.contractmanager.domain.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoXiao
 * @version $Rev$
 */

public class ContractDao
{
    //this.getFilesDir().getAbsolutePath()+"/contract.db";
    private static final String PATH = "/data/data/cn.edu.qtc.crpmanager/files/address.db";


    //根据姓名/电话号码/职位，获取信息
    public static List<Contract> getContract(String text, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        if (TextUtils.isEmpty(text))
        {
            return get(context);
        }

        List<Contract> list = new ArrayList<Contract>();
        String sql = "";
        if (text.matches("\\d+"))
        {
            //数字电话号码
            sql = "select * from contract where phoneNum like ?";
        } else
        {
            //职位
            sql = "select * from contract where department like ?";

            Cursor cursor = database.rawQuery(sql, new String[]{"%" + text + "%"});

            while (cursor.moveToNext())
            {
                Contract contract = new Contract();
                contract.setId(cursor.getInt(0));
                contract.setName(cursor.getString(1));
                contract.setPhoneNum(cursor.getString(2));
                contract.setCatalogId(cursor.getInt(3));
                contract.setGender(cursor.getString(4));
                contract.setDepartment(cursor.getString(5));

                list.add(contract);
            }

            cursor.close();

            //姓名
            sql = "select * from contract where Name like ?";

        }

        Cursor cursor = database.rawQuery(sql, new String[]{"%" + text + "%"});


        while (cursor.moveToNext())
        {
            Contract contract = new Contract();
            contract.setId(cursor.getInt(0));
            contract.setName(cursor.getString(1));
            contract.setPhoneNum(cursor.getString(2));
            contract.setCatalogId(cursor.getInt(3));
            contract.setGender(cursor.getString(4));
            contract.setDepartment(cursor.getString(5));

            list.add(contract);
        }

        cursor.close();

        return list;


    }


    /*
       *获取单个人的详细信息
       * */
    public static Contract getItem(int id, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        String sql = "select * from contract where id=?";


        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});


        Contract contract = null;
        if (cursor.moveToNext())
        {
            contract = new Contract();

            contract.setId(cursor.getInt(0));
            contract.setName(cursor.getString(1));
            contract.setPhoneNum(cursor.getString(2));
            contract.setCatalogId(cursor.getInt(3));
            contract.setGender(cursor.getString(4));
            contract.setDepartment(cursor.getString(5));


        }
        cursor.close();

        return contract;

    }

    /*
    *获取所有的通讯录
    * */
    public static List<Contract> get(Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        String sql = "select * from contract";


        Cursor cursor = database.rawQuery(sql, new String[]{});

        List<Contract> list = new ArrayList<Contract>();

        while (cursor.moveToNext())
        {
            Contract contract = new Contract();

            contract.setId(cursor.getInt(0));
            contract.setName(cursor.getString(1));
            contract.setPhoneNum(cursor.getString(2));
            contract.setCatalogId(cursor.getInt(3));
            contract.setGender(cursor.getString(4));
            contract.setDepartment(cursor.getString(5));


            list.add(contract);
        }
        cursor.close();

        return list;

    }


    /*
    * 添加通讯录
    * */
    public static void add(Contract contract, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);


        String sql = "insert into contract(name,phonenum,catalogId,Gender,department) values(?,?,?,?,?)";


        database.execSQL(sql, new Object[]{contract.getName(), contract.getPhoneNum(), contract.getCatalogId(), contract.getGender(), contract.getDepartment()});


    }

    /*
    * 修改通讯录
    * */
    public static void update(Contract contract, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        String sql = "update contract set name=?,phoneNum=?,catalogId=?,gender=?,department=? where id=?";

        database.execSQL(sql, new Object[]{contract.getName(), contract.getPhoneNum(), contract.getCatalogId(), contract.getGender(), contract.getDepartment(), contract.getId()});
    }

    /*
    * 删除通讯录
    * */
    public static void delete(int id, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);


        String sql = "delete from contract where id=?";

        database.execSQL(sql, new Object[]{id});
    }
}
