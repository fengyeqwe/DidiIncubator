package com.didiincubator.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyListBean;
import com.didiincubator.Beans.ApplyResultBean;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Beans.GongWei;
import com.didiincubator.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 枫叶1 on 2016/6/6.
 * 申请列表适配器
 */
public class ApplyAdapter extends BaseAdapter {
    public static final String MYHTTP = "http://10.201.1.46:8080/Didiweb/";
    public Gson mGson;
    private LayoutInflater mInflater;
    private ArrayList<ApplyListBean> mList;
    private RequestQueue mQueue;
    public ApplyAdapter(Context context, ArrayList<ApplyListBean> list){
        mInflater=LayoutInflater.from(context);
        this.mList=list;
        mGson=new Gson();
        mQueue=NoHttp.newRequestQueue();

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        ImageView img;
        TextView price,area,local,state;
        public  ViewHolder(View view){
            img= (ImageView) view.findViewById(R.id.item_apply_image);
            price= (TextView) view.findViewById(R.id.item_apply_area);
            area= (TextView) view.findViewById(R.id.item_apply_area);
            local= (TextView) view.findViewById(R.id.item_apply_gongWei);
            state= (TextView) view .findViewById(R.id.item_apply_state);

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (viewHolder==null) {
            convertView = mInflater.inflate(R.layout.item_applylist, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ApplyListBean a=mList.get(position);
        String url="";
        Glide.with(mInflater.getContext()).load(a.getDidi().getHeadPortrait()).into(viewHolder.img);
        viewHolder.price.setText(a.getGongwei().getPrice()+"");
        viewHolder.area.setText(a.getGongwei().getAreaAvg());
        viewHolder.local.setText(a.getGongwei().getCountRemainer()+"");
        viewHolder.state.setText(a.getApplyresult().getState());
        return convertView;
    }

  /*  private void getData(ApplyBean applyBean, final ViewHolder viewHolder) {
       String url=MYHTTP+"DidiServlet";
         Request<JSONArray> request= NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",applyBean.getDidi_id());
        mQueue.add(1, request, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                Log.e("apply",result.toString());
                List<DidiBean> list = mGson.fromJson(result.toString(), new TypeToken<List<DidiBean>>() {
                }.getType());
                if (list.size()!=0) {
                    DidiBean didi = list.get(0);
                    Glide.with(mInflater.getContext()).load(didi.getHeadPortrait()).into(viewHolder.img);
                    getGongwei(didi.getStation_id());
                }


            }

            private void getGongwei(int id) {
                String url=MYHTTP+"gongweiServlet";
                Request<JSONArray> request= NoHttp.createJsonArrayRequest(url);
                request.add("method","select");
                request.add("id",id);
                mQueue.add(1, request, new OnResponseListener<JSONArray>() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<JSONArray> response) {
                        JSONArray result = response.get();
                        List<GongWei> list = mGson.fromJson(result.toString(), new TypeToken<List<GongWei>>() {
                        }.getType());
                        GongWei gongwei=list.get(0);
                        viewHolder.price.setText(gongwei.getPrice()+"");
                        viewHolder.area.setText(gongwei.getAreaAvg());
                        viewHolder.local.setText(gongwei.getCountRemainer());
                    }

                    @Override
                    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        String stateurl=MYHTTP+"applyresultServlet";
        Request<JSONArray> stateRequest= NoHttp.createJsonArrayRequest(url);
        request.add("method","select");
        request.add("id",applyBean.getId());
        mQueue.add(1, stateRequest, new OnResponseListener<JSONArray>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                JSONArray result = response.get();
                List<ApplyResultBean> list = mGson.fromJson(result.toString(), new TypeToken<List<ApplyResultBean>>() {
                }.getType());
                ApplyResultBean applyResult=list.get(0);
                viewHolder.state.setText(applyResult.getState());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }*/
}
