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
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.R;

import java.util.List;

public class AdaptadorAvatar extends RecyclerView.Adapter<AdaptadorAvatar.ViewHolderAvatar> implements View.OnClickListener{

    private View.OnClickListener listener;
    List<Avatar> listaAvatars;
    View vista;

    int posicionMarcada=0;

    public AdaptadorAvatar(List<Avatar> listaAvatars  ) {
        this.listaAvatars = listaAvatars;
    }

    @NonNull
    @Override
    public ViewHolderAvatar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista= LayoutInflater.from(viewGroup.getContext()).inflate(com.example.paraulalogic.R.layout.item_grid_avatar,viewGroup,false);

        vista.setOnClickListener(this);

        return new ViewHolderAvatar(vista);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolderAvatar viewHolderAvatar, int i) {

        final int pos=i;
        final ViewHolderAvatar viewHolder=viewHolderAvatar;

        viewHolderAvatar.imgAvatar.setImageResource(listaAvatars.get(i).getAvatarId());
        viewHolderAvatar.cardAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicionMarcada = pos;
                Utilitats.avatarSeleccion = listaAvatars.get(pos);
                Utilitats.avatarIdSeleccion = pos+1;
                notifyDataSetChanged();


            }
        });

        if (Utilitats.avatarIdSeleccion==0){
            if (posicionMarcada == i){
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorBlanco));
            }
        }else{
            if (Utilitats.avatarIdSeleccion-1==pos){
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                viewHolderAvatar.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.colorBlanco));
            }
        }

    }

    @Override
    public int getItemCount() {
        return listaAvatars.size();
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


    public class ViewHolderAvatar extends RecyclerView.ViewHolder {

        CardView cardAvatar;
        ImageView imgAvatar;
        TextView barraSeleccion;

        public ViewHolderAvatar(@NonNull View itemView) {
            super(itemView);
            cardAvatar=itemView.findViewById(com.example.paraulalogic.R.id.cardAvatar);
            imgAvatar=itemView.findViewById(com.example.paraulalogic.R.id.idAvatar);
            barraSeleccion=itemView.findViewById(com.example.paraulalogic.R.id.barraSeleccionId);

        }

    }
}

