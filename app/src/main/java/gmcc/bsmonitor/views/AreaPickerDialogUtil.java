package gmcc.bsmonitor.views;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import gmcc.bsmonitor.R;
import gmcc.bsmonitor.activity.MainActivity;
import gmcc.bsmonitor.views.wheel.OnWheelChangedListener;
import gmcc.bsmonitor.views.wheel.WheelView;
import gmcc.bsmonitor.views.wheel.adapter.ArrayWheelAdapter;


/**
 * Created by lijun on 15/7/27.
 */
public class AreaPickerDialogUtil extends BaseAreaPicker implements View.OnClickListener, OnWheelChangedListener {

    private WheelView mWheelCity;
    private WheelView mWheelDistrict;

    private Button mBtnConfirm;

    private AlertDialog dialog;

    private String initDistrict;

    private LinearLayout rootView;
    private MainActivity mActivity;

    public AreaPickerDialogUtil(MainActivity mActivity, String initDistrict) {
        this.mActivity = mActivity;
        this.initDistrict = initDistrict;
        rootView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.aero_choose_wheel_layout, null);

        setUpViews(rootView);

        setUpListener();
    }

    public AlertDialog aeroPickerDialog(String mCurrentDistrict){

//        initAreaPicker(mCurrentDistrict);

        setUpData();
        dialog = new AlertDialog.Builder(mActivity)
                .setView(rootView)
                .show();

        return dialog;
    }

//    private void initAreaPicker(String mCurrentDistrict) {
//        mWheelCity.setCurrentItem();
//        mWheelDistrict.setCurrentItem();
//    }

    private void setUpListener() {

        Log.e("WHEEL", "City:"+(mWheelCity == null)+"District:"+(mWheelDistrict == null));
        mWheelCity.addChangingListener(this);

        mWheelDistrict.addChangingListener(this);

        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpViews(View rootView) {

        mWheelCity = (WheelView) rootView.findViewById(R.id.wv_city);
        mWheelDistrict = (WheelView) rootView.findViewById(R.id.wv_district);
        mBtnConfirm = (Button) rootView.findViewById(R.id.btn_aero_choose_confirm);

        Log.e("WHEEL in setupViews", "City:"+(mWheelCity == null)+"District:"+(mWheelDistrict == null));

    }

    private void setUpData(){
        initProvinceDatas(mActivity);

        mWheelCity.setViewAdapter(new ArrayWheelAdapter<String>(mActivity.getApplicationContext(), mCityNames));
        mWheelCity.setVisibleItems(7);
        mWheelDistrict.setVisibleItems(7);
        updataAreas();

    }

    private void updataAreas() {
        int pCurrent = mWheelCity.getCurrentItem();
        mCurrentCityName = mCityNames[pCurrent];

        String[] districts = mDistrictDatasMap.get(mCurrentCityName);
        if (districts == null){
            districts = new String[]{ "" };
        }
        mWheelDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mActivity.getApplicationContext(), districts));
        mWheelDistrict.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(mActivity.getApplicationContext(),mCurrentCityName + mCurrentDistrictName, Toast.LENGTH_SHORT).show();
        mActivity.updateAreaTitle(mCurrentCityName, mCurrentDistrictName);
        dialog.dismiss();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == mWheelCity){
            updataAreas();
        }else if (wheel == mWheelDistrict){
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }
}
