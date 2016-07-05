package com.chung.sosandcommunicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.chung.fragment.LeftMenuFragment;
import com.chung.sosandcommunicate.R;
import com.chung.server.PhotoUploadOrDownload;
import com.chung.tools.HeadPicToCircle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class AccountActivity extends Activity {

	private File headPIC;
	
	private SharedPreferences sharedPreferences;
	private Dialog dialog;
	private RelativeLayout rl_head_icon;
	private ImageView iv_head_icon;
	private RelativeLayout rl_phone;
	private TextView tv_head;
	
	//private ImageView iv_head_icon;
	private TextView tv_name;
	private TextView tv_sex;
	private TextView tv_phone;
	private TextView tv_university;
	private TextView tv_college;
	private TextView tv_major;
	private TextView tv_ID;

	/*请求码*/
	public static final int CHANGE_REQUEST_CODE = 1;
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private int changePhoneRequest = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);

		headPIC = new File(AccountActivity.this.getFilesDir(),sharedPreferences.getString("studentID", null) + ".png");
		iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
		if(headPIC.canRead()){
			iv_head_icon.setImageBitmap(HeadPicToCircle.toRoundBitmap(BitmapFactory.decodeFile(headPIC.getPath())));
		}else {
			iv_head_icon.setImageResource(R.drawable.user);
		}
		
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("账户详情");

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(sharedPreferences.getString("name", null));
		
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_sex.setText(sharedPreferences.getString("sex", null));
		
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_phone.setText(sharedPreferences.getString("phone", null));
		
		tv_university = (TextView) findViewById(R.id.tv_university);
		tv_university.setText(sharedPreferences.getString("university", null));
		
		tv_college = (TextView) findViewById(R.id.tv_college);
		tv_college.setText(sharedPreferences.getString("college", null));
		
		tv_major = (TextView) findViewById(R.id.tv_major);
		tv_major.setText(sharedPreferences.getString("major", null));
		
		tv_ID = (TextView) findViewById(R.id.tv_student_id);
		tv_ID.setText(sharedPreferences.getString("studentID", null));
		
		rl_head_icon = (RelativeLayout) findViewById(R.id.rl_head_icon);
		rl_head_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
		
		rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
		rl_phone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changePhoneRequest = 1;
				Intent intent = new Intent();
				intent.setClass(AccountActivity.this,ChangeAccountActivity.class);
				intent.putExtra("TVName", "修改手机");
				startActivityForResult(intent,CHANGE_REQUEST_CODE);//表示可以返回结果
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void showDialog() {
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes(); //  获取对话框当前的参数
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下两句保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	public void tackphoto(View v) {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 判断存储卡是否可以用，可用进行存储
		if (hasSDCard()) {//若有sd卡，用学号命名文件
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), sharedPreferences.getString("studentID", null)+".png")));
		}
		startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
		dialog.dismiss();
	}

	public void choosephoto(View v) {
		Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*"); // 设置文件类型
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
		dialog.dismiss();
	}

	public void canclechangehead(View v) {
		dialog.dismiss();
	}

	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //Intent返回值的回调函数

		// 结果码不等于取消时
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:	 
				startPhotoZoom(data.getData());
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					setImageToView(data);
				}
				break;
			case 1:
				if(changePhoneRequest == 0){
					if (hasSDCard()) {
						File tempFile = new File(Environment.getExternalStorageDirectory(),sharedPreferences.getString("studentID", null)+".png");
						startPhotoZoom(Uri.fromFile(tempFile));
					} else {
						Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
					}
				}else{
			        //设置修改后的phone
			        tv_phone.setText(data.getExtras().getString("changedPhone"));
			        changePhoneRequest = 0;
			        Log.i("changePhoneTo", data.getExtras().getString("changedPhone"));
				}
				break;
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 判断存储卡是否可用
	 * @param picdata
	 */
	public static boolean hasSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	
	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	private void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			
			try {
				   FileOutputStream out = new FileOutputStream(headPIC);
				   photo.compress(Bitmap.CompressFormat.PNG, 100, out);
				   out.flush();
				   out.close();
				   Log.i("headPIC", "SAVED");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				   Log.i("headPIC", "UNSAVED1");
				   e.printStackTrace();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				   Log.i("headPIC", "UNSAVED2");
				   e.printStackTrace();
			}
			
			PhotoUploadOrDownload.uploadFile(headPIC.getPath());//上传方形头像至服务器
			Log.i("headPIC--path", headPIC.getPath());
			
			iv_head_icon.setImageBitmap(HeadPicToCircle.toRoundBitmap(photo));//设置圆形头像
			
			LeftMenuFragment.left_menu_headPic.setImageBitmap(HeadPicToCircle.toRoundBitmap(photo));
			
			LoginActivity.iv_login_head.setImageBitmap(HeadPicToCircle.toRoundBitmap(photo));

		}
	}
	
	/**文件重命名 
	*//* 
	public void renameFile(String file, String toFile) {
		 
        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
        	Log.i("File does not exist: ", file);
            return;
        }
 
        File newFile = new File(toFile);
 
        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
        	Log.i(file, "has been renamed to " + toFile);
        } else {
        	Log.i("rename","ERROR");
        }
 
    }
 
	*//**文件拷贝
	*//* 
	 public void copyFile(String oldPath, String newPath) {
	      try {
			   int bytesum = 0;   
	           int byteread = 0;   
	           File oldfile = new File(oldPath);   
	           if (oldfile.exists()) { //文件存在时   
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	               
	               @SuppressWarnings("resource")
				   FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1024];   
//	               int length;  
	               while ( (byteread = inStream.read(buffer)) != -1) {   
	                   bytesum += byteread; //字节数 文件大小   
	                   Log.i("bytesum: ", String.valueOf(bytesum));
	                   fs.write(buffer, 0, byteread);   
	               }   
	               inStream.close();   
	           }   
	       }   
	       catch (Exception e) {
	    	   Log.i("copyFile", "ERROR" + e); 
	           e.printStackTrace();
	       }   
	  
	  }*/

}
