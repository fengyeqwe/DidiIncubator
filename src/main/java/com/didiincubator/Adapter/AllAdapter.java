package com.didiincubator.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.didiincubator.parser.CollectionAdd;
import com.didiincubator.parser.CollectionCancle;

import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by 何晓 on 2016/6/2.
 */
//所有孵化器的信息的适配器
public class AllAdapter extends BaseAdapter{
    final   static  boolean isPress=true;
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
        viewHolder.all_name.setText("名字："+incubator.getName());
        viewHolder.all_sketch.setText("简述："+incubator.getSketch());
        //viewHolder.textView2.setText(incubator.getSketch());
        //viewHolder.textView3.setText(incubator.getType_didi());
        Glide.with(context).load(incubator.getHeadPortrait()).centerCrop().crossFade()
                .into(viewHolder.all_picture);
        viewHolder.collectionImageListener(incubator);
        return convertView;
    }
    public class ViewHolder{
        TextView all_name,all_sketch;
        ImageView all_picture;
        CheckBox all_collection;
        public ViewHolder(View view){
            all_name= (TextView) view.findViewById(R.id.all_name);
            all_sketch= (TextView) view.findViewById(R.id.all_sketch);
            all_picture= (ImageView) view.findViewById(R.id.all_picture);
            all_collection= (CheckBox) view.findViewById(R.id.all_collection);
        }

        //监听是否选中收藏按钮
        public  void collectionImageListener(final DidiBean incubator){
            final int id=incubator.getId();
            all_collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        //说明用户选中了第position行
                        CollectionAdd collectionAdd=new CollectionAdd();
                        //Toast.makeText(context, "收藏了："+id, Toast.LENGTH_SHORT).show();
                        incubator.setChecked(true);
                        collectionAdd.addIncubator(id);
                    }else {
                        //说明用户取消了第position
                        CollectionCancle collectionCancle=new CollectionCancle();
                        //Toast.makeText(context, "取消收藏了："+id, Toast.LENGTH_SHORT).show();
                        incubator.setChecked(false);
                        collectionCancle.cancleIncubator(id);
                    }
                }
            });
            //重置每一行checkbox状态，必须放在监听事件后面
            boolean isChecked=incubator.isChecked();
            all_collection.setChecked(isChecked);
            //isPress=isChecked;
        }
    }

}
