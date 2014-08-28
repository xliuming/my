package com.shaobao.ts.view;
import com.shaobao.ts.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment1 extends Fragment {
//	OnBackListener mListener;

//	public interface OnBackListener {
//		public void backEvent();
//	}
	
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnBackListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
//        }
//    }


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.fragment1, container, false);
		Button backBtn = (Button)parentView.findViewById(R.id.button1);
		Button toOtherActivityBtn = (Button)parentView.findViewById(R.id.button2);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(mListener!=null)mListener.backEvent();
			}
		});
		
		toOtherActivityBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), OtherActivity1.class);
//				startActivity(intent);
			}
		});
		
		
		return parentView;
		// return super.onCreateView(, container, savedInstanceState);
	}
}
