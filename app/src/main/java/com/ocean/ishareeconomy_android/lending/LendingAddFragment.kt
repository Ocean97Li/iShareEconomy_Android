package com.ocean.ishareeconomy_android.lending

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendobjectAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentAddLendingBinding
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.AddLendingViewModel
import kotlinx.android.synthetic.main.fragment_add_lending.*

class LendingAddFragment: Fragment(), SelectedColor, OnShareListener {

    /**
     * Implementation of interface [OnShareListener]
     *  Gets the [LendingActivity] and
     *  navigates back to the lending objects overview: [LendingMasterFragment]
     */
    override fun navigateBackToMaster() {
        (activity as LendingActivity).navigateToMaster()
    }

    /**
     * Method to set the buttons
     */
    override fun setSelected(type: String) {
        val grey = ContextCompat.getColor(context!!, R.color.colorGrey)
        val blue = ContextCompat.getColor(context!!, R.color.colorAccent)

        transportation_button.setBackgroundColor(grey)
        val tool_shape = GradientDrawable()
        val service_shape = GradientDrawable()
        val transport_shape = GradientDrawable()

        tool_shape.setColor(grey)
        service_shape.setColor(grey)
        transport_shape.setColor(grey)

        tool_shape.cornerRadius = 26f
        service_shape.cornerRadius = 26f
        transport_shape.cornerRadius = 26f

        tool_button.background = tool_shape
        service_button.background = service_shape
        transportation_button.background = transport_shape

        when(type) {
            "tool" -> tool_shape.setColor(blue)
            "service" -> service_shape.setColor(blue)
            "transportation" -> transport_shape.setColor(blue)
        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
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
//        viewModel.lending.observe(viewLifecycleOwner, Observer<List<LendingObject>> { lending ->
//            lending?.apply {
//                viewModelAdapter?.objects = lending
//            }
//        })
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

        val binding: FragmentAddLendingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_lending,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        val viewModel= ViewModelProviders.of(this,
            AddLendingViewModel.Factory(activity!!.application, loginResponseObject._id, token))
            .get(AddLendingViewModel::class.java)
        viewModel.colorSetter = this
        viewModel.navigateBack = this
        binding.viewModel = viewModel

        return binding.root
    }
}



