package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
        if (mesFlux == null)
            mesFlux = new ArrayList<FluxRSS>();

        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ParcourirFlux(new URL(texteAdresse.getText().toString()));
                        } catch (MalformedURLException e) {
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                afficherDonnees();
                                sauvegarderDonnees();
                            }
                        });
                    }
                }).start();
            }
        });

        if (Intent.ACTION_SEND.equals(getIntent().getAction())) {
            texteAdresse.setText(getIntent().getDataString());
        }
    }

    void afficherDonnees() {
        aa = new FluxRSSAdapter(this, 0, mesFlux);
        listeFlux.setAdapter(aa);
    }

    void chargerDonnees() {
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

    void sauvegarderDonnees() {
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

    public void ParcourirFlux(URL url) {
        FluxRSS flux = new FluxRSS(url.toString());
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            int typeEvenement = xpp.getEventType();
            boolean titreTrouve = false;
            boolean imageTrouve = false;
            boolean dansImage = false;
            while (typeEvenement != XmlPullParser.END_DOCUMENT) {
                if (typeEvenement == XmlPullParser.START_TAG) {
                    if (!titreTrouve && xpp.getName().equalsIgnoreCase("title")) {
                        flux.titre = xpp.nextText();
                        titreTrouve = true;
                    } else if (!imageTrouve && xpp.getName().equalsIgnoreCase("image"))
                        dansImage = true;
                    else if (dansImage && xpp.getName().equalsIgnoreCase("url")) {
                        if (dansImage) {
                            String imageUrl = xpp.nextText();
                            InputStream inputStream = new URL(imageUrl).openConnection().getInputStream();
                            flux.vignette = BitmapFactory.decodeStream(inputStream);
                            imageTrouve = true;
                        }
                    } else flux.nbElementsNonLus++;
                }
                typeEvenement = xpp.next();
            }
        } catch (MalformedURLException e) {
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }

        mesFlux.add(flux);
    }
}






