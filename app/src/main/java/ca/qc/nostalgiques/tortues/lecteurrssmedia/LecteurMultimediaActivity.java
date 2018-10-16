package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
        TextView texteUrl = findViewById(R.id.texteURL);
        TextView texteDate = findViewById(R.id.texteDate);
        VideoView vid = findViewById(R.id.videoView);
        if(element.video !=null) {
            MediaController mediaC = new MediaController(this);
            Uri u = Uri.parse(element.video);
            vid.setVideoURI(u);
            vid.setMediaController(mediaC);
            mediaC.setAnchorView(vid);
            vid.start();
        }
        else {
            vid.getLayoutParams().height = 0;
            vid.setVisibility(View.INVISIBLE);
        }

        texteTitre.setText(element.titre);
        texteUrl.setText(element.lien);
        texteDescription.setText(Html.fromHtml(element.description));
        texteDate.setText(element.date);
        texteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(element.lien)));
            }
        });
    }
}