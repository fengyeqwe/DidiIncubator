package com.didiincubator.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;

import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by 何晓 on 2016/6/2.
 */
public class AllAdapter extends BaseAdapter{
    List<DidiBean> list;
    Context context;
    LayoutInflater inflater;
    public AllAdapter(List<DidiBean> list,Context context){
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
            convertView=inflater.inflate(R.layout.egg_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        DidiBean incubator=list.get(position);
        viewHolder.textView1.setText(incubator.getName());
        viewHolder.textView2.setText(incubator.getSketch());
        viewHolder.textView3.setText(incubator.getType_didi());
        return convertView;
    }
    public class ViewHolder{
        TextView textView1,textView2,textView3;
        public ViewHolder(View view){
           // textView1= (TextView) view.findViewById(R.id.tv_1);
            textView2= (TextView) view.findViewById(R.id.tv_2);
           //textView3= (TextView) view.findViewById(R.id.tv_3);

        }

    }
}
