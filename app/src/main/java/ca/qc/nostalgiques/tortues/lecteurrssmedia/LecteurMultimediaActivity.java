package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LecteurMultimediaActivity extends AppCompatActivity {
    ElementDeFlux element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecteurmultimedia);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras().getBundle("extra");

        element = (ElementDeFlux) bundle.get("element");

        genererPage();
    }

    private void genererPage(){
        TextView texteTitre = findViewById(R.id.texteTitre);
        TextView texteDescription = findViewById(R.id.texteDescription);
        texteTitre.setText(element.titre);
        texteDescription.setText(element.description);
    }
}