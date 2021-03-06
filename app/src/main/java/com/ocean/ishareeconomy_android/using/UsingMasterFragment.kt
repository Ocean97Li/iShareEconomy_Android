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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendObjectAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentUsingBinding
import com.ocean.ishareeconomy_android.lending.LendObjectClick
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.LendingViewModel
import com.ocean.ishareeconomy_android.viewmodels.UsingViewModel

/**
 * Part of *lending*.
 *
 * Activity responsible for displaying the list of [LendingObject] currently shared by the user
 * @property token the jwt [String] used to make authenticated api calls
 * @property loginResponseObject the [LoginResponseObject] parsed from [token], identifying the user
 * @property sharedPreferences the [SharedPreferences] used to fetch stored values
 * @property viewModelAdapter RecyclerView Adapter for converting a list of [LendingObject] to cards.
 * @property viewModel the [LendingViewModel] according to the MVVM-pattern, handles domain logic.
 *
 * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
 * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
 * do in this Fragment.
 *
 */
class UsingMasterFragment : Fragment() {

    private val viewModel: UsingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(
            this,
            UsingViewModel.Factory(activity.application, loginResponseObject.id, token)
        )
            .get(UsingViewModel::class.java)
    }
    private var viewModelAdapter: LendObjectAdapter? = null

    // API related
    private lateinit var token: String
    private lateinit var loginResponseObject: LoginResponseObject
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.selectObject(null)
        viewModel.using.observe(viewLifecycleOwner, Observer<List<LendingObject>> { using ->
            using?.apply {
                viewModelAdapter?.objects = using.toMutableList()
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)

        token = sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
        loginResponseObject = jwtToLoginResponseObject(token)!!

        val binding: FragmentUsingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_using,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModelAdapter = LendObjectAdapter(LendObjectClick {
            context?.packageManager ?: return@LendObjectClick
            val masterDetail = (activity as UsingActivity).masterDetail
            viewModel.selectObject(it)
            if (masterDetail) {
                (activity as UsingActivity).onDetailClick()
            } else {
                val directions = UsingMasterFragmentDirections.actionUsingMasterFragmentToUsingDetailFragment()
                findNavController().navigate(directions)
            }
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view_using).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

}
