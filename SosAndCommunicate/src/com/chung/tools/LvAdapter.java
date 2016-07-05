package com.chung.tools;

import java.util.List;  
import java.util.Map;

import com.chung.server.SendAndStoreData;
import com.chung.sosandcommunicate.LoginActivity;
import com.chung.sosandcommunicate.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LvAdapter extends BaseAdapter {
	private String url = LoginActivity.SAE_URL+"index.php/Likecomment/like/";// SAE URL
	private List<Map<String, Object>> data;
	private LayoutInflater mInflater = null;
	private int listviewType;
//	private Context context;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			default:
				break;
			}
		};
	};
	
    public LvAdapter(List<Map<String, Object>> list, Context context , int type) { 
    	this.mInflater = LayoutInflater.from(context);
        this.data = list;
        this.listviewType = type;
//        this.context = context;
    }  
  
    @Override  
    public int getCount() {  
        return data.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return data.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @SuppressLint("InflateParams")
	@Override  
    public View getView(int position, View convertView, ViewGroup parent) { 
    	ViewHolder holder0 = null;
    	ViewHolder holder1 = null;
    	ViewHolder holder2 = null;
    	if(convertView == null){//根据自定义的Item布局加载布局
    		
            if(listviewType == 0){//如果是帖子
            	holder0 = new ViewHolder();
	            convertView = mInflater.inflate(R.layout.main_list_item, null);
	            
	            holder0.main_img = (ImageView)convertView.findViewById(R.id.main_img);
	            holder0.main_username = (TextView)convertView.findViewById(R.id.main_username);
	            holder0.main_blogTime = (TextView)convertView.findViewById(R.id.main_blogTime);
	            holder0.main_blogType = (TextView)convertView.findViewById(R.id.main_blogType);
	            holder0.main_theme = (TextView)convertView.findViewById(R.id.main_theme);
	            holder0.main_info = (TextView)convertView.findViewById(R.id.main_info);
	            
	            convertView.setTag(holder0);//将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            }
            if(listviewType == 1) {//如果是帮助
            	holder1 = new ViewHolder();
            	convertView = mInflater.inflate(R.layout.helper_list_item, null);
	            
            	holder1.helper_img = (ImageView)convertView.findViewById(R.id.helper_img);
            	holder1.helper_username = (TextView)convertView.findViewById(R.id.helper_username);
            	holder1.helper_time = (TextView)convertView.findViewById(R.id.helper_time);
            	holder1.helper_accepted = (TextView)convertView.findViewById(R.id.helper_accepted);
				
	            convertView.setTag(holder1);//将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			}
            if(listviewType == 2) {//如果是评论
            	holder2 = new ViewHolder();
            	convertView = mInflater.inflate(R.layout.comment_list_item, null);
	            
	            holder2.comment_img = (ImageView)convertView.findViewById(R.id.comment_img);
	            holder2.comment_username = (TextView)convertView.findViewById(R.id.comment_username);
	            holder2.comment_floor = (TextView)convertView.findViewById(R.id.comment_floor);
	            holder2.comment_time = (TextView)convertView.findViewById(R.id.comment_time);
	            holder2.comment_like = (TextView)convertView.findViewById(R.id.comment_like);
	            holder2.comment_info = (TextView)convertView.findViewById(R.id.comment_info);
	            holder2.ll_comment_like = (LinearLayout)convertView.findViewById(R.id.ll_comment_like);
	            holder2.iv_comment_like = (ImageView)convertView.findViewById(R.id.iv_comment_like);
				
	            convertView.setTag(holder2);//将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			}
            
            
        }else{
        	if(listviewType == 0){
        		holder0 = (ViewHolder)convertView.getTag();
        	}
        	if(listviewType == 1){
        		holder1 = (ViewHolder)convertView.getTag();
        	}
        	if(listviewType == 2){
        		holder2 = (ViewHolder)convertView.getTag();
			}
        }
    	if(listviewType == 0){//如果是帖子
    		holder0.main_img.setImageBitmap(BitmapFactory.decodeFile((String) data.get(position).get("img")));
    		holder0.main_username.setText((String)data.get(position).get("username"));
    		holder0.main_blogTime.setText((String)data.get(position).get("blogTime"));
    		holder0.main_blogType.setText((String)data.get(position).get("blogType"));
    		holder0.main_theme.setText((String)data.get(position).get("theme"));
    		holder0.main_info.setText((String)data.get(position).get("info"));
    	}
    	if(listviewType == 1){//如果是帮助
    		holder1.helper_img.setImageBitmap(BitmapFactory.decodeFile((String) data.get(position).get("img")));
    		holder1.helper_username.setText((String)data.get(position).get("username"));
    		holder1.helper_time.setText((String)data.get(position).get("time"));
    		holder1.helper_accepted.setText((String)data.get(position).get("accepted"));
    	}
    	if(listviewType == 2){//如果是评论
    		holder2.comment_img.setImageBitmap(BitmapFactory.decodeFile((String) data.get(position).get("img")));
  	        holder2.comment_username.setText((String)data.get(position).get("username"));
  	        holder2.comment_floor.setText("");//((String)data.get(position).get("floor"));
  	        holder2.comment_time.setText((String)data.get(position).get("time"));
  	        holder2.comment_like.setText((String)data.get(position).get("like"));
  	        int count = Integer.valueOf((String) data.get(position).get("like"));
  	        if(count == 0){
  	        	 holder2.iv_comment_like.setBackgroundResource(R.drawable.like);
  	        }else{
  	        	holder2.iv_comment_like.setBackgroundResource(R.drawable.like_filled);  	        	
  	        }
  	        final String likeCount = String.valueOf((count + 1));
  	        final TextView likeText = holder2.comment_like;
  	        final ImageView likeView = holder2.iv_comment_like;
  	        final String args[] = {String.valueOf(data.get(position).get("commentID")), likeCount};
  	        holder2.ll_comment_like.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(context, "您赞了该评论！", Toast.LENGTH_SHORT).show();
					likeText.setText(likeCount);
					likeView.setImageResource(R.drawable.like_filled);
					SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url, args , 2);
					sendAndStoreData.saveDataToInternet();
				}
			});
  	        
  	        holder2.comment_info.setText((String)data.get(position).get("info"));
		}
        return convertView;
    }  
  
}