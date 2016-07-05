package com.chung.tools;

import java.util.List;

import com.chung.sosandcommunicate.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 消息ListView的Adapter
 */
public class ChatMsgViewAdapter extends BaseAdapter {
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Context context;
	
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;

	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {//收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {//自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.chat_location = (ImageView) convertView.findViewById(R.id.chat_location);
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
			
		viewHolder.tvSendTime.setText(entity.getDate());
		viewHolder.tvUserName.setText(entity.getName());
		if(Patterns.WEB_URL.matcher(entity.getMessage()).matches()){
			initImageLoader(context);
			imageLoader.displayImage(entity.getMessage(),viewHolder.chat_location);	
			viewHolder.tvContent.setBackground(null);
			viewHolder.tvContent.setText("");
		}else{
			viewHolder.tvContent.setText(entity.getMessage());
			viewHolder.chat_location.setBackground(null);
		}
		return convertView;
	}

	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build(); 
		
		ImageLoader.getInstance().init(config);
	}
	
	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView chat_location;
		public boolean isComMsg = true;
	}

}
