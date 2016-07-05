package com.chung.sosandcommunicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.sosandcommunicate.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("历史记录");

		// 获得Layout里面的ListView
		ListView list = (ListView) findViewById(R.id.history_list);
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, getData(),
				R.layout.history_list_item, new String[] { "history_img",
						"history_username", "history_theme", "history_info" },
				new int[] { R.id.history_img, R.id.history_username,
						R.id.history_theme, R.id.history_info });
		// 添加并且显示
		list.setAdapter(listItemAdapter);

		// 添加单击监听
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_username = (TextView) view
						.findViewById(R.id.history_username);
				String history_username = tv_username.getText().toString();
				Log.i("HistoryActivity", history_username);
				TextView tv_theme = (TextView) view
						.findViewById(R.id.history_theme);
				String history_theme = tv_theme.getText().toString();
				Log.i("HistoryActivity", history_theme);
				Toast.makeText(HistoryActivity.this,
						history_username + "," + history_theme,
						Toast.LENGTH_SHORT).show();

			}

		});

	}

	// 生成多维动态数组，并加入数据
	private List<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("history_img", R.drawable.ylc);
		map.put("history_username", "叶良辰");
		map.put("history_theme", "求借车");
		map.put("history_info",
				"　　理工哪位大侠的小电驴能借我用一天感激不尽理工哪位大侠的小电驴能借我用一天感激不尽理工哪位大侠的小电驴能借我用一天感激不尽！");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.zrt);
		map.put("history_username", "赵日天");
		map.put("history_theme", "求借钱");
		map.put("history_info",
				"　　最近手头太紧急需借点钱可打欠条求帮忙最近手头太紧急需借点钱可打欠条求帮忙最近手头太紧急需借点钱可打欠条求帮忙！");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.ncmm);
		map.put("history_username", "奶茶妹妹");
		map.put("history_theme", "关于宿舍网");
		map.put("history_info",
				"　　宿舍里想用无线路由怎么破解啊求大神教宿舍里想用无线路由怎么破解啊求大神教宿舍里想用无线路由怎么破解啊求大神教");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.frjj);
		map.put("history_username", "芙蓉姐姐");
		map.put("history_theme", "关于G20");
		map.put("history_info",
				"　　G20是个什么鬼为啥这牛叉规矩这么多G20是个什么鬼为啥这牛叉规矩这么多G20是个什么鬼为啥这牛叉规矩这么多。");
		listitem.add(map);

		return listitem;
	}

}
