package com.fhanjacson.amca.wheels.ui.search

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.base.GlideApp
import com.fhanjacson.amca.wheels.model.Vehicle
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: FirestorePagingAdapter<Vehicle, VehicleListViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val mVehiclesCollection = mFirestore.collection("vehicleList")
    private val mQuery = mVehiclesCollection.orderBy("price", Query.Direction.DESCENDING)
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var vehicleList: List<Vehicle>
    private lateinit var shimmerView: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        shimmerView = root.shimmer_view_container

        recyclerView = root.vehicleRecyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(10)
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeRefreshLayout = root.refreshLayout

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        shimmerView.startShimmerAnimation()
        setupAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fab = view.filterFAB
        fab.setOnClickListener {
            Toast.makeText(context, "filter", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_searchFragment2_to_vehicleFilterFragment)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewAdapter.refresh()
        }
    }

    override fun onStart() {
        super.onStart()
        viewAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewAdapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        shimmerView.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerView.stopShimmerAnimation()
        super.onPause()
    }

    private fun setupAdapter() {

        // Init Paging Configuration
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        // Init Adapter Configuration
        val options = FirestorePagingOptions.Builder<Vehicle>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, config, Vehicle::class.java)
            .build()

        // Instantiate Paging Adapter
        viewAdapter = object: FirestorePagingAdapter<Vehicle, VehicleListViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): VehicleListViewHolder {
                val view = layoutInflater.inflate(R.layout.item_vehicle, parent, false)
                return VehicleListViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: VehicleListViewHolder,
                position: Int,
                model: Vehicle
            ) {
                holder.vehiclePrimaryName.text = holder.itemView.context.getString(
                    R.string.text_vehicle_primary_name,
                    model.brand,
                    model.model
                )
                holder.vehiclePrice.text = holder.itemView.context.getString(
                    R.string.text_currenct_short,
                    model.price.toString()
                )

                holder.vehicleImageView.setImageDrawable(null)

                val gsRef = storage.getReferenceFromUrl(model.images[0])
                GlideApp.with(context!!)
                    .load(gsRef)
                    .into(holder.vehicleImageView)

            }

            override fun onError(e: Exception) {
                super.onError(e)
                Log.e("MainActivity", e.message.toString())
            }


            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        swipeRefreshLayout.isRefreshing = true
                        shimmerView.startShimmerAnimation()
                        shimmerView.visibility = View.VISIBLE

                    }

                    LoadingState.LOADING_MORE -> {
                        swipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADED -> {
                        swipeRefreshLayout.isRefreshing = false
                        shimmerView.stopShimmerAnimation()
                        shimmerView.visibility = View.GONE
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            context,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewAdapter.retry()
                        swipeRefreshLayout.isRefreshing = false
                    }

                    LoadingState.FINISHED -> {
                        swipeRefreshLayout.isRefreshing = false
                        shimmerView.stopShimmerAnimation()
                        shimmerView.visibility = View.GONE
                    }
                }
            }


        }

        recyclerView.adapter = viewAdapter

    }





}