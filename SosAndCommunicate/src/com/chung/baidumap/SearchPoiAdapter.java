package com.chung.baidumap;

import java.util.List;

import com.chung.sosandcommunicate.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ÀàËµÃ÷£º
 * 
 * @date 2014-10-30
 * @version 1.0
 */
public class SearchPoiAdapter extends BaseAdapter {
	private Context mContext;
	private List<LocationBean> cityPoiList;

	public SearchPoiAdapter(Context context, List<LocationBean> list) {
		this.mContext = context;
		this.cityPoiList = list;
	}

	@Override
	public int getCount() {
		if (cityPoiList != null) {
			return cityPoiList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (cityPoiList != null) {
			return cityPoiList.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class CityPoiHolder {
		public TextView tvMLIPoiName, tvMLIPoiAddress;
	}

	private CityPoiHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new CityPoiHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.mapview_location_poi_lv_item, null);
			holder.tvMLIPoiName = (TextView) convertView.findViewById(R.id.tvMLIPoiName);
			holder.tvMLIPoiAddress = (TextView) convertView.findViewById(R.id.tvMLIPoiAddress);
			convertView.setTag(holder);
		} else {
			holder = (CityPoiHolder) convertView.getTag();
		}
		LocationBean cityPoi = cityPoiList.get(position);
		holder.tvMLIPoiName.setText(cityPoi.getLocName());
		holder.tvMLIPoiAddress.setText(cityPoi.getAddStr());
		return convertView;
	}
}
