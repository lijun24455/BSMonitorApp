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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import gmcc.bsmonitor.R;
import gmcc.bsmonitor.utils.SystemUtils;


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
    private View mRootView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        initAppbar();

        initView();

    }

    private void initView() {

        mRootView = findViewById(R.id.main_content);

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
