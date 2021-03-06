package com.example.daniel.eksperiment1.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.activities.MainActivity;
import com.example.daniel.eksperiment1.adapters.FronterAdapter;
import com.example.daniel.eksperiment1.models.Fronter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FredagFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new FronterAsync().execute();

        return inflater.inflate(R.layout.fragment_fredag, container, false);
    }

    private class FronterAsync extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;
        private String brugernavn, password;
        private ArrayList<Fronter> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            brugernavn = getActivity().getIntent().getStringExtra("brugernavn");
            password = getActivity().getIntent().getStringExtra("password");

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Fronter");
            progressDialog.setMessage("Indlæser Fronter...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
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

                Document skema1 = Jsoup.connect("https://www.easytools.dk/easj")
                        .cookies(cookie.cookies())
                        .get();

                Document skema = Jsoup.connect(skema1.getElementById("aspnetForm").attr("abs:action"))
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT", "")
                        .data("__LASTFOCUS", "")
                        .data("__VIEWSTATE", skema1.getElementById("__VIEWSTATE").val())
                        .data("__VIEWSTATEGENERATOR", skema1.getElementById("__VIEWSTATEGENERATOR").val())
                        .data("ctl00$ddlLanguageselector", "da-DK")
                        .data("ctl00$ContentPlaceHolder1$ddlLandekoder", "DK")
                        .data("ctl00$ContentPlaceHolder1$txtPhoneNumber", "")
                        .data("ctl00$ContentPlaceHolder1$MobilePhoneWatermark_ClientState", "")
                        .data("ctl00$ContentPlaceHolder1$Button2.x", "1")
                        .data("ctl00$ContentPlaceHolder1$Button2.y", "1")
                        .data("ctl00$ContentPlaceHolder1$ddlWeek", "18-2016")
                        .data("ctl00$ContentPlaceHolder1$ddl_NoOWeeks", "1")
                        .data("ctl00$ContentPlaceHolder1$HidStartDate", "")
                        .post();

                Elements scripts = skema.select(".emptySkemacell:nth-child(6) script");
                for (Element script : scripts) {
                    Document html = Jsoup.parse(script.html().substring(script.html().indexOf("content: '  ")).replace("content: '  ", "").split("'")[0].toString());
                    arrayList.add(new Fronter(html.select(".popheader").text(), html.select(".tooltip tr").first().text(), html.select(".tooltip tr").first().nextElementSibling().text(), html.select(".tooltip tr").first().nextElementSibling().nextElementSibling().text()));
                }

            } catch (IOException e) {
                Log.e(MainActivity.ERROR, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListAdapter listAdapter = new FronterAdapter(getActivity(), arrayList);
            ListView listView = (ListView) getView().findViewById(R.id.listViewFronterFredag);
            listView.setAdapter(listAdapter);
            progressDialog.dismiss();
        }
    }
}
