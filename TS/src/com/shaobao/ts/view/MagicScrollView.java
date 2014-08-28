package com.shaobao.ts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class MagicScrollView extends ScrollView {

    private List<ScrollListener> mListeners = new ArrayList<MagicScrollView.ScrollListener>();
    private int state = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int STOP = 3;

    public interface ScrollListener {
        void onScrollChanged(int state, int t);
    }

    public MagicScrollView(Context context) {
        super(context);
    }

    public MagicScrollView(Context context, AttributeSet set) {
        super(context, set);
    }

    public MagicScrollView(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
    }

    public void AddListener(ScrollListener listener) {
        if (mListeners == null)
            mListeners = new ArrayList<MagicScrollView.ScrollListener>();

        mListeners.add(listener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > oldt) {
            state = UP;
        } else if (t < oldt) {
            state = DOWN;
        } else {
            state = STOP;
        }
        Log.v("Scroll","Scroll test:" + t);
        sendScroll(state, t);
        
    }

    public void sendScroll(int state, int scroll) {
        for (ScrollListener listener : mListeners) {
            listener.onScrollChanged(state, scroll);
        }
    }

}
