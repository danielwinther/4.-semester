package com.example.daniel.eksperiment1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.eksperiment1.R;
import com.example.daniel.eksperiment1.models.Fronter;

import java.util.ArrayList;

public class FronterAdapter extends ArrayAdapter<Fronter> {
    public FronterAdapter(Context context, ArrayList<Fronter> resource) {
        super(context, R.layout.adapter_fronter, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View fronter = layoutInflater.inflate(R.layout.adapter_fronter, parent, false);
        Fronter item = getItem(position);

        TextView fag = (TextView) fronter.findViewById(R.id.fagFronter);
        TextView tid = (TextView) fronter.findViewById(R.id.tidFronter);
        TextView lokale = (TextView) fronter.findViewById(R.id.lokaleFronter);
        TextView underviser = (TextView) fronter.findViewById(R.id.underviserFronter);

        fag.setText(item.getFag());
        tid.setText(item.getTid());
        lokale.setText(item.getLokale());
        underviser.setText(item.getUnderviser());

        return fronter;
    }
}
