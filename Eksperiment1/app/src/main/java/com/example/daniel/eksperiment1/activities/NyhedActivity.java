package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.adapters.NyhedAdapter;
import com.example.daniel.eksperiment1.models.Nyhed;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class NyhedActivity extends Activity {
    private String link;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyhed);
    }

    @Override
    protected void onStart() {
        super.onStart();

        link = getIntent().getStringExtra("kategori");
        new NyhedAsync().execute();
    }

    private class NyhedAsync extends AsyncTask<Void, Void, Void> {
        private String title;
        private ArrayList<Nyhed> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NyhedActivity.this);
            progressDialog.setTitle("Nyheder");
            progressDialog.setMessage("Indlæser nyheder...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(link).get();
                title = document.title();
                Elements nyheder = document.select("#main-content-image-list > div > div > div.view-content > ul > li");

                for (Element nyhed : nyheder) {
                    arrayList.add(new Nyhed(nyhed.getElementsByClass("title").text(), nyhed.getElementsByClass("publication-date").text(), nyhed.getElementsByClass("subheader").text(), nyhed.getElementsByTag("img").attr("abs:src"), nyhed.getElementsByTag("a").attr("abs:href")));
                }
            } catch (IOException e) {
                Log.e("error", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView titleTextView = (TextView) findViewById(R.id.title);
            titleTextView.setText(title);

            ListAdapter listAdapter = new NyhedAdapter(NyhedActivity.this, arrayList);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Nyhed nyhed = (Nyhed) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getApplicationContext(), NyhedDetaljerActivity.class);
                    intent.putExtra("nyhed", nyhed.getLink());
                    startActivity(intent);
                }
            });

            progressDialog.dismiss();
        }
    }
}