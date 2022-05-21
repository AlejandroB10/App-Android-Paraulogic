package com.example.paraulalogic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paraulalogic.Activitat.AjustesActivity;
import com.example.paraulalogic.Activitat.GestionarJugadorActivity;
import com.example.paraulalogic.Activitat.InformacioActivity;
import com.example.paraulalogic.Activitat.RankingActivity;
import com.example.paraulalogic.Activitat.RegistreJugadorActivity;
import com.example.paraulalogic.Clases.ConexionSQLiteHelper;
import com.example.paraulalogic.Clases.PreferenciaJuego;
import com.example.paraulalogic.Clases.Resultado;
import com.example.paraulalogic.Clases.Utilitats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;

//Autor: Alejandro Rafael Bordón Duarte

public class MainActivity extends AppCompatActivity {

    private TextView text, textPalabra, txtParaules, txtPuntuacio;
    private String r = "";
    private Button[] botones = new Button[7];
    private UnsortedArraySet<Character> array;
    private BSTSetMapping<String, Integer> palabras = new BSTSetMapping();
    //Definimos un objeto TreeSet que se encarga de almacenar las palabras del diccionario
    TreeSet<String> tree = new TreeSet();
    private Toast toast;
    private static final int duration = 50000; // 50 seconds
    private Button configurar, ranking, ajuda;
    //private Button registrar;
    TextView textNickName;
    ImageView imagenAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);

        Utilitats.obtenerListaAvatars(); //Construim la llista de avatasr
        Utilitats.consultLlistaJugadors(this);
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(this, Utilitats.NOM_BD, null, 1);

        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        PreferenciaJuego.asignarPreferenciasJugador(preferences, getApplicationContext());

        //Obtenemos los identificadores de los botones y lo almacenamos en un array de enter
        int[] idBoton = new int[]{R.id.button3, R.id.button4, R.id.button8, R.id.button5,
                R.id.button6, R.id.button7, R.id.button9};
        //Asignamos el valor del textView a la variable text
        text = (TextView) findViewById(R.id.textView);
        //Asignamos el valor del textView
        textPalabra = (TextView) findViewById(R.id.textView3);
        txtParaules = findViewById(R.id.txtParaules);
        txtPuntuacio = findViewById(R.id.txtCorrectes);

        // registrar = findViewById(R.id.avatarImage);
        configurar = findViewById(R.id.configuracio);
        ranking = findViewById(R.id.ranking);
        ajuda = findViewById(R.id.ajuda);

        textNickName = findViewById(R.id.textNickName);
        imagenAvatar = findViewById(R.id.avatarImage);

        carregarUsuari();

        //Inicializamos cada boton del array de botones
        for (int i = 0; i < botones.length; i++) {
            botones[i] = findViewById(idBoton[i]);
        }

        //Inicialitzam els valors del tauler de puntuació
        Utilitats.paraules = 0;
        Utilitats.puntuacio = 0;

        //Generamos las letras de forma aleatoria
        array = lletresAleatoris();
        //Definimos un objeto iterator
        Iterator iArray = array.iterator();
        int i = 0;
        //Iteramos mientras el array tenga datos
        while (iArray.hasNext()) {
            //Seteamos el valor de los botones
            botones[i].setText(iArray.next() + "");
            i++;
        }

        //Llamamos al metodo que se encarga de cargar el diccionario y creamos el
        //arcbol rojo/negro
        cargarDiccionario();

        //Introducimos la palabra en el textView
        Button botonIntroducir = findViewById(R.id.button10);
        Context context = getApplicationContext();
        botonIntroducir.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                //Comprobam primer si la paraula te tres lletres
                if (r.length() >= 3) {
                    //Comprobam si te la lletra d'enmig
                    if (r.contains(botones[6].getText())) {
                        //Comprobam si se troba dins el diccionari
                        if (tree.contains(r.toLowerCase(Locale.ROOT))) {
                            //Afigim dins el arbre
                            incrementarFrec(r);
                            text.setText("");
                            r="";
                        } else {
                            CharSequence text = "La palabra no se encuentra en el diccionario!";
                            Toast.makeText(context, text, duration).show();
                        }
                    } else {
                        CharSequence text = "No contiene la letra de en medio!";
                        Toast.makeText(context, text, duration).show();
                    }
                } else {
                    CharSequence text = "La palabra no tiene tres letras!";
                    Toast.makeText(context, text, duration).show();
                }
                //Definimos un objeto iterator
                Iterator pArray = palabras.iterator();
                String result = "";
                int j = 0;
                //Iteramos mientras el array tenga datos
                while (pArray.hasNext()) {
                    System.out.println(result);
                    BSTSetMapping.Pair p = (BSTSetMapping.Pair) pArray.next();
                    result += p.getKey() + " (" + p.getValor() + ") ";
                    j++;
                }
                textPalabra.setText("Has trobat " + j + " paraules: " + result);
            }
        });

        Button botonRemove = findViewById(R.id.button);
        botonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprobar si el ultimo elemento
                if (r.length() > 0) {
                    r = r.substring(0, r.length() - 1);
                    text.setText(r);
                }

            }
        });

        Button botonAleatorio = findViewById(R.id.button2);
        botonAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                int j = rnd.nextInt(6);
                for (int i = 0; i < 5; i++) {
                    CharSequence temp = botones[i].getText();
                    botones[i].setText(botones[j].getText());
                    botones[j].setText(temp);
                }
            }
        });
        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestionarAjuste();
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarRanking();
            }
        });
        ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarAjuda();
            }
        });
    }

    private void asignaValor() {
        txtParaules.setText(Integer.toString(Utilitats.paraules));
        txtPuntuacio.setText(Utilitats.puntuacio + "");
        //Actualitzam la base de dades
        actualitzarResultat();
    }

    public UnsortedArraySet<Character> lletresAleatoris() {
        //Instanciamos un nuevo objeto UnsortedArraySet
        UnsortedArraySet<Character> array = new UnsortedArraySet<Character>(7);
        Random rnd = new Random();
        int j = 0;
        while (j < 7) {
            char c = (char) (rnd.nextInt(25) + 65); //Generamos un numero random entre 0-25,
            // para obtener las letras mayúsculas [A-Z]
            //Cada vez que generemos una letra nueva comprobamos si ya se encuentra dentro del array
            if (!array.contains(c)) {
                //Añadimos el caracater en el array
                array.add(c);
                j++;
            }
        }
        array.toString();
        return array;
    }

    //Métode que se encarga de setear las letras de los botones
    public void setLletres(View view) {
        Button btn = (Button) findViewById(view.getId());
        r += btn.getText().toString();
        text.setText(r);
        carregarUsuari();
    }

    //Método que se encaraga de añadir una  dentro del arbol
    public void incrementarFrec(String key) {
        //Comprobamos si la palabra se encuentra dentro del arbol
        if (this.palabras.get(key) != null) {
            //Si es este caso incrementamos la frecuencia de la palabra
            this.palabras.put(key, this.palabras.get(key) + 1);
            System.out.println("Introducimos por segunda vez");
            //En caso contrario añadimos la palabra con la frecuencia 1
        } else {
            System.out.println("Primera vez que se introduce");
            //Incrementam el nombre de paraules en el tauler resultant
            Utilitats.paraules++;
            //Incrementam també la puntuació obtinguda en el joc
            Utilitats.puntuacio += 10;
            //Afegim la paraula
            this.palabras.put(key, 1);
            //Asignam els valors obtinguts al tauler
            asignaValor();
        }
    }

    //Método que s'encarrega de carregar el diccionari dins un arbre vermell i negre
    public void cargarDiccionario() {
        try {
            InputStream is = getResources().openRawResource(R.raw.catala_filtrat);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line = null;
            //Leemos linia a linia el fichero
            line = r.readLine();
            while (line != null) {
                //Añadimos la palabra en el arbol
                tree.add(line);
                //Leemos la siguiente linea
                line = r.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    //Métode que s'encarrega de gestiona la actualització dels punts i de les paraules encertades
    private void actualitzarResultat() {
        int jugador = PreferenciaJuego.jugadorId;
        //Consultam el nombre de puntuacio i de paraules que te el jugador actualment
        int[] puntuacio = consultarPuntsParaules("select p.punts,p.paraules from " + Utilitats.TABLA_PUNTUACIO + " p where p.id=" + PreferenciaJuego.jugadorId);

        //Actualizamos las puntuaciones de los jugadores
        int punts = Utilitats.puntuacio + puntuacio[0];
        int paraules = Utilitats.paraules + puntuacio[1];
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilitats.NOM_BD, null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        //Enviam els elements que esteim capturam a la base de dades
        ContentValues values = new ContentValues();
        values.put(Utilitats.CAMP_ID_JUGADOR, jugador);
        values.put(Utilitats.CAMPO_PUNTUACIO, punts);
        values.put(Utilitats.CAMPO_PARAULES, paraules);
        int idResult = db.update(Utilitats.TABLA_PUNTUACIO, values, Utilitats.CAMP_ID + "=" + jugador, null);
        if (idResult != -1) {
            Toast.makeText(getApplicationContext(), "El jugador se ha actualizat correctament!!\n", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No se ha pogut actualizat el Jugador!!\n", Toast.LENGTH_LONG).show();
        }
        //Tancam la conexio amb la base de dades
        db.close();
    }

    private int[] consultarPuntsParaules(String s) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilitats.NOM_BD, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Resultado resultados = null;
        int[] pp = new int[2];
        //select * from usuarios
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            for (int i = 0; i < 2; i++) {
                pp[i] = cursor.getInt(i);
            }
        }

        db.close();
        return pp;
    }

    //Método que s'encarrega de generar un Dialog Alert
    public AlertDialog dialogGestioUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Gestionar Jugador").setMessage("Indiqueu si voleu registrar un jugador o " +
                "si voleu seleccionar un ja existent\n\n" +
                "També podreu modificar un jugador des de l'opció seleccionar")
                .setNegativeButton("REGISTRAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilitats.avatarIdSeleccion = 0;//solo cuando se haga lo de seleccionar avatar en jugador
                                Utilitats.avatarSeleccion = Utilitats.listaAvatars.get(0);//se asigna el primer elemento para que siempre se tenga esta referencia hasta el evento clic
                                //  Toast.makeText(getApplicationContext(),"Avatar Seleccion: "+Utilidades.avatarSeleccion.getId(),Toast.LENGTH_SHORT).show();
                                registreJugador();
                                //Esperam 3 segons perque se mostri el dialog
                                new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                        builder1.setTitle("Gestionar Jugador").setMessage("L'Usuari s'ha registrat correctament")
                                                .setPositiveButton("ACCEPTAR",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                carregarUsuari();
                                                            }
                                                        }).show();
                                    }
                                }.start();
                            }
                        })
                .setPositiveButton("SELECCIONAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Utilitats.listaJugadores != null && Utilitats.listaJugadores.size() > 0) {
                                    gestionarUsuarioSeleccionado();
                                    //Esperam 3 segons perque se mostri el dialog
                                    new CountDownTimer(3000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                            builder1.setTitle("Gestionar Jugador").setMessage("L'Usuari s'ha seleccionat correctament")
                                                    .setPositiveButton("ACCEPTAR",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    carregarUsuari();
                                                                }
                                                            }).show();
                                        }
                                    }.start();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Debe registrar un Jugador", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        carregarUsuari();
        return builder.create();
    }

    private void gestionarUsuarioSeleccionado() {
        Intent intent = new Intent(this, GestionarJugadorActivity.class);
        startActivity(intent);
    }

    public void gestionarUsuario() {
        dialogGestioUsuario().show();
    }

    public void gestionarAjuste() {
        Intent intent = new Intent(this, AjustesActivity.class);
        startActivity(intent);
    }

    public void registreJugador() {
        Intent intent = new Intent(this, RegistreJugadorActivity.class);
        startActivity(intent);
    }

    public void consultarAjuda() {
        Intent intent = new Intent(this, InformacioActivity.class);
        startActivity(intent);
    }

    public void consultarRanking() {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.avatarImage) {
            gestionarUsuario();
            carregarUsuari();

        }
    }

    public void carregarUsuari() {
        textNickName.setText(PreferenciaJuego.nickName);
        if (PreferenciaJuego.avatarId == 0) {
            imagenAvatar.setImageResource(R.drawable.avatar1);
        } else {
            imagenAvatar.setImageResource(Utilitats.listaAvatars.get(PreferenciaJuego.avatarId - 1).getAvatarId());
        }
    }
}

