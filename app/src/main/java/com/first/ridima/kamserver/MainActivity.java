
package com.first.ridima.kamserver;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.util.Log;
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

        import static android.content.ContentValues.TAG;

/**
 * Created by Ridima on 19-01-2018.
 */


public class MainActivity extends AppCompatActivity {

    private TextView tvregister;
    private EditText edittextname;
    private EditText edittextemail;

    private TextView tv2;
    private  EditText edittextPassword;
    private Button buttonregi;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvregister = (TextView)findViewById(R.id.tvregister);
        edittextname = (EditText)findViewById(R.id.edittextname);
        edittextemail = (EditText)findViewById(R.id.edittextemail);
        tv2 = (TextView) findViewById(R.id.tv2);
        edittextPassword = (EditText)findViewById(R.id.edittextPassword);
        buttonregi = (Button)findViewById(R.id.buttonregi);
        firebaseAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        buttonregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }       //END OF onCreate()

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void registerUser(){

        //Getting values
        final String email = edittextemail.getText().toString().trim();
        String password = edittextPassword.getText().toString().trim();
        final String name = edittextname.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }



        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }



        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){



                            Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Could not Regitser..Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void onAlreadyRegistered(View view)
    {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);


    }

}


