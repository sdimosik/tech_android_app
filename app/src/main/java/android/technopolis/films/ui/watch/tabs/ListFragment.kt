package android.technopolis.films.ui.watch.tabs

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Parcelable
import android.technopolis.films.R
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.base.MainActivity
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var binding: FragmentListBinding? = null

    private val viewModel: WatchViewModel by activityViewModels()

    private val listAdapter: MediaAdapter by lazy { MediaAdapter() }
    private var tabType: MediaType? = null
    private var ARGS_TAG: String? = null
    private val myNetworkCallback by lazy { MyNetworkCallback() }
    private var networkState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var networkStateFlow: StateFlow<Boolean> = networkState.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = arguments?.getSerializable(TAB_TYPE_TAG) as MediaType
        ARGS_TAG = "${tabType}_SCROLL_OFFSET"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    private val onScrollListener: OnScrollListener by lazy { OnScrollListener() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerConnectivityCallback()
        setupRecyclerView()
        val mediaListRecyclerView = binding?.mediaListRecyclerView

        mediaListRecyclerView?.layoutManager?.onRestoreInstanceState(
            viewModel.getTabProperty(ARGS_TAG!!) as Parcelable?
        )

        mediaListRecyclerView?.addOnScrollListener(onScrollListener)

        binding?.swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (badConnectionSandbar.isShown) {
            badConnectionSandbar.dismiss()
        }
        if (noConnectionSandbar.isShown) {
            noConnectionSandbar.dismiss()
        }
        unregisterConnectivityCallback()
        binding?.mediaListRecyclerView?.removeOnScrollListener(onScrollListener)
        binding = null
    }

    private fun getState(): Bundle {
        val state = Bundle()
        state.putParcelable(
            ARGS_TAG,
            binding?.mediaListRecyclerView?.layoutManager?.onSaveInstanceState()
        )
        return state
    }

    private lateinit var badConnectionSandbar: Snackbar
    private lateinit var noConnectionSandbar: Snackbar

    private fun setupRecyclerView() {
        binding?.mediaListRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, HORIZONTAL) {})
            adapter = listAdapter
        }

        val medias = viewModel.observeList(tabType!!)
        getMoreData()
        medias.onEach {
            listAdapter.submitList(it)
        }.launchIn(MainScope())

        val status = viewModel.observeListStatus(tabType!!)

        val navView = (activity as MainActivity).findViewById<View>(R.id.nav_view)
        badConnectionSandbar = Snackbar.make(binding?.swipeRefreshLayout!!,
            resources.getString(R.string.bad_connection),
            Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(navView)
            .setBackgroundTint(resources.getColor(R.color.bad_connection_snackar_background))
            .setTextColor(resources.getColor(R.color.bad_connection_snackar_text))

        noConnectionSandbar = Snackbar.make(binding?.swipeRefreshLayout!!,
            resources.getString(R.string.no_connection),
            Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(navView)
            .setBackgroundTint(resources.getColor(R.color.bad_connection_snackar_background))
            .setTextColor(resources.getColor(R.color.bad_connection_snackar_text))

        if (!networkState.value) {
            noConnectionSandbar.show()
        }
        networkStateFlow.onEach {
            println(it)
            if (!it) {
                noConnectionSandbar.show()
            } else if (noConnectionSandbar.isShown) {
                noConnectionSandbar.dismiss()
            }
        }.launchIn(MainScope())

        status.onEach {
            if (it) {
                MainScope().launch {
                    delay(TIME_TO_BAD_CONNECTION_NOTIFICATION)
                    if (status.value) {
                        badConnectionSandbar.show()
                    }
                }
            } else {
                listAdapter.notifyDataSetChanged()
                showList(listAdapter.currentList.isEmpty())
                badConnectionSandbar.dismiss()
            }
        }.launchIn(MainScope())
    }

    private fun showList(isEmpty: Boolean) {
        if (isEmpty) {
            binding?.mediaListRecyclerView?.visibility = View.GONE
            binding?.mediaListEmptyList?.visibility = View.VISIBLE
        } else {
            binding?.mediaListRecyclerView?.visibility = View.VISIBLE
            binding?.mediaListEmptyList?.visibility = View.GONE
        }
    }

    inner class OnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            viewModel.onPageChanged(getState())
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val pos = linearLayoutManager.findLastCompletelyVisibleItemPosition()
            val last = recyclerView.adapter!!.itemCount
            if ((pos == last - 1) && (last != 0)) {
                getMoreData()
            }
        }
    }

    private fun getMoreData() {
        if (networkState.value) {
            println("in")
            viewModel.getMoreData(tabType!!)
        }
    }

    private fun updateData() {
        if (networkState.value) {
            println("in")
            viewModel.updateList(tabType!!)
        }
    }

    inner class OnRefreshListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            MainScope().launch(Dispatchers.Unconfined) {
                val swipeRefreshLayout = binding?.swipeRefreshLayout
                swipeRefreshLayout?.isRefreshing = true
                updateData()
                if (!networkState.value) {
                    swipeRefreshLayout?.isRefreshing = false
                    cancel()
                }
                var prev = true
                viewModel.observeListStatus(tabType!!).onEach {
                    println(it)
                    if (it) {
                        prev = !prev
                    }
                    if (it == prev) {
                        swipeRefreshLayout?.isRefreshing = false
                        cancel()
                    }
                }.launchIn(this)
            }
        }
    }

    inner class MyNetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            println("onAvailable")
            networkState.value = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            println("onLost")
            networkState.value = false
        }
    }

    private fun registerConnectivityCallback() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(builder, myNetworkCallback)
    }

    private fun unregisterConnectivityCallback() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(
            myNetworkCallback
        )
    }

    companion object {
        const val TAB_TYPE_TAG = "TAB_TYPE"
        const val TIME_TO_BAD_CONNECTION_NOTIFICATION = 10_000L

        fun newInstance(type: MediaType): ListFragment {
            val listFragment = ListFragment()
            val state = Bundle()
            state.putSerializable(TAB_TYPE_TAG, type)
            listFragment.arguments = state
            return listFragment
        }
    }
}
