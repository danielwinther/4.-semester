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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Blogindlaeg;
import com.example.daniel.eksperiment1.models.Facebook;
import com.example.daniel.eksperiment1.models.Kategori;
import com.example.daniel.eksperiment1.models.Nyhed;
import com.squareup.picasso.Picasso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BlogActivity extends Activity {

    public static final String URL = "http://danielwinther.dk/projects/4semester";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Blog().execute();
    }

    private class Blog extends AsyncTask<Void, Void, Void> {
        private String title;
        private String billedeUrl;
        private ArrayList<Blogindlaeg> arrayList = new ArrayList<>();
        private ArrayList<Facebook> arrayList1 = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BlogActivity.this);
            progressDialog.setTitle("Blogindlæg");
            progressDialog.setMessage("Indlæser blogindlæg...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Connection.Response response = Jsoup.connect(URL + "/wp-login.php")
                        .method(Connection.Method.POST)
                        .data("log", "daniel")
                        .data("pwd", "admin")
                        .execute();

                Document document = Jsoup.connect(URL)
                        .cookies(response.cookies())
                        .get();

                title = document.title();
                billedeUrl = document.select("#branding > a > img").attr("abs:src");
                Elements blogindlaeg = document.select("#content article");
                for (Element indlaeg : blogindlaeg) {
                    arrayList.add(new Blogindlaeg(indlaeg.getElementsByTag("h1").text(), indlaeg.getElementsByClass("entry-date").text(), indlaeg.getElementsByClass("entry-content").text(), indlaeg.select(".cat-links > a").text(), indlaeg.select("h1 > a").attr("abs:href")));
                }

                Connection.Response response1 = Jsoup.connect("https://m.facebook.com/login.php?refsrc=https%3A%2F%2Fm.facebook.com%2F&lwv=100&refid=8")
                        .method(Connection.Method.POST)
                        .data("_fb_noscript", "true")
                        .execute();

                Document document1 = Jsoup.connect("https://m.facebook.com/danieldk1992/friends?startindex=0")
                        .cookies(response1.cookies())
                        .get();

                Elements venner = document1.select(".v.cb");
                for (Element ven : venner) {
                    arrayList1.add(new Facebook(ven.select(".v.m img").attr("abs:src"), ven.getElementsByClass("cc").text(), ven.select(".cd.ce").text(), ven.getElementsByClass("cc").attr("abs:href")));
                }

            } catch (IOException e) {
                Log.e("error", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("d", arrayList1.toString());

            TextView titleTextView = (TextView) findViewById(R.id.titleBlog);
            ImageView billede = (ImageView) findViewById(R.id.billedeBlog);

            titleTextView.setText(title);
            Picasso.with(getApplicationContext()).load(billedeUrl).into(billede);

            ListAdapter listAdapter = new ArrayAdapter(BlogActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ListView listView = (ListView) findViewById(R.id.listViewBlog);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Blogindlaeg blogindlaeg = (Blogindlaeg) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getApplicationContext(), BlogActivity.class);
                    intent.putExtra("blogindlaeg", blogindlaeg.getLink());
                    startActivity(intent);
                }
            });

            progressDialog.dismiss();
        }
    }
}
