package com.fhanjacson.amca.wheels.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Vehicle
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var shimmerView: ShimmerFrameLayout
    private lateinit var xvehicleList: List<Vehicle>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        shimmerView = root.shimmer_view_container

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val vehicleListUpdateObserver = Observer<List<Vehicle>> {
            val vehicleList: List<Vehicle> = it
            xvehicleList = it
            viewManager = LinearLayoutManager(context)
            viewAdapter = VehicleListAdapter(vehicleList)
            recyclerView = view.vehicleRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }

            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
        }


//        searchViewModel.getVehicleList().observe(viewLifecycleOwner, vehicleListUpdateObserver)
        searchViewModel.vehicleList.observe(viewLifecycleOwner, vehicleListUpdateObserver)

        val fab = view.filterFAB
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment2_to_vehicleFilterFragment)


//            Toast.makeText(context, "$randomLat:$randomLon", Toast.LENGTH_SHORT).show()


//            val asd = FirestoreRepository()
//            asd.fireStoreDB.runBatch { batch ->
//                for (vehicle in xvehicleList) {

//                    when(vehicle.type) {
//                        Constant.VEHICLE_TYPE_HATCHBACK -> vehicle.seat = 5
//                        Constant.VEHICLE_TYPE_SUV -> vehicle.seat = 7
//                        Constant.VEHICLE_TYPE_VAN -> vehicle.seat = 8
//                    }


//
//                    val df = DecimalFormat("#.#######")
//                    df.roundingMode = RoundingMode.CEILING
//
//                    val lat = 3.0586745
//                    val lon = 101.6917068
//
//                    val minusorplus1 = if (ThreadLocalRandom.current().nextInt(0, 2) == 1) -1 else 1
//                    val minusorplus2 = if (ThreadLocalRandom.current().nextInt(0, 2) == 1) -1 else 1
//
//                    val randomLat = df.format(
//                        lat + (ThreadLocalRandom.current().nextInt(10, 500000) / 10000000.0 * minusorplus1)).toDouble()
//                    val randomLon = df.format(
//                        lon + (ThreadLocalRandom.current().nextInt(10, 500000) / 10000000.0 * minusorplus2)).toDouble()
//                    val mpg = ThreadLocalRandom.current().nextInt(20, 51)
//                    val transmissionList = arrayOf("at" , "mt")
//                    val transmission = transmissionList[ThreadLocalRandom.current().nextInt(0, 2)]
//                    val trip = ThreadLocalRandom.current().nextInt(20, 100)
//                    val rating = ThreadLocalRandom.current().nextInt(375, 500) / 100.toDouble()

//                    val charPool : List<Char> = ('A'..'Z').toList()
//                    val randomString = (1..2)
//                        .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
//                        .map(charPool::get)
//                        .joinToString("")
//
//                    val randomNumber = String.format("%04d", ThreadLocalRandom.current().nextInt(1000, 10000))
//                    vehicle.licenseplate = "W$randomString$randomNumber"
//
//                    val geo = GeoPoint(randomLat, randomLon)
//
//                    vehicle.location = geo


//                    val ref = asd.vehicleList().document(vehicle.id)
//                    batch.set(ref, vehicle)
//                }
//            }.addOnSuccessListener {
//                Toast.makeText(context, "BATCH WRITE SUCCESS", Toast.LENGTH_SHORT).show()
//            }

        }


    }



    override fun onResume() {
        super.onResume()
        shimmerView.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerView.stopShimmerAnimation()
        super.onPause()
    }
}