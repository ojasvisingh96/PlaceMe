/*
Creates a registeration screen for first time users. Once the user fills all the required details his/her record is added as a document to firestore database under students collection
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void register(View v)
    {
        String nameRegister=((TextView)findViewById(R.id.nameRegister)).getText().toString();
        String cgpaRegister=((TextView)findViewById(R.id.cgpaRegister)).getText().toString();
        String rollRegister=((TextView)findViewById(R.id.rollRegister)).getText().toString();
        RadioGroup branch=findViewById(R.id.branchRegister);
        String branchRegister="";
        Log.d("Test ",branch.getCheckedRadioButtonId()+"");
        if(branch.getCheckedRadioButtonId()!=-1)
            branchRegister=((RadioButton)findViewById(branch.getCheckedRadioButtonId())).getText().toString();
        RadioGroup degree=findViewById(R.id.degreeRegister);
        String degreeRegister="";
        if(degree.getCheckedRadioButtonId()!=-1)
            degreeRegister=((RadioButton)findViewById(degree.getCheckedRadioButtonId())).getText().toString();
        Log.d("test ",nameRegister+cgpaRegister+rollRegister+branchRegister+degreeRegister);
        CheckBox acceptRegister=findViewById(R.id.acceptRegister);

        if(acceptRegister.isChecked()&&nameRegister.equals("")==false&&cgpaRegister.equals("")==false&&rollRegister.equals("")==false&&branchRegister.equals("")==false&&degreeRegister.equals("")==false)
        {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String personId = acct.getId();

            DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" + personId);  //creating a document for storing student record under students collection
            HashMap<String,String> studentRec=new HashMap<>();
            OnSuccessListener insertRec=new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getApplicationContext(),"Successfully Registered !!!",Toast.LENGTH_LONG).show();
                    Intent studentHome=new Intent(getApplicationContext(),Student.class);
                    startActivity(studentHome);
                }
            };
            studentRec.put("name",nameRegister);
            studentRec.put("cgpa",cgpaRegister);
            studentRec.put("branch",branchRegister);
            studentRec.put("degree",degreeRegister);
            studentRec.put("roll",rollRegister);
            studentDB.set(studentRec).addOnSuccessListener(insertRec);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please complete all the details",Toast.LENGTH_LONG).show();

        }


    }
}
