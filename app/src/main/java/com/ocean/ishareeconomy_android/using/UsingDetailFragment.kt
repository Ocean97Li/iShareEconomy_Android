package com.ocean.ishareeconomy_android.using

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendingDetailAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentLendingDetailBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.viewmodels.LendingDetailViewModel

/**
 * Part of *using*.
 *
 * Fragment responsible for displaying adding items to the list of shared objects
 */
class UsingDetailFragment: Fragment() {

    var viewModel = LendingDetailViewModel()
    private var viewModelAdapter: LendingDetailAdapter? = null

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.lendingObject.observe(viewLifecycleOwner, Observer<LendingObject> { using ->
            using?.apply {
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