package com.yang.yunwang.base.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yang.yunwang.base.R;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.base.util.ConstantUtils;

import java.util.Calendar;
import java.util.List;

/**
 * 公共标准上图下文样式RecycleView适配器
 */
public class CommonImageTextRecAdapter extends RecyclerView.Adapter<CommonImageTextRecAdapter.MainViewHolder> {

    private final List<String> actios;
    private final Bundle[] bundles;
    private Context context;
    private List<String> list;
    private int[] res;
    private int[] res_selected;
//    private Intent[] intents;
    private int layout;
    private long sTime;

//    public CommonImageTextRecAdapter(Context context, List<String> list, int[] res, int[] res_selected, Intent[] intents, int layout) {
//        this.context = context;
//        this.list = list;
//        this.res = res;
//        this.res_selected = res_selected;
//        if (intents != null) {
//            this.intents = intents;
//        } else {
//            this.intents = new Intent[]{};
//        }
//        this.layout = layout;
//    }

    public CommonImageTextRecAdapter(Context context, List<String> list, int[] res, int[] res_selected, List<String> actios, Bundle[] bundles, int rec_menu_item) {
        this.context = context;
        this.list = list;
        this.res = res;
        this.res_selected = res_selected;
        this.actios = actios;
        this.bundles = bundles;
        this.layout = rec_menu_item;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new MainViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int pos) {
        holder.bind(pos);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        public TextView fun_name;
        public ImageView fun_ico;
        private ViewGroup item_view;
        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime = 0;

        public MainViewHolder(ViewGroup itemView) {
            super(itemView);
            fun_name = (TextView) itemView.findViewById(R.id.recycle_txt);
            fun_ico = (ImageView) itemView.findViewById(R.id.recycle_img);
            itemView.setOnTouchListener(this);
            this.item_view = itemView;
        }

        public void bind(int pos) {
            fun_name.setText(list.get(pos));
            fun_ico.setImageDrawable(context.getResources().getDrawable(res[pos]));
            item_view.setTag(pos);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //重新设置按下时的背景图片
                fun_ico.setImageDrawable(context.getResources().getDrawable(res_selected[(int) view.getTag()]));
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                //再修改为抬起时的正常图片
                fun_ico.setImageDrawable(context.getResources().getDrawable(res[(int) view.getTag()]));
                if (bundles.length != 0) {
                    boolean f = bundles[(int) view.getTag()].getBoolean(ConstantUtils.CLICKABLE, true);
                    if (f) {
//                        context.startActivity(intents[(int) view.getTag()]);
                        long currentTime = Calendar.getInstance().getTimeInMillis();
                        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                            lastClickTime = currentTime;
                            MyBundle myBundle = new MyBundle();
                            myBundle.setBundle(bundles[(int) view.getTag()]);
                            MyRouter.newInstance(actios.get((int) view.getTag()))
                                    .withBundle(myBundle)
                                    .navigation();
                            KLog.i(currentTime - lastClickTime);
                        }else {
                            KLog.i(currentTime - lastClickTime);
                        }

                    } else {
//                        Toast.makeText(context, "暂无权限", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                fun_ico.setImageDrawable(context.getResources().getDrawable(res_selected[(int) view.getTag()]));
            } else {
                fun_ico.setImageDrawable(context.getResources().getDrawable(res[(int) view.getTag()]));
            }
            return false;
        }

    }
}
