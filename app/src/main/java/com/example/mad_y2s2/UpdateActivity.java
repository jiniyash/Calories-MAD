package com.example.mad_y2s2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    EditText pName;
    EditText uName;
    EditText pEmail;
    EditText pAge;
    EditText pWeight;
    EditText pHeight;

    Button btnUpdateProfile;
    UserProfile user;
    DatabaseReference dbRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        pName = findViewById(R.id.pName);
        uName = findViewById(R.id.pUnamw);
        pEmail=findViewById(R.id.pEmail);
        pAge = findViewById(R.id.pAge);
        pWeight = findViewById(R.id.pWeight);
        pHeight = findViewById(R.id.pHeight);

        pName.setText(""+getIntent().getStringExtra("wName"));
        uName.setText(""+getIntent().getStringExtra("wUname"));
        pEmail.setText(""+getIntent().getStringExtra("wEmail"));
        pAge.setText(""+getIntent().getStringExtra("wAge"));
        pWeight.setText(""+getIntent().getStringExtra("wWeight"));
        pHeight.setText(""+getIntent().getStringExtra("wHeight"));

        int bmiHeight = Integer.valueOf(pHeight.getText().toString());
        int bmiWeight = Integer.valueOf(pWeight.getText().toString());

        float bmiHEIGHT = bmiHeight/100;

        float bmi = (float) bmiWeight / (bmiHEIGHT*bmiHEIGHT);
        final String pBmi = String.valueOf(bmi);


        btnUpdateProfile = findViewById(R.id.updateButton);
        user = new UserProfile();


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("-MIGqe4jxNjLJTCAFpfS")){
                           try {
                               user.setName(pName.getText().toString().trim());
                               user.setEmail(pEmail.getText().toString().trim());
                               user.setUsername(uName.getText().toString().trim());
                               user.setAge(Integer.parseInt(pAge.getText().toString().trim()));
                               user.setWeight(Integer.parseInt(pWeight.getText().toString().trim()));
                               user.setHeight(Integer.parseInt(pHeight.getText().toString().trim()));
                               //user.setBmi(Integer.parseInt(pBmi.trim()));


                               dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child("-MIGqe4jxNjLJTCAFpfS");
                               dbRef.setValue(user);
                               Toast.makeText(getApplicationContext(),"Updation Successful",Toast.LENGTH_LONG).show();
                           }
                           catch (NumberFormatException e){
                               Toast.makeText(getApplicationContext(),"Invalid Amount",Toast.LENGTH_LONG).show();
                           }
                        }
                        else {
                            Toast.makeText(getApplicationContext()," Nothing to update ",Toast.LENGTH_LONG).show();
                        }

                        Button btnUpdate = (Button)findViewById(R.id.btn_save);
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                            }
                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }
}
