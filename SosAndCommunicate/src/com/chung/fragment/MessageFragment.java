package com.chung.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.sosandcommunicate.ChatActivity;
import com.chung.sosandcommunicate.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message, container, false);

		// 获得Layout里面的ListView
		ListView list = (ListView) view.findViewById(R.id.message_list);
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.message_list_item,
				new String[] { "img", "username", "theme", "info" },
				new int[] { R.id.message_img, R.id.message_username, R.id.message_theme, R.id.message_info });
		// 添加并且显示
		list.setAdapter(listItemAdapter);
		// 添加单击监听
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(),ChatActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}

	private List<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.outbox);
		map.put("username", "叶良辰");
		map.put("theme", "");
		map.put("info", "您已向“叶良辰”发送帮助请求");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.inbox);
		map.put("username", "叶良辰");
		map.put("theme", "");
		map.put("info", "“叶良辰”已接受您的帮助请求");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.outbox);
		map.put("username", "奶茶妹妹");
		map.put("theme", "");
		map.put("info", "淘宝校园网破解路由器");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.inbox);
		map.put("username", "救世主");
		map.put("theme", "");
		map.put("info", "好的");
		listitem.add(map);

		return listitem;
	}

}
