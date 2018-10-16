package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FluxRSSActivity extends AppCompatActivity {
    int indexFlux;
    ArrayAdapter aa;
    ListView listeElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxrss);

        Bundle b = getIntent().getExtras();
        indexFlux = b.getInt("indexFlux");

        listeElements = findViewById(R.id.listeElements);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    if(MainActivity.mesFlux.get(indexFlux).elements.size() == 0)
                        MainActivity.mesFlux.set(indexFlux, LecteurRSS.LireFlux(new URL(MainActivity.mesFlux.get(indexFlux).url)));
                }
                catch (MalformedURLException e) { }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        afficherDonnees();
                    }
                });
            }
        }).start();
    }

    private void afficherDonnees(){
        aa = new ElementDeFluxAdapter(this, 0, MainActivity.mesFlux.get(indexFlux).elements);
        listeElements.setAdapter(aa);
    }
}