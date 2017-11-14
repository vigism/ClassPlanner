package com.example.rushil.gpacalculator;

        import android.support.v4.app.*;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment= new TermListFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
