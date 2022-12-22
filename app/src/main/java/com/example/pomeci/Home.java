package com.example.pomeci;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class Home extends AppCompatActivity {
    View xd;
    FirebaseUser user;
    public static String clienteOespe="";
    TextView consejoDia;
    private final FirebaseFirestore db=FirebaseFirestore.getInstance();
    private final CollectionReference dirRef= db.collection("Especialistas");
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        View ArtSalud=findViewById(R.id.rectangle_9);
        View PodCast=findViewById(R.id.rectangle_10);
        View Espec=findViewById(R.id.rectangle_11);
        FloatingActionButton fab = findViewById(R.id.fab);
        consejoDia=findViewById(R.id.mant_n_tu_o);
//Obtener dia y colocar consejo
        Calendar d = Calendar.getInstance();
        int dia =  d.get(Calendar.DAY_OF_WEEK);
        if(dia==Calendar.SUNDAY){
            //Domingo
            consejoDia.setText("Coma a horas regulares y evite omitir comidas o sustituir una comida por un refrigerio alto en calorías y en grasa. Si tiene hambre entre comidas, coma un refrigerio saludable");
        }if(dia==Calendar.MONDAY){
            //Lunes
            consejoDia.setText("Trate de hacer más sopas, especialmente aquellas con zanahorias, papas, frijoles rojos y negros y otros vegetales o legumbres que le gusten.");
        }
        if(dia==Calendar.TUESDAY){
            //Martes
            consejoDia.setText("Prepare sus platos de pollo o pescado al horno, a la parrilla, a la brasa o al vapor en vez de freírlos. Use aceites saludables como el aceite de oliva o de canola en vez de manteca o mantequilla.");
        }
        if(dia==Calendar.WEDNESDAY){
            //Martes
            consejoDia.setText("Cocine y coma comidas con menos sal. Use caldos bajos en sal o jugos cítricos para darle sabor a los alimentos.");
        }
        if(dia==Calendar.THURSDAY){
            //Martes
            consejoDia.setText("Reduce el consumo de azúcar. El consumo excesivo de azúcar es una de las principales causas de obesidad y diabetes a nivel mundial. No te recomendamos que la saques completamente de tu dieta, pero sí que reduzcas las cantidades.");
        }
        if(dia==Calendar.FRIDAY){
            //Martes
            consejoDia.setText("Algunos consejos para deshacerte del estrés son escuchar música relajante, darte un baño, leer, hacer ejercicio o cualquier otra actividad que cuente como un tiempo exclusivo para ti.");
        }
        if(dia==Calendar.SATURDAY){
            //Martes
            consejoDia.setText("Una de las actividades física con mayores ventajas es el cardio. Facilita el control de los factores de riesgo como la hipertensión arterial, la obesidad, la diabetes, el estrés y la dislipemia.");
        }
        TextView CorreoUsuario=findViewById(R.id.hola_usuari);

        xd=findViewById(R.id.ellipse_2);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String correo=user.getEmail();
        Query query=dirRef.whereEqualTo("Correo",correo);
        DocumentReference docRef = db.collection("Especialistas").document(correo);
        db.collection("Especialistas")
                .whereEqualTo("Correo", correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                xd.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        xd.setOnClickListener(v -> {
            Intent c=new Intent(Home.this,ObtenerCitas.class);
            clienteOespe="Espe";
            c.putExtra(clienteOespe,clienteOespe);
            startActivity(c);
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String usuario=user.getEmail();
        CorreoUsuario.setText("Hola: "+usuario);

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Se ha cerrado sesión exitosamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            }
        });
        ArtSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toArticSalud();
            }
        });
        PodCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPodcast();
            }
        });
        Espec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEspecialistas();
            }
        });
        }

    private void toArticSalud() {
        Intent b = new Intent(this, salud_articulos.class);
        startActivity(b);
    }

    private void toPodcast() {
        Intent b = new Intent(this, podcast.class);
        startActivity(b);
    }
    private void toEspecialistas() {
        Intent b = new Intent(this, Especialistas.class);
        startActivity(b);
    }
    public void bFab(View view){
        Snackbar.make(view, "Manten presionado para cerrar sesión y salir de la app", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }
}