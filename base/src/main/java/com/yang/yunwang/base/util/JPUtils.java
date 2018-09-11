package com.yang.yunwang.base.util;

import android.content.Context;

import com.yang.yunwang.base.util.utils.jpush.TagAliasOperatorHelper;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.yang.yunwang.base.util.utils.jpush.TagAliasOperatorHelper.ACTION_CLEAN;
import static com.yang.yunwang.base.util.utils.jpush.TagAliasOperatorHelper.ACTION_SET;


/**
 * Created by Administrator on 2018/1/24.
 */

public class JPUtils {
    public static int sequence = 1;

    public static void setTags(Context context, String tagS) {
        String rID = JPushInterface.getRegistrationID(context);
        int action = -1;
        action = ACTION_SET;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        Set<String> tags;
        tags = new LinkedHashSet<String>();
        tags.add(tagS);
        tagAliasBean.tags = tags;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);

    }

    public static void cleanTags(Context context) {
        int action = -1;
        action = ACTION_CLEAN;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        tagAliasBean.tags = null;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }
}
