package com.shaobao.ts.view;

import android.R.integer;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.entity.UserEntity;
import com.umeng.analytics.MobclickAgent;

public class SetFlag extends Fragment implements OnClickListener {
	
	private static final String TAG = "SetFlag";
	private Button btExit= null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment4, container, false);
		Log.v(TAG, "onCreateView");
		initView(view);
		return view;
	}
	private void initView(View view )
	{
		btExit = (Button)view.findViewById(R.id.bt_exit);
		btExit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.bt_exit:
			exitDialog();
			
			break;

		default:
			break;
		}
		
	}
	private void exitDialog()
	{
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage(getString(R.string.ts_confirm_exit));
		builder.setTitle(R.string.hint);
		builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (getActivity() != null)
				{
					release();
					
					
				}
				dialog.dismiss();
				
			}

		
		});
		builder.setNegativeButton(R.string.ts_cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				
			}

		
		});
			
		builder.create().show();
	}

	private void release()
	
	{
		getActivity().stopService(new Intent(getActivity().getApplicationContext(), OrderService.class));
		if (OrderService.orderEntitys != null) {
			OrderService.orderEntitys.clear();
//			OrderService.orderEntitys = null;
			
		}
		if (OrderService.userEntity != null)
		{
			OrderService.userEntity = new UserEntity();
		}
		OrderService.release = true;
		getActivity().finish();
		
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); 
	}
}
