package com.example.furni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    TextInputEditText nusername;
    TextInputEditText npassword;
    Button sign_in;
    TextView sign_up;
    TextView move_to_signup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        nusername=findViewById(R.id.username);
        npassword=findViewById(R.id.password);

        sign_in=findViewById(R.id.login_button);
        sign_up=findViewById(R.id.signup_act);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });

        mAuthState=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser= mAuth.getCurrentUser();
                if (firebaseUser!=null){
                    // users signin
                }
                else {
                    // signout
                }
            }
        };

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=nusername.getText().toString();
                String password=npassword.getText().toString();

                if (email.isEmpty()){
                    nusername.setError("Username can not be empty");
                    nusername.requestFocus();
                }
                else if (password.isEmpty()){
                    npassword.setError("Password can not be empty");
                    npassword.requestFocus();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent i=new Intent(MainActivity.this,MainApp.class);
                                String userID=task.getResult().getUser().getUid();
                                String nemail = nusername.getText().toString();
                                String pass = npassword.getText().toString();
                                i.putExtra("nusername",nemail);
                                i.putExtra("npassword",pass);
                                i.putExtra("UserID",userID);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
        });

    }
}
