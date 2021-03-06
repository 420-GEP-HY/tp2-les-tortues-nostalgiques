package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class FluxRSSAdapter extends ArrayAdapter<FluxRSS> {
    List<FluxRSS> listeFlux;


    public FluxRSSAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FluxRSS> objects) {
        super(context, resource, objects);

        this.listeFlux = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_flux, parent, false);
        }

        Button boutonSupprimer = convertView.findViewById(R.id.boutonSupprimer);
        TextView texteTitre = convertView.findViewById(R.id.texteTitre);
        TextView texteNbElementsNonLus = convertView.findViewById(R.id.nbElementsNonLus);
        final ImageView imageVignette = convertView.findViewById(R.id.imageVignette);

        Thread thread = new Thread(new Runnable(){
            public void run() {
                try {
                    Bitmap logo = null;
                    try{
                        InputStream is = new URL(listeFlux.get(position).vignette).openStream();
                        logo = BitmapFactory.decodeStream(is);
                        imageVignette.setImageBitmap(logo);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        texteTitre.setText(listeFlux.get(position).titre);
        texteNbElementsNonLus.setText(Integer.toString(listeFlux.get(position).nbElementsNonLus));

        texteTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startElementsActivity(position);
            }
        });

        imageVignette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startElementsActivity(position);
            }
        });

        boutonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listeFlux.size() > position) {
                    listeFlux.remove(listeFlux.get(position));
                    notifyDataSetInvalidated();
                }
            }
        });

        return convertView;
    }



    private void startElementsActivity(int position) {
        Intent fluxRSSActivity = new Intent(getContext(), FluxRSSActivity.class);
        fluxRSSActivity.putExtra("indexFlux", position);
        getContext().startActivity(fluxRSSActivity);
    }
}

