package com.example.rushil.gpacalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Rushil on 7/2/2017.
 */

public class TermNameFragment extends DialogFragment {

    private static final String ARG_NAME="name";
    public static final String EXTRA_NAME="nameEXTRA";

    private EditText newTermEditText;

    public static TermNameFragment newInstance(){
        TermNameFragment fragment=new TermNameFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.new_term_dialog,null);
        newTermEditText=(EditText)v.findViewById(R.id.dialog_new_term);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Enter term name: ")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                String name=newTermEditText.getText().toString();
                                sendResult(Activity.RESULT_OK,name);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode,String name){
        if(getTargetFragment()==null){
            return;
        }

        Intent intent=new Intent();
        intent.putExtra(EXTRA_NAME,name);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
