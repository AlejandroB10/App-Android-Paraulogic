package com.example.paraulalogic.Clases;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Utilitats {
    //permite tener la referencia del avatar seleccionado para el momento del registro en la BD
    public static Avatar avatarSeleccion=null;
    public static int avatarIdSeleccion=0;
    public static Avatar seleccion = null;
    public static  int avatarId = 0;
    public static ArrayList<Avatar> listaAvatars=null;
    public static ArrayList<Jugador> listaJugadores=null;

    public static final String NOM_BD = "parulogic_bd";

    //Cream les constants per als diferents camps de la tabla jugador
    public static final String TAULA_JUGADOR = "jugador";
    public static final String CAMP_ID = "id";
    public static final String CAMP_NOM = "nombre";
    public static final String CAMP_AVATAR = "avatar";

    public static final String CREAR_TAULA_JUGADOR = "CREATE TABLE "+TAULA_JUGADOR+ " ("+CAMP_ID+" " +
            "INTEGER PRIMARY KEY,"+CAMP_NOM+" TEXT,"+CAMP_AVATAR+" INTEGER)";

    //Constants camps taula puntuacio
    public static final String TABLA_PUNTUACIO="puntuacio_jugador";
    public static final String CAMP_ID_JUGADOR="id";
    public static final String CAMPO_PUNTUACIO="punts";
    public static final String CAMPO_PARAULES="paraules";

     public static final String CREAR_TAULA_PUNTUACIO="CREATE TABLE "+TABLA_PUNTUACIO+" ("+CAMP_ID_JUGADOR+" INTEGER, "+CAMPO_PUNTUACIO+" INTEGER,"+CAMPO_PARAULES+
            " INTEGER)";

    public static int paraules;
    public static int puntuacio;

    public static void obtenerListaAvatars() {

        //se instancian los avatars y se llena la lista
        listaAvatars=new ArrayList<Avatar>();

        listaAvatars.add(new Avatar(1,com.example.paraulalogic.R.drawable.avatar2,"Avatar2"));
        listaAvatars.add(new Avatar(2,com.example.paraulalogic.R.drawable.avatar3,"Avatar3"));
        listaAvatars.add(new Avatar(3,com.example.paraulalogic.R.drawable.avatar4,"Avatar4"));
        listaAvatars.add(new Avatar(4,com.example.paraulalogic.R.drawable.avatar5,"Avatar5"));
        listaAvatars.add(new Avatar(5,com.example.paraulalogic.R.drawable.avatar6,"Avatar6"));
        listaAvatars.add(new Avatar(6,com.example.paraulalogic.R.drawable.avatar7,"Avatar7"));
        listaAvatars.add(new Avatar(7,com.example.paraulalogic.R.drawable.avatar8,"Avatar8"));
        listaAvatars.add(new Avatar(8,com.example.paraulalogic.R.drawable.avatar9,"Avatar9"));
        listaAvatars.add(new Avatar(9,com.example.paraulalogic.R.drawable.avatar10,"Avatar10"));
        listaAvatars.add(new Avatar(10,com.example.paraulalogic.R.drawable.avatar11,"Avatar11"));
        listaAvatars.add(new Avatar(11,com.example.paraulalogic.R.drawable.avatar12,"Avatar12"));
        listaAvatars.add(new Avatar(12,com.example.paraulalogic.R.drawable.avatar13,"Avatar13"));
        listaAvatars.add(new Avatar(13,com.example.paraulalogic.R.drawable.avatar14,"Avatar14"));
        listaAvatars.add(new Avatar(14,com.example.paraulalogic.R.drawable.avatar15,"Avatar15"));
        listaAvatars.add(new Avatar(15,com.example.paraulalogic.R.drawable.avatar16,"Avatar16"));
        listaAvatars.add(new Avatar(16,com.example.paraulalogic.R.drawable.avatar17,"Avatar17"));
        listaAvatars.add(new Avatar(17,com.example.paraulalogic.R.drawable.avatar18,"Avatar18"));
        listaAvatars.add(new Avatar(18,com.example.paraulalogic.R.drawable.avatar19,"Avatar19"));
        listaAvatars.add(new Avatar(19,com.example.paraulalogic.R.drawable.avatar20,"Avatar20"));
        listaAvatars.add(new Avatar(20,com.example.paraulalogic.R.drawable.avatar21,"Avatar21"));
        listaAvatars.add(new Avatar(21,com.example.paraulalogic.R.drawable.avatar22,"Avatar22"));
        listaAvatars.add(new Avatar(22,com.example.paraulalogic.R.drawable.avatar23,"Avatar23"));
        listaAvatars.add(new Avatar(23,com.example.paraulalogic.R.drawable.avatar24,"Avatar24"));
        listaAvatars.add(new Avatar(24,com.example.paraulalogic.R.drawable.avatar25,"Avatar25"));
        listaAvatars.add(new Avatar(25,com.example.paraulalogic.R.drawable.avatar26,"Avatar26"));
        listaAvatars.add(new Avatar(26,com.example.paraulalogic.R.drawable.avatar27,"Avatar27"));

        avatarSeleccion= listaAvatars.get(0);
    }

    public static void consultLlistaJugadors (Activity activity){
        //Activamos una conexion con la base de datos para leer los datos de los usuarios
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(activity, NOM_BD, null, 1);
        SQLiteDatabase db = con.getReadableDatabase();

        Jugador jugador = null;
        listaJugadores = new ArrayList<Jugador>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TAULA_JUGADOR, null);

        while (cursor.moveToNext()){
            jugador = new Jugador();
            jugador.setId(cursor.getInt(0));
            jugador.setNombre(cursor.getString(1));
            jugador.setAvatar(cursor.getInt(2));

            listaJugadores.add(jugador);
        }
        //Cerramos la consulta con la bd
        db.close();


    }


}
