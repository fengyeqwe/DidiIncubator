package com.didiincubator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.didiincubator.Beans.Incubator;
import com.didiincubator.R;

import java.util.List;

/**
 * Created by 何晓 on 2016/5/30.
 */
public class CollectionAdapter extends BaseAdapter{
    List<Incubator> list;
    Context context;
    LayoutInflater inflater;
    public CollectionAdapter(List<Incubator> list,Context context){
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
    public class ViewHolder{
        TextView sketch,details,area,num,local,type,name;
        public ViewHolder(View view){
            sketch= (TextView) view.findViewById(R.id.sketch);
            details= (TextView) view.findViewById(R.id.details);
            area= (TextView) view.findViewById(R.id.area);
            num= (TextView) view.findViewById(R.id.num);
            local= (TextView) view.findViewById(R.id.local);
            type= (TextView) view.findViewById(R.id.type);
            name= (TextView) view.findViewById(R.id.name);
        }
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
        Incubator incubator=list.get(position);
        viewHolder.sketch.setText(incubator.getSketch());
        viewHolder.details.setText(incubator.getDetails());
        viewHolder.area.setText(Double.toString(incubator.getArea()));
        viewHolder.num.setText(Integer.toString(incubator.getNum()));
        viewHolder.local.setText(incubator.getLocal());
        viewHolder.type.setText(incubator.getType());
        viewHolder.name.setText(incubator.getName());
        return convertView;
    }
}
