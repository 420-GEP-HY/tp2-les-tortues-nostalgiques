package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;

public class FluxRSS implements Serializable {
    String url;
    String titre = "";
    Bitmap vignette;
    int nbElementsNonLus = 0;
    ArrayList<ElementDeFlux> elements = new ArrayList<ElementDeFlux>();

    FluxRSS(String url){
        this.url = url;
    }
}
