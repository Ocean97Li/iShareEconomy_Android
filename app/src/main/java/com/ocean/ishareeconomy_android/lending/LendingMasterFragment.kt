package com.ocean.ishareeconomy_android.lending

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendObjectAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentLendingBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.LendingViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_lending.*
import kotlinx.android.synthetic.main.fragment_lending.*

/**
 * Part of *lending*.
 *
 * Activity responsible for displaying the list of [LendingObject] currently shared by the user
 * @property token the jwt [String] used to make authenticated api calls
 * @property loginResponseObject the [LoginResponseObject] parsed from [token], identifying the user
 * @property sharedPreferences the [SharedPreferences] used to fetch stored values
 *
 * @property deleting the [SharedPreferences.Editor] used to store values
 * @property viewModelAdapter RecyclerView Adapter for converting a list of [LendingObject] to cards.
 *
 * @property viewModel the [LendingViewModel] according to the MVVM-pattern, handles domain logic.
 * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
 * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
 * do in this Fragment.
 *
 */
class LendingMasterFragment: Fragment() {

    var deleting = false
    private lateinit var viewOfLayout: View
    private val viewModel: LendingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this,
            LendingViewModel.Factory(activity.application, loginResponseObject.id, token))
            .get(LendingViewModel::class.java)
    }
    private var viewModelAdapter: LendObjectAdapter? = null

    // API related
    private lateinit var token: String
    private lateinit var loginResponseObject: LoginResponseObject
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Called when fragments view is initiated
     *
     * @param view: [View]
     * @param savedInstanceState: [Bundle?]
     *
     * @return [Unit]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewOfLayout = view

        setItemTouchHelper()
    }

    /**
     * Helper method that sets up the onSwipe listener for the delete [LendingObject] functionality
     *
     * @return [Unit]
     */
    private fun setItemTouchHelper() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                /**
                 * Defines the action to take when an item is dragged,
                 * this feature is disabled.
                 */
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                /**
                 * Defines the action to take when an item is swiped,
                 * Only one type of swipe is allowed, so it's always to the right
                 * notifies [viewModelAdapter] of type [LendObjectAdapter] to update and animate the deletion
                 * notifies [viewModel] of type [LendingViewModel] to call the repository
                 *
                 * @param viewHolder an [RecyclerView.ViewHolder] that contains the swiped item's information
                 * @param direction an [Int] that represents the swipe direction
                 *
                 * @return [Unit]
                 */
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val position = viewHolder.adapterPosition
                    val lendObject = viewModelAdapter!!.objects[position]
                    if(lendObject.waitingList.isEmpty() && lendObject.user == null) {
                        viewModel.removeObject(lendObject)
                        viewModelAdapter!!.objects.removeAt(position)
                        viewModelAdapter!!.notifyItemRemoved(position)
                    }
                }

                /**
                 * Defines the background layout for the swiping action,
                 * makes use of the third party [RecyclerViewSwipeDecorator] library for this
                 *
                 * @param c: [Canvas]
                 * @param recyclerView: [RecyclerView]
                 * @param viewHolder: [RecyclerView.ViewHolder]
                 * @param dX: [Float]
                 * @param dY: [Float]
                 * @param actionState: [Int]
                 * @param isCurrentlyActive: [Boolean]
                 *
                 *  @return [Unit]
                 */
                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.colorDelete
                            )
                        )
                        .addActionIcon(R.drawable.ic_delete_white_24dp)
                        .create()
                        .decorate()
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

                /**
                 * Returns the available swipe directions
                 *
                 * @return if [deleting] == true then the set swipe direction is used,
                 * otherwise swiping is disabled by returning 0
                 */
                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    if (deleting) {
                        val position = viewHolder.adapterPosition
                        val lendObject = viewModelAdapter!!.objects[position]

                        if (lendObject.waitingList.isEmpty() && lendObject.user == null) {
                            return super.getSwipeDirs(recyclerView, viewHolder)
                        } else {
                            showBackBarWith(R.string.remove_not_possible, R.color.colorDelete)
                        }
                    }
                    return 0
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_view_lending)
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
                viewModelAdapter?.objects = lending.toMutableList()
            }
        })
    }

    /**
     * Called to inflate the ToolBarMenu
     *
     * @param menu the top navigation menu
     * @param inflater: the class that inflates the menu
     *
     * @return [Unit]
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lending_top_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * If you return a View from here, you will later be called in
     * [onDestroyView] when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Returns the [View]? for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)

        token = sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
        loginResponseObject = jwtToLoginResponseObject(token)!!

        setHasOptionsMenu(true)

        val binding: FragmentLendingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lending,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModelAdapter = LendObjectAdapter( LendObjectClick{
            context?.packageManager ?: return@LendObjectClick
            val masterDetail = (activity as LendingActivity).masterDetail
            if (masterDetail || !deleting) {
                viewModel.selectObject(it)
                if (masterDetail) {
                    (activity as LendingActivity).onDetailClick()
                } else {
                    val directions = LendingMasterFragmentDirections.actionLendingMasterFragmentToLendingDetailFragment()
                    findNavController().navigate(directions)
                }
            }
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view_lending).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    /**
     * Called to inflate the ToolBarMenu
     *
     * @param item the selected menuItem
     *
     * @return [Unit]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_lending_object_button -> {
                val masterDetail = (activity as LendingActivity).masterDetail
                if (masterDetail) {
                    (activity as LendingActivity).navigateToAdd()
                } else {
                    val directions = LendingMasterFragmentDirections.actionLendingMasterFragmentToLendingAddFragment()
                    findNavController().navigate(directions)
                }
            }

            R.id.remove_lending_object_button -> {
                deleting = deleting.not()
                if (deleting) {
                   item.setIcon(R.drawable.ic_delete_white_24dp)
                    showBackBarWith(R.string.remove_instruction)
                } else {
                    item.setIcon(R.drawable.ic_delete_grey_24dp)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Helper method that launches a snackbar, with customizable text, color and length
     *
     * @param textId refers to the [R.string].textId
     * @param colorId refers to the [R.color].colorId
     * @param textId refers to the duration [Int] for which the [Snackbar] will be displayed.
     *
     * @return [Unit]
     */
    private fun showBackBarWith(textId: Int, colorId: Int = R.color.colorAccent, length: Int = Snackbar.LENGTH_LONG) {
        val snackbar = Snackbar.make(
            viewOfLayout.findViewById(R.id.frame_layout_lending),
            getText(textId).toString(),
            length
        )
        val params =
            snackbar.view.layoutParams as FrameLayout.LayoutParams
        params.bottomMargin = activity!!.nav_view_lending!!.height
        snackbar.view.setBackgroundColor(ContextCompat.getColor(context!!, colorId))
        snackbar.view.layoutParams = params
        snackbar.show()
    }
}