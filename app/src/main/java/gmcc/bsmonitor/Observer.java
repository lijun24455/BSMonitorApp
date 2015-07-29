package gmcc.bsmonitor;

import java.util.ArrayList;

import gmcc.bsmonitor.model.BaseStation;

/**
 * Created by lijun on 15/7/29.
 */
public interface Observer {
    public void update(ArrayList<BaseStation> mBaseStationList);
    public void updateMarkers(ArrayList<BaseStation> mBaseStationList);
}
