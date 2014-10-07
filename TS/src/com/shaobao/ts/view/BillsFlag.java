package com.shaobao.ts.view;

import java.util.ArrayList;
import java.util.List;

import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.dao.PictureDAO;
import com.shaobao.ts.entity.PictureEntity;
import com.shaobao.ts.util.DisplayUtil;
import com.umeng.analytics.MobclickAgent;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BillsFlag extends Fragment implements OnItemClickListener{

	private ListView listView = null;
	private Context context= null;
	public  ChartAdapter chartAdapter = null;
	private static final String TAG = "BillsFlag";
	public static List<PictureEntity> pictures = new ArrayList<PictureEntity>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity().getApplicationContext();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View parentView = inflater.inflate(R.layout.fragment3, container, false);
//		return super.onCreateView(inflater, container, savedInstanceState);
		Log.v(TAG, "onCreateView");
		initView(parentView);
		return parentView;
	}
	private void initView(View parentView )
	{
//		if (loginDialog == null)
//    	{
//    		  loginDialog =new ProgressDialog(getActivity(), R.style.ProgressDialog_Theme);
//		}
		initData();
		listView = (ListView)parentView.findViewById(R.id.lv);
		DisplayUtil.setListEmptyView(context, listView, context.getString(R.string.ts_no_bills));
		listView.setOnItemClickListener(this);
		
		chartAdapter = new ChartAdapter(pictures, getActivity());
		listView.setAdapter(chartAdapter);
//		ChangePWUtil.change(getActivity(), OrderService.userEntity.getToken(),  OrderService.userEntity.getName(), "10");
		
	}
	private void initData()
	{
		// TODO Auto-generated method stub
		PictureDAO pictureDAO = new PictureDAO(getActivity());
		List<PictureEntity> pictureEntities = pictureDAO.GetPicturesByUser(OrderService.userEntity.getName());
		pictures.clear();
		pictures.addAll(pictureEntities);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
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
