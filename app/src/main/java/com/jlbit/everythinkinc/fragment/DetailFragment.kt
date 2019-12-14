package com.jlbit.everythinkinc.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jlbit.everythinkinc.MainActivity
import com.jlbit.everythinkinc.R
import com.jlbit.everythinkinc.model.User
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mainActivity: MainActivity
    private lateinit var user: User
    private lateinit var mMap: GoogleMap

    private lateinit var imageUser: ImageView
    private lateinit var textUser: TextView
    private lateinit var textDate: TextView
    private lateinit var textPhone: TextView
    private lateinit var textMail: TextView
    private lateinit var textCountry: TextView

    fun getInstance(user: User): DetailFragment? {
        val fragment = DetailFragment()
        fragment.user = user
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_detail, container, false)

        mainActivity.actionBar.title = "${user.name.first} ${user.name.last}"

        imageUser = v.image_user
        textUser = v.text_user
        textDate = v.text_date
        textPhone = v.text_phone
        textMail = v.text_mail
        textCountry = v.text_country

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)

        return v
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(mainActivity)
            .load(user.picture.large)
            .centerCrop()
            .thumbnail(0.1f)
            .placeholder(R.drawable.load)
            .error(R.drawable.error)
            .into(imageUser)

        textUser.text = "${user.name.title} ${user.name.first} ${user.name.last}"
        textDate.text = user.dob.date?.subSequence(0,10)
        textPhone.text = "${user.phone}\n${user.cell}"
        textMail.text = user.email
        textCountry.text = "${user.location.state}, ${user.location.country}"
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        val userLocation = LatLng(user.location.coordinates.latitude!!.toDouble(), user.location.coordinates.longitude!!.toDouble())

        mMap.addMarker(MarkerOptions().position(userLocation).title("${user.location.city}, ${user.location.state}, ${user.location.country}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 4f))
    }

}