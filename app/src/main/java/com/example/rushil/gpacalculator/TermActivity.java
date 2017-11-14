package com.example.rushil.gpacalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by Rushil on 5/7/2017.
 */

public class TermActivity extends AppCompatActivity {

    public static final String EXTRA_TERM_ID="extra_term_id_key";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);

        int id=(int) getIntent().getSerializableExtra(EXTRA_TERM_ID);
        if(fragment==null){
            fragment=TermFragment.newInstance(id);
            fm.beginTransaction().add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext,int id){
        Intent intent=new Intent(packageContext,TermActivity.class);
        intent.putExtra(EXTRA_TERM_ID,id);
        return intent;
    }
}
