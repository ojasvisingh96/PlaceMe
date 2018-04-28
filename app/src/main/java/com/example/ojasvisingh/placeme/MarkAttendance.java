/*
Used to mark attendance of students for the specified job and stage
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkAttendance extends AppCompatActivity {
    static ArrayList<String> shortlisted=new ArrayList<>();
    static ArrayList<String> unlisted=new ArrayList<>();

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        final TextView title=findViewById(R.id.attendanceTitle);
        shortlisted=new ArrayList<>();
        unlisted=new ArrayList<>();
        name=getIntent().getStringExtra("comp");
        final RecyclerView students=findViewById(R.id.studentAtdList);
        DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +name);
        final ArrayList<String> Names=new ArrayList<>();
        final ArrayList<String> ids=new ArrayList<>();
        final ArrayList<String> marks=new ArrayList<>();
        final StudentMarkAdapter studentsad=new StudentMarkAdapter(Names,marks,ids);
        final HashMap<String,String> idTomk=new HashMap<>();

        students.setLayoutManager(new LinearLayoutManager(this));
        students.setAdapter(studentsad);
        processDB.get().addOnSuccessListener(this,new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final HashMap<String,String> enrolled=(HashMap)documentSnapshot.getData();

                for(String key:enrolled.keySet())
                {
                    if(key.equals("stage"))
                    {
                        title.setText("Attendance for "+enrolled.get(key));

                    }
                    if(key.equals("date")==false&&key.equals("time")==false&&key.equals("stage")==false)
                    {
                        idTomk.put(key,enrolled.get(key));
                        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +key);
                        studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Names.add(documentSnapshot.getString("name"));
                                marks.add(idTomk.get(documentSnapshot.getId()));
                                ids.add(documentSnapshot.getId());
                                Log.d("Id ",documentSnapshot.getId());


                                studentsad.notifyDataSetChanged();



                            }
                        });


                    }

                }

            }
        });
    }
    public void updateAttendance(View v)
    {
        final DocumentReference studentDB= FirebaseFirestore.getInstance().document("process/" +name);
        for(String id:shortlisted)
        {
            studentDB.update(id,"true");

        }
        for(String id:unlisted)
        {
            studentDB.update(id,"false");

        }
        Toast.makeText(getApplicationContext(),"Successfully Updated Attendance",Toast.LENGTH_LONG).show();





    }
}
