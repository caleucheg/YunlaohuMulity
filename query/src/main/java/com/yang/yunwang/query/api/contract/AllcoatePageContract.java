package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;
import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public interface AllcoatePageContract {
    interface Model {
        void initOrderListData(List<String> name_list,
                               List<String> cno_list, List<Integer> flag_list,
                               List<String> eidter_list, List<String> creater_list);

        void addData(List<String> name_list,
                     List<String> cno_list, List<Integer> flag_list, List<String> eidter_list, List<String> creater_list);

        void clear();

        AllocateBean loadInstance();
    }

    interface View extends BaseView<Presenter>{
        void setInfo(AllocateBean bean);

        void refreshComplete();

        void loadMoreComplete();

        void dataNotifyChanged();

        int getRecItmesCount();

        void setAdapter(AllocateBean bean);

        String getCno();

        void finishActivity();

        Intent loadInstance();
    }

    interface Presenter extends BasePresenter{
        void initData();

        void allocateComfirm(boolean fromHome, int leftLength, AllocateBean bean, int leftLengthF);
    }
}
