package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OjasviSingh on 30-04-2018.
 */

public class QuestionBankAdapter extends RecyclerView.Adapter<QuestionBankAdapter.ViewHolder> {

    private ArrayList<ArrayList<String>> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView question;
        public TextView answer;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            question = (TextView) v.findViewById(R.id.question_bank_question);
            answer = (TextView) v.findViewById(R.id.question_bank_answer);
        }


    }

    public QuestionBankAdapter(ArrayList<ArrayList<String>> myDataset) {
        values = myDataset;
    }

    public void add(int position, ArrayList<String> item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public QuestionBankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout_question_bank, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionBankAdapter.ViewHolder holder, int position) {

        final ArrayList<String> value = values.get(position);

        holder.question.setText(value.get(0));
        holder.answer.setText(value.get(1));


    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
