package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.graphics.Bitmap;
import android.sax.Element;

import java.io.Serializable;
import java.net.URI;

public class ElementDeFlux implements Serializable {

    boolean estLu = false;
    String titre = "";
    String description = "";
    String lien = "";
    String date = "";
    Bitmap vignette = null;

    public ElementDeFlux() {}
}
