package com.example.dopewallpaper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //check if the user is logged in
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity( new Intent(getApplicationContext() , HomeActivity.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString();
                String getpassword = password.getText().toString();
                callSignin(getemail, getpassword);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String getemail = email.getText().toString();
                String getpassword = password.getText().toString();
                callSignup(getemail, getpassword);

            }
        });
    }

    private void callSignup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Test", "CreateUserWithEmail:onComplete"+ task.isSuccessful());
                        if(!task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userProfile();
                            Toast.makeText(SignIn.this, "Account Created",  Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(email.getText().toString()).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.d("test", "User profile updated");
                    }
                }
            });
        }


    }

    private void callSignin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Test","Sign in is successful:" + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Test", "Sign In With Email", task.getException());
                    Toast.makeText(SignIn.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }else{
                    Intent i = new Intent(SignIn.this, HomeActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }
}
