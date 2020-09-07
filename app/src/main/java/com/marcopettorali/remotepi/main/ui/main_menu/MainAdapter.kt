package com.marcopettorali.remotepi.main.ui.main_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.ButtonHolderBinding

private val ITEM_VIEW_TYPE_BUTTON = 0
val BUTTONS_LIST = listOf(
    DataItem.ButtonItem(
        id = 0,
        name = "YouTube",
        backgroundColor = R.color.youtubeColor
    ),

    DataItem.ButtonItem(
        id = 1,
        name = "Browser",
        backgroundColor = R.color.browserColor
    ),

    DataItem.ButtonItem(
        id = 2,
        name = "Quit",
        backgroundColor = R.color.quitColor
    )
)


class MainAdapter(val clickListener: ButtonListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(ButtonDiffCallBack()) {

    init {
        submitList(BUTTONS_LIST)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ButtonHolder -> {
                val buttonItem = getItem(position) as DataItem.ButtonItem
                holder.bind(buttonItem, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_BUTTON -> ButtonHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    class ButtonHolder private constructor(val binding: ButtonHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem.ButtonItem, clickListener: ButtonListener) {
            binding.buttonItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ButtonHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ButtonHolderBinding.inflate(layoutInflater, parent, false)
                return ButtonHolder(binding)
            }
        }
    }

}

class ButtonDiffCallBack() : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(p0: DataItem, p1: DataItem): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: DataItem, p1: DataItem): Boolean {
        return p0 == p1
    }
}

class ButtonListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(button: DataItem.ButtonItem) = clickListener(button.id)
}

sealed class DataItem {
    abstract val id: Long

    data class ButtonItem(
        override val id: Long,
        val name: String,
        val backgroundColor: Int
    ) : DataItem()

}


