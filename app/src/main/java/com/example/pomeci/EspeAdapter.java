package com.example.pomeci;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EspeAdapter extends FirestoreRecyclerAdapter<Note_espe, EspeAdapter.NoteHolder> {
    private OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EspeAdapter(@NonNull FirestoreRecyclerOptions<Note_espe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EspeAdapter.NoteHolder holder, int position, @NonNull Note_espe model) {
        Glide.with(holder.itemView).load(model.getFoto()).placeholder(R.drawable.downloading_black).centerCrop().into(holder.FotoE);
        holder.textViewNombre.setText(model.getNombre());
        holder.textViewEspec.setText(model.getEspecialidad());
        holder.textViewTelefono.setText(model.getTelefono());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.especialista_item,parent,false);
        return new NoteHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        ImageView FotoE;
        TextView textViewNombre,textViewEspec,textViewTelefono;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            FotoE=itemView.findViewById(R.id.Foto);
            textViewNombre=itemView.findViewById(R.id.nombEspe);
            textViewEspec=itemView.findViewById(R.id.motivo);
            textViewTelefono=itemView.findViewById(R.id.horario);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}

