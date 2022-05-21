package com.example.paraulalogic.Clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.paraulalogic.R;

public class PreferenciaJuego {
    public static final String COLOR_TEMA="colorTema";
    public static final String FORMA_BANNER="formaBanner";
    public static final String JUGADOR_ID="jugadorId";
    public static final String NICKNAME="nickName";
    public static final String AVATAR_ID="avatarId";

    public static int colorTema;
    public static int formaBanner;

    public static int jugadorId;
    public static String nickName;
    public static int avatarId;

    public static void asignarPreferenciasJugador(SharedPreferences preferences, Context applicationContext){

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(JUGADOR_ID,""+jugadorId);
        editor.putString(NICKNAME,""+nickName);
        editor.putString(AVATAR_ID,""+avatarId);
        editor.commit();

        obtenerPreferencias(preferences,applicationContext);
    }

    public static void asignarPreferenciasTema(SharedPreferences preferences, Context applicationContext){

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(COLOR_TEMA,""+colorTema);
        editor.putString(FORMA_BANNER,""+formaBanner);
        editor.commit();

        obtenerPreferencias(preferences,applicationContext);
    }

    public static void obtenerPreferencias(SharedPreferences preferences, Context applicationContext) {

        jugadorId=Integer.parseInt(preferences.getString(JUGADOR_ID,"0"));
        nickName=preferences.getString(NICKNAME,"NA");
        avatarId=Integer.parseInt(preferences.getString(AVATAR_ID,"1"));

    }
}
