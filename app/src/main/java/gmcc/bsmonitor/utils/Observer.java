package gmcc.bsmonitor.utils;

import java.util.ArrayList;

import gmcc.bsmonitor.model.BaseStation;
import gmcc.bsmonitor.model.BaseStationInfo;

/**
 * Created by lijun on 15/7/29.
 */
public interface Observer {
    public void update(ArrayList<BaseStationInfo> mBaseStationList);
    public void updateMarkers(ArrayList<BaseStationInfo> mBaseStationList);
}
