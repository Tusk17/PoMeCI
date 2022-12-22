package com.example.pomeci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DetallesEspe extends AppCompatActivity {
    FirebaseFirestore mfirestore;
    ImageView Foto;
    TextView Nombre,TipoConsultorio,Direccion,Especialidad,Exp;
    TextView Cedula,Descripcion,Telefono,Correo,Horario;
    ImageView Agenda;
    public static String FotoS,NombreS,EspecialidadS,HorarioS,CorreoS;
    String TipoConsultorioS,DireccionS,ExpS,CedulaS;
    String DescripcionS,TelefonoS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_espe);

        mfirestore=FirebaseFirestore.getInstance();
        Foto=findViewById(R.id.FotoCentral);
        Nombre=findViewById(R.id.NombreEspe);
        TipoConsultorio=findViewById(R.id.tipoConsult);
        Direccion=findViewById(R.id.direccion);
        Especialidad=findViewById(R.id.especialidad);
        Exp=findViewById(R.id.experiencia);
        Cedula=findViewById(R.id.cedula);
        Descripcion=findViewById(R.id.descripcion);
        Telefono=findViewById(R.id.telefono);
        Correo=findViewById(R.id.correo);
        Horario=findViewById(R.id.horas);
        Agenda=findViewById(R.id.agenda);

        obtenerDatos();

        Agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendarCita();
            }
        });
    }

    private void agendarCita() {
        Intent c = new Intent(this, AgendarCita.class);
        startActivity(c);
    }

    private void obtenerDatos() {
        Intent intent = getIntent();
        String Dir = intent.getStringExtra(Especialistas.dir);

        mfirestore.collection("Especialistas").document(Dir).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    FotoS=value.getString("Foto");
                    NombreS=value.getString("Nombre");
                    TipoConsultorioS=value.getString("TipoConsultorio");
                    DireccionS=value.getString("Direccion");
                    EspecialidadS=value.getString("Especialidad");
                    ExpS=value.getString("Experiencia");
                    CedulaS=value.getString("Cedula");
                    DescripcionS=value.getString("Descripcion");
                    TelefonoS=value.getString("Telefono");
                    CorreoS=value.getString("Correo");
                    HorarioS=value.getString("Horario");

                    Glide.with(DetallesEspe.this).load(FotoS).placeholder(R.drawable.downloading_black)
                            .centerCrop().into(Foto);
                    Nombre.setText(NombreS);
                    TipoConsultorio.setText(TipoConsultorioS);
                    Direccion.setText(DireccionS);
                    Especialidad.setText(EspecialidadS);
                    Exp.setText(ExpS);
                    Cedula.setText(CedulaS);
                    Descripcion.setText(DescripcionS);
                    Telefono.setText(TelefonoS);
                    Correo.setText(CorreoS);
                    Horario.setText(HorarioS);

                }
            }
        });
    }

}