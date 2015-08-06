package gmcc.bsmonitor.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import gmcc.bsmonitor.R;
import gmcc.bsmonitor.model.BaseStationInfo;
import gmcc.bsmonitor.utils.SystemUtils;


public class AlarmDetailActivity extends AppCompatActivity {

    private View mShadowColor;  //收缩栏上的覆盖透明色，根据告警类型设置为相应颜色

    private LinearLayout mAlarmDetailBlock;

    private TextView mAlarmLocation;
    private TextView mAlarmDeviceType;
    private TextView mAlarmDate;
    private TextView mAlarmClearDate;
    private TextView mAlarmManufactureLevel;
    private TextView mAlarmNetAdminLevel;
    private TextView mAlarmManufactoryID;
    private TextView mAlarmNetAdminID;

    private TextView mNetworkName;
    private TextView mDeviceManufacture;
    private TextView mDeviceType;
    private TextView mDeviceSerialId;

    private BaseStationInfo mBaseStationInfo;

    private View mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        initData();
        initAppbar();
        initView();
    }

    private void initData() {
        try{
            Intent intent = getIntent();
            this.mBaseStationInfo = (BaseStationInfo) intent.getSerializableExtra("ALARM");
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    private void initView() {

        mRootView = findViewById(R.id.main_content);
        mShadowColor = findViewById(R.id.v_shadow_color);

        mAlarmDetailBlock = (LinearLayout) findViewById(R.id.ll_alarm_detail_block);

        mAlarmLocation = (TextView) findViewById(R.id.tv_alarm_detail_location);
        mAlarmDeviceType = (TextView) findViewById(R.id.tv_alarm_detail_device_type);
        mAlarmDate = (TextView) findViewById(R.id.tv_alarm_detail_date);
        mAlarmClearDate = (TextView) findViewById(R.id.tv_alarm_detail_date_clear);
        mAlarmManufactureLevel = (TextView) findViewById(R.id.tv_alarm_detail_manufacture_level);
        mAlarmNetAdminLevel = (TextView) findViewById(R.id.tv_alarm_detail_net_admin_level);
        mAlarmManufactoryID = (TextView) findViewById(R.id.tv_alarm_detail_alarm_id_manufacture);
        mAlarmNetAdminID = (TextView) findViewById(R.id.tv_alarm_detail_alarm_id_net_admin);

        mNetworkName = (TextView) findViewById(R.id.tv_alarm_detail_station_name);
        mDeviceManufacture = (TextView) findViewById(R.id.tv_alarm_detail_station_device_manufacture);
        mDeviceType = (TextView) findViewById(R.id.tv_alarm_detail_station_device_type);
        mDeviceSerialId = (TextView) findViewById(R.id.tv_alarm_detail_station_device_id);

        if (mBaseStationInfo.getWarningTitle().contentEquals("null")){
            mAlarmDetailBlock.setVisibility(View.GONE);
        }

        mAlarmLocation.setText(mBaseStationInfo.getCity());
        mAlarmDeviceType.setText(mBaseStationInfo.getWarningDeviceType());
        mAlarmDate.setText(mBaseStationInfo.getWarningHappenTime());
        mAlarmClearDate.setText(mBaseStationInfo.getWarningClearTime());
        mAlarmManufactureLevel.setText(mBaseStationInfo.getWarningFactoryLevel());
        mAlarmNetAdminLevel.setText(mBaseStationInfo.getWarningNetAdminLevel());
        mAlarmManufactoryID.setText(mBaseStationInfo.getFactoryId());
        mAlarmNetAdminID.setText(mBaseStationInfo.getWarningNetAdminId());

        mNetworkName.setText(mBaseStationInfo.getBtsName());
        mDeviceManufacture.setText(mBaseStationInfo.getFactoryName());
        mDeviceType.setText(mBaseStationInfo.getDeviceType());
        mDeviceSerialId.setText(mBaseStationInfo.getBtsId());

    }

    /**
     * 初始化Appbar，里头设置告警的标题
     */
    private void initAppbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id){
            case R.id.item_share:
                break;
            case R.id.item_speak:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //分享功能代码
    private void shareTo() {
        new AsyncTask<Void, Void, File>() {
            Dialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = SystemUtils.getCustomeDialog(AlarmDetailActivity.this,
                        R.style.load_dialog, R.layout.custom_progress_dialog);
                TextView titleTxtv = (TextView) dialog
                        .findViewById(R.id.dialogText);
                //	titleTxtv.setText(R.string.please_wait);
                dialog.show();
            }

            @Override
            protected File doInBackground(Void... params) {
                try {
                    new File(getFilesDir(), "share.png").deleteOnExit();
                    FileOutputStream fileOutputStream = openFileOutput(
                            "share.png", 1);
                    mRootView.setDrawingCacheEnabled(true);
                    mRootView.getDrawingCache().compress(
                            Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    return new File(getFilesDir(), "share.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(File result) {
                super.onPostExecute(result);
                dialog.cancel();
                if (result == null) {
                    Toast.makeText(AlarmDetailActivity.this, R.string.share_fail,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");

                intent.putExtra("android.intent.extra.STREAM",
                        Uri.fromFile(result));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(intent, getResources()
                        .getString(R.string.share_to)));
            }
        }.execute();
    }


}
