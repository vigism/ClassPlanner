package com.example.rushil.gpacalculator;

/**
 * Created by Rushil on 5/7/2017.
 */

public class Class  {

    private String name;
    private String grade;
    private int units;
    private int uniqId;
    private int termId;

    public Class(){
        name="";
        grade="";
        units=0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setUniqId(int id){
        this.uniqId=id;
    }
    public int getUniqId(){
        return this.uniqId;
    }

    public void setTermId(int termId){
        this.termId=termId;
    }
    public int getTermId(){
        return termId;
    }
}
