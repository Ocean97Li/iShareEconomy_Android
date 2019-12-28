import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.models.LendingObject

/**
 * Part of *adapters.viewholders*.
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