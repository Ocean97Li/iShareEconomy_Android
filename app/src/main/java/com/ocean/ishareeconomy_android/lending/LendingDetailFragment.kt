package com.ocean.ishareeconomy_android.lending

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.FragmentAddLendingBinding
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.AddLendingViewModel

/**
 * Part of *lending*.
 *
 * Fragment responsible for displaying adding items to the list of shared objects
 */
class LendingDetailFragment: Fragment() {
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

//        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
//        spEditor = sharedPreferences.edit()
//
//        token = sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
//        loginResponseObject = jwtToLoginResponseObject(token)!!

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
        binding.viewModel = viewModel

        return binding.root
    }
}