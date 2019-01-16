package com.yang.yunwang.base.util.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextLengthFilter implements InputFilter {

    /**
     * unicode编码，判断是否为汉字（中文汉字区间）
     */

    String regEx = "[\\u4e00-\\u9fa5]";
    private int maxLength;// 最大英文/数字长度 一个汉字算两个字母
    private Context mContext;
    private String mToast;

//    private TextView tvShowNum;
    /**
     * 标示是单数还是双数
     */

    private boolean isMupli = false;

    /**
     * 设置文本框最大输入长度
     *
     * @param context 上下文
     * @param length  最大长度(备注：字符长度为应为英文长度)
     * @param toast   提示语
     */


    public EditTextLengthFilter(Context context, int length, String toast) {
        init(context, length, toast);
    }

    /**
     * 初始化EditText必要参数
     *
     * @param context 上下文
     * @param length  最大长度(备注：字符长度为应为英文长度，单数不处理，双数 /2 )
     * @param toast   提示语
     * @param
     */
    private void init(Context context, int length, String toast) {
        mContext = context;
        isMupli = length % 2 == 0;
        maxLength = isMupli ? length / 2 : length;
        mToast = toast != null ? toast : "输入文字超过最大长度";
//        tvShowNum = showNum;
    }


    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dStart 原内容起始坐标，一般为0
     * @param dEnd   原内容终点坐标，一般为dest长度-1
     * @return 返回输入的字符
     */

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        // 计算原始长度
        int destCount = getLength(dest.toString());
        // 计算原始长度是否会改变
        boolean changeLength = dEnd - dStart != 0 && end - start != 0;
        int deleteLength = 0;
        if (changeLength) {
            // 说明文字会被替换，需要计算替换长度
            String sub = dest.subSequence(dStart, dEnd).toString();
            deleteLength = getLength(sub);
        }
        // 计算输入长度
        int sourceCount = getLength(source.toString());
        //   判断添加文字之后长度 initLength 为 不做处理的长度
        int initLength = destCount + sourceCount - deleteLength;
        int length = isMupli ? (initLength + 1) / 2 : initLength;
        if (length > maxLength) {


            // 多余部分显示可以显示数据
            int overSize = isMupli ? 2 * maxLength - (destCount - deleteLength) : maxLength -
                    (destCount - deleteLength);
            String overContent = "";
            if (sourceCount != 0) {
                int subSize = getLength(source.toString(), overSize);
                if (subSize > 0) {
                    overContent = source.subSequence(0, subSize).toString();
                }
            }
            if (!TextUtils.isEmpty(mToast)) {
//                       ToastUtil.showTextViewPrompt(AppContextUtils.getAppContext(), mToast);
            }
            operator(maxLength);
            return overContent;
        } else {
            int d = dEnd - dStart;
            // 说明是在删除文字
            if (d > 0 && length > 0 && start == 0 && end == 0) {
                // 说明是全部删除英文字母 &&   说明是全部删除中文字母
                if (d == destCount || d == dest.length()) {
                    length = 0;
                } else {
                    // sourceCount 字符长度如果为0 则说明在删除文字
                    if (sourceCount == 0) {
                        int sub = getLength(dest.subSequence(dStart, dEnd).toString());
                        if (sub > 1) {
                            length = length - (sub + 1) / 2;
                        } else if (sub == 1) {
                            if (destCount % 2 != 0) {
                                length = length - 1;
                            }
                        }
                    }
                }
            }
            operator(length);
            return source;
        }
    }


    /**
     * 这里显示我们平时看到的12/40的需求，
     */


    private void operator(int length) {
//        if (tvShowNum != null) {
//            String num = length + "/" + maxLength;
//            tvShowNum.setText(num);
//        }

    }


    /**
     * 计算字符长度 中文算两个
     *
     * @param str 原始字符长度
     * @return 返回总长度
     */


    private int getLength(String str) {
        int count = str.length();
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
//                count = count + 1;
            }
        }
        return count;
    }

    /**
     * 获取可以显示的长度
     *
     * @param str       待截取文本
     * @param maxLength 可以显示的大小
     * @return 返回截取文本的长度
     */


    private int getLength(String str, int maxLength) {
        // 对于用户显示的长度
        int count = 0;
        // 实际字节的长度
        int cutIndex = 0;
        if (maxLength > 0) {
            char[] currentCh = str.toCharArray();
            for (char c : currentCh) {
                if (c >= 0x4e00 && c <= 0x9fbb) {
                    // 中文加2个
                    count = count + 2;
                } else {
                    count++;
                }
                if (count > maxLength) {
                    return cutIndex;
                }
                cutIndex++;
            }
        }
        return cutIndex;
    }

}

