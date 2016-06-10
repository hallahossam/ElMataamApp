package com.example.halla.elmataamapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.halla.elmataamapp.R;

import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {
    //TODO hb2a ashof 7al lw l user 2am 3ml add lel interests b3den delete b3d keda register!
    //User r5m y3ny

    ArrayList<String> mInterests = new ArrayList<>();
    Context mContext;


    public ListAdapter(ArrayList<String> interests, Context context){
        mInterests = interests;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mInterests.size();
    }

    @Override
    public Object getItem(int position) {
        return mInterests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_interests,parent,false);
            viewHolder = new viewHolder();
            viewHolder.interest = (TextView) convertView.findViewById(R.id.tv_interests);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.btn_delete_interest);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (viewHolder) convertView.getTag();
        }
        viewHolder.interest.setText(mInterests.get(position));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterests.remove(position);
                notifyDataSetChanged();

            }
        });
        return convertView;
    }


    public class viewHolder{
        TextView interest;
        ImageButton delete;
    }

    public ArrayList<String> interestList(){
        return mInterests;
    }
}
