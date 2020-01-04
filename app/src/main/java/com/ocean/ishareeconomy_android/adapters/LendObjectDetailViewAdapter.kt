package com.ocean.ishareeconomy_android.adapters

import LendObjectViewHolder
import ObjectOwnerViewHolder
import ObjectUserViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.databinding.ListItemOwnerBinding
import com.ocean.ishareeconomy_android.databinding.ListItemUserBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.ObjectOwner
import com.ocean.ishareeconomy_android.models.ObjectUser
import com.ocean.ishareeconomy_android.viewmodels.*

/**
 * Part of *adapters*.
 *
 * RecyclerView Adapter for setting up data binding on the items in the list.
 *
 * @property context: [Context]? Store parent context, needed to fetch colors
 * @property lendObject: [LendingObject] The object that our Adapter will show the details of
 */
class LendingDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context? = null
    var lendObject: LendingObject? = null
        set(value) {
            field = value
            owner = when (value?.owner != null) {
                true -> value!!.owner
                false -> null
            }
            user = when (value?.user != null) {
                true -> value!!.user
                false -> null
            }
            waitingList = when (!value?.waitingList.isNullOrEmpty()) {
                true -> value!!.waitingList
                false -> emptyList()
            }
            notifyDataSetChanged()
        }
    var owner: ObjectOwner? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var user: ObjectUser? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var waitingList: List<ObjectUser> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
                val withDataBinding: ListItemOwnerBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    ObjectOwnerViewHolder.LAYOUT,
                    parent,
                    false)
                ObjectOwnerViewHolder(withDataBinding)
            }
            3 -> {
                val withDataBinding: ListItemUserBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    ObjectUserViewHolder.LAYOUT,
                    parent,
                    false)
                ObjectUserViewHolder(withDataBinding)
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
        var count = 3
        if (lendObject != null) {
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
     * update the contents of the [RecyclerView.ViewHolder] to reflect the item at the given
     * position.
     *
     * @return [Unit]
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var waitingListIndexAdjust  = 3

        when(position) {
            0 -> {
                (holder as LendObjectViewHolder).viewDataBinding.also {
                    val obj = lendObject!!
                    val vm = LendObjectViewModel(obj, context!!)
                    it.`object` = vm
                }
            }
            1 -> {
                (holder as ObjectOwnerViewHolder).viewDataBinding.also {
                    val vm = OwnerObjectViewModel(owner!!)
                    it.owner = vm
                }
            }
            2 -> {
                (holder as ObjectUserViewHolder).viewDataBinding.also {
                    val vm = UserObjectViewModel(user)
                    it.user = vm
                }
            }
            else -> {
                (holder as ObjectUserViewHolder).viewDataBinding.also {
                    val user = waitingList[position - waitingListIndexAdjust]
                    val vm = UserObjectViewModel(user)
                    it.user = vm
                }
            }
        }

    }
}
