package com.example.rushil.gpacalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushil on 6/26/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="GPADATA";
    public static final String TERM_TABLE_NAME="termTable";
    public static final String CLASS_TABLE_NAME="classTable";

    //column names
    private static final String KEY_ID="_id";
    private static final String KEY_NAME="name";
    private static final String KEY_TOTAL_GPA="totalGPA";
    private static final String KEY_TOTAL_UNITS="totalUnits";
    private static final String KEY_UNITS="units";
    private static final String KEY_GPA="gpa";
    private static final String KEY_TERM_ID="termId";
    private static final String KEY_UNIQ_ID="uniqId";



    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TERM_TABLE="create table "+TERM_TABLE_NAME+"("
                +KEY_ID+" integer primary key,"+KEY_NAME+" text,"
                +KEY_TOTAL_UNITS+" integer,"+KEY_TOTAL_GPA+" double"+")";
        db.execSQL(CREATE_TERM_TABLE);
        String CREATE_CLASS_TABLE="create table "+CLASS_TABLE_NAME+"("
                +KEY_UNIQ_ID+" integer primary key,"
                +KEY_TERM_ID+" integer,"+KEY_NAME+" text,"+KEY_UNITS
                +" integer," +KEY_GPA+" text"+")";
        db.execSQL(CREATE_CLASS_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+TERM_TABLE_NAME);
    db.execSQL("drop table if exists "+CLASS_TABLE_NAME);
        onCreate(db);
    }

    public void addTerm(Term term){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,term.getName());
        values.put(KEY_TOTAL_UNITS,term.getTotalUnits());
        values.put(KEY_TOTAL_GPA,term.getTotalGPA());
        db.insert(TERM_TABLE_NAME,null,values);
        db.close();
    }

    public List<Term> getAllTerms(){

        List<Term> termList=new ArrayList<Term>();

        String selectQuery="select * from "+TERM_TABLE_NAME;

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Term term=new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
                term.setTotalUnits(Integer.parseInt(cursor.getString(2)));
                term.setTotalGPA(cursor.getDouble(3));
                termList.add(term);
            }while(cursor.moveToNext());
        }
        return termList;
    }

    public Term getTerm(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TERM_TABLE_NAME, new String[]{ KEY_ID,
                KEY_NAME,KEY_TOTAL_UNITS,KEY_TOTAL_GPA},KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        Term term =new Term(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        term.setTotalUnits(Integer.parseInt(cursor.getString(2)));
        term.setTotalGPA(cursor.getDouble(3));

        return term;
    }

    public void deleteTerm(Term term){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TERM_TABLE_NAME,KEY_ID+" =?", new String[]{String.valueOf(term.getID())});
        db.close();
        deleteClasses(term.getID());
    }

    public void addClass(Class c,int termId){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,c.getName());
        values.put(KEY_UNITS,c.getUnits());
        values.put(KEY_GPA,c.getGrade());
        values.put(KEY_TERM_ID,termId);
        db.insert(CLASS_TABLE_NAME,null,values);
    }

    public ArrayList<Class> getClasses(int termId){
        ArrayList<Class> classList=new ArrayList<>();
        String selectQuery="select "+KEY_UNIQ_ID+","+KEY_TERM_ID+","+KEY_NAME+","+KEY_UNITS+","+KEY_GPA+" from "+ CLASS_TABLE_NAME
                +" where "+KEY_TERM_ID+" = "+String.valueOf(termId);
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Class newClass=new Class();
                newClass.setUniqId(Integer.parseInt(cursor.getString(0)));
                newClass.setTermId(Integer.parseInt(cursor.getString(1)));
                newClass.setName(cursor.getString(2));
                newClass.setUnits(Integer.parseInt(cursor.getString(3)));
                newClass.setGrade(cursor.getString(4));
                classList.add(newClass);
            }while(cursor.moveToNext());
        }
        return classList;
    }

    public void deleteClass(Class c){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(CLASS_TABLE_NAME,KEY_UNIQ_ID+" =?", new String[]{String.valueOf(c.getUniqId())});
        db.close();
    }

    public void deleteClasses(int termId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(CLASS_TABLE_NAME,KEY_TERM_ID+" =?", new String[]{String.valueOf(termId)});
        db.close();
    }

    public void updateClass(Class c,int termId){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,c.getName());
        values.put(KEY_UNITS,c.getUnits());
        values.put(KEY_GPA,c.getGrade());
        values.put(KEY_TERM_ID,termId);

        db.update(CLASS_TABLE_NAME,values,KEY_UNIQ_ID+" =?",new String[] {String.valueOf(c.getUniqId())});
        db.close();
    }

    public void updateTotals(int totalUnits,double totalGPA,int termId){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_TOTAL_UNITS,totalUnits);
        values.put(KEY_TOTAL_GPA,totalGPA);
        db.update(TERM_TABLE_NAME,values,KEY_ID+" =?",new String[] {String.valueOf(termId)});
        db.close();
    }
}
