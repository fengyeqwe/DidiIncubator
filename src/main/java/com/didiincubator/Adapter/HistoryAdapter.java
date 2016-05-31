package com.didiincubator.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.didiincubator.Beans.DidiBean;
import com.didiincubator.R;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2016/5/30.
 * 历史记录列表的adapter
 */
public class HistoryAdapter implements ExpandableListAdapter {

    private final Context mContext;
    private final LayoutInflater inflater;
    private List<String> time;
    private List<ArrayList<DidiBean>> didis;
    private TextView mTextView;
    private RequestQueue mQueue;

    /**
     *
     * @param context
     * @param time 日期list
     * @param didis 每天浏览的孵化器的list
     */
    public  HistoryAdapter(Context context, List<String> time, List<ArrayList<DidiBean>> didis){
      mContext=context;
        inflater= LayoutInflater.from(context);
        this.time=time;
        this.didis=didis;
        mQueue=NoHttp.newRequestQueue();


  }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return time.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return didis.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return time.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return didis.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LinearLayout ll=new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView textView=new TextView(mContext);
        textView.setText(time.get(groupPosition));
        textView.setPadding(46, 0, 0, 0);
        textView.setTextSize(20);
        ll.addView(textView);
        return ll;
    }
    //child布局复用
   public class ViewHolder{
        ImageView history_item_image;
        TextView history_item_name,history_item_price,history_item_adress;
        public ViewHolder(View view){
            history_item_image= (ImageView) view.findViewById(R.id.history_item_image);
            history_item_name= (TextView) view.findViewById(R.id.history_item_name);
            history_item_price= (TextView) view.findViewById(R.id.history_item_price);
            history_item_adress= (TextView) view.findViewById(R.id.history_item_adress);

        }

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_history,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        DidiBean didi=didis.get(groupPosition).get(childPosition);
        setImage(viewHolder,didi);
        viewHolder.history_item_name.setText(didi.getName());
        viewHolder.history_item_price.setText(didi.getStation_id()+"价格表");
        viewHolder.history_item_adress.setText("地址，数据库未添加");

        return convertView;
    }

    private void setImage(final ViewHolder viewHolder, DidiBean didi) {
        String url="http://o7f489fjp.bkt.clouddn.com/a1.PNG";
        Request<Bitmap> request= NoHttp.createImageRequest(url);
        mQueue.add(1, request, new OnResponseListener<Bitmap>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<Bitmap> response) {

                viewHolder.history_item_image.setImageBitmap(response.get());
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
//创建textView
    public TextView getTextView() {
        AbsListView.LayoutParams lp=new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,64);
        TextView textView=new TextView(mContext);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);//设置内容居中左对齐
        textView.setPadding(46, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }
}
