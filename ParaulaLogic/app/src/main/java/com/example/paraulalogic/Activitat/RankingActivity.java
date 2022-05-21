package com.example.paraulalogic.Activitat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.paraulalogic.Adapter.AdaptadorResultado;
import com.example.paraulalogic.Clases.ConexionSQLiteHelper;
import com.example.paraulalogic.Clases.Jugador;
import com.example.paraulalogic.Clases.PreferenciaJuego;
import com.example.paraulalogic.Clases.Resultado;
import com.example.paraulalogic.Clases.Utilitats;
import com.example.paraulalogic.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    Spinner consltTipusResult;

    RecyclerView recyclerJugador;
    Jugador jugadorSeleccionat;
    ArrayList<Resultado> listaResultados;

    ImageButton btnEnrere, btnAjuda;
    TextView separador, misatge;

    FloatingActionButton btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        consltTipusResult = findViewById(R.id.comboTipoResultados);
        misatge = findViewById(R.id.txtSinDatos);

        //Asignamos los spinners
        ArrayAdapter<CharSequence> adapterTipoRes = ArrayAdapter.createFromResource(this, R.array.combo_resultados, android.R.layout.simple_spinner_item);
        consltTipusResult.setAdapter(adapterTipoRes);

        recyclerJugador = findViewById(R.id.recyclerJugadoresId);
        recyclerJugador.setLayoutManager(new LinearLayoutManager(this));
        recyclerJugador.setHasFixedSize(true);

        separador = findViewById(R.id.separadorId);
        //separador.setBackgroundColor(ContextCompat.getColor(activity, PreferenciaJuego.colorTema));
        btnEnrere = findViewById(R.id.btnIcoAtras);
        btnAjuda = findViewById(R.id.btnAyuda);

        btnEnrere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //se hace una consulta inicial
        consultarResultado("select j.id,j.nombre,j.avatar,p.punts from " + Utilitats.TAULA_JUGADOR + " j," + Utilitats.TABLA_PUNTUACIO + " p where j.id=p.id and j.id=" + PreferenciaJuego.jugadorId);

        btnBuscar = findViewById(R.id.idFabConsultar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jugadorId = PreferenciaJuego.jugadorId;
                String tipoConsulta = consltTipusResult.getSelectedItem().toString();
                String consulta = "";
                if (!tipoConsulta.equals("Individual")) {
                    consulta = "select j.id,j.nombre,j.avatar,p.punts from " + Utilitats.TAULA_JUGADOR + " j," + Utilitats.TABLA_PUNTUACIO + " p where j.id=p.id";
                } else {
                    consulta = "select j.id,j.nombre,j.avatar,p.punts from " + Utilitats.TAULA_JUGADOR + " j," + Utilitats.TABLA_PUNTUACIO + " p where j.id=p.id and j.id=" + jugadorId;
                }
                consultarResultado(consulta);
            }
        });
    }
    private void consultarResultado(String s) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Resultado resultados = null;
        listaResultados = new ArrayList<Resultado>();
        //select * from usuarios
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            resultados = new Resultado();
            resultados.setId(cursor.getInt(0));
            resultados.setNombre(cursor.getString(1));
            resultados.setAvatar(cursor.getInt(2));
            resultados.setPuntos(cursor.getInt(3));

            listaResultados.add(resultados);
        }

        if (listaResultados.size() > 0) {
            misatge.setText("Se encontraron " + listaResultados.size() + " resultados");
        } else {
            misatge.setText("No hay datos asociados!");
        }

        db.close();
        llenarAdaptadorJugadores();
    }

    private void llenarAdaptadorJugadores() {
        AdaptadorResultado adaptadorResultados = new AdaptadorResultado(listaResultados);
        recyclerJugador.setAdapter(adaptadorResultados);
    }

}