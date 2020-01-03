package com.vivek.listviewwithrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater inflater=null;
    ArrayList<Data> dataArrayList;

    public Adapter(Context mContext, ArrayList<Data> dataArrayList)
    {
        this.mContext = mContext;
        this.dataArrayList = dataArrayList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder
    {
        TextView textView_TitleData,textView_DescriptionData;
        ImageView imageView_ImageData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView==null)
        {
            holder=new Holder();
            convertView=inflater.inflate(R.layout.layout_listview_data,null);

            holder.textView_TitleData=convertView.findViewById(R.id.textView_TitleData);
            holder.textView_DescriptionData=convertView.findViewById(R.id.textView_DescriptionData);
            holder.imageView_ImageData=convertView.findViewById(R.id.imageView_ImageData);

            convertView.setTag(holder);

        }
        else
        {
            holder=(Holder)convertView.getTag();
        }
//
        holder.textView_TitleData.setText(dataArrayList.get(position).getTitle());
        holder.textView_DescriptionData.setText(dataArrayList.get(position).getDescription());

        if (dataArrayList.get(position).getImage().matches("null"))
        {
            System.out.println("Images Null In Adapter");
        }
        else
        {
            String aUrl = dataArrayList.get(position).getImage().replace("http", "https");

            Picasso.get()
                    .load(aUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView_ImageData);

//            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg").into(holder.imageView_ImageData);
        }

        return convertView;

    }
}
