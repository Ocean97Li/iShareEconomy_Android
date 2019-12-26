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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.adapters.LendobjectAdapter
import com.ocean.ishareeconomy_android.databinding.FragmentLendingBinding
import com.ocean.ishareeconomy_android.models.LendingObject
import com.ocean.ishareeconomy_android.models.LoginResponseObject
import com.ocean.ishareeconomy_android.network.jwtToLoginResponseObject
import com.ocean.ishareeconomy_android.viewmodels.LendingViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_lending.*
import kotlinx.android.synthetic.main.fragment_lending.*

class LendingMasterFragment: Fragment() {

    var deleting = false

    private lateinit var viewOfLayout: View

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
            LendingViewModel.Factory(activity.application, loginResponseObject.id, token))
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewOfLayout = view

        setItemTouchHelper()
    }

    /**
     * Helper method that sets up the onSwipe lisetener for the delete [LendingObject] functionality
     *
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
                 * Only one type of swipe is allowed, so it's alsways to the right
                 * notifies the [LendobjectAdapter] to update and animate the deletion
                 * notifies the [LendingViewModel] to call the repository
                 */
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    viewModel.removeObject(viewModelAdapter!!.objects[position])
                    viewModelAdapter!!.objects.removeAt(position)
                    viewModelAdapter!!.notifyItemRemoved(position)

                }

                /**
                 * Defines the background layout for the swiping action,
                 * makes use of the third party [RecyclerViewSwipeDecorator] library for this
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
                                R.color.customRed
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
                 * @return if [deleting] == true then the set swipe direction is used,
                 * otherwise swiping is disabled by returning 0
                 */
                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    if (deleting) {
                        return super.getSwipeDirs(recyclerView, viewHolder)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.lending_top_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
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

        setHasOptionsMenu(true)

        val binding: FragmentLendingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lending,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = LendobjectAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view_lending).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_lending_object_button -> {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,  (activity as LendingActivity)!!.addFragment)
                    .commit()
            }

            R.id.remove_lending_object_button -> {
                deleting = deleting.not()
                if (deleting) {
                   item.setIcon(R.drawable.ic_delete_white_24dp)
                    val snackbar = Snackbar.make(
                        viewOfLayout.findViewById(R.id.frame_layout_lending),
                        getText(R.string.remove_instruction).toString(),
                        Snackbar.LENGTH_LONG)
                    val params =
                        snackbar.getView().getLayoutParams() as FrameLayout.LayoutParams
                    params.bottomMargin = activity!!.nav_view!!.height
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
                    snackbar.getView().setLayoutParams(params)
                    snackbar.show()
                } else {
                    item.setIcon(R.drawable.ic_delete_grey_24dp)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}