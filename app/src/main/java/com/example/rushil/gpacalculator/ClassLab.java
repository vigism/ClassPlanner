package com.example.rushil.gpacalculator;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Rushil on 5/7/2017.
 */

public class ClassLab {

    private ArrayList<Class> classList;
    private static ClassLab classLab;
    private DatabaseManager db;
    private int termId;


    private ClassLab(Context context,int termId){
        db=new DatabaseManager(context);
        this.termId=termId;
        classList=db.getClasses(termId);
        this.classLab=this;


    }

    public static ClassLab getClassLab(Context context,int termId){
        if (classLab==null){
            return new ClassLab(context,termId);
        }else{
            return classLab;
        }
    }

    public static void clear(){
        classLab=null;
    }

    public void removeClass(Class c){


        db.deleteClass(c);
    }

    public void addClass(Class c){
        db.addClass(c,termId);
    }

    public ArrayList<Class> getClassList(){

        return classList;
    }

    public void update(){
        this.classList=db.getClasses(termId);
    }

    public void updateClass(Class c){
        db.updateClass(c,termId);
        updateTotals();
    }

    public void updateTotals(){
        int totalUnits=MathUtil.getTermUnits(classList);
        double totalGPA=MathUtil.getTermGPA(classList);
        db.updateTotals(totalUnits,totalGPA,termId);
    }
}
