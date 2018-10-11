package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.net.URI;

public class ElementDeFlux implements Serializable {

    boolean estLu = false;
    String titre;
    String description;
    String lien;
    Bitmap vignette;

    public ElementDeFlux() {

    }
}
