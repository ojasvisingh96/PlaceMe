package com.example.ojasvisingh.placeme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OjasviSingh on 13-03-2018.
 */

public class MyOtherAdapter extends RecyclerView.Adapter<MyOtherAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView username;
        public TextView rollno;
        public TextView degree;
        public TextView branch;
        public TextView cgpa;
        public TextView defaulter;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            username = (TextView) v.findViewById(R.id.username);
            rollno = (TextView) v.findViewById(R.id.rollno);
            degree = (TextView) v.findViewById(R.id.degree);
            branch = (TextView) v.findViewById(R.id.branch);
            cgpa = (TextView) v.findViewById(R.id.cgpa);
            defaulter = (TextView) v.findViewById(R.id.defaulter);
        }
    }

    public void add(int position, ArrayList<String> item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyOtherAdapter(ArrayList<ArrayList<String>> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyOtherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.other_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyOtherAdapter.ViewHolder vh = new MyOtherAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyOtherAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        final ArrayList<String> value = values.get(position);

        holder.username.setText(value.get(0));
        holder.rollno.setText(value.get(1));
        holder.degree.setText(value.get(2));
        holder.branch.setText(value.get(3));
        holder.cgpa.setText(value.get(4));
        holder.defaulter.setText(value.get(5));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
