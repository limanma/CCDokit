package com.example.ccdokit

import android.app.Activity
import android.app.Application
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.ViewCompat.setTranslationX
import androidx.core.view.ViewCompat.setTranslationY
import java.lang.ref.WeakReference

/**
 * Created by Chen Wei on 2022/9/27.
 */
const val TAG = "EasyFloat"

object EasyFloat : Application.ActivityLifecycleCallbacks {
    private var lastPosition = Point(0, 0)
    private var cacheMap = mutableMapOf<Int, View>()
    private var clickAction: (() -> Unit)? = null//浮窗点击事件
    private var floatEnable = false

    /**
     * 开始悬浮
     */
    fun startFloat(activity: WeakReference<Activity>, clickAction: () -> Unit) {
        this.floatEnable = true
        this.clickAction = clickAction
        showFloatView(activity)
    }

    /**
     * 展示悬浮窗
     */
    private fun showFloatView(activity: WeakReference<Activity>) {
        if (!floatEnable) return

        activity.get()?.let {
            if (cacheMap[it.hashCode()] != null) {
                cacheMap[it.hashCode()]?.x = lastPosition.x.toFloat()
                cacheMap[it.hashCode()]?.y = lastPosition.y.toFloat()
                return
            }

            val floatView = LayoutInflater.from(it).inflate(R.layout.view_float, null).apply {
                x = lastPosition.x.toFloat()
                y = lastPosition.y.toFloat()
                isEnabled = true
                isClickable = true
                setOnTouchListener(ItemViewTouchListener(lastPosition, clickAction))
            }
            val layoutParam = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            (it.window.decorView as? FrameLayout)?.addView(floatView, layoutParam)
            cacheMap[it.hashCode()] = floatView
            it.application.registerActivityLifecycleCallbacks(this)
        }
    }

    /**
     * 隐藏悬浮窗
     */
    fun stopFloat(activity: WeakReference<Activity>) {
        activity.get()?.run {
            cacheMap[hashCode()]?.let { (this.window.decorView as? FrameLayout)?.removeView(it) }
            cacheMap.clear()
            floatEnable = false
        }
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        Log.i(TAG, "$activity onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, "$activity onActivityStarted")
        showFloatView(WeakReference(activity))
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "$activity onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG, "$activity onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG, "$activity onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        cacheMap.remove(activity.hashCode())
    }
}

class ItemViewTouchListener(val point: Point, var clickAction: (() -> Unit)?) : View.OnTouchListener {
    private var moveX = 0f
    private var moveY = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                moveX = event.x
                moveY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                view.translationX = view.x + (event.x - moveX)
                view.translationY = view.y + (event.y - moveY)
                point.x = (view.x + event.x - moveX).toInt()
                point.y = (view.y + event.y - moveY).toInt()
            }
            MotionEvent.ACTION_UP -> {
                if (event.eventTime - event.downTime < 200) {//判断为点击事件
                    clickAction?.invoke()
                }
            }
        }
        return false
    }
}
