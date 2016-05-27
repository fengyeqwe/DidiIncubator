package com.didiincubator.Presenter;


import com.didiincubator.Model.DetailModel;
import com.didiincubator.Model.IdetailModel;
import com.didiincubator.View.IdetailVIew;

/**
 * Created by 枫叶1 on 2016/5/24.
 */
public class DetailPresenter {
    private IdetailVIew mIdetailVIew;
    private IdetailModel mIdetailModel;

    public DetailPresenter(IdetailVIew view) {

        mIdetailVIew=view;
        mIdetailModel = new DetailModel() {
        };
        mIdetailModel.load(1);
    }
    public void loadUI(){
        mIdetailVIew.setName(mIdetailModel.getLoad().getName());
    }

public void phone(){
   // mIdetailVIew.phone(mIdetailModel.getLoad().getphone());
    mIdetailVIew.phone(125865);

}
}