/*
Admin end implementation , presently diplays list of registered students
 */
package com.example.ojasvisingh.placeme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    ArrayList<String> studentRecs=new ArrayList<>();
    ListView studentList;
    ArrayAdapter studentListAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        studentList=findViewById(R.id.studentList);
        studentListAd=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,studentRecs);
        studentList.setAdapter(studentListAd);

    }



    @Override
    protected void onStart() {
        super.onStart();
        CollectionReference studentDB= FirebaseFirestore.getInstance().collection("students");
        Log.d("student rec",studentDB.toString());
        studentDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot studentRec: documentSnapshots.getDocuments())
                {
                    String studentRecstr=studentRec.getData().toString();
                    Log.d("student record ",studentRecstr);
                    if(studentRecs.contains(studentRecstr)==false) {
                        studentRecs.add(studentRecstr);
                        studentListAd.notifyDataSetChanged();
                    }

                }

            }
        });
    }
}
