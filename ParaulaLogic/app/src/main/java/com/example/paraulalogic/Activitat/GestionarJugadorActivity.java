package com.example.paraulalogic.Activitat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paraulalogic.Adapter.AdaptadorAvatar;
import com.example.paraulalogic.Adapter.AdaptadorJugador;
import com.example.paraulalogic.Clases.ConexionSQLiteHelper;
import com.example.paraulalogic.Clases.Jugador;
import com.example.paraulalogic.Clases.PreferenciaJuego;
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.MainActivity;
import com.example.paraulalogic.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Locale;

public class GestionarJugadorActivity extends AppCompatActivity {
    int evento = 0;

    RecyclerView recyclerAvatar, recyclerJugadores;
    ImageButton btnAtras, btnAyuda;
    EditText text;

    TextView barraSeleccion;

    FloatingActionMenu grupoBotones;
    FloatingActionButton fabConfirmar, fabActualizar, fabEliminar;

    Jugador jugadorSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_jugador);

        recyclerAvatar = findViewById(R.id.recyclerAvatarsId);
        recyclerJugadores = findViewById(R.id.recyclerJugadoresId);

        barraSeleccion = findViewById(R.id.barraSeleccionId);
        grupoBotones = findViewById(R.id.grupoFab);
        fabConfirmar = findViewById(R.id.idFabConfirmar);
        fabActualizar = findViewById(R.id.idFabActualizar);
        fabEliminar = findViewById(R.id.idFabEliminar);

        btnAtras = findViewById(R.id.btnIcoAtras);
        text = findViewById(R.id.campoNickName);

        recyclerJugadores.setLayoutManager(new LinearLayoutManager(this));
        recyclerJugadores.setHasFixedSize(true);

        recyclerAvatar.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerAvatar.setHasFixedSize(true);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evento == 0) {
                    //TORNAR AL PRINCIPI
                    finish();
                    text.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Seleccioneu un jugador i premeu el botó de Confirmar!!", Toast.LENGTH_LONG).show();
                }

            }
        });

        fabActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizaJugador();
                llenarAdapadorJugador();//Actualitzam la llista de jugadors
                grupoBotones.close(true);//El menu se cirra automaticamente

                evento = 0;
            }
        });
        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString() != null && !text.getText().toString().trim().equals("")) {
                    PreferenciaJuego.nickName = "" + text.getText().toString();
                    PreferenciaJuego.avatarId = Utilitats.avatarSeleccion.getId();
                    PreferenciaJuego.jugadorId = jugadorSelect.getId();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    PreferenciaJuego.asignarPreferenciasJugador(preferences, getApplicationContext());

                    grupoBotones.close(true);
                    text.setText("");
                    evento = 0;
                    //TORNAR AL PRINCIPI
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Seleccioni un Usuari!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString() != null && !text.getText().toString().trim().equals("")) {
                    dialogGestioUsuario().show();
                    grupoBotones.close(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Seleccioni un usuari per Eliminar!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        llenarAdapadorJugador();//Actualitzam la llista de jugadors
        llenarAdaptadorAvatar(0);

    }

    private void llenarAdapadorJugador() {
        //Rellenam el adaptador del jugadors
        AdaptadorJugador miAdapatadorJugador = new AdaptadorJugador(Utilitats.listaJugadores);
        miAdapatadorJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grupoBotones.close(true);
                jugadorSelect = Utilitats.listaJugadores.get(recyclerJugadores.getChildAdapterPosition(v));
                text.setText(jugadorSelect.getNombre());
                Utilitats.avatarSeleccion = Utilitats.listaAvatars.get(jugadorSelect.getAvatar() - 1);
                llenarAdaptadorAvatar(jugadorSelect.getAvatar());
            }
        });
        recyclerJugadores.setAdapter(miAdapatadorJugador);
    }

    private void llenarAdaptadorAvatar(int avatar) {
        Utilitats.avatarIdSeleccion = avatar;
        AdaptadorAvatar miAdapatador = new AdaptadorAvatar(Utilitats.listaAvatars);
        recyclerAvatar.scrollToPosition(avatar - 1);
        recyclerAvatar.setAdapter(miAdapatador);
    }

    //Método que s'encarrega de generar un Dialog Alert
    public AlertDialog dialogGestioUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA!!!").setMessage("Segur que vols eliminar l'usuari " + jugadorSelect.getNombre().toUpperCase(Locale.ROOT) + " i tota la seva informació?")
                .setNegativeButton("CANCEL·LAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton("ACCEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Eliminam el jugador
                                eliminarJugador();
                                eliminarResultat();
                                llenarAdapadorJugador();//Actualitzam la llista de jugadors
                            }
                        });


        return builder.create();
    }

    private void eliminarJugador() {
        if (text.getText().toString() != null && !text.getText().toString().trim().equals("")) {
            ConexionSQLiteHelper con = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);
            SQLiteDatabase db = con.getWritableDatabase();

            int result = db.delete(Utilitats.TAULA_JUGADOR, Utilitats.CAMP_ID + "=" + jugadorSelect.getId(), null);
            if (result != -1) {
                //Toast.makeText(this, "El jugador s'ha eliminat correctament", Toast.LENGTH_LONG).show();
                text.setText("");//Reseteam el camp de text
                recyclerJugadores.scrollToPosition(jugadorSelect.getId());
                recyclerAvatar.scrollToPosition(0);
                Utilitats.consultLlistaJugadors(this); //Tornam a consulta la base de dades amb la llista de jugadors

                PreferenciaJuego.avatarId = 1;
                PreferenciaJuego.nickName = "NA";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                PreferenciaJuego.asignarPreferenciasJugador(preferences, this);

                evento = 1;
            } else {
                Toast.makeText(this, "El jugador no s'ha pogut eliminar", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void actualizaJugador() {
        if (text.getText().toString() != null && !text.getText().toString().trim().equals("")) {
            int avatar = Utilitats.avatarSeleccion.getId();
            String nickname = text.getText().toString();

            ConexionSQLiteHelper con = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);
            SQLiteDatabase db = con.getWritableDatabase();

            //Enviam els elements que esteim capturam a la base de dades
            ContentValues values = new ContentValues();
            values.put(Utilitats.CAMP_NOM, nickname);
            values.put(Utilitats.CAMP_AVATAR, avatar);
            int idResult = db.update(Utilitats.TAULA_JUGADOR, values, Utilitats.CAMP_ID + "=" + jugadorSelect.getId(), null);
            if (idResult != -1) {
                Toast.makeText(this, "El jugador se ha actualizat correctament!!\n", Toast.LENGTH_LONG).show();
                recyclerJugadores.scrollToPosition(jugadorSelect.getId() - 1);
                Utilitats.consultLlistaJugadors(this);
            } else {
                Toast.makeText(this, "No se ha pogut actualizat el Jugador!!\n", Toast.LENGTH_LONG).show();
            }
            //Tancam la conexio amb la base de dades
            db.close();
        } else {
            Toast.makeText(this, "Verifiqui les dades de registre!!", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarResultat() {
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);
        SQLiteDatabase db = con.getWritableDatabase();

        int result = db.delete(Utilitats.TABLA_PUNTUACIO, Utilitats.CAMP_ID + "=" + jugadorSelect.getId(), null);
        if (result != -1) {
            //Toast.makeText(this, "El jugador s'ha eliminat correctament", Toast.LENGTH_LONG).show();
            text.setText("");//Reseteam el camp de text
            Utilitats.paraules = 0;
            Utilitats.puntuacio = 0;
            //Tancam la conexio amb la base de dades
            db.close();
        }
    }

}