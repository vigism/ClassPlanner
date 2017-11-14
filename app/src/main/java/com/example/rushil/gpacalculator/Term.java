package com.example.rushil.gpacalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Rushil on 5/7/2017.
 */

public class Term {

    private String name;
    private double totalGPA;
    private int totalUnits;
    int id;

    public Term(int id, String name){

        this.name=name;
        this.id=id;
        totalGPA=0;
        totalUnits=0;
    }

    public Term(String name){
        this.name=name;
        totalGPA=0;
        totalUnits=0;
    }

    public int getID(){
        return id;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setId(int id){
        this.id=id;
    }

    public double getTotalGPA() {
        return totalGPA;
    }

    public void setTotalGPA(double totalGPA) {
        this.totalGPA = totalGPA;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }

    public int getId() {
        return id;
    }
}
