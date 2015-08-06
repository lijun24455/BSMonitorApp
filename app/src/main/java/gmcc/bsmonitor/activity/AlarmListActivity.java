package gmcc.bsmonitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import gmcc.bsmonitor.R;
import gmcc.bsmonitor.model.BaseStation;
import gmcc.bsmonitor.model.BaseStationInfo;


public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBtnNaviBack;
    private ImageView mBtnScreen;

    private ListView mAlarmListView;

    private ArrayList<BaseStationInfo> mStationInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        initView();
        initDate();
        initListView(mStationInfos);
    }

    private void initListView(final ArrayList<BaseStationInfo> mStationInfos) {
        if (mStationInfos == null){
            return;
        }
        AlarmListAdapter listAdapter = new AlarmListAdapter(this, mStationInfos);
        mAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(AlarmListActivity.this, mStationInfos.get(position).getBtsName(), Toast.LENGTH_SHORT).show();
                BaseStationInfo currentItem = mStationInfos.get(position);
                Intent intent = new Intent(AlarmListActivity.this, AlarmDetailActivity.class);
                intent.putExtra("ALARM", currentItem);
                startActivity(intent);

            }
        });
        mAlarmListView.setAdapter(listAdapter);
    }

    private void initDate() {
        try {
            Bundle objectBundle = getIntent().getBundleExtra("BUNDLE");
            mStationInfos = (ArrayList<BaseStationInfo>) objectBundle.getSerializable("ALARM");
            Log.i("LIST","bundle list size is --------->"+mStationInfos.size());
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }

    }

    private void initView() {
        mBtnNaviBack = (ImageView) findViewById(R.id.iv_app_bar_navi_back);
        mBtnScreen = (ImageView) findViewById(R.id.iv_app_bar_action_more);
        mAlarmListView = (ListView) findViewById(R.id.lv_alarm_list);

        mBtnNaviBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_app_bar_navi_back:
                finish();
                break;
            case R.id.iv_app_bar_action_more:
                //跳转到筛选信息页面

                break;
            default:
                break;
        }
    }
}
