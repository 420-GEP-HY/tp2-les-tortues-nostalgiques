package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.graphics.BitmapFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class LecteurRSS {
    public static FluxRSS ParcourirFlux (URL url) {
        FluxRSS flux = new FluxRSS(url.toString());
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            int typeEvenement = xpp.getEventType();
            boolean titreTrouve = false;
            boolean imageTrouve = false;
            boolean dansImage = false;
            while(typeEvenement != XmlPullParser.END_DOCUMENT)
            {
                if(typeEvenement == XmlPullParser.START_TAG) {
                    if (!titreTrouve && xpp.getName().equalsIgnoreCase("title"))
                    {
                        flux.titre = xpp.nextText();
                        titreTrouve = true;
                    }
                    else if (!imageTrouve && xpp.getName().equalsIgnoreCase("image"))
                        dansImage = true;
                    else if (dansImage && xpp.getName().equalsIgnoreCase("url"))
                    {
                        if (dansImage)
                        {
                            String imageUrl = xpp.nextText();
                            flux.vignette = imageUrl;
                            imageTrouve = true;
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("item"))
                        flux.nbElementsNonLus++;
                }
                typeEvenement = xpp.next();
            }
        }
        catch (MalformedURLException e) { }
        catch (XmlPullParserException e) { }
        catch (IOException e ) { }

        return flux;
    }

    public static FluxRSS LireFlux (URL url) {
        FluxRSS flux = ParcourirFlux(url);
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            ElementDeFlux element = new ElementDeFlux();

            int typeEvenement = xpp.getEventType();
            boolean dansItem = false;
            while(typeEvenement != XmlPullParser.END_DOCUMENT)
            {
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

                                String Url = xpp.getAttributeValue(null, "url");
                                String type = xpp.getAttributeValue(null,"type");
                                if(type.contains("video")|| type.contains("audio"))
                                    element.video = Url;
                                else if(type.contains("image"))
                                element.vignette = Url;
                        }
                    }
                }
                else if(typeEvenement == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                {
                    dansItem = false;
                    flux.elements.add(element);
                    element = new ElementDeFlux();
                }
                typeEvenement = xpp.next();
            }
        }
        catch (MalformedURLException e) { }
        catch (XmlPullParserException e) { }
        catch (IOException e ) { }
        return flux;
    }
}