package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registrar extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private static final String TAG = "AnonymousAuth";
    EditText email,con1,con2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.editTextTextEmailAddress);
        con1=findViewById(R.id.editTextTextPassword);
        con2=findViewById(R.id.editTextTextPassword2);

    }
    public void xd(View view){
        String e=email.getText().toString().trim();
        String c1=con1.getText().toString().trim();
        String c2=con2.getText().toString().trim();
        if(e.isEmpty()){
            Toast.makeText(this,"No hay email",Toast.LENGTH_LONG).show();
        }
        if(c1.isEmpty()){
            Toast.makeText(this,"No hay contraseña",Toast.LENGTH_LONG).show();
        }
        if(c2.isEmpty()){
            Toast.makeText(this,"No hay contraseña2",Toast.LENGTH_LONG).show();
        }
        if(c1.equals(c2)){
            checkEmail(e,c1);
        }else{
            Toast.makeText(this,"Los campos de contraseña no coinciden",Toast.LENGTH_LONG).show();
        }
    }

    private void checkEmail(String e,String c1) {
        mAuth.fetchSignInMethodsForEmail(e).addOnCompleteListener(task -> {
            boolean check= !task.getResult().getSignInMethods().isEmpty();
            if(check){
                Toast.makeText(getApplicationContext(),"El email ya está en uso",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Email disponible, usuario creado",Toast.LENGTH_LONG).show();
                createAccount(e,c1);
            }
        });
    }

    public void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),"Se te ha enviado un correo de verificación, una vez verificado ve a 'Acceder'",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registrar.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
        Intent b = new Intent(this, Home.class);
        startActivity(b);
        }else{finish();}
    }

    public void toMainA(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void toAccess(View view) {
        Intent intent = new Intent(this, access.class);
        startActivity(intent);
    }


}
