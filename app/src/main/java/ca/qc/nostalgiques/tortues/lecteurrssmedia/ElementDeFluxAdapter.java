package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Context;
import android.content.Intent;
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

        texteTitre.setText(elements.get(position).titre);
        if(elements.get(position).estLu)
            texteTitre.setTextColor(Color.GRAY);

        texteTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elements.get(position).estLu = true;
                notifyDataSetInvalidated();
                startLecteurMultimediaActivity(position);
            }
        });

        return convertView;
    }

    private void startLecteurMultimediaActivity(int position) {
        Intent lecteurMultimediaActivity = new Intent(getContext(), LecteurMultimediaActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("element", elements.get(position));
        lecteurMultimediaActivity.putExtra("extra", b);
        startActivity(getContext(), lecteurMultimediaActivity, new Bundle());
    }
}