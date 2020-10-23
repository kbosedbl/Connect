package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText userName , fullName , password , email;
    Button register;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.username);
        fullName = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginfromregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                String str_username = userName.getText().toString();
                String str_fullname = fullName.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                if(str_email.length() == 0 || str_username.length() == 0 || str_fullname.length() == 0 || str_password.length() == 0)
                    Toast.makeText(RegisterActivity.this , "Please give all the details" , Toast.LENGTH_SHORT).show();
                else if(str_password.length() <= 6){
                    Toast.makeText(RegisterActivity.this , "Minimum Password length is 6" , Toast.LENGTH_SHORT).show();
                }
                else{
                    doRegister(str_email,str_fullname,str_password,str_username);
                }
            }
        });
    }

    private void doRegister(String email, final String fullname, String password, final String username) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                    HashMap<String , Object> hashMap = new HashMap<>();
                    hashMap.put("id" , userid);
                    hashMap.put("username" , username.toLowerCase());
                    hashMap.put("fullname" , fullname);
                    hashMap.put("bio" , "");
                    hashMap.put("imageurl" , "https://www.poutstation.com/upload/photos/avatar.jpg");

                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this , MainActivity2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this , "You cant register with this email id or password" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this , "You cant register with this email id or password" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}