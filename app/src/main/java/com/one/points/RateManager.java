package com.one.points;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Telephony;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
        private DBHelper dbHelper;
        private String TBNAME;
        SQLiteDatabase db;

        public RateManager(Context context){
                dbHelper = new DBHelper(context);
                TBNAME = dbHelper.TB_NAME;
        }

        public void add(RateItem item){     //添加单行
                db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("curname",item.getCurName());
                values.put("currate",item.getCurRate());

                db.insert(TBNAME,null,values);
                db.close();
        }

        public void addAll(List<RateItem> list){   //添加所有
                db = dbHelper.getWritableDatabase();
                for(RateItem item:list){
                        ContentValues values = new ContentValues();
                        values.put("curname",item.getCurName());
                        values.put("currate",item.getCurRate());
                        db.insert(TBNAME,null,values);
                }
                db.close();
        }

        public void delete(int id){            //删除单行
                db = dbHelper.getWritableDatabase();
                db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
                db.close();
        }

        public void deleteAll(){               //删除所有
                db = dbHelper.getWritableDatabase();
                db.delete(TBNAME,null,null);
                db.close();
        }

        public void update(RateItem item){       //修改
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("curname",item.getCurName());
                values.put("currate",item.getCurRate());
                db.update(TBNAME,values,"ID=?",new String[]{String.valueOf(item.getId())});
                db.close();
        }

        public List<RateItem> listAll(){                //查询所有
                List<RateItem> ratelist = null;
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
                if(cursor!=null){
                        ratelist = new ArrayList<RateItem>();
                        while(cursor.moveToNext()) {
                                RateItem item = new RateItem();
                                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                                item.setCurName(cursor.getString(cursor.getColumnIndex("curname")));
                                item.setCurRate(cursor.getString(cursor.getColumnIndex("currate")));
                                ratelist.add(item);
                        }
                        cursor.close();
                }
                db.close();
                return ratelist;
        }

        public RateItem findById(int id){
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null);
                RateItem item = null;
                if(cursor!=null&&cursor.moveToFirst()){
                        item = new RateItem();
                        item.setId((cursor.getInt(cursor.getColumnIndex("ID"))));
                        item.setCurName(cursor.getString(cursor.getColumnIndex("curname")));
                        item.setCurRate(cursor.getString(cursor.getColumnIndex("currate")));
                        cursor.close();
                }
                db.close();
                return item;
        }
}
