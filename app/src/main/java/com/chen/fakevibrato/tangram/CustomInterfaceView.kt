package com.chen.fakevibrato.tangram

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.alibaba.fastjson.util.TypeUtils.getClass
import com.chen.fakevibrato.R
import com.chen.fakevibrato.utils.DisplayUtils
import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle
import com.tmall.wireless.tangram.support.ExposureSupport
import io.rong.imkit.utilities.RongUtils.dip2px
import java.lang.reflect.Type
import java.util.*

/**
 * 使用接口方式的自定义View
 */
class CustomInterfaceView : LinearLayout, ITangramViewLifeCycle {

    private var mImageView: ImageView? = null
    private var mTextView: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        val padding = DisplayUtils.dp2px(context, 10f)
        setPadding(padding, padding, padding, padding)
        mImageView = ImageView(context)
        addView(mImageView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mTextView = TextView(context)
        mTextView?.setPadding(0, padding, 0, 0)
        addView(mTextView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }


    override fun postBindView(cell: BaseCell<*>?) {
        if (cell != null) {
            if (cell.pos % 2 == 0) {
                setBackgroundColor(0xffff0000.toInt());
                mImageView?.setImageResource(R.mipmap.ic_launcher);
            } else {
                setBackgroundColor(0xff00ff00.toInt());
                mImageView?.setImageResource(R.mipmap.ic_launcher_round);
            }
        }
        mTextView?.setText(String.format(Locale.CHINA, "%s%d: %s", this@CustomInterfaceView.javaClass.simpleName,
                cell?.pos, cell?.optParam("text")));
    }

    override fun postUnBindView(cell: BaseCell<*>?) {

    }

    override fun cellInited(cell: BaseCell<*>?) {
        setOnClickListener(cell)
        if (cell?.serviceManager != null) {
            val exposureSupport = cell?.serviceManager?.getService(ExposureSupport::class.java)
            if (exposureSupport != null) {
                exposureSupport!!.onTrace(this, cell, cell.type)
            }
        }
    }


}