package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class ElementDeFluxAdapter extends ArrayAdapter<ElementDeFlux> {
    List<ElementDeFlux> elements;

    public ElementDeFluxAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ElementDeFlux> objects) {
        super(context, resource, objects);

        this.elements = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_elementdeflux, parent, false);
        }

        TextView texteTitre = convertView.findViewById(R.id.texteTitre);
         ImageView imageVignette = convertView.findViewById(R.id.imageVignette);

      
        imageVignette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elements.get(position).estLu = true;
                notifyDataSetInvalidated();
                startLecteurMultimediaActivity(position);
            }
        });

        texteTitre.setText(elements.get(position).titre);
        if(elements.get(position).estLu)
            texteTitre.setTextColor(Color.GRAY);

        texteTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elements.get(position).estLu = true;
                MainActivity.mesFlux.get(((FluxRSSActivity)getContext()).indexFlux).elements.set(position, elements.get(position));
                int nonLus = 0;
                for (ElementDeFlux e : MainActivity.mesFlux.get(((FluxRSSActivity)getContext()).indexFlux).elements)
                    if(!e.estLu)
                        nonLus++;
                MainActivity.mesFlux.get(((FluxRSSActivity)getContext()).indexFlux).nbElementsNonLus = nonLus;
                notifyDataSetInvalidated();
                startLecteurMultimediaActivity(position);
            }
        });

        return convertView;
    }

    private void startLecteurMultimediaActivity(int position) {
        Intent lecteurMultimediaActivity = new Intent(getContext(), LecteurMultimediaActivity.class);
        Bundle b = new Bundle();
        b.putInt("indexFlux", ((FluxRSSActivity)getContext()).indexFlux);
        b.putInt("indexElement", position);
        lecteurMultimediaActivity.putExtra("extra", b);
        startActivity(getContext(), lecteurMultimediaActivity, new Bundle());
    }
}