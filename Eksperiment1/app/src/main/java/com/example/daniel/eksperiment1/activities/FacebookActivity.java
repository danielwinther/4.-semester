package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.adapters.FacebookAdapter;
import com.example.daniel.eksperiment1.models.Blogindlaeg;
import com.example.daniel.eksperiment1.models.Facebook;
import com.squareup.picasso.Picasso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FacebookActivity extends Activity {
    public static final String URL = "https://m.facebook.com";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
    }

    public void login(View view) {
        new FacebookVenner().execute();
    }

    private class FacebookVenner extends AsyncTask<Void, Void, Void> {
        private String title;
        private String email;
        private String password;
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
                Elements venner = document.select(".v.cb");
                for (Element ven : venner) {
                    arrayList.add(new com.example.daniel.eksperiment1.models.Facebook(ven.select(".v.m img").attr("abs:src"), ven.getElementsByClass("cc").text(), ven.select(".cd.ce").text(), ven.getElementsByClass("cc").attr("abs:href")));
                }

            } catch (IOException e) {
                Log.e("error", e.toString());
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
