package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Marcadores extends AppCompatActivity {
    private final FirebaseFirestore db=FirebaseFirestore.getInstance();
    public static String Dire;
    String siono="cualquier";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String usuario=user.getEmail();
    podcast obj=new podcast();
    private final CollectionReference dirRef=db.collection(usuario);
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcadores);


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query=dirRef.orderBy("titulo",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Note> options= new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();

        adapter = new NoteAdapter(options);

        RecyclerView recyclerView= findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            Intent c = new Intent(this, podcast.class);
            Intent b = new Intent(this, podcast.class);
            Intent d = new Intent(this, salud_articulos.class);
            Dire=documentSnapshot.getString("dir");
            assert Dire != null;
            if(Dire.contains("podcasts.google")||Dire.contains("podiumpodcast")||Dire.contains("tunein.com")){
                siono="podcast";
            }
            if(Dire.contains("paho")||Dire.contains("elespanol")||Dire.contains("news")||Dire.contains("eluniversal")
                    ||Dire.contains("salud.nih")||Dire.contains("milenio")){
                siono="salud";
            }
            switch (siono){
                case "salud":
                    d.putExtra(Dire,Dire);
                    startActivity(d);

                    break;
                case "podcast":
                    c.putExtra(Dire,Dire);
                    startActivity(c);

                    break;
                case "cualquier":
                    b.putExtra(Dire,Dire);
                    startActivity(b);
                    break;
            }

            finish();
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