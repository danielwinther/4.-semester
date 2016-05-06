package com.example.daniel.eksperiment1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Nyhed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NyhedAdapter extends ArrayAdapter<Nyhed> {
    public NyhedAdapter(Context context, ArrayList<Nyhed> resource) {
        super(context, R.layout.adapter_nyhed, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View nyhed = layoutInflater.inflate(R.layout.adapter_nyhed, parent, false);
        Nyhed item = getItem(position);

        ImageView billede = (ImageView) nyhed.findViewById(R.id.billede);
        TextView titel = (TextView) nyhed.findViewById(R.id.titel);
        TextView udgivelsesdato = (TextView) nyhed.findViewById(R.id.udgivelsesdato);
        TextView broedtekst = (TextView) nyhed.findViewById(R.id.broedtekst);

        Picasso.with(getContext()).load(item.getBillede()).fit().into(billede);
        titel.setText(item.getTitel());
        udgivelsesdato.setText(item.getUdgivelsesdato());
        broedtekst.setText(item.getBroedtekst());
        return nyhed;
    }
}
