package com.ocean.ishareeconomy_android.lending

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.FragmentLendingBinding
import com.ocean.ishareeconomy_android.databinding.ListItemLendobjectBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.models.ObjectUser
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.LendObjectViewModel
import com.ocean.ishareeconomy_android.viewmodels.LendingViewModel
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import java.util.*
import kotlin.coroutines.coroutineContext

class LendingMasterFragment: Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: LendingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this,
            LendingViewModel.Factory(activity.application, loginResponseObject._id, token))
            .get(LendingViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of LendObject to cards.
     */
    private var viewModelAdapter: LendobjectAdapter? = null

    // API related
    private lateinit var token: String
    private lateinit var loginResponseObject: LoginResponseObject
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.lending.observe(viewLifecycleOwner, Observer<List<LendingObject>> { lending ->
            lending?.apply {
                viewModelAdapter?.objects = lending
            }
        })
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        spEditor = sharedPreferences.edit()

        token = sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
        loginResponseObject = jwtToLoginResponseObject(token)!!

        val binding: FragmentLendingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lending,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = LendobjectAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

}

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
        val withDataBinding: ListItemLendobjectBinding= DataBindingUtil.inflate(
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
            it.`object` = LendObjectViewModel(obj,getObjectUsageColor(obj))

//            it.callback = callback
        }
    }

    private fun getObjectUsageColor(lendObject: LendingObject): ColorDrawable {
        if (lendObject.user != null) {
            return ColorDrawable(getColor(context!!, R.color.customYellow))
        } else {
            return ColorDrawable(getColor(context!!, R.color.customGreen))
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
