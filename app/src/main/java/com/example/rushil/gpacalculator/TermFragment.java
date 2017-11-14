package com.example.rushil.gpacalculator;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rushil on 5/7/2017.
 */

public class TermFragment extends Fragment {

    private static final String ARG_TERM_ID="arg_term_id_key";

    RecyclerView classRecyclerView;
    ClassLab classLab;
    ClassAdapter classAdapter;
    Term term;
    int termId;

    public static TermFragment newInstance(int id){
        Bundle args=new Bundle();
        args.putSerializable(ARG_TERM_ID,id);
        TermFragment fragment=new TermFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        int termId=(int)getArguments().getSerializable(ARG_TERM_ID);
        this.termId=termId;
        DatabaseManager db= new DatabaseManager(getContext());
        TermLab.clear();
        term=TermLab.getTermLab(getActivity().getApplicationContext(),db).getTerm(termId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view=inflater.inflate(R.layout.term_fragment,container,false);
        ClassLab.clear();
        classLab=ClassLab.getClassLab(getActivity(),term.getID());

        classRecyclerView=(RecyclerView) view.findViewById(R.id.classListRecyclerView);
        classRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Class> classList=classLab.getClassList();
        classAdapter= new ClassAdapter(classList);
        classRecyclerView.setAdapter(classAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_term,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_new_class:
                classLab.addClass(new Class());
                classAdapter.update();
                classRecyclerView.smoothScrollToPosition(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class ClassHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        EditText classNameEditText;
        EditText classUnitsEditText;
        Spinner classGradeSpinner;

        Button removeButton;

        Class thisClass;

        public ClassHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            classNameEditText=(EditText) itemView.findViewById(R.id.classNameEditText);
            classUnitsEditText=(EditText) itemView.findViewById(R.id.classUnitsEditText);
            classGradeSpinner=(Spinner) itemView.findViewById(R.id.classGradeSpinner);
            ArrayAdapter<CharSequence> gradeSpinnerAdapter=ArrayAdapter.createFromResource(getActivity(),
                    R.array.grades_array,android.R.layout.simple_spinner_item);
            gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classGradeSpinner.setAdapter(gradeSpinnerAdapter);
            removeButton=(Button) itemView.findViewById(R.id.removeClassButton);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classLab.removeClass(thisClass);
                    classLab.update();
                    classAdapter.setClassList(classLab.getClassList());
                    classAdapter.notifyItemRemoved(getAdapterPosition());
                }
            });

            classNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    thisClass.setName(classNameEditText.getText().toString());
                    classLab.updateClass(thisClass);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            classUnitsEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(classUnitsEditText.getText().toString().isEmpty()){
                        thisClass.setUnits(0);
                        classLab.updateClass(thisClass);
                    }else {
                        thisClass.setUnits(Integer.parseInt(classUnitsEditText.getText().toString()));
                        classLab.updateClass(thisClass);
                    }
                    }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            classGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    thisClass.setGrade(String.valueOf(parent.getItemAtPosition(position)));
                    classLab.updateClass(thisClass);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void onClick(View view){
            view.setFocusable(true);
        }

        public void bindClass(Class thisClass){
            this.thisClass=thisClass;
            bindItems();
        }

        private void bindItems(){
            classNameEditText.setText(thisClass.getName());
            if(!(thisClass.getUnits()==0)) {
                classUnitsEditText.setText(String.valueOf(thisClass.getUnits()));
            }
            classGradeSpinner.setSelection(((ArrayAdapter)classGradeSpinner.getAdapter()).getPosition(thisClass.getGrade()));

        }
    }

    private class ClassAdapter extends RecyclerView.Adapter<ClassHolder>{

        ArrayList<Class> classList;

        public ClassAdapter(ArrayList<Class> classList){
            this.classList=classList;
        }

        @Override
        public ClassHolder onCreateViewHolder(ViewGroup parent,int viewType){

            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view =inflater.inflate(R.layout.class_list_item,parent,false);
            return new ClassHolder(view);

        }


        @Override
        public void onBindViewHolder(ClassHolder holder, int position){
            holder.bindClass(classList.get(position));
        }

        @Override
        public int getItemCount(){
            return classList.size();
        }

        public void setClassList(ArrayList<Class> classList){
            this.classList=classList;
        }

        public void update(){
            classLab.update();
            this.classList=classLab.getClassList();
            this.notifyDataSetChanged();
        }

    }
}
