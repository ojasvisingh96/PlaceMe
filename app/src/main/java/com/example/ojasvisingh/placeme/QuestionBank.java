package com.example.ojasvisingh.placeme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionBank extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String companyName;

    private TextView compname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);

        compname = (TextView) findViewById(R.id.QuestionBankCompanyName);
        companyName=getIntent().getStringExtra("comp");

        compname.setText(companyName);

        mRecyclerView = (RecyclerView) findViewById(R.id.question_bank_my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();

        mAdapter = new QuestionBankAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);


        DocumentReference questionsDB= FirebaseFirestore.getInstance().document("questions/" + companyName);

        questionsDB.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap<String, Object> bank = (HashMap) document.getData();



                        for (String s : bank.keySet()){
                            ArrayList<String> temp = new ArrayList<String>();
                            Log.d("question", s + " answer " + bank.get(s));
                            temp.add(s);
                            temp.add(bank.get(s).toString());
                            myDataset.add(temp);
                        }

                        mAdapter.notifyDataSetChanged();

                        Log.d("tag", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });





//        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("questions");
//        jobsDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
//                {
//                    ArrayList<String> temp = new ArrayList<String>();
//                    temp.add(jobRec.get("hxxh").toString());
//                    temp.add(jobRec.get("hxxhshsh").toString());
//
//                    myDataset.add(temp);
//                }
//
////                Log.w("recycle", myDataset.toString());
//                Log.w("recycle", "" + mAdapter.getItemCount() );
//                // specify an adapter (see also next example)
//                mAdapter.notifyDataSetChanged();
//            }
//        });
    }
}
