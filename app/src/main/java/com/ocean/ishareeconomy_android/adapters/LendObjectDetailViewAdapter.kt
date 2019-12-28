package com.ocean.ishareeconomy_android.adapters

import LendObjectViewHolder
import ObjectOwnerViewHolder
import ObjectUserViewHolder
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.viewmodels.LendObjectDetailViewModel
import com.ocean.ishareeconomy_android.viewmodels.LendObjectViewModel

/**
 * Part of *adapters*.
 *
 * RecyclerView Adapter for setting up data binding on the items in the list.
 *
 * @property context: [Context]? Store parent context, needed to fetch colors
 * @property lendObject: [LendingObject] The object that our Adapter will show the details of
 */
class LendObjectDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context? = null
    var lendObject: LendingObject? = null

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to represent
     * an item.
     *
     * @return [Unit]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when(viewType) {
            1 -> {
                val withDataBinding: ListItemLendobjectBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    LendObjectViewHolder.LAYOUT,
                    parent,
                    false)
                LendObjectViewHolder(withDataBinding)
            }
            2 -> {
                val withDataBinding: ListItemLendobjectBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    LendObjectViewHolder.LAYOUT,
                    parent,
                    false)
                LendObjectViewHolder(withDataBinding)
            }
            3 -> {
                val withDataBinding: ListItemLendobjectBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    LendObjectViewHolder.LAYOUT,
                    parent,
                    false)
                LendObjectViewHolder(withDataBinding)
            }
            else -> throw IllegalArgumentException("No such viewType!")
        }

    }

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] to determine the type of item.
     *
     * @return the type [Int], based on the position of the item.
     */
    override fun getItemViewType(position: Int): Int {
        return when(position) {
            //TO DO add titles?
            0 -> 1
            1 -> 2
            else -> 3
        }
    }

    /**
     * Determines how many items need to be drawn
     *
     * @return [Int]
     */
    override fun getItemCount(): Int {
        var count = 2
        if (lendObject != null) {
            if (lendObject!!.user != null) {
                count++
            }
            if (lendObject!!.waitingList.isNotEmpty()) {
                count += lendObject!!.waitingList.size
            }
        } else {
            count = 0
        }
        return count
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            0 -> {
                (holder as LendObjectViewHolder).viewDataBinding.also {
                    val obj = lendObject!!
                    val vm = LendObjectViewModel(obj,getObjectUsageColor(obj))
                    it.`object` = vm
                    it.lendObjectType.setImageResource(vm.type())
                }
            }
            1 -> {
                (holder as ObjectOwnerViewHolder).viewDataBinding.also {
                    val obj = lendObject!!
                    val vm = LendObjectDetailViewModel(obj,getObjectUsageColor(obj))
                    it.`object` = vm
                }
            }
            2 -> {
                (holder as ObjectOwnerViewHolder).viewDataBinding.also {
                    val obj = lendObject!!
                    val vm = LendObjectDetailViewModel(obj,getObjectUsageColor(obj))
                    it.`object` = vm
                }
            }
            else -> {
                (holder as ObjectUserViewHolder).viewDataBinding.also {
                    val obj = lendObject!!
                    val vm = LendObjectDetailViewModel(obj,getObjectUsageColor(obj))
                    it.`object` = vm
                }
            }
        }

    }

    /**
     * Helper method that returns a background color for the lendobject's type icon,
     * depending on it's state:
     *
     * Green for available, Yellow if there is a current user.
     *
     * @return a [ColorDrawable] color.
     */
    private fun getObjectUsageColor(lendObject: LendingObject): ColorDrawable {
        return if (lendObject.user != null) {
            ColorDrawable(ContextCompat.getColor(context!!, R.color.customYellow))
        } else {
            ColorDrawable(ContextCompat.getColor(context!!, R.color.customGreen))
        }
    }
}
