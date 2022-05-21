package com.example.paraulalogic.Activitat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.paraulalogic.Fragment.AjustesFragment;
import com.example.paraulalogic.R;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorAjustes,
                new AjustesFragment()).commit();
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnIcoAtras:
                finish();break;
        }
    }
}