package com.example.rushil.gpacalculator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rushil on 5/7/2017.
 */

public class TermListFragment extends Fragment {

    private final static String NEW_TERM_FRAGMENT="newTerm";
    private static final int REQUEST_NAME=0;

    private RecyclerView termRecyclerView;
    private TermAdapter listAdapter;
    private TermLab termLab;

    private TextView totalUnitsTextView;
    private TextView totalGPATextView;

    List<Term> termList;

    @Override
    public void onResume(){
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view=inflater.inflate(R.layout.list_fragment,container,false);
        setHasOptionsMenu(true);
        termRecyclerView=(RecyclerView) view.findViewById(R.id.termRecyclerView);
        termRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));

        DatabaseManager db=new DatabaseManager(getContext());

        this.termLab=TermLab.getTermLab(getActivity().getApplicationContext(),db);
        termList=termLab.getTerms();
        listAdapter=new TermAdapter(termList);
        termRecyclerView.setAdapter(listAdapter);

        totalUnitsTextView=(TextView)view.findViewById(R.id.totalUnitsTextView);
        totalUnitsTextView.setText(String.valueOf(termLab.getTotalUnits()));
        totalGPATextView=(TextView)view.findViewById(R.id.totalGPATextView);
        totalGPATextView.setText(String.valueOf(termLab.getTotalGPA()));
        if(totalGPATextView.getText()=="NaN"){
            totalGPATextView.setText("0.0");
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.term_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_new_term:
                FragmentManager fm=getFragmentManager();
                TermNameFragment dialog=TermNameFragment.newInstance();
                dialog.setTargetFragment(TermListFragment.this,REQUEST_NAME);
                dialog.show(fm,NEW_TERM_FRAGMENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_NAME){
            String name=(String)data.getSerializableExtra(TermNameFragment.EXTRA_NAME);
            termLab.addTerm(name);
            termList=termLab.getTerms();
            listAdapter.update();
            termRecyclerView.scrollToPosition(0);
        }
    }
    private class TermHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Term thisTerm;
        Button removeButton;
        TextView termNameTextView;
        TextView termUnitsTextView;
        TextView termGPATextView;

        public TermHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            removeButton=(Button)itemView.findViewById(R.id.removeTermButton);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    termLab.deleteTerm(thisTerm);
                    termLab.update();
                    listAdapter.setTermList(termLab.getTerms());
                    listAdapter.notifyItemRemoved(getAdapterPosition());
                }
            });

            termNameTextView=(TextView) itemView.findViewById(R.id.termNameTextView);
            termGPATextView=(TextView) itemView.findViewById(R.id.termGPATextView);
            termUnitsTextView=(TextView) itemView.findViewById(R.id.termUnitsTextView);

        }

        @Override
        public void onClick(View view){
            Intent i=TermActivity.newIntent(getActivity(),thisTerm.getID());
            startActivity(i);
        }

        public void bindTerm(Term term){
            thisTerm=term;
            termNameTextView.setText(thisTerm.getName());
            termGPATextView.setText(String.valueOf(thisTerm.getTotalGPA()));
            termUnitsTextView.setText(String.valueOf(thisTerm.getTotalUnits()));
        }

    }

    private class TermAdapter extends RecyclerView.Adapter<TermHolder>{

        private List<Term> termList;

        public TermAdapter(List<Term> termList){
            this.termList=termList;
        }

        @Override
        public TermHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item,parent,false);
            return new TermHolder(view);
        }

        @Override
        public void onBindViewHolder(TermHolder holder, int position){
            holder.bindTerm(termList.get(position));

        }

        public void setTermList(List<Term> termList){
            this.termList=termList;
        }

        @Override
        public int getItemCount(){
            return termList.size();
        }

        public void update(){
            termLab.update();
            this.termList=termLab.getTerms();
            this.notifyDataSetChanged();

        }



    }

}
