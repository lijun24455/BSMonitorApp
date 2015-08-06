package gmcc.bsmonitor.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import gmcc.bsmonitor.R;
import gmcc.bsmonitor.TestData;
import gmcc.bsmonitor.model.BaseStationInfo;

/**
 * Created by lijun on 15/8/4.
 */
public class AlarmListAdapter extends BaseAdapter {

    private ArrayList<BaseStationInfo> data;
    private LayoutInflater mLayoutInflater;

    private final int TYPE_NORMAL = 0;
    private final int TYPE_SERVICE_OUT = 1;
    private final int TYPE_POWER_OFF = 2;

    private final int TYPE_COUNT = 3;

    public AlarmListAdapter() {
    }

    public AlarmListAdapter(Context context ,ArrayList<BaseStationInfo> data) {
        this.data = data;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data == null){
            return 0;
        }else
            return data.size();

    }

    @Override
    public Object getItem(int position) {
        if (data == null){
            return null;
        }else
            return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NormalAlarmViewHolder normalAlarmViewHolder = null;
        ServiceOutAlarmViewHolder serviceOutAlarmViewHolder = null;
        PowerOffAlarmViewHolder powerOffAlarmViewHolder = null;

        int currentType = getItemViewType(position);

        if (convertView == null){
            switch (currentType){
                case TYPE_NORMAL:
                    convertView = mLayoutInflater.inflate(R.layout.alarm_list_item_normal, parent, false);
                    normalAlarmViewHolder = new NormalAlarmViewHolder();

                    normalAlarmViewHolder.tvAlarmTitle = (TextView) convertView.findViewById(R.id.tv_label_alarm_title);
                    normalAlarmViewHolder.tvAlarmLocation = (TextView) convertView.findViewById(R.id.tv_label_alarm_location);
                    normalAlarmViewHolder.tvAlarmState = (TextView) convertView.findViewById(R.id.tv_label_alarm_state);
                    normalAlarmViewHolder.tvAlarmTime = (TextView) convertView.findViewById(R.id.label_alarm_time);

                    convertView.setTag(normalAlarmViewHolder);
                    break;
                case TYPE_SERVICE_OUT:
                    convertView = mLayoutInflater.inflate(R.layout.alarm_list_item_out_of_service, parent, false);
                    serviceOutAlarmViewHolder = new ServiceOutAlarmViewHolder();

                    serviceOutAlarmViewHolder.tvAlarmTitle = (TextView) convertView.findViewById(R.id.tv_label_alarm_title);
                    serviceOutAlarmViewHolder.tvAlarmLocation = (TextView) convertView.findViewById(R.id.tv_label_alarm_location);
                    serviceOutAlarmViewHolder.tvAlarmState = (TextView) convertView.findViewById(R.id.tv_label_alarm_state);
                    serviceOutAlarmViewHolder.tvAlarmTime = (TextView) convertView.findViewById(R.id.label_alarm_time);

                    convertView.setTag(serviceOutAlarmViewHolder);
                    break;
                case TYPE_POWER_OFF:
                    convertView = mLayoutInflater.inflate(R.layout.alarm_list_item_power_off, parent, false);
                    powerOffAlarmViewHolder = new PowerOffAlarmViewHolder();

                    powerOffAlarmViewHolder.tvAlarmTitle = (TextView) convertView.findViewById(R.id.tv_label_alarm_title);
                    powerOffAlarmViewHolder.tvAlarmLocation = (TextView) convertView.findViewById(R.id.tv_label_alarm_location);
                    powerOffAlarmViewHolder.tvAlarmState = (TextView) convertView.findViewById(R.id.tv_label_alarm_state);
                    powerOffAlarmViewHolder.tvAlarmTime = (TextView) convertView.findViewById(R.id.label_alarm_time);

                    convertView.setTag(powerOffAlarmViewHolder);
                    break;
                default:
                    break;
            }
        }else {
            switch (currentType){
                case TYPE_NORMAL:
                    normalAlarmViewHolder = (NormalAlarmViewHolder) convertView.getTag();
                    break;
                case TYPE_SERVICE_OUT:
                    serviceOutAlarmViewHolder = (ServiceOutAlarmViewHolder) convertView.getTag();
                    break;
                case TYPE_POWER_OFF:
                    powerOffAlarmViewHolder = (PowerOffAlarmViewHolder) convertView.getTag();
                    break;
                default:
                    break;
            }
        }

        switch (currentType){
            case TYPE_NORMAL:
                normalAlarmViewHolder.tvAlarmTitle.setText(data.get(position).getBtsName());
                normalAlarmViewHolder.tvAlarmTime.setText(data.get(position).getWarningHappenTime());
                normalAlarmViewHolder.tvAlarmState.setText(data.get(position).getWarningNetAdminLevel());
                normalAlarmViewHolder.tvAlarmLocation.setText(data.get(position).getCity());
                break;
            case TYPE_SERVICE_OUT:
                serviceOutAlarmViewHolder.tvAlarmTitle.setText(data.get(position).getWarningTitle());
                serviceOutAlarmViewHolder.tvAlarmTime.setText(data.get(position).getWarningHappenTime());
                serviceOutAlarmViewHolder.tvAlarmState.setText(data.get(position).getWarningNetAdminLevel());
                serviceOutAlarmViewHolder.tvAlarmLocation.setText(data.get(position).getCity());
                break;
            case TYPE_POWER_OFF:
                powerOffAlarmViewHolder.tvAlarmTitle.setText(data.get(position).getWarningTitle());
                powerOffAlarmViewHolder.tvAlarmTime.setText(data.get(position).getWarningHappenTime());
                powerOffAlarmViewHolder.tvAlarmState.setText(data.get(position).getWarningNetAdminLevel());
                powerOffAlarmViewHolder.tvAlarmLocation.setText(data.get(position).getCity());
                break;
        }
        return convertView;
    }

    private class NormalAlarmViewHolder{
        TextView tvAlarmTitle;
        TextView tvAlarmLocation;
        TextView tvAlarmState;
        TextView tvAlarmTime;

    }
    private class ServiceOutAlarmViewHolder{
        TextView tvAlarmTitle;
        TextView tvAlarmLocation;
        TextView tvAlarmState;
        TextView tvAlarmTime;

    }
    private class PowerOffAlarmViewHolder{
        TextView tvAlarmTitle;
        TextView tvAlarmLocation;
        TextView tvAlarmState;
        TextView tvAlarmTime;

    }

    @Override
    public int getViewTypeCount() {
        return this.TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        int type = 0;
        String alarmTitle = data.get(position).getWarningTitle();
        switch (alarmTitle){
            case TestData.STATION_STATE_NORMAL_STRING:
                type = TYPE_NORMAL;
                break;
            case TestData.STATION_STATE_SERVICE_OUT_STRING:
                type = TYPE_SERVICE_OUT;
                break;
            case TestData.STATION_STATE_POWER_OFF_STRING:
                type = TYPE_POWER_OFF;
                break;
            default:
                type = TYPE_NORMAL;
                break;
        }
        return type;
    }

    public void setData(ArrayList<BaseStationInfo> data) {
        this.data = data;
    }
}
