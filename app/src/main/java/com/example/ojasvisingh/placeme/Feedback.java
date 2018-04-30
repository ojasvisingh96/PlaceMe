package com.example.ojasvisingh.placeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {
 private String Companyname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Companyname=getIntent().getStringExtra("comp");
        TextView txt=findViewById(R.id.title_qb);
        txt.setText("Question Bank for "+ Companyname);



    }

    public void submit_qb(View view) {
        final TextView txt=findViewById(R.id.question_txt);
        final TextView txt2=findViewById(R.id.answer_txt);
        if (txt.getText().toString().equals("")==false&& txt2.getText().toString().equals("")==false){
           final DocumentReference questionDB= FirebaseFirestore.getInstance().document("questions/" + Companyname);      //fetching my record
            final HashMap<String,String> qb=new HashMap<>();
            qb.put(txt.getText().toString(),txt2.getText().toString());
            questionDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()==false)
                    {
                        questionDB.set(qb).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();


                            }
                        });


                    }
                    else
                    {
                        questionDB.update(txt.getText().toString(),txt2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();


                            }
                        });
                    }

                }
            });





        }
        else
        {
            Toast.makeText(getApplicationContext(),"Kindly fill all the fields",Toast.LENGTH_LONG).show();

        }

//        txt.setText("Question Bank for "+ Companyname);
    }

}
