package com.chengxiaoxiao.com.contractmanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chengxiaoxiao.com.contractmanager.domain.Catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoXiao
 * @version $Rev$
 */

public class CatalogDao
{
    /*
    *获取所有的群组
    * */
    public static List<Catalog> get(Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        String sql = "select * from catalog";

        Cursor cursor = database.rawQuery(sql, new String[]{});

        List<Catalog> list = new ArrayList<Catalog>();

        while (cursor.moveToNext())
        {
            Catalog catalog = new Catalog();

            catalog.setId(cursor.getInt(0));
            catalog.setcName(cursor.getString(1));


            list.add(catalog);
        }
        cursor.close();

        return list;

    }


    /*
    * 添加群组
    * */
    public static void add(Catalog catalog, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);


        String sql = "insert into catalog(cname) values(?)";


        database.execSQL(sql, new Object[]{catalog.getcName()});


    }

    /*
    * 修改群组
    * */
    public static void update(Catalog catalog, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        String sql = "update catalog set cname=? where id=?";

        database.execSQL(sql, new Object[]{catalog.getcName(), catalog.getId()});
    }

    /*
    * 删除群组
    * */
    public static void delete(int id, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);


        String sql = "delete from catalog where id=?";

        database.execSQL(sql, new Object[]{id});
    }

    /*
       *获取单个群组的详细信息
       * */
    public static Catalog getItem(int id, Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        String sql = "select * from catalog where id=?";


        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});


        Catalog catalog = null;
        if (cursor.moveToNext())
        {
            catalog = new Catalog();

            catalog.setId(cursor.getInt(0));
            catalog.setcName(cursor.getString(1));

        }
        cursor.close();

        return catalog;

    }

    /*
       *获取收个详细信息
       * */
    public static Catalog getTopItem(Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        String sql = "select * from catalog";


        Cursor cursor = database.rawQuery(sql, new String[]{});


        Catalog catalog = null;
        if (cursor.moveToNext())
        {
            catalog = new Catalog();

            catalog.setId(cursor.getInt(0));
            catalog.setcName(cursor.getString(1));

        }
        cursor.close();

        return catalog;

    }

    /*
    * 根据name返回ID
    * */
    public static int equalName(String name,Context context)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        String sql = "select id from catalog where cname=?";
        Cursor cursor = database.rawQuery(sql, new String[]{name});

        int id = 0;
        if (cursor.moveToNext())
        {
            id=cursor.getInt(0);

        }
        cursor.close();


        if (id==0)
        {
            Catalog log = new Catalog();

            log.setcName(name);
            add(log,context);

            return equalName(name,context);
        }


        return id;

    }


    /*
    * 删除群组后，默认置默认群组名称
    * */
    public static void updateCatalogByDelete(int id, Context context)
    {
        delete(id, context);
        Catalog catalog =getTopItem(context);
        if (catalog==null)
        {
            catalog = new Catalog();
            catalog.setId(0);
        }

        String path = context.getFilesDir().getAbsolutePath() + "/contract.db";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        String sql = "update contract set catalogId=? where catalogId=?";

        database.execSQL(sql, new Object[]{catalog.getId(), id});

    }
}
