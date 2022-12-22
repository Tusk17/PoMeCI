package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class New_Note extends AppCompatActivity {

    private EditText editTextTitle;
    private TextView textViewURL;
    public ImageButton imageButtonSave;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String usuario=user.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        editTextTitle=findViewById(R.id.edit_text_title);
        textViewURL=findViewById(R.id.text_view_URL);

        imageButtonSave=findViewById(R.id.imageButtonSave);

        Intent intent = getIntent();
        String Dir = intent.getStringExtra(podcast.DirEnviar);
        String Dir2 = intent.getStringExtra(salud_articulos.DirEnviar);
        if(Dir!=null){
            textViewURL.setText(Dir);
        }
        if(Dir2!=null){
            textViewURL.setText(Dir2);
        }

        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo=editTextTitle.getText().toString();
                String dir=textViewURL.getText().toString();

                if(titulo.isEmpty()){
                    Toast.makeText(New_Note.this,"Ingrese un titulo a este marcador",Toast.LENGTH_LONG).show();
                }
                else{
                    CollectionReference dirRef= FirebaseFirestore.getInstance()
                            .collection(usuario);
                    dirRef.add(new Note(titulo,dir));
                    Toast.makeText(New_Note.this,"Añadido",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}