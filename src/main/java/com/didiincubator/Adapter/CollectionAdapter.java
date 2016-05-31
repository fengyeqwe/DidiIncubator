package com.didiincubator.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Presenter.Imageparser;
import com.didiincubator.R;
import com.didiincubator.listener.HttpListener;
import com.yolanda.nohttp.Response;

import java.util.List;

/**
 * Created by 何晓 on 2016/5/30.
 */
public class CollectionAdapter extends BaseAdapter{
    List<DidiBean> list;
    Context context;
    LayoutInflater inflater;
    Handler handler=new Handler();
    public CollectionAdapter(List<DidiBean> list,Context context){
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.collection_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        DidiBean incubator=list.get(position);
        viewHolder.name.setText("名称："+incubator.getName());
        new Imageparser(viewHolder.collectionImage,"http://o7f489fjp.bkt.clouddn.com/a1.PNG",handler).start();
        return convertView;
    }
    public class ViewHolder{
        ImageView collectionImage;
        TextView name,price,local;
        public ViewHolder(View view){
            collectionImage= (ImageView) view.findViewById(R.id.collection_picture);
            name= (TextView) view.findViewById(R.id.collection_name);
            price= (TextView) view.findViewById(R.id.collection_price);
            local= (TextView) view.findViewById(R.id.colletion_location);
        }
    }

}
