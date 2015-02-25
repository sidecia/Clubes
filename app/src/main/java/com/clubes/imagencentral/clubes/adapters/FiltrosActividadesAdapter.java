package com.clubes.imagencentral.clubes.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.data.DataFiltros;

import java.util.List;

/**
 * Created by Lau on 24/02/2015.
 */
public class FiltrosActividadesAdapter  extends ArrayAdapter<DataFiltros> {

    public FiltrosActividadesAdapter(Context context, List<DataFiltros> objects) {
        super(context, android.R.layout.simple_list_item_2, objects);
    }

    @Override //don't override if you don't want the default spinner to be a two line view
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {
        if(convertView == null)
            convertView = View.inflate(getContext(),
                    android.R.layout.simple_list_item_2,
                    null);
        TextView tvText1 = (TextView)convertView.findViewById(android.R.id.text1);
        TextView tvText2 = (TextView)convertView.findViewById(android.R.id.text2);
        tvText1.setText(getItem(position).getLabel());
        return convertView;
    }
}