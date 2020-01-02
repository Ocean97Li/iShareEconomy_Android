package com.ocean.ishareeconomy_android.using

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendingDetailAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentLendingDetailBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.LendingDetailViewModel

/**
 * Part of *using*.
 *
 * Fragment responsible for displaying adding items to the list of shared objects
 * @property viewModel: [LendingDetailViewModel] the viewModel for displaying the details of a selected [LendingObject]
 * @property viewModelAdapter: [LendingDetailAdapter] the viewModelAdapter that adapts the data to the [RecyclerView]
 */
class UsingDetailFragment: Fragment() {

    // API related
    private lateinit var token: String
    private lateinit var loginResponseObject: LoginResponseObject
    private lateinit var sharedPreferences: SharedPreferences

    val viewModel: LendingDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this,
            LendingDetailViewModel.Factory(activity.application, loginResponseObject.id))
            .get(LendingDetailViewModel::class.java)
    }
    private var viewModelAdapter: LendingDetailAdapter? = null

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.selectedLendObject.postValue(null)
        }
        viewModel.using.observe(viewLifecycleOwner, Observer<List<LendingObject>> { using ->
            if (viewModel.selectedLendObject.value == null && !using.isNullOrEmpty()) {
                viewModelAdapter?.lendObject = using[0]
            }
        })
        viewModel.selectedLendObject.observe(viewLifecycleOwner, Observer<LendingObject> { using ->
            if (using == null && !viewModel.using.value.isNullOrEmpty()) {
                viewModelAdapter?.lendObject = viewModel.using.value!![0]
            } else {
                viewModelAdapter?.lendObject = using
            }
        })
    }

    /**
     * Called to have the fragment instantiate its user interface view an all the binding configuration
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

        val binding: FragmentLendingDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lending_detail,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        viewModelAdapter = LendingDetailAdapter()

        binding.viewModel = viewModel

        binding.root.findViewById<RecyclerView>(R.id.recycler_view_lending_detail).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }
}