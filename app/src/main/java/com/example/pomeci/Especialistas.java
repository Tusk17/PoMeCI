package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Especialistas extends AppCompatActivity {
    private final FirebaseFirestore db=FirebaseFirestore.getInstance();
    private final CollectionReference dirRef=db.collection("Especialistas");
    private EspeAdapter adapter;
    public static String dir,clienteOespe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacialistas);
        ImageView misCitas=findViewById(R.id.imageViewDoctor);
        

        misCitas.setOnClickListener(v -> {
            Intent c=new Intent(Especialistas.this,ObtenerCitas.class);
            clienteOespe="Cliente";
            c.putExtra(clienteOespe,clienteOespe);
            startActivity(c);
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query=dirRef.orderBy("Nombre",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Note_espe> options= new FirestoreRecyclerOptions.Builder<Note_espe>()
                .setQuery(query,Note_espe.class).build();

        adapter = new EspeAdapter(options);

        RecyclerView recyclerView= findViewById(R.id.recycler_esp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            dir=documentSnapshot.getId();
            Intent c = new Intent(this, DetallesEspe.class);
            c.putExtra(dir,dir);
            startActivity(c);

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
