package com.example.francisco.appubicuas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.francisco.appubicuas.databinding.TemplateListTagsBinding;
import com.example.francisco.appubicuas.models.TagSearch;

import java.util.List;


public class TagsAdapter extends BaseAdapter{
    LayoutInflater inflater;
    List<TagSearch> data;


    public TagsAdapter(LayoutInflater inflater, List<TagSearch> data) {
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
        TemplateListTagsBinding binding = TemplateListTagsBinding.inflate(inflater);
        binding.setTagSearch(data.get(i));

        return binding.getRoot();
    }
}
