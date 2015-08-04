package gmcc.bsmonitor.model;

/**
 * 基站信息实体类
 * Created by lijun on 15/7/29.
 */
public class BaseStation {

    private String mIntId;
    private double mLongitude;
    private double mLatitude;
    private int mState;



    public BaseStation() {
    }

    public BaseStation(String mIntId, double mLongitude, double mLatitude, int mState) {
        this.mIntId = mIntId;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
        this.mState = mState;
    }

    public String getmIntId() {
        return mIntId;
    }

    public void setmIntId(String mIntId) {
        this.mIntId = mIntId;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public int getmState() {
        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    @Override
    public String toString() {
        return "BaseStation{" +
                "mIntId='" + mIntId + '\'' +
                ", mLongitude=" + mLongitude +
                ", mLatitude=" + mLatitude +
                ", mState=" + mState +
                '}';
    }
}
