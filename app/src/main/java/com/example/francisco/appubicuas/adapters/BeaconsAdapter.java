package com.example.francisco.appubicuas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.francisco.appubicuas.databinding.TemplateListPromotionsBinding;
import com.example.francisco.appubicuas.models.BeaconSearch;

import java.util.List;

/**
 * Created by jhovy on 4/12/2016.
 */

public class BeaconsAdapter extends BaseAdapter {

    List<BeaconSearch> data;
    LayoutInflater inflater;

    public BeaconsAdapter(LayoutInflater inflater, List<BeaconSearch> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TemplateListPromotionsBinding binding = TemplateListPromotionsBinding.inflate(inflater);
        binding.setBeaconSearch(data.get(i));
        return binding.getRoot();
    }
}
