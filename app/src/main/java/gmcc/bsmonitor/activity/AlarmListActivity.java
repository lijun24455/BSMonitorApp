package gmcc.bsmonitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import gmcc.bsmonitor.R;


public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBtnNaviBack;
    private ImageView mBtnScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        initView();
    }

    private void initView() {
        mBtnNaviBack = (ImageView) findViewById(R.id.iv_app_bar_navi_back);
        mBtnScreen = (ImageView) findViewById(R.id.iv_app_bar_action_more);
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
