package com.chung.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class HelpCommentListView extends ListView {

	public HelpCommentListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HelpCommentListView(Context context, AttributeSet as) {
		super(context, as);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
