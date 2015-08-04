package gmcc.bsmonitor.date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import gmcc.bsmonitor.activity.MainActivity;
import gmcc.bsmonitor.db.MySQLiteHelper;
import gmcc.bsmonitor.model.BaseStationInfo;

/**
 * Created by lijun on 15/8/3.
 */
public class GetDateToDBAsyncTask extends AsyncTask<String, Integer, Boolean> {

    public static final int TASK_RESULT_OK = 1;
    public static final int TASK_RESULT_ERROR = 2;

    private ProgressDialog dialog = null;
    private Handler mHandler = null;
    private Context mContext;

    private MySQLiteHelper mSQLiteHelper;

    public GetDateToDBAsyncTask(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.mHandler = handler;
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setTitle("数据载入中，请稍候");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        mSQLiteHelper=new MySQLiteHelper(mContext,"dbName",null,2);
        mSQLiteHelper.DeleteTableofDB(mSQLiteHelper.getTableName());//删除数据库中的表格（有则删除）
        if (DecodeSaveBsInfo(params[0])){
        //通过http接收网络数据，成功即解析json并保存至SQLite数据库
//            bsInfoClass=mSQLiteHelper.ReadBSofDB("-1698975994");//根据btsId查找该基站，并返回基站的所有数据，即BsInfoClass对象；
            Log.e("Success", "Success");
            return true;
        } else{
            Log.e("Failure", "Failure");
            return false;
        }
    }

    private boolean DecodeSaveBsInfo(String url) {
        String jsonString=RecieveBSinfo(url);
        if (!jsonString.equals("")){
            try{
                JSONArray jsonArray=new JSONArray(jsonString);
                JSONObject jsonObject;
                BaseStationInfo bsInfoClass=new BaseStationInfo();
                for (int ii=0;ii<jsonArray.length();ii++){
                    jsonObject=jsonArray.getJSONObject(ii);
                    String btsId=jsonObject.getString("btsId");
                    String btsName=jsonObject.getString("btsName");
                    String longitude=jsonObject.getString("longitude");
                    String latitude=jsonObject.getString("latitude");
                    String deviceType=jsonObject.getString("deviceType");
                    String factoryId=jsonObject.getString("factoryId");
                    String factoryName=jsonObject.getString("factoryName");
                    String city=jsonObject.getString("city");
                    String warningDeviceType=jsonObject.getString("warningDeviceType");
                    String warningFactoryLevel=jsonObject.getString("warningFactoryLevel");
                    String warningTitle=jsonObject.getString("warningTitle");
                    String warningNetAdminLevel=jsonObject.getString("warningNetAdminLevel");
                    String warningHappenTime=jsonObject.getString("warningHappenTime");
                    String warningClearTime=jsonObject.getString("warningClearTime");
                    String warningFactoryId=jsonObject.getString("warningFactoryId");
                    String warningNetAdminId=jsonObject.getString("warningNetAdminId");
                    bsInfoClass.setParam(btsId, btsName, longitude, latitude, deviceType, factoryId, factoryName, city, warningDeviceType,
                            warningFactoryLevel, warningTitle, warningNetAdminLevel, warningHappenTime, warningClearTime, warningFactoryId,
                            warningNetAdminId);
                    mSQLiteHelper.WriteBStoDB(bsInfoClass);
                }
            }catch (JSONException e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else 
            return false;
    }

    private String RecieveBSinfo(String urlPath) {
        String urlpath = urlPath;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        URL url = null;
        try {
            url = new URL(urlpath);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(conn.getInputStream(), "utf-8");
            BufferedReader bufReader = new BufferedReader(input);
            String line;
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line);
            }
            String buf = contentBuf.toString();
            return buf;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        dialog.dismiss();
        mHandler.sendEmptyMessage(result?TASK_RESULT_OK:TASK_RESULT_ERROR);
    }

}
