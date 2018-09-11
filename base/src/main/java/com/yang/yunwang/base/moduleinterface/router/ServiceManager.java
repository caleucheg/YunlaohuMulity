package com.yang.yunwang.base.moduleinterface.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yang.yunwang.base.moduleinterface.provider.IDycLibProvider;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;

/**
 *  on 2017/4/13.
 *
 * description：注意，这不是一个完全的单例模式，不能私有化构造器以及属性
 * update by:
 * update day:
 */
public class ServiceManager {
    //服务注入看自己的具体实现
    //自动注入
    @Autowired
    IHomeProvider homeProvider;
    //可以不使用@Autowired，手动发现服务
    IOrdersProvider module1Provider;
    IPayProvider module2Provider;
    IDycLibProvider module3Provider;


    public ServiceManager() {
        ARouter.getInstance().inject(this);

    }

    private static final class ServiceManagerHolder {
        private static final ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return ServiceManagerHolder.instance;
    }

    /**
     * @return
     */
    public IHomeProvider getHomeProvider() {
        return homeProvider;
    }


    public IOrdersProvider getModule1Provider() {
        return  module1Provider != null ? module1Provider : (module1Provider = ((IOrdersProvider) MyRouter.newInstance(IOrdersProvider.ORDRES_MAIN_SERVICE).navigation()));
    }

    public IPayProvider getModule2Provider() {
        return module2Provider != null ? module2Provider : (module2Provider = ((IPayProvider) MyRouter.newInstance(IPayProvider.MODULE2_MAIN_SERVICE).navigation()));
    }

    public IDycLibProvider getModule3Provider() {
        return module3Provider != null ? module3Provider : (module3Provider = ((IDycLibProvider) MyRouter.newInstance(IDycLibProvider.MODULE3_MAIN_SERVICE).navigation()));
    }

}
