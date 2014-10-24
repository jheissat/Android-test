package fr.julienheissat.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import fr.julienheissat.application.TaskManagerApplication;
import fr.julienheissat.modelController.LocationController;
import fr.julienheissat.modelController.Task;
import fr.julienheissat.modelController.TaskListController;
import fr.julienheissat.taskmanager.R;
import fr.julienheissat.utils.PlayConnectionService;


/**
 * Fragment displaying the map after connection to google play services, zooming to current location
 * and displaying list of tasks on the current Map
 * Using support library
 *
 *
 *
 */



public class MapShowFragment extends Fragment implements LocationController.LocationControllerListener
{


    public static final String MAPSHOWFRAGMENTLIFECYCLE = "Map fragment lifecycle";
    public static final String MAPSHOWFRAGMENTLOCATIONCALLBACKS = "Map fragment location callbacks";
    private static final Float DEFAULT_ZOOM=13f;

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private ProgressBar mProgressBar;
    private TextView mAddress;
    private MapView mMapView;

    private GoogleMap mMap;
    private TaskManagerApplication mApp;
    private TaskListController taskListController;

    private boolean mLocationRetrieved;
    private boolean mAddressRetrieved;
    private Location mCurrentLocation;

    private Float mSavedZoomLevel;
    private Double mSavedLat;
    private Double mSavedLng;


    private SharedPreferences mSharedPreferences;


    private OnMapFragmentInteractionListener mListener;

    //null constructor required by the fragmentManager
    public MapShowFragment()
    {
    }

    /**
     * Fragment lifecycle methods
     */

    // TODO: Rename and change types and number of parameters
    public static MapShowFragment newInstance(String param1, String param2)
    {
        Log.d(MAPSHOWFRAGMENTLIFECYCLE, "new instance method invoked");

        MapShowFragment fragment = new MapShowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public static MapShowFragment newInstance()
    {
        Log.d(MAPSHOWFRAGMENTLIFECYCLE, "new instance");
        MapShowFragment fragment = new MapShowFragment();
        return fragment;
    }


    @Override
    public void onAttach(Activity activity)
    {

        Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnAttach");
        super.onAttach(activity);

        // locationController.queryLatestAddress();
        try
        {
            mListener = (OnMapFragmentInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreate");

        if (savedInstanceState != null)
        {
            Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreate - Bundle not empty");
        }
        else
        {
            Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreate - Bundle empty");
        }

        mSharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        mApp = (TaskManagerApplication) this.getActivity().getApplication();
        mApp.getLocationController().register(this);
        taskListController = mApp.getTaskListController();
        this.setCurrentLocation();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreateView");
        View fragmentView = inflater.inflate(R.layout.fragment_map, container, false);


        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()))
        {
            case ConnectionResult.SUCCESS:
                Log.i(MAPSHOWFRAGMENTLIFECYCLE, "OnCreateView connection success");

                MapsInitializer.initialize(getActivity());

                mMapView = (MapView) fragmentView.findViewById(R.id.map);
                mMapView.onCreate(savedInstanceState);

                break;
            case ConnectionResult.SERVICE_MISSING:
                Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreateView - Service missing");
                Toast.makeText(getActivity(), "Service missing", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreateView- Update required");
                Toast.makeText(getActivity(), "Update required", Toast.LENGTH_SHORT).show();
                break;

            default:
                Log.d(MAPSHOWFRAGMENTLIFECYCLE, "OnCreateView Issue connecting Google map");
        }

        // Get progress bar and address objects from layout
        mAddress = (TextView) fragmentView.findViewById(R.id.address);
        mProgressBar = (ProgressBar) fragmentView.findViewById(R.id.address_progress);

        if (!mAddressRetrieved)
        {
            mApp.getLocationController().queryLatestAddress();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        if (mMapView != null)
        {

            mMap = mMapView.getMap();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);


            if (savedInstanceState != null) {

                Log.i(MAPSHOWFRAGMENTLIFECYCLE, "OnActivityCreated - SavedinstanceState retrieved - ");
                mSavedLat = savedInstanceState.getDouble("mSavedLat");
                mSavedLng = savedInstanceState.getDouble("mSavedLng");
                mSavedZoomLevel = savedInstanceState.getFloat("mSavedZoomLevel");


                if (mSavedLat!=null && mSavedLng!=null && mSavedZoomLevel!=null)
                {
                    LatLng latlng = new LatLng(mSavedLat, mSavedLng);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latlng)
                            .zoom(mSavedZoomLevel)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    setMarkersOnMap();
                    return;

                }
            }


            if (mCurrentLocation !=null)
            {
                Log.i(MAPSHOWFRAGMENTLIFECYCLE, "OnActivityCreated - mCurrentLocation retrieved");
                LatLng latlng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latlng)
                        .zoom(DEFAULT_ZOOM)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.addMarker(new MarkerOptions()
                        .position(latlng));
                setMarkersOnMap();
                return;
            }

            else
            {
                mApp.getLocationController().connectLocationClient();
            }

        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CameraPosition myCam=mMap.getCameraPosition();
        outState.putDouble("mSavedLat",myCam.target.latitude);
        outState.putDouble("mSavedLng", myCam.target.longitude);
        outState.putFloat("mSavedZoomLevel", myCam.zoom);

        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach()
    {
        super.onDetach();

        // locationController.unregister(this);
        mListener = null;
    }

    @Override
    public void onResume() {

        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    /**
     * LocationController listener callback methods
     */

    // callback from locationController if location updates have been requested and there are location changes
    @Override
    public void onLocationControllerChanged(LocationController locationController)
    {
        Log.i(MAPSHOWFRAGMENTLOCATIONCALLBACKS, "locationChanged");


    }

    // callback from locationController when location controller is connected
    @Override
    public void onLocationControllerConnected(LocationController locationController)
    {
        Log.i(MAPSHOWFRAGMENTLOCATIONCALLBACKS, "locationControllerConnected");

        if (!mLocationRetrieved) {
            Log.i(MAPSHOWFRAGMENTLOCATIONCALLBACKS, "LocationControllerConnected - Location retrieved");

            mCurrentLocation = mApp.getLocationController().getLatestLocation();

            if (mCurrentLocation != null) {

                LatLng latlng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latlng)
                        .zoom(DEFAULT_ZOOM)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.addMarker(new MarkerOptions()
                        .position(latlng));
                setMarkersOnMap();
            }
        }

        if (locationController.getLatestAddress() == null)
        {
            locationController.queryLatestAddress();
            mProgressBar.setVisibility(View.VISIBLE);

        }

    }


    // callback from locationController when location controller is disconnected
    @Override
    public void onLocationControllerDisConnected(LocationController locationController)
    {
        Log.i(MAPSHOWFRAGMENTLOCATIONCALLBACKS, "locationControllerDisConnected");
    }

    // callback from locationController when address has been requested and new address has been received
    @Override
    public void onLocationControllerAddressChanged(LocationController locationController)
    {
        Log.i(MAPSHOWFRAGMENTLOCATIONCALLBACKS, "addressChanged");
        locationController.getLatestAddress();
        mProgressBar.setVisibility(View.GONE);

        if (locationController.getLatestAddress() != null)
        {
            mAddress.setText(locationController.getLatestAddress());
            mProgressBar.setVisibility(View.GONE);
            mAddressRetrieved=true;
        }


    }

    //Method to get location from Location controller
    private boolean setCurrentLocation()
    {
        if (!mLocationRetrieved)
        {
            mCurrentLocation = mApp.getLocationController().getLatestLocation();

            if (mCurrentLocation != null)
            {
                mLocationRetrieved=true;
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putLong(getString(R.string.pref_user_last_lat), Double.doubleToLongBits(mCurrentLocation.getLatitude()));
                editor.putLong(getString(R.string.pref_user_last_lng), Double.doubleToLongBits(mCurrentLocation.getLongitude()));
                editor.commit();

                return true;

            } else
            {
                mApp.getLocationController().connectLocationClient();
                return false;
            }
        } else
        {
            return true;
        }

    }

    //Method to set markers on the map
    public void setMarkersOnMap() {
        if (this.taskListController != null && mMap != null) {
            ArrayList<Task> tasks = this.taskListController.getList();



                for (Task t : tasks) {
                    if (t.getLatitude() != null && t.getLongitude() != null) {
                        Integer imgId = getResources().getIdentifier("ic_p" + t.getId(), "drawable", getActivity().getPackageName());

                        mMap.addMarker(new MarkerOptions()
                                .title(t.getName())
                                .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier(t.getPriority().getPriorityRessource(),
                                        "drawable", getActivity().getPackageName())))
                                .position(new LatLng(t.getLatitude(), t.getLongitude())));

                    }

                }





        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnMapFragmentInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
