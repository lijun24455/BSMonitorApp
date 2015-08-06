package gmcc.bsmonitor.model;

import java.io.Serializable;

/**
 * 基站信息实体类
 * Created by lijun on 15/8/2.
 */
public class BaseStationInfo implements Serializable {

//      基站信息--------------------------------------
    private String btsId;               //基站ID
    private String btsName;             //网元名称
    private String longitude;           //经度
    private String latitude;            //纬度
    private String deviceType;          //设备类型
    private String factoryId;           //厂家原始ID
    private String factoryName;         //厂家名称
    private String city;                //所在城市
//      告警信息--------------------------------------
    private String warningDeviceType;   //告警设备类型
    private String warningFactoryLevel; //告警厂家ID
    private String warningTitle;        //告警标题
    private String warningNetAdminLevel;//网管告警级别
    private String warningHappenTime;   //告警发生时间
    private String warningClearTime;    //告警清除时间
    private String warningFactoryId;    //厂家告警ID
    private String warningNetAdminId;   //网管告警ID


    public BaseStationInfo() {
    }

    public BaseStationInfo(String btsId,
                           String btsName,
                           String longitude,
                           String latitude,
                           String deviceType,
                           String factoryId,
                           String factoryName,
                           String city,
                           String warningDeviceType,
                           String warningFactoryLevel,
                           String warningTitle,
                           String warningNetAdminLevel,
                           String warningHappenTime,
                           String warningClearTime,
                           String warningFactoryId,
                           String warningNetAdminId) {
        this.btsId = btsId;
        this.btsName = btsName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.deviceType = deviceType;
        this.factoryId = factoryId;
        this.factoryName = factoryName;
        this.city = city;
        this.warningDeviceType = warningDeviceType;
        this.warningFactoryLevel = warningFactoryLevel;
        this.warningTitle = warningTitle;
        this.warningNetAdminLevel = warningNetAdminLevel;
        this.warningHappenTime = warningHappenTime;
        this.warningClearTime = warningClearTime;
        this.warningFactoryId = warningFactoryId;
        this.warningNetAdminId = warningNetAdminId;
    }

    public void setParam(String btsId,
                         String btsName,
                         String longitude,
                         String latitude,
                         String deviceType,
                         String factoryId,
                         String factoryName,
                         String city,
                         String warningDeviceType,
                         String warningFactoryLevel,
                         String warningTitle,
                         String warningNetAdminLevel,
                         String warningHappenTime,
                         String warningClearTime,
                         String warningFactoryId,
                         String warningNetAdminId){
        this.btsId = btsId;
        this.btsName = btsName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.deviceType = deviceType;
        this.factoryId = factoryId;
        this.factoryName = factoryName;
        this.city = city;
        this.warningDeviceType = warningDeviceType;
        this.warningFactoryLevel = warningFactoryLevel;
        this.warningTitle = warningTitle;
        this.warningNetAdminLevel = warningNetAdminLevel;
        this.warningHappenTime = warningHappenTime;
        this.warningClearTime = warningClearTime;
        this.warningFactoryId = warningFactoryId;
        this.warningNetAdminId = warningNetAdminId;

    }

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWarningDeviceType() {
        return warningDeviceType;
    }

    public void setWarningDeviceType(String warningDeviceType) {
        this.warningDeviceType = warningDeviceType;
    }

    public String getWarningFactoryLevel() {
        return warningFactoryLevel;
    }

    public void setWarningFactoryLevel(String warningFactoryLevel) {
        this.warningFactoryLevel = warningFactoryLevel;
    }

    public String getWarningTitle() {
        return warningTitle;
    }

    public void setWarningTitle(String warningTitle) {
        this.warningTitle = warningTitle;
    }

    public String getWarningNetAdminLevel() {
        return warningNetAdminLevel;
    }

    public void setWarningNetAdminLevel(String warningNetAdminLevel) {
        this.warningNetAdminLevel = warningNetAdminLevel;
    }

    public String getWarningHappenTime() {
        return warningHappenTime;
    }

    public void setWarningHappenTime(String warningHappenTime) {
        this.warningHappenTime = warningHappenTime;
    }

    public String getWarningClearTime() {
        return warningClearTime;
    }

    public void setWarningClearTime(String warningClearTime) {
        this.warningClearTime = warningClearTime;
    }

    public String getWarningFactoryId() {
        return warningFactoryId;
    }

    public void setWarningFactoryId(String warningFactoryId) {
        this.warningFactoryId = warningFactoryId;
    }

    public String getWarningNetAdminId() {
        return warningNetAdminId;
    }

    public void setWarningNetAdminId(String warningNetAdminId) {
        this.warningNetAdminId = warningNetAdminId;
    }

    @Override
    public String toString() {
        return "BaseStationInfo{" +
                "btsId='" + btsId + '\'' +
                ", btsName='" + btsName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", factoryId='" + factoryId + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", city='" + city + '\'' +
                ", warningDeviceType='" + warningDeviceType + '\'' +
                ", warningFactoryLevel='" + warningFactoryLevel + '\'' +
                ", warningTitle='" + warningTitle + '\'' +
                ", warningNetAdminLevel='" + warningNetAdminLevel + '\'' +
                ", warningHappenTime='" + warningHappenTime + '\'' +
                ", warningClearTime='" + warningClearTime + '\'' +
                ", warningFactoryId='" + warningFactoryId + '\'' +
                ", warningNetAdminId='" + warningNetAdminId + '\'' +
                '}';
    }

}
