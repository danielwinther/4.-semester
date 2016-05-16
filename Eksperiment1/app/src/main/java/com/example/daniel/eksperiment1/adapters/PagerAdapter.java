package com.example.daniel.eksperiment1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.daniel.eksperiment1.fragments.FredagFragment;
import com.example.daniel.eksperiment1.fragments.MandagFragment;
import com.example.daniel.eksperiment1.fragments.OnsdagFragment;
import com.example.daniel.eksperiment1.fragments.TirsdagFragment;
import com.example.daniel.eksperiment1.fragments.TorsdagFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MandagFragment mandag = new MandagFragment();
                return mandag;
            case 1:
                TirsdagFragment tirsdag = new TirsdagFragment();
                return tirsdag;
            case 2:
                OnsdagFragment onsdag = new OnsdagFragment();
                return onsdag;
            case 3:
                TorsdagFragment torsdag = new TorsdagFragment();
                return torsdag;
            case 4:
                FredagFragment fredag = new FredagFragment();
                return fredag;
            default:
                MandagFragment defaultDag = new MandagFragment();
                return defaultDag;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
