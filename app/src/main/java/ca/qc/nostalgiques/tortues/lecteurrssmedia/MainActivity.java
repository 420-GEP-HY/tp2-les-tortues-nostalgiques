package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<FluxRSS> mesFlux;
    ArrayAdapter aa;
    ListView listeFlux;
    EditText texteAdresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeFlux = findViewById(R.id.listeFlux);
        texteAdresse = findViewById(R.id.texteAdresse);
        Button boutonAjouter = findViewById(R.id.boutonAjouter);

        chargerDonnees();
        if(mesFlux == null)
            mesFlux = new ArrayList<FluxRSS>();

        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesFlux.add(LecteurRSS.lire(Uri.parse(texteAdresse.getText().toString())));
                afficherDonnees();
                sauvegarderDonnees();
            }
        });

        if (Intent.ACTION_SEND.equals(getIntent().getAction())) {
            texteAdresse.setText(getIntent().getDataString());
        }
    }

    void afficherDonnees(){
        aa = new FluxRSSAdapter(this, 0, mesFlux);
        listeFlux.setAdapter(aa);
    }

    void chargerDonnees(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput("mesflux");
            ObjectInputStream is = new ObjectInputStream(fis);
            mesFlux = (ArrayList<FluxRSS>) is.readObject();
            afficherDonnees();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sauvegarderDonnees(){
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("mesflux", Context.MODE_PRIVATE);
            ObjectOutputStream od = new ObjectOutputStream(fos);
            od.writeObject(mesFlux);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        sauvegarderDonnees();
        super.onPause();
    }
}
