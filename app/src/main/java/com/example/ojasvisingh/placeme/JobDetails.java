/*
Displays job details of a particular job to student and presents with enroll option
 */
package com.example.ojasvisingh.placeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class JobDetails extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        name=getIntent().getStringExtra("comp");
    }
    public void enroll(View v)      //enrolling student for the process
    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        final String personId = acct.getId();
        final DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +name);
        processDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()==false)
                {
                    HashMap<String,String> enrollRec=new HashMap<>();
                    enrollRec.put(personId,"false");
                    processDB.set(enrollRec).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Successfully enrolled !!!",Toast.LENGTH_LONG).show();
                            final DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +personId);
                            studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.getString("enrolled")==null)
                                    {
                                        studentDB.update("enrolled",name);
                                    }
                                    else
                                    {
                                        String enrolled=documentSnapshot.getString("enrolled");
                                        studentDB.update("enrolled",enrolled+","+name);
                                    }
                                }
                            });
                        }
                    });
                }
                else if(documentSnapshot.getString(personId)==null) {
                    processDB.update(personId, "false").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Successfully enrolled !!!",Toast.LENGTH_LONG).show();
                            final DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +personId);
                            studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.getString("enrolled")==null)
                                    {
                                        studentDB.update("enrolled",name);
                                    }
                                    else
                                    {
                                        String enrolled=documentSnapshot.getString("enrolled");
                                        studentDB.update("enrolled",enrolled+","+name);
                                    }
                                }
                            });

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Already enrolled",Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
