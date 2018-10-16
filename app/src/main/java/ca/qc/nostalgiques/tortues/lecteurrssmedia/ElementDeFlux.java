package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.sax.Element;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;

public class ElementDeFlux implements Serializable {

    boolean estLu = false;
    String titre = "";
    String description = "";
    String lien = "";
    String date = "";
    String vignette = null;
    String video = null;

    public ElementDeFlux() {}
}
