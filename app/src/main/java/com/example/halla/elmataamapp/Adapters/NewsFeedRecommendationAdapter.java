package com.example.halla.elmataamapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.halla.elmataamapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 20/06/2016.
 */
public class NewsFeedRecommendationAdapter extends BaseAdapter {

    ArrayList<String> resName, resRate, resImage, resBudget;
    Context mContext;

    public NewsFeedRecommendationAdapter(ArrayList<String> resName,ArrayList<String> resRate,ArrayList<String> resImage,
                                         ArrayList<String> resBudget, Context mContext){
        this.resName = resName;
        this.resBudget = resBudget;
        this.resImage = resImage;
        this.resRate = resRate;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return resName.size();
    }

    @Override
    public Object getItem(int position) {
        return resName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_newfeed_recommendation, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.restaurantImage = (ImageView) convertView.findViewById(R.id.iv_rating_rectangle);
            viewHolder.restaurantBudget = (TextView) convertView.findViewById(R.id.tv_rating_currency);
            viewHolder.restaurantName = (TextView) convertView.findViewById(R.id.tv_rating_resturantName);
            viewHolder.restaurantRating = (TextView) convertView.findViewById(R.id.tv_rating_starRating);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.restaurantBudget.setText(resBudget.get(position));
        viewHolder.restaurantName.setText(resName.get(position));
        viewHolder.restaurantRating.setText(resRate.get(position));

        return convertView;
    }

    public class ViewHolder{
        ImageView restaurantImage;
        TextView restaurantRating;
        TextView restaurantName;
        TextView restaurantBudget;
    }
}
