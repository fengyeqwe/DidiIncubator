package com.didiincubator.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.didiincubator.Beans.DidiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2016/5/30.
 * 历史记录列表的adapter
 */
public class HistoryAdapter implements ExpandableListAdapter {

    private final Context mContext;
    private List<String> time;
    private List<ArrayList<DidiBean>> didis;
    private TextView mTextView;

    /**
     *
     * @param context
     * @param time 日期list
     * @param didis 每天浏览的孵化器的list
     */
    public  HistoryAdapter(Context context, List<String> time, List<ArrayList<DidiBean>> didis){
      mContext=context;
        this.time=time;
        this.didis=didis;


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
        ll.addView(textView);
        return ll;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = getTextView();
        String text=didis.get(groupPosition).get(childPosition).getId()+"孵化器";//测试用语句
        textView.setText(text);

        return textView;
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
