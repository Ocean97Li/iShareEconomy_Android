package com.ocean.ishareeconomy_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.lending.LendObjectClick
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.viewmodels.LendObjectViewModel

/**
 * Part of *adapters*.
 *
 * RecyclerView Adapter for setting up data binding on the items in the list.
 *
 * @property context: [Context?] Store parent context, needed to fetch colors
 * @property objects: [MutableList<LendingObject>] The [LendingObject] items that our Adapter will show
 */
class LendObjectAdapter(val callback: LendObjectClick) : RecyclerView.Adapter<LendObjectViewHolder>() {

    var context: Context? = null
    var objects: MutableList<LendingObject> = mutableListOf()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LendObjectViewHolder {
        context = parent.context
        val withDataBinding: ListItemLendobjectBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            LendObjectViewHolder.LAYOUT,
            parent,
            false)
        return LendObjectViewHolder(withDataBinding)
    }

    /**
     * Determines how many items need to be drawn
     *
     * @return [Int]
     */
    override fun getItemCount() = objects.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @return [Unit]
     */
    override fun onBindViewHolder(holder: LendObjectViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val obj = objects[position]
            val vm = LendObjectViewModel(obj, context!!)
            it.`object` = vm
            it.callback = this.callback
        }
    }
}

/**
 * Part of *viewholders*.
 *
 * ViewHolder for [LendingObject] items. All work is done by data binding.
 */
class LendObjectViewHolder(val viewDataBinding: ListItemLendobjectBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_lendobject
    }
}
