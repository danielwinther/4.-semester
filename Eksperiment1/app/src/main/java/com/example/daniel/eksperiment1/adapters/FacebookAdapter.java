package com.example.daniel.eksperiment1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Facebook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FacebookAdapter extends ArrayAdapter<Facebook> {
    public FacebookAdapter(Context context, ArrayList<Facebook> resource) {
        super(context, R.layout.adapter_facebook, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View facebook = layoutInflater.inflate(R.layout.adapter_facebook, parent, false);
        Facebook item = getItem(position);

        ImageView billede = (ImageView) facebook.findViewById(R.id.facebookBillede);
        TextView navn = (TextView) facebook.findViewById(R.id.facebookNavn);
        TextView tekst = (TextView) facebook.findViewById(R.id.facebookTekst);

        Picasso.with(getContext()).load(item.getBillede()).into(billede);
        navn.setText(item.getNavn());
        tekst.setText(item.getTekst());

        return facebook;
    }
}
