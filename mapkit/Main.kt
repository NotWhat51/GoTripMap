package com.example.mapkit

import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error

class MainActivity : AppCompatActivity(), UserLocationObjectListener, Session.SearchListener, CameraListener, DrivingSession.DrivingRouteListener {

    private lateinit var mapview: MapView
    private lateinit var locationmapkit: UserLocationLayer
    private lateinit var searchEdit: EditText
    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session
    private val ROUTE_START_LOCATION = Point(47.229410, 39.718281)
    private val ROUTE_END_LOCATION = Point(47.214004, 39.794605)
    private val SCREEN_CENTER = Point(
        (ROUTE_START_LOCATION.latitude+ROUTE_END_LOCATION.latitude)/2,
        (ROUTE_START_LOCATION.longitude+ROUTE_END_LOCATION.longitude)/2
    )
    private var mapObjects:MapObjectCollection? = null
    private var drivingRouter:DrivingRouter? = null
    private var drivingSession:DrivingSession? = null

    private fun submitQuery(qyery:String){
        //searchSession = searchManager.submit(query, VisibleRegionUtils.toPolygon(mapview.mapWindow.map.visibleRegion), SearchOptions(), this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("1c7699cf-6a79-4ac0-8155-5c4e8311e11b")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapview = findViewById(R.id.mapview)
        mapview.mapWindow.map.move(
            CameraPosition(Point(47.219775, 39.718409), 15.0f, 0.0f,0.0f),
            Animation(Animation.Type.SMOOTH, 10f), null)
    }

    override fun onObjectAdded(p0: UserLocationView) {

    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        if(finished){
            submitQuery(searchEdit.text.toString())
        }
    }

    override fun onSearchResponse(p0: Response) {
        val mapObjects:MapObjectCollection = mapview.mapWindow.map.mapObjects
    }

    override fun onSearchError(p0: Error) {

    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        for(route in p0){
            mapObjects!!.addPolyline(route.geometry)
        }
    }

    override fun onDrivingRoutesError(p0: Error) {

    }
}