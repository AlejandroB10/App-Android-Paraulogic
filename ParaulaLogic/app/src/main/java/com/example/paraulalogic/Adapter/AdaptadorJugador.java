package com.example.paraulalogic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paraulalogic.Clases.Avatar;
import com.example.paraulalogic.Clases.Jugador;
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.R;

import java.util.List;

public class AdaptadorJugador extends RecyclerView.Adapter<AdaptadorJugador.ViewHolderJugador> implements View.OnClickListener{


    private View.OnClickListener listener;
    List<Jugador> listaJugadores;
    View vista;

    int posicionMarcada=0;

    public AdaptadorJugador(List<Jugador> listaJugadores  ) {
        this.listaJugadores = listaJugadores;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_jugador,viewGroup,false);

        vista.setOnClickListener(this);

        return new ViewHolderJugador(vista);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {

        final int pos=i;
        final ViewHolderJugador viewHolder=viewHolderJugador;

        viewHolderJugador.imgAvatar.setImageResource(Utilitats.listaAvatars.get(listaJugadores.get(i).getAvatar()-1).getAvatarId());
        viewHolderJugador.nombre.setText(listaJugadores.get(i).getNombre());


    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }


    public class ViewHolderJugador extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView nombre;

        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            nombre=itemView.findViewById(R.id.idNombre);

        }

    }
}

