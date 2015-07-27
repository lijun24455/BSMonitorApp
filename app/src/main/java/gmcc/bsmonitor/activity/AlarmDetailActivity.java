package gmcc.bsmonitor.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import gmcc.bsmonitor.R;


public class AlarmDetailActivity extends AppCompatActivity {

    private View mShadowColor;  //收缩栏上的覆盖透明色，根据告警类型设置为相应颜色

    private TextView mAlarmLocation;
    private TextView mAlarmState;
    private TextView mAlarmDate;
    private TextView mAlarmLocationObject;
    private TextView mAlarmLevel;
    private TextView mNetworkType;
    private TextView mDeviceManufacture;
    private TextView mDeviceType;
    private TextView mDeviceSerialId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        initAppbar();

        initView();

    }

    private void initView() {
        mShadowColor = findViewById(R.id.v_shadow_color);

        mAlarmLocation = (TextView) findViewById(R.id.tv_alarm_detail_location);
        mAlarmState = (TextView) findViewById(R.id.tv_alarm_detail_state);
        mAlarmDate = (TextView) findViewById(R.id.tv_alarm_detail_date);
        mAlarmLocationObject = (TextView) findViewById(R.id.tv_alarm_detail_location_object);
        mAlarmLevel = (TextView) findViewById(R.id.tv_alarm_detail_level);
        mNetworkType = (TextView) findViewById(R.id.tv_alarm_detail_network_type);
        mDeviceManufacture = (TextView) findViewById(R.id.tv_alarm_detail_device_manufacture);
        mDeviceType = (TextView) findViewById(R.id.tv_alarm_detail_device_type);
        mDeviceSerialId = (TextView) findViewById(R.id.tv_alarm_detail_device_serial_id);
    }

    /**
     * 初始化Appbar，里头设置告警的标题
     */
    private void initAppbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(toolbar);


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("告警标题");
        //此处根据告警类型来设置颜色；
// 退服       collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.BS_Alarm_OutOfService_Red));
// 断电       collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.BS_Alarm_PowerOff_Yellow));



    }

    //浮动Button点击响应事件，跳转至地图页面
    public void showMapView(View view) {
        Snackbar.make(view, "checkin success!", Snackbar.LENGTH_SHORT).show();
        //此处填入跳转至mapview的代码。。。
    }


}
