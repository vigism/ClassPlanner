package com.example.rushil.gpacalculator;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Rushil on 7/9/2017.
 */

public class MathUtil {

    public static double getGPAOf(String grade) {

        if(grade==null){
            return 0;
        }
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;
            case "D":
                return 1.0;
            case "F":
                return 0;
            case "W":
                return 5;
            default:
                break;
        }
        return 0;
    }



    public static int getTermUnits(List<Class> classList){
        int totalUnits=0;
        for(int i=0;i<classList.size();i++){
            totalUnits+=classList.get(i).getUnits();
        }
        return totalUnits;
    }

    public static double getTermGPA(List<Class> classList){
        int totalUnits=getTermUnits(classList);
        double gradePoints=0;
        for(int i=0;i<classList.size();i++){
            double gradePoint=classList.get(i).getUnits()*getGPAOf(classList.get(i).getGrade());
            gradePoints+=gradePoint;
        }
        double finalGPA=gradePoints/totalUnits;
        return finalGPA;
    }

    public static int getTotalUnits(List<Term> termList){
        int totalUnits=0;
        for(int i=0;i<termList.size();i++){
           totalUnits+=termList.get(i).getTotalUnits();
        }
        return totalUnits;
    }

    public static double getTotalGPA(List<Term> termList){
        int totalUnits=getTotalUnits(termList);
        double gradePoints=0;
        for(int i=0;i<termList.size();i++){
            double gradePoint=termList.get(i).getTotalUnits()*termList.get(i).getTotalGPA();
            gradePoints+=gradePoint;
        }
        double finalGPA=gradePoints/totalUnits;
        return finalGPA;
    }

}
