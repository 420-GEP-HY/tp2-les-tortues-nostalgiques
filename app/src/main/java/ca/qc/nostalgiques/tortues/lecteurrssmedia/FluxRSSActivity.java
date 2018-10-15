package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
    FluxRSS flux;
    ArrayAdapter aa;
    ListView listeElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxrss);


        Bundle b = getIntent().getExtras();
        String url = b.getString("url");
        flux = new FluxRSS(url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    LireFlux(new URL(flux.url));
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

        listeElements = findViewById(R.id.listeElements);
        afficherDonnees();
    }

    private void afficherDonnees(){
        aa = new ElementDeFluxAdapter(this, 0, flux.elements);
        listeElements.setAdapter(aa);
    }

    public void LireFlux (URL url) {
        FluxRSS flux = new FluxRSS(url.toString());
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            flux.elements.add(new ElementDeFlux());

            int typeEvenement = xpp.getEventType();
            boolean dansItem = false;
            while(typeEvenement != XmlPullParser.END_DOCUMENT)
            {
                ElementDeFlux element = flux.elements.get(flux.elements.size() - 1);
                if(typeEvenement == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                        dansItem = true;
                    else if (dansItem)
                    {
                        if (xpp.getName().equalsIgnoreCase("title"))
                                element.titre = xpp.nextText();
                        else if (xpp.getName().equalsIgnoreCase("link"))
                            element.lien = xpp.nextText();
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                            element.date = xpp.nextText();
                        else if (xpp.getName().equalsIgnoreCase("description"))
                            element.description = xpp.nextText();
                        else if (xpp.getName().equalsIgnoreCase("enclosure"))
                        {
                            try
                            {
                                String imageUrl = xpp.nextText();
                                InputStream inputStream = new URL(imageUrl).openConnection().getInputStream();
                                element.vignette = BitmapFactory.decodeStream(inputStream);
                            }
                            catch (MalformedURLException e) {}
                        }
                    }
                }
                else if(typeEvenement == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                {
                    dansItem = false;
                    flux.elements.add(new ElementDeFlux());
                }
                typeEvenement = xpp.next();
            }
            if(flux.elements.size() > 0)
                flux.elements.remove(flux.elements.size() - 1);
        }
        catch (MalformedURLException e) { }
        catch (XmlPullParserException e) { }
        catch (IOException e ) { }
    }
}
