/*
Admin end implementation , presently diplays list of registered students
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //recycle view

        mRecyclerView = (RecyclerView) findViewById(R.id.my_other_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();

        mAdapter = new MyOtherAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);



        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("students");
        jobsDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
                {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(jobRec.get("name").toString());
                    temp.add(jobRec.get("roll").toString());
                    temp.add(jobRec.get("degree").toString());
                    temp.add(jobRec.get("branch").toString());
                    temp.add(jobRec.get("cgpa").toString());

                    myDataset.add(temp);
                }

//                Log.w("recycle", myDataset.toString());
                Log.w("recycle", "" + mAdapter.getItemCount() );
                // specify an adapter (see also next example)
                mAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
    }
    public void viewJobs(View v)
    {
        Intent viewJobs=new Intent(getApplicationContext(),AdminJob.class);
        startActivity(viewJobs);
    }
}
