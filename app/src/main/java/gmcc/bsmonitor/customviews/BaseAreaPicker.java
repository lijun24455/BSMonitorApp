package gmcc.bsmonitor.customviews;

import android.app.Activity;
import android.content.res.AssetManager;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import gmcc.bsmonitor.customviews.districtpicker.model.CityModel;
import gmcc.bsmonitor.customviews.districtpicker.model.DistrictModel;
import gmcc.bsmonitor.customviews.districtpicker.service.XmlParserHandler;

/**
 * Created by lijun on 15/7/27.
 */
public class BaseAreaPicker {



    protected String[] mCityNames;


    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();


    protected String mCurrentCityName;

    protected String mCurrentDistrictName ="";

    protected String mCurrentZipCode ="";


    protected void initProvinceDatas(Activity activity)
    {
        List<CityModel> cityList = null;
        AssetManager asset = activity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            cityList = handler.getDataList();
            if (cityList!= null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                mCurrentDistrictName = districtList.get(0).getName();
                mCurrentZipCode = districtList.get(0).getZipcode();
            }

            mCityNames = new String[cityList.size()];
            for (int i = 0; i<cityList.size(); i++){
                mCityNames[i] = cityList.get(i).getName();
                List<DistrictModel> districtList = cityList.get(i).getDistrictList();
                String[] districtNameArray = new String[districtList.size()];
                DistrictModel[] districtArray = new DistrictModel[districtList.size()];
                for (int j = 0; j<districtList.size();j++){
                    DistrictModel districtModel = new DistrictModel(districtList.get(j).getName(), districtList.get(j).getZipcode());
                    mZipcodeDatasMap.put(districtList.get(j).getName(), districtList.get(j).getZipcode());
                    districtArray[j] = districtModel;
                    districtNameArray[j] = districtModel.getName();
                }
                mDistrictDatasMap.put(mCityNames[i], districtNameArray);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}
