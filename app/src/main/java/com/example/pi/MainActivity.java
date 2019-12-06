package com.example.pi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userId;
    EditText userPassword;
    Button signInBtn;
    Button registerBtn;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId=findViewById(R.id.editSignInEmail);
        userPassword=findViewById(R.id.editSignInPassword);

        Toast.makeText(MainActivity.this,"Welcome!",Toast.LENGTH_SHORT).show();

        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.btnSignIn).setOnClickListener(this);

        findViewById(R.id.btnRegister).setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnSignIn:{
                userLogin();
                break;
            }
            case R.id.btnRegister:{
                finish();
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            }
        }

    }

    private void userLogin(){

        String mUserId=userId.getText().toString().trim();
        String mPassword=userPassword.getText().toString().trim();

        if(mUserId.isEmpty()){
            userId.setError("Email is required ");

            userId.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mUserId).matches()){
            userId.setError("This Email is already registered");
            userId.requestFocus();
            return;
        }
        if(mPassword.isEmpty()){
            userPassword.setError("Password is required");
            userId.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mUserId,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    finish();
                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }
}
