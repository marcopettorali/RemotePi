package com.marcopettorali.remotepi.main.ui.main_menu

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.marcopettorali.remotepi.R

@BindingAdapter("buttonImage")
fun ImageView.setButtonImage(item: DataItem.ButtonItem?) {
    item?.let {
        setImageResource(when(item.name){
            "YouTube" -> R.drawable.youtube_icon
            "Browser" -> R.drawable.browser_icon
            "Quit" -> R.drawable.quit_icon
            else -> R.drawable.unknown_icon
        })
    }
}

@BindingAdapter("buttonName")
fun TextView.setButtonName(item: DataItem.ButtonItem?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("buttonBackground")
fun ConstraintLayout.setBackgroundColor(item : DataItem.ButtonItem?){
    item?.let{
        setBackgroundResource(item.backgroundColor)
    }

}