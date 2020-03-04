package com.fhanjacson.amca.wheels.base

import android.content.Context
import android.widget.ScrollView
import com.google.android.gms.maps.SupportMapFragment
import android.view.MotionEvent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.AttributeSet


class MapScrollview(mContext: Context, attrs: AttributeSet): ScrollView(mContext, attrs) {


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN ->
                //Log.i("CustomScrollView", "onInterceptTouchEvent: DOWN super false" );
                super.onTouchEvent(ev)

            MotionEvent.ACTION_MOVE -> return false // redirect MotionEvents to ourself

            MotionEvent.ACTION_CANCEL ->
                // Log.i("CustomScrollView", "onInterceptTouchEvent: CANCEL super false" );
                super.onTouchEvent(ev)

            MotionEvent.ACTION_UP ->
                //Log.i("CustomScrollView", "onInterceptTouchEvent: UP super false" );
                return false

            else -> {
            }
        }//Log.i("CustomScrollView", "onInterceptTouchEvent: " + action );

        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)
        //Log.i("CustomScrollView", "onTouchEvent. action: " + ev.getAction() );
        return true
    }
}