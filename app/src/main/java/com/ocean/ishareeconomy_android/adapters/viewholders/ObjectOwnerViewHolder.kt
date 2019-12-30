import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemOwnerBinding
import com.ocean.ishareeconomy_android.models.ObjectOwner

/**
 * Part of *viewholders*.
 *
 * ViewHolder for [ObjectOwner] items. All work is done by data binding.
 */
class ObjectOwnerViewHolder(val viewDataBinding: ListItemOwnerBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_owner
    }
}