package gmcc.bsmonitor.Application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by lijun on 15/7/28.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
