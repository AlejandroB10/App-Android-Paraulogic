package com.example.paraulalogic.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paraulalogic.Clases.Resultado;
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.R;

import java.util.List;

public class AdaptadorResultado extends RecyclerView.Adapter<AdaptadorResultado.ViewHolderJugador> {

    //private View.OnClickListener listener;
    List<Resultado> listaJugador;
    View vista;

    public AdaptadorResultado(List<Resultado> listaJugador) {
        this.listaJugador = listaJugador;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_resultado,viewGroup,false);

        return new ViewHolderJugador(vista);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {


        //se resta uno ya que buscamos la lista de elementos que inicia en la pos 0
        viewHolderJugador.imgAvatar.setImageResource(Utilitats.listaAvatars.get(listaJugador.get(i).getAvatar()-1).getAvatarId());
        viewHolderJugador.txtNombre.setText(listaJugador.get(i).getNombre());
        viewHolderJugador.txtPuntos.setText(listaJugador.get(i).getPuntos()+"");
    }

    @Override
    public int getItemCount() {
        return listaJugador.size();
    }


    public class ViewHolderJugador extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView txtNombre;
        TextView txtPuntos;

        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            txtNombre=itemView.findViewById(R.id.idNombre);
            txtPuntos=itemView.findViewById(R.id.idPuntos);
        }

    }
}
