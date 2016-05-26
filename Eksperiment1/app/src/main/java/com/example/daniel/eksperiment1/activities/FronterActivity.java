package com.example.daniel.eksperiment1.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Fronter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class FronterActivity extends AppCompatActivity {
    private String mandag, tirsdag, onsdag, torsdag, fredag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new FronterAsync().execute();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Mandag"));
        tabLayout.addTab(tabLayout.newTab().setText("Tirsdag"));
        tabLayout.addTab(tabLayout.newTab().setText("Onsdag"));
        tabLayout.addTab(tabLayout.newTab().setText("Torsdag"));
        tabLayout.addTab(tabLayout.newTab().setText("Fredag"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new com.example.daniel.eksperiment1.adapters.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private class FronterAsync extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private String navn, uge, brugernavn, password;
        private ArrayList<Fronter> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            brugernavn = getIntent().getStringExtra("brugernavn");
            password = getIntent().getStringExtra("password");

            progressDialog = new ProgressDialog(FronterActivity.this);
            progressDialog.setTitle("Fronter");
            progressDialog.setMessage("Indlæser Fronter...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document login = Jsoup.connect("https://login.emu.dk/")
                        .data("one", "sso.emu.dk")
                        .data("two", "IGNORER")
                        .data("creds_from_greq", "1")
                        .data("three", "1")
                        .data("four", "a5a")
                        .data("five", "GET")
                        .data("six", "sso.emu.dk")
                        .data("seven", "L3VuaWxvZ2luL2xvZ2luLmNnaQ==")
                        .data("relay_url", "https://sso.emu.dk/PubCookie.reply")
                        .data("eight", "aWQ9ZnJvbnRlcmVhc2o=")
                        .data("fr", "NFR")
                        .data("hostname", "sso.emu.dk")
                        .data("nine", "1")
                        .data("file", "")
                        .data("flag", "0")
                        .data("referer", "https://fronter.com/easj/")
                        .data("sess_re", "0")
                        .data("pinit", "0")
                        .data("reply", "1")
                        .data("user", brugernavn)
                        .data("pass", password)
                        .post();

                Connection.Response cookie = Jsoup.connect("https://sso.emu.dk/PubCookie.reply")
                        .method(Connection.Method.POST)
                        .data("get_args", "id=frontereasj")
                        .data("redirect_url", "https://sso.emu.dk/L3VuaWxvZ2luL2xvZ2luLmNnaQ==")
                        .data("pubcookie_g", login.select("input[type='hidden']:nth-child(4)").val())
                        .execute();

                Document dokument = Jsoup.connect("https://www.easytools.dk/easj")
                        .cookies(cookie.cookies())
                        .get();

                navn = dokument.select("#ctl00_ContentPlaceHolder1_lblName").text();
                uge = dokument.select("#ctl00_ContentPlaceHolder1_ddlWeek option[selected]").text();

                mandag = dokument.select(".headercell:nth-child(2)").text();
                tirsdag = dokument.select(".headercell:nth-child(3)").text();
                onsdag = dokument.select(".headercell:nth-child(4)").text();
                torsdag = dokument.select(".headercell:nth-child(5)").text();
                fredag = dokument.select(".headercell:nth-child(6)").text();

            } catch (IOException e) {
                Log.e(MainActivity.ERROR, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.getTabAt(0).setText(mandag);
            tabLayout.getTabAt(1).setText(tirsdag);
            tabLayout.getTabAt(2).setText(onsdag);
            tabLayout.getTabAt(3).setText(torsdag);
            tabLayout.getTabAt(4).setText(fredag);

            progressDialog.dismiss();

            Toast.makeText(getApplicationContext(), navn, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Uge: " + uge, Toast.LENGTH_LONG).show();
        }
    }
}
