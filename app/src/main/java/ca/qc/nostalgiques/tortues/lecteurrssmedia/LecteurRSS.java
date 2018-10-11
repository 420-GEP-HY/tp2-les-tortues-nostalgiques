package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.net.Uri;

public class LecteurRSS {

    public static FluxRSS lire(Uri uri) {
        FluxRSS flux = new FluxRSS(uri.getHost() + uri.getPath());

        flux.titre = "Titre temporaire";
        flux.nbElementsNonLus = 404;
        ElementDeFlux e1 = new ElementDeFlux();
        e1.titre = "Titre1";
        e1.lien = "www.google.ca";
        flux.elements.add(e1);
        ElementDeFlux e2 = new ElementDeFlux();
        e2.titre = "Titre2";
        e2.lien = "www.bing.ca";
        flux.elements.add(e2);

        return flux;
    }
}
