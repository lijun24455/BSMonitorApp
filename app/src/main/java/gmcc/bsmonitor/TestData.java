package gmcc.bsmonitor;


import java.util.ArrayList;

import gmcc.bsmonitor.model.BaseStation;
import gmcc.bsmonitor.model.BaseStationInfo;
import gmcc.bsmonitor.utils.Observer;
import gmcc.bsmonitor.utils.Subject;

/**
 * 测试数据
 * Created by lijun on 15/7/29.
 */
public class TestData implements Subject {

    public static final int STATION_STATE_NORMAL = 1;
    public static final int STATION_STATE_SERVICE_OUT = 2;
    public static final int STATION_STATE_POWEROFF = 3;
    public static final int STATION_STATE_OVERALL = 4;

    public static final String STATION_STATE_NORMAL_STRING = "null";
    public static final String STATION_STATE_SERVICE_OUT_STRING = "小区退服";
    public static final String STATION_STATE_POWER_OFF_STRING = "小区断电";



    public static double[][] pointLat = {
            {-863931008, 25.203121, 113.196419, 1},
            {-1911315071, 23.27916, 116.74168, 1},
            {-157504865, 23.95222092, 116.58280182, 1},
            {476442012, 24.8607878, 113.4332875, 1},
            {700858277, 22.958901, 116.05301, 1},
            {-1168781389, 23.24641, 115.43156, 1}
    };

    public void setmBaseStationList(ArrayList<BaseStationInfo> mBaseStationList) {
        this.mBaseStationList = mBaseStationList;
        notifyObservers();
    }

    private ArrayList<BaseStationInfo> mBaseStationList;
    private ArrayList observers;

    public TestData(){
        observers = new ArrayList();
        mBaseStationList = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i > 0){
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i<observers.size(); i++){
            Observer observer = (Observer) observers.get(i);
            observer.update(mBaseStationList);
            observer.updateMarkers(mBaseStationList);
        }
    }

    public static ArrayList<BaseStationInfo> getNormalBaseStationInfos(ArrayList<BaseStationInfo> allBaseStationInfos){
        ArrayList<BaseStationInfo> result = new ArrayList<>();
        BaseStationInfo tmp;
        for(int i = 0; i < allBaseStationInfos.size(); i++){
            tmp = allBaseStationInfos.get(i);
            if (tmp.getWarningTitle().contentEquals(STATION_STATE_NORMAL_STRING)){
                result.add(tmp);
            }
        }
        return result;
    }
    public static ArrayList<BaseStationInfo> getServiceOutBaseStationInfos(ArrayList<BaseStationInfo> allBaseStationInfos){
        ArrayList<BaseStationInfo> result = new ArrayList<>();
        BaseStationInfo tmp;
        for(int i = 0; i < allBaseStationInfos.size(); i++){
            tmp = allBaseStationInfos.get(i);
            if (tmp.getWarningTitle().contentEquals(STATION_STATE_SERVICE_OUT_STRING)){
                result.add(tmp);
            }
        }
        return result;
    }
    public static ArrayList<BaseStationInfo> getPowerOffBaseStationInfos(ArrayList<BaseStationInfo> allBaseStationInfos){
        ArrayList<BaseStationInfo> result = new ArrayList<>();
        BaseStationInfo tmp;
        for(int i = 0; i < allBaseStationInfos.size(); i++){
            tmp = allBaseStationInfos.get(i);
            if (tmp.getWarningTitle().contentEquals(STATION_STATE_POWER_OFF_STRING)){
                result.add(tmp);
            }
        }
        return result;
    }


    synchronized private void warningChanged() {
        notifyObservers();
    }
}
