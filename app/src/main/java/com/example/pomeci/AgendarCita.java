package com.example.pomeci;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgendarCita extends AppCompatActivity {
    TextView Nombre,Especialidad,Horario;
    ImageView Foto;
    Button BotonConfirmar,BotonCancelar;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    EditText NombreC,Genero,Edad,HorarioAprox,Motivo,Fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agendar_cita);
//Inicializar realtime y obtener correo del usuario activo
        FirebaseApp.initializeApp(this);
        db=FirebaseDatabase.getInstance();
        dbRef=db.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String usuario=user.getEmail();
//Sincronización con la interfaz
        Nombre=findViewById(R.id.nombreEspeAgenda);
        Especialidad=findViewById(R.id.especialidadAgenda);
        Horario=findViewById(R.id.horasAgenda);
        Foto=findViewById(R.id.fotoAgenda);

        NombreC=findViewById(R.id.editTextNombre);
        Genero=findViewById(R.id.editTextGenero);
        Edad=findViewById(R.id.editTextEdad);
        HorarioAprox=findViewById(R.id.editTextHorarioAprox);
        Motivo=findViewById(R.id.editTextMotivo);
        Fecha=findViewById(R.id.editTextDate);

        BotonConfirmar=findViewById(R.id.buttonConfirmar);
        BotonCancelar=findViewById(R.id.buttonCancelar);
//Colocar datos del especialista
        Nombre.setText(DetallesEspe.NombreS);
        Especialidad.setText(DetallesEspe.EspecialidadS);
        Horario.setText(DetallesEspe.HorarioS);
        Glide.with(AgendarCita.this).load(DetallesEspe.FotoS).placeholder(R.drawable.downloading_black)
                .centerCrop().into(Foto);
//Botones cancelar y confirmar
        BotonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edad.getText().toString().isEmpty()||Fecha.getText().toString().isEmpty()||Genero.getText().toString().isEmpty()||
                        HorarioAprox.getText().toString().isEmpty()||Motivo.getText().toString().isEmpty()||
                        NombreC.getText().toString().isEmpty()){
                    Toast.makeText(AgendarCita.this,"Por favor rellene los campos faltantes",Toast.LENGTH_LONG).show();
                }else {
                    String cID=UUID.randomUUID().toString();
                Cita c=new Cita();
                c.setCitasID(cID);
                c.setCorreoCliente(usuario);
                c.setCorreoEspe(DetallesEspe.CorreoS);
                c.setEdad(Edad.getText().toString());
                c.setFecha(Fecha.getText().toString());
                c.setGenero(Genero.getText().toString());
                c.setHorario(HorarioAprox.getText().toString());
                c.setMotivo(Motivo.getText().toString());
                c.setNombre(NombreC.getText().toString());
                    String usuarioPath = usuario.replaceAll("\\.","");
                    String EspecialistaPath=DetallesEspe.CorreoS.replaceAll("\\.","").toLowerCase();
                dbRef.child("CitasC"+usuarioPath).child(cID).setValue(c);
                dbRef.child("CitasE"+EspecialistaPath).child(cID).setValue(c);
                Toast.makeText(AgendarCita.this,"Cita agendada exitosamente",Toast.LENGTH_LONG).show();
                finish();}
            }
        });
        BotonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}