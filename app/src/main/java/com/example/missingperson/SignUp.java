package com.example.missingperson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText textInputEditTextFullname, textInputEditTextUsername,textInputEditTextPassword,textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    private long backPressedTime;


    @Override
    public void onBackPressed() {
        if(backPressedTime +2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        }else
        {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView login = (TextView) findViewById(R.id.Signtext);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,Login.class);
                startActivity(i);
                finishAffinity();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textInputEditTextFullname= findViewById(R.id.NameText);
        textInputEditTextUsername = findViewById(R.id.editTextUser);
        textInputEditTextPassword = findViewById(R.id.editTextTextPassword);
        textInputEditTextEmail = findViewById(R.id.editTextTextPersonName);
        buttonSignUp = findViewById(R.id.butregis);
        progressBar = findViewById(R.id.progress);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username,password,email;
                fullname= String.valueOf(textInputEditTextFullname.getText());
                username= String.valueOf(textInputEditTextUsername.getText());
                password= String.valueOf(textInputEditTextPassword.getText());
                email= String.valueOf(textInputEditTextEmail.getText());

                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://192.168.10.214/lostperson/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent  = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"All fields are Required",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}