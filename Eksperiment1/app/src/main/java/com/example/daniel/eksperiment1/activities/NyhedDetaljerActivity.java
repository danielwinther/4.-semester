package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NyhedDetaljerActivity extends Activity {
    private String link;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyhed_detaljer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        link = getIntent().getStringExtra("nyhed");
        new NyhedDetaljer().execute();
    }

    private class NyhedDetaljer extends AsyncTask<Void, Void, Void> {
        private String titel, udgivelsesdato, broedtekst, forfatter, billede, indhold;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NyhedDetaljerActivity.this);
            progressDialog.setTitle("Nyhed");
            progressDialog.setMessage("Indlæser nyhed...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(link).get();
                titel = document.getElementsByTag("h1").first().text();
                udgivelsesdato = document.getElementsByClass("blog-dateline").first().text();
                broedtekst = document.getElementsByClass("subheader").first().text();
                billede = document.select(".pane-content img").first().attr("abs:src");
                forfatter = document.getElementsByClass("byline-author").first().text();
                indhold = document.select(".panel-pane .node").first().text();
            } catch (IOException e) {
                Log.e("error", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView titelTextView = (TextView) findViewById(R.id.titelDetaljer);
            TextView udgivelsesdatoTextView = (TextView) findViewById(R.id.udgivelsesdatoDetaljer);
            TextView broedtekstTextView = (TextView) findViewById(R.id.broedtekstDetaljer);
            ImageView billedeTextView = (ImageView) findViewById(R.id.billedeDetaljer);
            TextView forfatterTextView = (TextView) findViewById(R.id.forfatterDetaljer);
            TextView indholdTextView = (TextView) findViewById(R.id.indholdDetaljer);

            titelTextView.setText(titel);
            udgivelsesdatoTextView.setText(udgivelsesdato);
            broedtekstTextView.setText(broedtekst);
            if (billede != null) {
                Picasso.with(getApplicationContext()).load(billede).into(billedeTextView);
            }
            forfatterTextView.setText(forfatter);
            indholdTextView.setText(indhold);

            progressDialog.dismiss();
        }
    }
}
