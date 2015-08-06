package gmcc.bsmonitor.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;
import java.util.HashMap;

import gmcc.bsmonitor.model.BaseStationInfo;
import gmcc.bsmonitor.utils.Observer;
import gmcc.bsmonitor.R;
import gmcc.bsmonitor.TestData;
import gmcc.bsmonitor.model.BaseStation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GISFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GISFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GISFragment extends Fragment implements View.OnClickListener, Observer{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private MapView mapView;

    private LinearLayout mBtnOverall;
    private LinearLayout mBtnNormal;
    private LinearLayout mBtnServiceOut;
    private LinearLayout mBtnPowerOff;

    private TextView mTvOverall;
    private TextView mTvNormal;
    private TextView mTvServiceOut;
    private TextView mTvPowerOff;

    private final BitmapDescriptor mMarkerNormal = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_normal);
    private final BitmapDescriptor mMarkerServiceOut = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_service_out);
    private final BitmapDescriptor mMarkerPowerOff = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_power_off);

    private BaiduMap mBaiduMap;

    private OnFragmentInteractionListener mListener;
    private OnGetGeoCoderResultListener getGeoCoderResultListener;

    private HashMap<Double,Marker> map = new HashMap<Double,Marker>();

//----------测试用数据
    private ArrayList<BaseStationInfo> currentStationList = null;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GISFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GISFragment newInstance(String param1, String param2) {
        GISFragment fragment = new GISFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GISFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("Fragment", "-------->onCreate()");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getGeoCoderResultListener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    return;
                }
                //获取地理编码结果
                int level = 13;
                if (result.getAddress().equals("广东省")){
                    level = 8;
                }
                if (result.getAddress().equals("其他")){
                    level = 11;
                }
                Log.e("AREA","result.address is ###########>>>>>"+result.getAddress());
                LatLng newLat = result.getLocation();
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(newLat, level);
                mBaiduMap.animateMapStatus(u);

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                }
                //获取反向地理编码结果
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Fragment", "-------->onCreateView()");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gis, container,false);

        mapView = (MapView) rootView.findViewById(R.id.mv_baidu_mapview);

        mBtnOverall = (LinearLayout) rootView.findViewById(R.id.ll_gis_overall);
        mBtnNormal = (LinearLayout) rootView.findViewById(R.id.ll_gis_normal);
        mBtnServiceOut = (LinearLayout) rootView.findViewById(R.id.ll_gis_service_out);
        mBtnPowerOff = (LinearLayout) rootView.findViewById(R.id.ll_gis_power_off);
        mBtnOverall.setOnClickListener(this);
        mBtnNormal.setOnClickListener(this);
        mBtnServiceOut.setOnClickListener(this);
        mBtnPowerOff.setOnClickListener(this);

        mTvOverall = (TextView) rootView.findViewById(R.id.tv_float_overall);
        mTvNormal = (TextView) rootView.findViewById(R.id.tv_float_normal);
        mTvServiceOut = (TextView) rootView.findViewById(R.id.tv_float_service_out);
        mTvPowerOff = (TextView) rootView.findViewById(R.id.tv_float_power_off);

        setUpMap();

        return rootView;
    }

    private void setUpMap() {
        Log.i("Fragment", "-------->setUpMap()");

        mBaiduMap = mapView.getMap();

        LatLng point = new LatLng(23.147267, 113.312213);//广州市
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.animateMapStatus(u);
        //设置缩放级别
        MapStatusUpdate lev = MapStatusUpdateFactory.zoomTo(7);
        mBaiduMap.animateMapStatus(lev);

        setUpMapListener(mBaiduMap);

    }

    private void setUpMapListener(BaiduMap mBaiduMap) {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            public void onMapClick(LatLng point) {
                Log.i("TEST1", "button2");
            }

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                return false;
            }

        });


        //地图 Marker 覆盖物点击事件监听接口
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO Auto-generated method stub
                Log.i("TEST1", "button");

                return false;
            }
        });
    }


    private void drawTestDataOnMap(BaiduMap mBaiduMap) {
        int i;
        //载入marker并给HashMap赋值
        for(i =0;i<6;i++){
            LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
            OverlayOptions Latoption_1_i = new MarkerOptions()
                    .position(Latpoint_i)
                    .icon(mMarkerNormal);

            Marker mMarker_1_i = (Marker)mBaiduMap.addOverlay(Latoption_1_i);
            String ID =String.valueOf(TestData.pointLat[i][0]);
            //        String marker =String.valueOf(mMarker_1_i);
            mMarker_1_i.setTitle(ID);
            map.put(TestData.pointLat[i][0],mMarker_1_i);
        }

        for(i=0;i<6;i++){

            if(TestData.pointLat[i][3]==2){
                Marker marker=map.get(TestData.pointLat[i][0]);
                LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
                marker.remove();
                OverlayOptions Latoption_2_i = new MarkerOptions()
                        .position(Latpoint_i)
                        .icon(mMarkerServiceOut);
                Marker mMarker_2_i = (Marker)mBaiduMap.addOverlay(Latoption_2_i);
                map.put(TestData.pointLat[i][0],mMarker_2_i);

            }
            else if(TestData.pointLat[i][3]==3){
                Marker marker=map.get(TestData.pointLat[i][0]);
                LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
                marker.remove();
                OverlayOptions Latoption_3_i = new MarkerOptions()
                        .position(Latpoint_i)
                        .icon(mMarkerPowerOff);
                Marker mMarker_3_i = (Marker)mBaiduMap.addOverlay(Latoption_3_i);
                map.put(TestData.pointLat[i][0],mMarker_3_i);

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

//    @Override
//    public void onClick(View v) {
//        if (mBaiduMap == null){
//            return;
//        }
//        switch (v.getId()){
//            case R.id.ll_gis_overall:
//                mBaiduMap.clear();
//                for(int i =0;i<6;i++){
//                    LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                    OverlayOptions Latoption_1_i = new MarkerOptions()
//                            .position(Latpoint_i)
//                            .icon(mMarkerNormal);
//
//                    Marker mMarker_1_i = (Marker)mBaiduMap.addOverlay(Latoption_1_i);
//                    String ID =String.valueOf(TestData.pointLat[i][0]);
//                    mMarker_1_i.setTitle(ID);
//                    map.put(TestData.pointLat[i][0],mMarker_1_i);
//                }
//
//                for(int i=0;i<6;i++){
//
//                    if(TestData.pointLat[i][3]==2){
//                        Marker marker=map.get(TestData.pointLat[i][0]);
//                        LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                        marker.remove();
//                        OverlayOptions Latoption_2_i = new MarkerOptions()
//                                .position(Latpoint_i)
//                                .icon(mMarkerServiceOut);
//                        Marker mMarker_2_i = (Marker)mBaiduMap.addOverlay(Latoption_2_i);
//                        map.put(TestData.pointLat[i][0],mMarker_2_i);
//
//                    }
//                    else if(TestData.pointLat[i][3]==3){
//                        Marker marker=map.get(TestData.pointLat[i][0]);
//                        LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                        marker.remove();
//                        OverlayOptions Latoption_3_i = new MarkerOptions()
//                                .position(Latpoint_i)
//                                .icon(mMarkerPowerOff);
//                        Marker mMarker_3_i = (Marker)mBaiduMap.addOverlay(Latoption_3_i);
//                        map.put(TestData.pointLat[i][0],mMarker_3_i);
//
//                    }
//                }
//                Log.i("TEST1", "button onClick");
//
//
//                break;
//            case R.id.ll_gis_normal:
//                mBaiduMap.clear();
//                for(int i =0;i<6;i++){
//                    if(TestData.pointLat[i][3]==1){
//                        LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                        OverlayOptions Latoption_1_i = new MarkerOptions()
//                                .position(Latpoint_i)
//                                .icon(mMarkerNormal);
//                        Marker mMarker_1_i = (Marker)mBaiduMap.addOverlay(Latoption_1_i);
//                        String ID =String.valueOf(TestData.pointLat[i][0]);
//                        mMarker_1_i.setTitle(ID);
//                    }
//                }
//                break;
//            case R.id.ll_gis_service_out:
//                mBaiduMap.clear();
//                for(int i =0;i<6;i++){
//                    if(TestData.pointLat[i][3]==2){
//                        LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                        OverlayOptions Latoption_2_i = new MarkerOptions()
//                                .position(Latpoint_i)
//                                .icon(mMarkerServiceOut);
//                        Marker mMarker_2_i = (Marker)mBaiduMap.addOverlay(Latoption_2_i);
//                        String ID =String.valueOf(TestData.pointLat[i][0]);
//                        mMarker_2_i.setTitle(ID);
//                    }
//                }
//                break;
//            case R.id.ll_gis_power_off:
//                mBaiduMap.clear();
//                for(int i =0;i<6;i++){
//                    if(TestData.pointLat[i][3]==3){
//                        LatLng Latpoint_i = new LatLng(TestData.pointLat[i][1], TestData.pointLat[i][2]);
//                        OverlayOptions Latoption_3_i = new MarkerOptions()
//                                .position(Latpoint_i)
//                                .icon(mMarkerPowerOff);
//                        Marker mMarker_3_i = (Marker)mBaiduMap.addOverlay(Latoption_3_i);
//                        String ID =String.valueOf(TestData.pointLat[i][0]);
//                        mMarker_3_i.setTitle(ID);
//                    }
//                }
//                break;
//            default:
//                break;
//
//        }
//    }


    @Override
    public void onClick(View v) {
        if (mBaiduMap == null){
            return;
        }
        switch (v.getId()){
            case R.id.ll_gis_overall:
//                drawMarkers(mBaiduMap, currentStationList, TestData.STATION_STATE_OVERALL);
                updateMarkers(currentStationList);

                break;
            case R.id.ll_gis_normal:
                drawMarkers(mBaiduMap, currentStationList, TestData.STATION_STATE_NORMAL_STRING);
                break;
            case R.id.ll_gis_service_out:
                drawMarkers(mBaiduMap, currentStationList, TestData.STATION_STATE_SERVICE_OUT_STRING);
                break;
            case R.id.ll_gis_power_off:
                drawMarkers(mBaiduMap, currentStationList, TestData.STATION_STATE_POWER_OFF_STRING);
                break;
            default:
                break;
        }
    }

    private void drawMarkers(BaiduMap mBaiduMap, ArrayList<BaseStationInfo> mBaseStationList, String mType){
        switch (mType){
            case TestData.STATION_STATE_NORMAL_STRING:
                mBaiduMap.clear();
                for(int i = 0; i<mBaseStationList.size(); i++){
                    BaseStationInfo tmp = mBaseStationList.get(i);
                    if (tmp.getWarningTitle().contentEquals(TestData.STATION_STATE_NORMAL_STRING)){
                        LatLng latLng = new LatLng(Double.valueOf(tmp.getLongitude()), Double.valueOf(tmp.getLatitude()));
                        OverlayOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(mMarkerNormal);
                        Marker marker = (Marker) mBaiduMap.addOverlay(options);
                    }
                }
                break;
            case TestData.STATION_STATE_SERVICE_OUT_STRING:
                mBaiduMap.clear();
                for(int i = 0; i<mBaseStationList.size(); i++){
                    BaseStationInfo tmp = mBaseStationList.get(i);
                    if (tmp.getWarningTitle().contentEquals(TestData.STATION_STATE_SERVICE_OUT_STRING)){
                        LatLng latLng = new LatLng(Double.valueOf(tmp.getLongitude()), Double.valueOf(tmp.getLatitude()));
                        OverlayOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(mMarkerServiceOut);
                        Marker marker = (Marker) mBaiduMap.addOverlay(options);
                    }
                }
                break;
            case TestData.STATION_STATE_POWER_OFF_STRING:
                mBaiduMap.clear();
                for(int i = 0; i<mBaseStationList.size(); i++){
                    BaseStationInfo tmp = mBaseStationList.get(i);
                    if (tmp.getWarningTitle().contentEquals(TestData.STATION_STATE_POWER_OFF_STRING)){
                        LatLng latLng = new LatLng(Double.valueOf(tmp.getLongitude()), Double.valueOf(tmp.getLatitude()));
                        OverlayOptions options = new MarkerOptions()
                                .position(latLng)
                                .icon(mMarkerPowerOff);
                        Marker marker = (Marker) mBaiduMap.addOverlay(options);
                    }
                }
                break;
//            case TestData.STATION_STATE_OVERALL:

//                mBaiduMap.clear();
//                BaseStationInfo tmp = null;
//                OverlayOptions options = null;
//                BitmapDescriptor icon = null;
//                for(int i = 0; i<mBaseStationList.size(); i++){
//                    tmp = mBaseStationList.get(i);
//                    LatLng latLng = new LatLng(Double.valueOf(tmp.getLongitude()), Double.valueOf(tmp.getLatitude()));
//                    switch (tmp.getmState()){
//                        case TestData.STATION_STATE_NORMAL:
//                            icon = mMarkerNormal;
//                            break;
//                        case TestData.STATION_STATE_SERVICE_OUT:
//                            icon = mMarkerServiceOut;
//                            break;
//                        case TestData.STATION_STATE_POWEROFF:
//                            icon = mMarkerPowerOff;
//                            break;
//                        default:
//                            icon = mMarkerNormal;
//                    }
//                    options = new MarkerOptions()
//                            .position(latLng)
//                            .icon(icon);
//                    Marker marker = (Marker) mBaiduMap.addOverlay(options);
//                }
                default:
                    break;

        }
    }

    /**
     * 响应Activity里面地区选择后的重定位事件
     *
     * @param cityStr       城市
     * @param addressStr    地区
     */
    public void reFocus(String cityStr, String addressStr){

        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(getGeoCoderResultListener);
        if (!addressStr.equals("全省") && !addressStr.equals("其他")){
            mSearch.geocode(new GeoCodeOption()
                    .city(cityStr)
                    .address(addressStr));
        }else {
            Log.e("AREA","your choice is----->"+cityStr+addressStr);
            mSearch.geocode(new GeoCodeOption()
            .city(cityStr)
            .address(cityStr));
        }


    }

    /**
     * 更新告警数量的文字部分
     *
     * @param mBaseStationList
     */
    @Override
    public void update(ArrayList<BaseStationInfo> mBaseStationList) {

        currentStationList = mBaseStationList;
        BaseStationInfo tmp;
        int mOverallNum = currentStationList.size();
        int mNormalNum = 0;
        int mServiceOutNum = 0;
        int mPowerOffNum = 0;

        for(int i = 0; i<currentStationList.size(); i++){
            tmp = currentStationList.get(i);
            switch (tmp.getWarningTitle()){
                case TestData.STATION_STATE_NORMAL_STRING:
                    mNormalNum+=1;
                    break;
                case TestData.STATION_STATE_SERVICE_OUT_STRING:
                    mServiceOutNum+=1;
                    break;
                case TestData.STATION_STATE_POWER_OFF_STRING:
                    mPowerOffNum+=1;
                    break;
                default:
                    break;
            }
        }
        mTvOverall.setText(mOverallNum+"");
        mTvNormal.setText(mNormalNum+"");
        mTvServiceOut.setText(mServiceOutNum+"");
        mTvPowerOff.setText(mPowerOffNum+"");

    }


    @Override
    synchronized public void updateMarkers(ArrayList<BaseStationInfo> mBaseStationList) {

        mBaiduMap.clear();
        currentStationList = mBaseStationList;
        BaseStationInfo tmp = null;
        BitmapDescriptor icon = null;
        if (currentStationList == null){
            return;
        }
        OverlayOptions option = null;
        LatLng point = null;
        for(int i = 0; i<currentStationList.size(); i++){
            tmp = currentStationList.get(i);
            point = new LatLng(Double.valueOf(tmp.getLongitude()), Double.valueOf(tmp.getLatitude()));

//            Log.i("STATION", tmp.toString());
            switch (tmp.getWarningTitle()){
                case TestData.STATION_STATE_NORMAL_STRING:
                    icon = mMarkerNormal;
                    break;
                case TestData.STATION_STATE_SERVICE_OUT_STRING:
                    icon = mMarkerServiceOut;
                    break;
                case TestData.STATION_STATE_POWER_OFF_STRING:
                    icon = mMarkerPowerOff;
                    break;
                default:
//                    Log.i("updateMarkers","------handle as---->default::"+tmp.getWarningTitle());
                    icon = mMarkerNormal;
                    break;
            }
            option = new MarkerOptions()
                    .position(point)
                    .icon(icon);
            mBaiduMap.addOverlay(option);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
