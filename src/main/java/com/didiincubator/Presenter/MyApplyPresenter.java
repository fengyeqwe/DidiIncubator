package com.didiincubator.Presenter;

import android.os.Handler;
import android.os.Message;

import com.didiincubator.Beans.ApplyBean;
import com.didiincubator.Beans.ApplyResultBean;
import com.didiincubator.Beans.DidiBean;
import com.didiincubator.Model.IMyApplyModel;
import com.didiincubator.Model.MyApplyModel;
import com.didiincubator.View.IMyApplyView;

/**
 * Created by 枫叶1 on 2016/6/3.
 */
public class MyApplyPresenter {
    public static final int MSG_1 = 0X1;
    public static final int MSG_2 = 0X2;
    public static final int MSG_3 = 0X3;
    IMyApplyModel mMyApplyModel;
    IMyApplyView mMyApplyView;
    public MyApplyPresenter(IMyApplyView view){
        mMyApplyModel=new MyApplyModel();
        mMyApplyView=view;
    }
    public void setUI(){

        final Handler handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what== MSG_1){
                   ApplyBean applyBean = (ApplyBean) msg.getData().getSerializable("applyBean");
                    final Handler handler3=new Handler(){


                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what== MSG_2){
                                DidiBean   didi= (DidiBean) msg.getData().getSerializable("applyBean");
                                mMyApplyView.setImage(didi.getHeadPortrait());
                                mMyApplyView.setName(didi.getName());
                                // mMyApplyView.setPrice(didi.get);
                                mMyApplyView.setAdress(didi.getAddress());
                            }
                        }
                    };
                    mMyApplyModel.getDidi(applyBean.getDidi_id(),handler3);
                }
            }
        };
       mMyApplyModel.getApply(mMyApplyView.getId(),handler1);

        final Handler handler2=new Handler(){


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what== MSG_3){
                    ApplyResultBean applyResultBean = (ApplyResultBean) msg.getData().getSerializable("applyResult");
                    mMyApplyView.setState(applyResultBean.getState()+"");
                    mMyApplyView.setExtra(applyResultBean.getExtra()+"");
                }
            }
        };
        ApplyResultBean applyResultBean = mMyApplyModel.getApplyResult(mMyApplyView.getId(),handler2);

       // DidiBean didi = mMyApplyModel.getDidi(applyBean.getDidi_id());


    }

}
