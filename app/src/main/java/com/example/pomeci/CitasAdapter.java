package com.example.pomeci;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    ArrayList<Cita> list;
    private View.OnClickListener listener;
    public static String citaBorrar="idk";

    public CitasAdapter(Context context,ArrayList<Cita>list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.obtener_citas_item,parent,false);
        v.setOnClickListener(this);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirebaseUser user;
        user= FirebaseAuth.getInstance().getCurrentUser();
        String usuarioPath = user.getEmail().replaceAll("\\.","");

        Cita cita=list.get(position);
        holder.Nombre.setText(cita.getNombre());
        holder.Motivo.setText(cita.getMotivo());
        holder.Horario.setText(cita.getHorario());
        holder.Genero.setText(cita.getGenero());
        holder.Fecha.setText(cita.getFecha());
        holder.Edad.setText(cita.getEdad());

        holder.Cancelar.setOnClickListener(v -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(holder.Cancelar.getContext());
            builder.setTitle("Borrar");
            builder.setMessage("¿Está seguro?");
            builder.setPositiveButton("Si",(dialog, which) -> {
                citaBorrar=list.get(position).getCitasID();
                list.remove(citaBorrar);
                Toast.makeText(context.getApplicationContext(),citaBorrar,Toast.LENGTH_LONG).show();
                Cita c = new Cita();
                c.setCitasID(list.get(position).getCitasID());
                FirebaseDatabase.getInstance().getReference().child("CitasE"+usuarioPath).child(citaBorrar).removeValue();
                FirebaseDatabase.getInstance().getReference().child("CitasC"+usuarioPath).child(citaBorrar).removeValue();
                notifyDataSetChanged();
                list.clear();
            });
            builder.setNegativeButton("No",(dialog, which) -> {});
            builder.show();
        });
        holder.Editar.setOnClickListener(v -> {
            citaBorrar=list.get(position).getCitasID();
            final DialogPlus d=DialogPlus.newDialog(holder.Editar.getContext())
                    .setContentHolder(new ViewHolder(R.layout.agendar_cita))
                    .setExpanded(true,1100).create();
            View myView=d.getHolderView();
//Declarar elementos del view dialog y sincronizar
            final TextView Nombre=myView.findViewById(R.id.nombreEspeAgenda);
            final TextView Especialidad=myView.findViewById(R.id.especialidadAgenda);
            final TextView Horario=myView.findViewById(R.id.horasAgenda);
            final ImageView Foto=myView.findViewById(R.id.fotoAgenda);
            final EditText NombreC=myView.findViewById(R.id.editTextNombre);
            final EditText Genero=myView.findViewById(R.id.editTextGenero);
            final EditText Edad=myView.findViewById(R.id.editTextEdad);
            final EditText HorarioAprox=myView.findViewById(R.id.editTextHorarioAprox);
            final EditText Motivo=myView.findViewById(R.id.editTextMotivo);
            final EditText Fecha=myView.findViewById(R.id.editTextDate);
//Ocultar, mostrar botones y obtener datos
            Button actualizar,confirmar,cancelar;
            confirmar=myView.findViewById(R.id.buttonConfirmar);
            actualizar=myView.findViewById(R.id.buttonActualizar);
            cancelar=myView.findViewById(R.id.buttonCancelar);
            confirmar.setVisibility(View.GONE);
            actualizar.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.GONE);

            Especialidad.setVisibility(View.GONE);
            Foto.setVisibility(View.GONE);
            Nombre.setText(cita.getNombre());
            Horario.setText(cita.getHorario());
            NombreC.setText(cita.getNombre());
            Genero.setText(cita.getGenero());
            Edad.setText(cita.getEdad());
            HorarioAprox.setText(cita.getHorario());
            Motivo.setText(cita.getMotivo());
            Fecha.setText(cita.getFecha());

            actualizar.setOnClickListener(v1 -> {
                Map<String,Object> map=new HashMap<>();
                map.put("nombre",NombreC.getText().toString());
                map.put("genero",Genero.getText().toString());
                map.put("edad",Edad.getText().toString());
                map.put("horario",HorarioAprox.getText().toString());
                map.put("motivo",Motivo.getText().toString());
                map.put("fecha",Fecha.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("CitasC"+usuarioPath)
                .child(citaBorrar).updateChildren(map)
                .addOnSuccessListener(unused -> {
                Toast.makeText(context.getApplicationContext(),"Se ha actualizado exitosamente",Toast.LENGTH_LONG).show();
                }).addOnFailureListener(e -> {
                Toast.makeText(context.getApplicationContext(),"No se ha podido actualizar",Toast.LENGTH_LONG).show();
                d.dismiss();
                });

                FirebaseDatabase.getInstance().getReference().child("CitasE"+usuarioPath)
                        .child(citaBorrar).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(context.getApplicationContext(),"Se ha actualizado exitosamente",Toast.LENGTH_LONG).show();
                        }).addOnFailureListener(e -> {
                    Toast.makeText(context.getApplicationContext(),"No se ha podido actualizar",Toast.LENGTH_LONG).show();
                    d.dismiss();
                });
            });

            d.show();
            notifyDataSetChanged();
            list.clear();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        Button Editar,Cancelar;
        TextView Nombre,Motivo,Horario,Genero,Fecha,Edad;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre=itemView.findViewById(R.id.nombEspe);
            Motivo=itemView.findViewById(R.id.motivo);
            Horario=itemView.findViewById(R.id.horario);
            Genero=itemView.findViewById(R.id.genero);
            Fecha=itemView.findViewById(R.id.fecha);
            Edad=itemView.findViewById(R.id.edad);
            Editar=itemView.findViewById(R.id.editar);
            Cancelar=itemView.findViewById(R.id.cancelar);
        }
    }
}
