package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OjasviSingh on 13-03-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ArrayList<String>> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView name;
        public TextView profile;
        public TextView ctc;
        public TextView location;
        public TextView date;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            name = (TextView) v.findViewById(R.id.name);
            profile = (TextView) v.findViewById(R.id.profile);
            ctc = (TextView) v.findViewById(R.id.ctc);
            location = (TextView) v.findViewById(R.id.location);
            date = (TextView) v.findViewById(R.id.date);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("context",view.getContext().getClass().getName());
            if(view.getContext().getClass()==Student.class)
            {
                Log.d("class","student");
                Intent jobDetails=new Intent(view.getContext(),JobDetails.class);
                jobDetails.putExtra("comp",name.getText().toString());
                view.getContext().startActivity(jobDetails);

            }
            else
            {
                Intent jobDetails=new Intent(view.getContext(),AdminJobDetails.class);
                jobDetails.putExtra("comp",name.getText().toString());
                view.getContext().startActivity(jobDetails);

            }
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
    public MyAdapter(ArrayList<ArrayList<String>> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        final ArrayList<String> value = values.get(position);

        holder.name.setText(value.get(0));
        holder.profile.setText(value.get(1));
        holder.ctc.setText(value.get(2));
        holder.location.setText(value.get(3));
        holder.date.setText(value.get(4));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}

