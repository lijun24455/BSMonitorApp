package gmcc.bsmonitor;


import java.util.ArrayList;

import gmcc.bsmonitor.model.BaseStation;

/**
 * 测试数据
 * Created by lijun on 15/7/29.
 */
public class TestData implements Subject{

    public static final int STATION_STATE_NORMAL = 1;
    public static final int STATION_STATE_SERVICE_OUT = 2;
    public static final int STATION_STATE_POWEROFF = 3;


    public static double[][] pointLat = {
            {-863931008, 25.203121, 113.196419, 1},
            {-1911315071, 23.27916, 116.74168, 1},
            {-157504865, 23.95222092, 116.58280182, 1},
            {476442012, 24.8607878, 113.4332875, 1},
            {700858277, 22.958901, 116.05301, 1},
            {-1168781389, 23.24641, 115.43156, 1}
    };
    public static double[][] warningStatic={
            {-863931008,2},
            {-157504865,3},
            {700858277,3}
    };

    public ArrayList<BaseStation> getmBaseStationList() {
        return mBaseStationList;
    }

    public void setmBaseStationList(ArrayList<BaseStation> mBaseStationList) {
        this.mBaseStationList = mBaseStationList;
        notifyObservers();
    }

    private ArrayList<BaseStation> mBaseStationList;
    private ArrayList observers;

    public TestData(){
        observers = new ArrayList();
        mBaseStationList = new ArrayList<BaseStation>();
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

    public void setWarning(String intId, int state){

        int index = -1;
        for(int i = 0; i<mBaseStationList.size(); i++){
            if (mBaseStationList.get(i).getmIntId().equals(intId)){
                index = i;
                mBaseStationList.get(index).setmState(state);
                warningChanged();
                return;
            }
        }
    }



    synchronized private void warningChanged() {
        notifyObservers();
    }
}
