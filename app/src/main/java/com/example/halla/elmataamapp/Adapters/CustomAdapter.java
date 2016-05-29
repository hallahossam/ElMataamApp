package com.example.halla.elmataamapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.halla.elmataamapp.R;

/**
 * Created by user on 3/20/2016.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    String []  restaurantName;
    String [] restaurantRate;
   // int [] restaurantImage;
    Context mContext;

    public CustomAdapter(String [] resName, String [] rate, Context context){
        restaurantName = resName;
        this.restaurantRate = rate;
        mContext = context;
    }

    @Override
    public int getCount() {
        return restaurantName.length;
    }

    @Override
    public Object getItem(int i) {
        return restaurantName[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_search, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.restImage = (ImageView) view.findViewById(R.id.img_search);
            viewHolder.restName = (TextView) view.findViewById(R.id.tv_resname_search);
            viewHolder.resRate = (TextView) view.findViewById(R.id.tv_resrate_search);
            view.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.restName.setText(restaurantName[i]);
        viewHolder.resRate.setText(restaurantRate[i]);
        viewHolder.restImage.setOnClickListener(this);
        //viewHolder.restImage.setImageResource(restaurantImage[i]);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.img_search:
                Dialog imageDialog = new Dialog(mContext);
                imageDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                imageDialog.setContentView(R.layout.popup_image);
                imageDialog.show();
                break;
        }
    }

    public class ViewHolder{
        ImageView restImage;
        TextView restName;
        TextView resRate;
    }
}
