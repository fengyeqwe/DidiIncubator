package com.didiincubator.Presenter;

import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Model.ApplyModel;
import com.didiincubator.Model.IApplyModel;
import com.didiincubator.View.IApplyView;

/**
 * Created by fengye on 2016/5/25.
 */
public class ApplyPresenter {
    IApplyView mIApplyView;
    IApplyModel mIApplyModel;

    public ApplyPresenter(IApplyView view) {
        mIApplyView = view;
        mIApplyModel = new ApplyModel();

    }

    public Boolean submit() {
        ApplyBean applyBean = new ApplyBean();
        applyBean.setUser_id(mIApplyView.getUser_id());
        applyBean.setDidi_id( mIApplyView.getDidi_id());
        applyBean.setCount(mIApplyView.getCount());
        applyBean.setTime(mIApplyView.getTime());
        applyBean.setLookhousetime(mIApplyView.getLookhousetime());
        applyBean.setExtra(mIApplyView.getExtra());
        //mIApplyView.getPhone();
        return mIApplyModel.submit(applyBean);
    }


}
