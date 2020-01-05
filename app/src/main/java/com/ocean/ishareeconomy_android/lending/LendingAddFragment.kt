package com.ocean.ishareeconomy_android.lending

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.FragmentAddLendingBinding
import com.ocean.ishareeconomy_android.models.LendObjectType
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.AddLendingViewModel
import kotlinx.android.synthetic.main.fragment_add_lending.*

/**
 * Part of *lending*.
 *
 * The [Fragment] responsible for adding items to the list of shared objects
 *
 * @property token the jwt [String] used to make authenticated api calls
 * @property loginResponseObject the [LoginResponseObject] parsed from [token], identifying the user
 * @property sharedPreferences the [SharedPreferences] used to fetch stored values
 *
 * @see [SelectedColor], [OnShareListener]
 */
class LendingAddFragment: Fragment(), SelectedColor, OnShareListener {

    // API related
    private lateinit var token: String
    private lateinit var loginResponseObject: LoginResponseObject
    private lateinit var sharedPreferences: SharedPreferences

    /**
     *  Implementation of interface [OnShareListener]
     *  Gets the [LendingActivity] and
     *  navigates back to the lending objects overview: [LendingMasterFragment]
     *
     *  @return [Unit]
     */
    override fun navigateBackToMaster() {
        // Close soft keyboard
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken,0)
        // Navigate back
        if ((activity as LendingActivity).masterDetail) {
            (activity as LendingActivity).navigateToMaster()
        } else {
            val directions = LendingAddFragmentDirections.actionLendingAddFragmentToLendingMasterFragment()
            findNavController().navigate(directions)
        }
    }

    /**
     * Method to set the buttons to the right color:
     * [R.color.colorGrey] by default, [R.color.colorAccent] when selected
     *
     * @param type the selected [LendObjectType] for which the button should be set to look selected
     *
     * @return [Unit]
     */
    override fun setSelected(type: LendObjectType) {
        val grey = ContextCompat.getColor(context!!, R.color.colorGrey)
        val blue = ContextCompat.getColor(context!!, R.color.colorAccent)

        val toolShape = GradientDrawable()
        val serviceShape = GradientDrawable()
        val transportShape = GradientDrawable()

        toolShape.setColor(grey)
        serviceShape.setColor(grey)
        transportShape.setColor(grey)

        toolShape.cornerRadius = 26f
        serviceShape.cornerRadius = 26f
        transportShape.cornerRadius = 26f

        tool_button.background = toolShape
        service_button.background = serviceShape
        transportation_button.background = transportShape

        when(type) {
            LendObjectType.Tool -> toolShape.setColor(blue)
            LendObjectType.Service -> serviceShape.setColor(blue)
            LendObjectType.Transportation -> transportShape.setColor(blue)
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * If you return a View from here, you will later be called in
     * [onDestroyView] when the view is being released.
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

        token = sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
        loginResponseObject = jwtToLoginResponseObject(token)!!

        val binding: FragmentAddLendingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_lending,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel= ViewModelProviders.of(this,
            AddLendingViewModel.Factory(activity!!.application, loginResponseObject.id, token))
            .get(AddLendingViewModel::class.java)
        viewModel.colorSetter = this
        viewModel.navigateBack = this
        binding.viewModel = viewModel

        return binding.root
    }
}



