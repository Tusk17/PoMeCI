package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ObtenerCitas extends AppCompatActivity {
    ImageView doctor;
    RecyclerView recyclerView;
    DatabaseReference dbRefer;
    CitasAdapter citasAdapter;
    ArrayList<Cita> list;
    String  Tipe="";
    String usuarioPath;
    Button Editar,Cancelar;
    public static String tipoCita="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obtener_citas);

        Editar=findViewById(R.id.editar);
        Cancelar=findViewById(R.id.cancelar);

        recyclerView = findViewById(R.id.recycler_esp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser user;
        user= FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        char xdd=user.getUid().charAt(0);
        usuarioPath = user.getEmail().replaceAll("\\.","");

        Intent intent = getIntent();
        Tipe = intent.getStringExtra(Home.clienteOespe);
        if ("Espe".equals(Tipe)) {
            citasEspecialista();
        }else {
            citasCliente();
        }

        //consulta database dbRef.orderByChild("CitasC").equalTo(usuario);

        list=new ArrayList<>();
        citasAdapter=new CitasAdapter(this,list);
        recyclerView.setAdapter(citasAdapter);


        dbRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Cita cita=dataSnapshot.getValue(Cita.class);
                    list.add(cita);
                }
                citasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        citasAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    public void citasCliente(){
        dbRefer = FirebaseDatabase.getInstance().getReference("CitasC"+usuarioPath);
        dbRefer.orderByChild("correoCliente");
        tipoCita="cliente";
    }
    public void citasEspecialista(){
        dbRefer = FirebaseDatabase.getInstance().getReference("CitasE"+usuarioPath);
        dbRefer.orderByChild("correoEspe");
        tipoCita="especialista";
    }
}