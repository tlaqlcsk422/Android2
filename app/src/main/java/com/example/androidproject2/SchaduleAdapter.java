package com.example.androidproject2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SchaduleAdapter extends BaseAdapter {

    private static final String TAG="list Adapter View";

    private final Context mContext;
    private LayoutInflater inflater;
    private ArrayList < String > title;
    private View view;

    public SchaduleAdapter(Context context, ArrayList < String > title) {
        this.mContext = context;
        this.title = title;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount () {
        return title.size();
    }

    @Override
    public Object getItem ( int position){
        return title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.text_item, parent, false);
            this.view = view;
        }

        TextView scheduleTv = view.findViewById(R.id.text);
        scheduleTv.setText(title.get(position));
        scheduleTv.setBackgroundColor(Color.CYAN);

        return view;
    }
}
