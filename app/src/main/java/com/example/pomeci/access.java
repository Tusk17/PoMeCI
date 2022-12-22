package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class access extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    EditText email,con1;
    TextView olvidar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.editTextTextEmailAddress);
        con1=findViewById(R.id.editTextTextPassword);
        olvidar=findViewById(R.id.olvidaste_t);
        olvidar.setOnClickListener(v -> {
            DialogPlus d=DialogPlus.newDialog(v.getContext())
                    .setContentHolder(new ViewHolder(R.layout.olvidar_password))
                    .setExpanded(true,1100).create();
            View myView=d.getHolderView();
            TextView correo=myView.findViewById(R.id.correoOlvidarPassword);
            Button enviar=myView.findViewById(R.id.buttonEnviarCorreo);
            enviar.setOnClickListener(e -> {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = correo.getText().toString();
                if(emailAddress.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    Toast.makeText(getApplicationContext(),"Correo invalido o campo incompleto",Toast.LENGTH_LONG).show();
                }else {
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Email enviado",Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
            d.show();
        });

    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(access.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent b = new Intent(this, Home.class);
            startActivity(b);
        }else{finish();}
    }

    public void xd(View view){
        String e=email.getText().toString().trim();
        String c1=con1.getText().toString().trim();
        if(e.isEmpty()){
            Toast.makeText(this,"No hay email",Toast.LENGTH_LONG).show();
        }
        if(c1.isEmpty()){
            Toast.makeText(this,"No hay contraseña",Toast.LENGTH_LONG).show();
        }else{
            signIn(e,c1);
        }
    }
    public void toMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void toRegistrar(View view) {
        Intent intent = new Intent(this, registrar.class);
        startActivity(intent);
    }
}
