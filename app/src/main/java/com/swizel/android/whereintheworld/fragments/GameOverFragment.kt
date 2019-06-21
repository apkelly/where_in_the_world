package com.swizel.android.whereintheworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.swizel.android.whereintheworld.R
import com.swizel.android.whereintheworld.viewmodels.WhereInTheWorldViewModel
import kotlinx.android.synthetic.main.fragment_game_over.*


class GameOverFragment : Fragment() {

    private val viewModel: WhereInTheWorldViewModel by viewModels({ requireActivity() })

    companion object {
        private val MAP_CENTER = LatLng(25.0, 0.0)
    }

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add Map dynamically, xml instantiation of fragments inside fragments isn't supported (and causes crashes).
        val options = GoogleMapOptions()
        options.camera(CameraPosition.builder().target(MAP_CENTER).build())
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
        options.compassEnabled(false)
        options.rotateGesturesEnabled(false)
        options.zoomControlsEnabled(false)
        options.zoomGesturesEnabled(false)
        options.mapToolbarEnabled(false)
        options.tiltGesturesEnabled(false)

        mapFragment = SupportMapFragment.newInstance(options).apply {
            getMapAsync { googleMap ->
                googleMap.uiSettings.isMyLocationButtonEnabled = false
                googleMap.isIndoorEnabled = false
            }
        }

        score.text = "${viewModel.calculateScore()}"

        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.mapStub, mapFragment)
        ft.commit()
    }
}