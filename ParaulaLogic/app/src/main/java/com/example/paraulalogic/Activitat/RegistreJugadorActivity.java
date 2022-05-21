package com.example.paraulalogic.Activitat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paraulalogic.Adapter.AdaptadorAvatar;
import com.example.paraulalogic.Clases.ConexionSQLiteHelper;
import com.example.paraulalogic.Clases.PreferenciaJuego;
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.R;
import com.github.clans.fab.FloatingActionButton;

public class RegistreJugadorActivity extends AppCompatActivity {
    RecyclerView recylerAvatar;
    FloatingActionButton fabRegistre;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_jugador);
        recylerAvatar = findViewById(R.id.recyclerAvatarsId);
        fabRegistre = findViewById(R.id.idFabRegistro);
        text = findViewById(R.id.campoNickName);

        recylerAvatar.setLayoutManager(new GridLayoutManager(this, 3));
        recylerAvatar.setHasFixedSize(true);


        fabRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Registram el jugador en la taula jugador
                registrarJugador();
                Utilitats.puntuacio = 0;
                Utilitats.paraules = 0;
                //Registram la puntuació en la taula puntuacio
                registrarResultatInici();
            }
        });
        final AdaptadorAvatar miAdapter = new AdaptadorAvatar(Utilitats.listaAvatars);
        recylerAvatar.setAdapter(miAdapter);

    }
    //Métode que s'encarrega de ficar el jugador dins la base de dades
    private void registrarResultatInici() {
        int jugador= PreferenciaJuego.jugadorId;
        int punts=Utilitats.puntuacio;
        int paraules=Utilitats.paraules;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,Utilitats.NOM_BD,null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilitats.CAMP_ID_JUGADOR,jugador);
        values.put(Utilitats.CAMPO_PUNTUACIO,punts);
        values.put(Utilitats.CAMPO_PARAULES,paraules);
        long idResultant=db.insert(Utilitats.TABLA_PUNTUACIO, Utilitats.CAMP_ID_JUGADOR,values);

        if(idResultant!=-1){
            //Toast.makeText(this,"El puntaje a sido Registrado con Exito! "+idResultant,Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void registrarJugador() {
        if (text.getText().toString()!= null && !text.getText().toString().trim().equals("")) {
            int avatar = Utilitats.avatarSeleccion.getId();
            String nickname = text.getText().toString();
            String registro = "Nom: " + nickname + "\n";
            registro += "Avatar Id: " + avatar;
            ConexionSQLiteHelper con = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);
            SQLiteDatabase db = con.getWritableDatabase();
            //Enviam els elements que esteim capturam a la base de dades
            ContentValues values = new ContentValues();
            values.put(Utilitats.CAMP_NOM, nickname);
            values.put(Utilitats.CAMP_AVATAR, avatar);
            long idResult = db.insert(Utilitats.TAULA_JUGADOR, Utilitats.CAMP_ID, values);
            if (idResult != -1) {
                //Toast.makeText(this, "El jugador se ha registrat correctament!!\n"+idResult+ " - "+registro, Toast.LENGTH_LONG).show();
                PreferenciaJuego.jugadorId= Integer.parseInt(idResult+"");
                PreferenciaJuego.nickName = text.getText().toString();
                PreferenciaJuego.avatarId = avatar;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                PreferenciaJuego.asignarPreferenciasJugador(preferences, this);
                text.setText("");
                //TORNAR AL PRINCIPI
                finish();
            } else {
                Toast.makeText(this, "No se ha pogut registrar el Jugador!!\n", Toast.LENGTH_LONG).show();
            }
            //Tancam la conexio amb la base de dades
            db.close();
        } else {
            Toast.makeText(this, "Verifiqui les dades de registre!!", Toast.LENGTH_LONG).show();
        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIcoAtras:
                finish();
                break;
        }
    }
}