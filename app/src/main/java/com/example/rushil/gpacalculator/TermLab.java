package com.example.rushil.gpacalculator;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rushil on 5/7/2017.
 */

public class TermLab {

    private static TermLab termLab;
    private  List<Term> termList;
    private DatabaseManager db;


    private TermLab(Context context, DatabaseManager db){
        termList=db.getAllTerms();
        this.db=db;
    }

    public static void clear(){
        termLab=null;
    }

    public static TermLab getTermLab(Context context,DatabaseManager db){

        if(termLab==null){
            return new TermLab(context,db);
        }

        return termLab;

    }

    public List<Term> getTerms(){
        return termList;
    }

    public void update(){
        this.termList=db.getAllTerms();
    }

    public Term getTerm(int id){
        return db.getTerm(id);
    }

    public void addTerm(){
        db.addTerm(new Term("New Term"));
    }

    public void addTerm(String name){
        db.addTerm(new Term(name));
    }

    public void deleteTerm(Term term){
        db.deleteTerm(term);
    }

    public int getTotalUnits(){
        return MathUtil.getTotalUnits(termList);
    }

    public double getTotalGPA(){
        return MathUtil.getTotalGPA(termList);
    }


}
