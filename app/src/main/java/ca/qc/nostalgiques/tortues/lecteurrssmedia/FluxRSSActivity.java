package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FluxRSSActivity extends AppCompatActivity {
    FluxRSS flux;
    ArrayAdapter aa;
    ListView listeElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxrss);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras().getBundle("extra");

        flux = (FluxRSS) bundle.get("flux");

        listeElements = findViewById(R.id.listeElements);

        afficherDonnees();
    }

    private void afficherDonnees(){
        aa = new ElementDeFluxAdapter(this, 0, flux.elements);
        listeElements.setAdapter(aa);
    }
}
