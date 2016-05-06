package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Kategori;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class KategoriActivity extends Activity {

    public static final String URL = "http://videnskab.dk";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new KategoriAsync().execute();
    }

    private class KategoriAsync extends AsyncTask<Void, Void, Void> {
        private ArrayList<Kategori> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KategoriActivity.this);
            progressDialog.setTitle("Kategorier");
            progressDialog.setMessage("Indlæser kategorier...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(URL).get();
                Element kat1 = document.select("li.menu-147 > a").first();
                Element kat2 = document.select("li.menu-144 > a").first();
                Element kat3 = document.select("li.menu-140 > a").first();
                Element kat4 = document.select("li.menu-145 > a").first();

                arrayList.add(new Kategori(kat1.text(), kat1.attr("abs:href")));
                arrayList.add(new Kategori(kat2.text(), kat2.attr("abs:href")));
                arrayList.add(new Kategori(kat3.text(), kat3.attr("abs:href")));
                arrayList.add(new Kategori(kat4.text(), kat4.attr("abs:href")));
            } catch (IOException e) {
                Log.e("error", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListAdapter listAdapter = new ArrayAdapter(KategoriActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ListView listView = (ListView) findViewById(R.id.kategori);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Kategori kategori = (Kategori) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getApplicationContext(), NyhedActivity.class);
                    intent.putExtra("kategori", kategori.getLink());
                    startActivity(intent);
                }
            });

            progressDialog.dismiss();
        }
    }
}
