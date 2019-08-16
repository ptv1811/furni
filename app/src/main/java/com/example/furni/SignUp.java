package com.example.furni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    TextInputEditText nemail;
    TextInputEditText npassword;
    TextInputEditText nc_password;
    Button signup;
    TextView move_signin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nemail=findViewById(R.id.su_nemail);
        npassword=findViewById(R.id.su_npass);
        nc_password=findViewById(R.id.su_ncpass);
        signup=findViewById(R.id.button_signup);
        move_signin=findViewById(R.id.move_signin);
        mAuth=FirebaseAuth.getInstance();

        move_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email= nemail.getText().toString();
                String Password= npassword.getText().toString();
                String CPassword=nc_password.getText().toString();

                if (Email.isEmpty()){
                    nemail.setError("Please type in your email");
                    nemail.requestFocus();
                }
                else if (Password.isEmpty()){
                    npassword.setError("Please type in password");
                    npassword.requestFocus();
                }
                else if (CPassword.isEmpty()){
                    nc_password.setError("Please type in confirm password");
                    nc_password.requestFocus();
                }
                else if (!Password.equals(CPassword)){
                    nc_password.setError("Confirm password must match password");
                    nc_password.requestFocus();
                }
                else if (!(Email.isEmpty() && Password.isEmpty()) && CPassword.equals(Password)){
                    mAuth.createUserWithEmailAndPassword(nemail.getText().toString(),npassword.getText().toString()).addOnCompleteListener(SignUp.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "Failed to sign up, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "Sign up succesfully", Toast.LENGTH_SHORT).show();

                                        Intent i=new Intent(SignUp.this,MainApp.class);

                                        String userID=task.getResult().getUser().getUid();
                                        String email =nemail.getText().toString();
                                        String pass= npassword.getText().toString();

                                        i.putExtra("nusername", email);
                                        i.putExtra("npassword", pass);
                                        i.putExtra("UserID", userID);
                                        startActivity(i);
                                    }
                                }
                            });
                }
            }
        });

    }
}
