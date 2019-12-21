package com.ocean.ishareeconomy_android.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.viewmodels.LendObjectViewModel


/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class LendobjectAdapter() : RecyclerView.Adapter<LendObjectViewHolder>() {

    /**
     * Store parent context
     */
    var context: Context? = null

    /**
     * The videos that our Adapter will show
     */
    var objects: List<LendingObject> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
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

    override fun getItemCount() = objects.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: LendObjectViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val obj = objects[position]
            val vm = LendObjectViewModel(obj,getObjectUsageColor(obj))
            it.`object` = vm
            it.lendobjectType.setImageResource(vm.type())
//            it.callback = callback
        }
    }

    private fun getObjectUsageColor(lendObject: LendingObject): ColorDrawable {
        if (lendObject.user != null) {
            return ColorDrawable(ContextCompat.getColor(context!!, R.color.customYellow))
        } else {
            return ColorDrawable(ContextCompat.getColor(context!!, R.color.customGreen))
        }
    }

}

/**
 * ViewHolder for LendObject items. All work is done by data binding.
 */
class LendObjectViewHolder(val viewDataBinding: ListItemLendobjectBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_lendobject
    }
}