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

                Document document = Jsoup.connect(URL + "/danieldk1992/friends?startindex=0")
                        .cookies(response.cookies())
                        .get();
                title = document.title();

                while (true) {
                    try {
                        String url = document.select("#m_more_friends > a").attr("abs:href");

                        for (Element ven : document.select(".v.cb, .v.bm")) {
                            arrayList.add(new Facebook(ven.select(".v.m img").attr("abs:src"), ven.select(".cc, .bo").text(), ven.select(".cd.ce, .bp.bq").text(), ven.select(".cc, .bo").attr("abs:href")));
                        }
                        if (url != null) {
                            document = Jsoup.connect(url).cookies(response.cookies()).get();
                        } else {
                            break;
                        }
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
