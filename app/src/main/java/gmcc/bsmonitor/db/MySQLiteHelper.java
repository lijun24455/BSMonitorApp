package gmcc.bsmonitor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import gmcc.bsmonitor.model.BaseStationInfo;

/**
 * Created by Administrator on 2015-07-04.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase db;
    private String DBname;
    private String tableName = "table_temp1";

    public MySQLiteHelper(Context context, String dbname, CursorFactory factory, int version) {
        super(context, dbname, factory, version);
        DBname = dbname;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName + "(btsId varchar(20),btsName varchar(40),longitude double," +
                "latitude double,deviceType varchar(20),factoryId varchar(20),factoryName varchar(20),city varchar(20),warningDeviceType varchar(20)" +
                ",warningFactoryLevel varchar(20),warningTitle varchar(20),warningNetAdminLevel varchar(20),warningHappenTime varchar(20)," +
                "warningClearTime varchar(20),warningFactoryId varchar(20),warningNetAdminId varchar(20))");
        //"btsId""btsName""longitude""latitude""deviceType""factoryId""factoryName""city""warningDeviceType""warningFactoryLevel""warningTitle"
        // "warningNetAdminLevel""warningHappenTime""warningClearTime""warningFactoryId""warningNetAdminId"
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 将基站数据写入数据库中
     * 成功返回true，否则返回false
     * *
     */
    public boolean WriteBStoDB(BaseStationInfo bsInfoClass) {
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query(tableName, new String[]{"btsId", "btsName", "longitude", "latitude", "deviceType", "factoryId", "factoryName", "city",
                    "warningDeviceType", "warningFactoryLevel", "warningTitle", "warningNetAdminLevel", "warningHappenTime", "warningClearTime",
                    "warningFactoryId", "warningNetAdminId"}, null, null, null, null, null);
            db.delete(tableName, "btsId=?", new String[]{bsInfoClass.getBtsId()});
            ContentValues cv = new ContentValues();
            cv.put("btsId", bsInfoClass.getBtsId());
            cv.put("btsName", bsInfoClass.getBtsName());
            cv.put("longitude", bsInfoClass.getLongitude());
            cv.put("latitude", bsInfoClass.getLatitude());
            cv.put("deviceType", bsInfoClass.getDeviceType());
            cv.put("factoryId", bsInfoClass.getFactoryId());
            cv.put("factoryName", bsInfoClass.getFactoryName());
            cv.put("city", bsInfoClass.getCity());
            cv.put("warningDeviceType", bsInfoClass.getWarningDeviceType());
            cv.put("warningFactoryLevel", bsInfoClass.getWarningFactoryLevel());
            cv.put("warningTitle", bsInfoClass.getWarningTitle());
            cv.put("warningNetAdminLevel", bsInfoClass.getWarningNetAdminLevel());
            cv.put("warningHappenTime", bsInfoClass.getWarningHappenTime());
            cv.put("warningClearTime", bsInfoClass.getWarningClearTime());
            cv.put("warningFactoryId", bsInfoClass.getWarningFactoryId());
            cv.put("warningNetAdminId", bsInfoClass.getWarningNetAdminId());
            db.insert(tableName, null, cv);
            db.close();
        } catch (Exception e) {
            Log.e("error", "error");
            return false;
        }
        return true;
    }

    /**
     * 更新数据库中单个BS的数据(待根据需求，完善)
     */
    public boolean UpdateBStoDB() {
        try {
            db = this.getReadableDatabase();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 从数据库中读取基站的数据
     * 成功则返回BsInfoClass对象，否则返回null
     */
    public BaseStationInfo ReadBSofDB(String SearchId) {
        db = this.getReadableDatabase();
        BaseStationInfo bsInfoClass = new BaseStationInfo();
        try {
            Cursor mcursor = db.query(tableName, new String[]{"btsId", "btsName", "longitude", "latitude", "deviceType", "factoryId", "factoryName", "city",
                    "warningDeviceType", "warningFactoryLevel", "warningTitle", "warningNetAdminLevel", "warningHappenTime", "warningClearTime",
                    "warningFactoryId", "warningNetAdminId"}, "btsId=?", new String[]{SearchId}, null, null, null);
            if (mcursor != null && mcursor.moveToFirst()) {
                int index = mcursor.getColumnIndex("btsId");
                String btsId = mcursor.getString(index);
                index = mcursor.getColumnIndex("btsName");
                String btsName = mcursor.getString(index);
                index = mcursor.getColumnIndex("longitude");
                String longitude = mcursor.getString(index);
                index = mcursor.getColumnIndex("latitude");
                String latitude = mcursor.getString(index);
                index = mcursor.getColumnIndex("deviceType");
                String deviceType = mcursor.getString(index);
                index = mcursor.getColumnIndex("factoryId");
                String factoryId = mcursor.getString(index);
                index = mcursor.getColumnIndex("factoryName");
                String factoryName = mcursor.getString(index);
                index = mcursor.getColumnIndex("city");
                String city = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningDeviceType");
                String warningDeviceType = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningFactoryLevel");
                String warningFactoryLevel = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningTitle");
                String warningTitle = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningNetAdminLevel");
                String warningNetAdminLevel = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningHappenTime");
                String warningHappenTime = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningClearTime");
                String warningClearTime = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningFactoryId");
                String warningFactoryId = mcursor.getString(index);
                index = mcursor.getColumnIndex("warningNetAdminId");
                String warningNetAdminId = mcursor.getString(index);
                bsInfoClass.setParam(btsId, btsName, longitude, latitude, deviceType, factoryId, factoryName, city, warningDeviceType, warningFactoryLevel,
                        warningTitle, warningNetAdminLevel, warningHappenTime, warningClearTime, warningFactoryId, warningNetAdminId);
                db.close();
                return bsInfoClass;
            } else return null;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     *
     */
    public ArrayList<BaseStationInfo> ReadAllBs(){
        ArrayList<BaseStationInfo> bsInfoClassList=new ArrayList<>();
        BaseStationInfo bsInfoClass;
        db = this.getReadableDatabase();
        Cursor mcursor = db.query(tableName, new String[]{"btsId", "btsName", "longitude", "latitude", "deviceType", "factoryId", "factoryName", "city",
                "warningDeviceType", "warningFactoryLevel", "warningTitle", "warningNetAdminLevel", "warningHappenTime", "warningClearTime",
                "warningFactoryId", "warningNetAdminId"},null,null, null, null, null);
        while (mcursor!=null&&mcursor.moveToNext()){
            int index = mcursor.getColumnIndex("btsId");
            bsInfoClass = new BaseStationInfo();
            String btsId = mcursor.getString(index);
            index = mcursor.getColumnIndex("btsName");
            String btsName = mcursor.getString(index);
            index = mcursor.getColumnIndex("longitude");
            String longitude = mcursor.getString(index);
            index = mcursor.getColumnIndex("latitude");
            String latitude = mcursor.getString(index);
            index = mcursor.getColumnIndex("deviceType");
            String deviceType = mcursor.getString(index);
            index = mcursor.getColumnIndex("factoryId");
            String factoryId = mcursor.getString(index);
            index = mcursor.getColumnIndex("factoryName");
            String factoryName = mcursor.getString(index);
            index = mcursor.getColumnIndex("city");
            String city = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningDeviceType");
            String warningDeviceType = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningFactoryLevel");
            String warningFactoryLevel = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningTitle");
            String warningTitle = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningNetAdminLevel");
            String warningNetAdminLevel = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningHappenTime");
            String warningHappenTime = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningClearTime");
            String warningClearTime = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningFactoryId");
            String warningFactoryId = mcursor.getString(index);
            index = mcursor.getColumnIndex("warningNetAdminId");
            String warningNetAdminId = mcursor.getString(index);
            bsInfoClass.setParam(btsId, btsName, longitude, latitude, deviceType, factoryId, factoryName, city, warningDeviceType, warningFactoryLevel,
                    warningTitle, warningNetAdminLevel, warningHappenTime, warningClearTime, warningFactoryId, warningNetAdminId);
            bsInfoClassList.add(bsInfoClass);
        }
        db.close();
        for(int i = 0; i<bsInfoClassList.size(); i++){
            Log.i("DB_GETALL",bsInfoClassList.get(i).toString());
        }
        return bsInfoClassList;
    }

    /**
     * 删除数据库中的表
     */
    public boolean DeleteTableofDB(String table) {
        db = this.getReadableDatabase();
        db.delete(table, null, null);
        db.close();
        return true;
    }


    /**
     * 根据BsID删除数据库中的基站数据
     */
    public boolean DeleteBSofDB(String BsID) {
        db = this.getReadableDatabase();
        db.delete(tableName, "btsId=?", new String[]{BsID});
        db.close();
        return true;
    }

    /**
     * 获取数据库表名
     *
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设定数据库表名，不设定则默认
     */
    private boolean setTableName(String tablename) {
        tableName = tablename;
        return true;
    }
}
