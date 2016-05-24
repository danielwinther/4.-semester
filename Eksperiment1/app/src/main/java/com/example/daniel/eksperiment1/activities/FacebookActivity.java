package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.adapters.FacebookAdapter;
import com.example.daniel.eksperiment1.models.Facebook;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class FacebookActivity extends Activity {
    public static final String URL = "https://m.facebook.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
    }

    public void login(View view) {
        new FacebookAsync().execute();
    }

    private class FacebookAsync extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private String title, email, password;
        private ArrayList<Facebook> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EditText emailTextView = (EditText) findViewById(R.id.email);
            EditText passwordTextView = (EditText) findViewById(R.id.password);
            email = emailTextView.getText().toString();
            password = passwordTextView.getText().toString();

            progressDialog = new ProgressDialog(FacebookActivity.this);
            progressDialog.setTitle("Facebook");
            progressDialog.setMessage("Indlæser Facebook...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Connection.Response response = Jsoup.connect(URL + "/login.php")
                        .method(Connection.Method.POST)
                        .data("email", email)
                        .data("pass", password)
                        .data("_fb_noscript", "true")
                        .execute();

                Document forside = Jsoup.connect(URL)
                        .cookies(response.cookies())
                        .get();
                Document venner = Jsoup.connect(forside.select("#header > div > a:nth-child(2)").attr("abs:href").split("\\?")[0] + "/friends")
                        .cookies(response.cookies())
                        .get();

                title = venner.title();

                while (true) {
                    try {
                        String url = venner.select("#m_more_friends > a").attr("abs:href");

                        for (Element ven : venner.select("#root .l")) {
                            arrayList.add(new Facebook(ven.select("td:nth-child(1) img").attr("abs:src"), ven.select("td:nth-child(2) a").text(), ven.select("td:nth-child(2) div:nth-child(2)").text(), ven.select("td:nth-child(1) a").attr("abs:href")));
                        }
                        venner = Jsoup.connect(url).cookies(response.cookies()).get();

                    } catch (IllegalArgumentException ex) {
                        break;
                    }
                }
            } catch (IOException e) {
                Log.e(MainActivity.ERROR, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView titleTextView = (TextView) findViewById(R.id.titleFacebook);
            titleTextView.setText(title + " (" + arrayList.size() + ")");

            ListAdapter listAdapter = new FacebookAdapter(FacebookActivity.this, arrayList);
            ListView listView = (ListView) findViewById(R.id.listViewFacebook);
            listView.setAdapter(listAdapter);

            progressDialog.dismiss();
        }
    }
}
