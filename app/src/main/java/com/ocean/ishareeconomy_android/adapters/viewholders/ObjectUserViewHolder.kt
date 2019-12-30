import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.ListItemUserBinding
import com.ocean.ishareeconomy_android.models.ObjectUser

/**
 * Part of *viewholders*.
 *
 * ViewHolder for [ObjectUser] items. All work is done by data binding.
 */
class ObjectUserViewHolder(val viewDataBinding: ListItemUserBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_user
    }
}