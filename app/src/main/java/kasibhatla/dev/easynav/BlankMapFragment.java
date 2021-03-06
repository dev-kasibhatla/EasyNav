package kasibhatla.dev.easynav;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MarkerAnchor;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.MapTilesType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "blank-map-fragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankMapFragment newInstance(String param1, String param2) {
        BlankMapFragment fragment = new BlankMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_blank_map, container, false);
         mapFragment = (MapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getAsyncMap(this);

        setMapProperties(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setMapProperties(View view){
        Button rasterButton = view.findViewById(R.id.button1);
        Button vectorButton = view.findViewById(R.id.button2);
        Button zoomIn = view.findViewById(R.id.btnMapZoomIn);
        Button zoomOut = view.findViewById(R.id.btnMapZoomOut);

        rasterButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tomtomMap.getUiSettings().setMapTilesType(MapTilesType.RASTER);
                    }
                }
        );

        vectorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tomtomMap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
                    }
                }
        );
        zoomIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tomtomMap.zoomIn();
                    }
                }
        );
        zoomOut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tomtomMap.zoomOut();
                    }
                }
        );
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private TomtomMap tomtomMap;
    private MapFragment mapFragment;

    @Override
    public void onMapReady(TomtomMap map){
        Log.i(TAG, "onMapReadyCallback works");
        tomtomMap = map;
        tomtomMap.setMyLocationEnabled(true);
        //get current location
        Location location = tomtomMap.getUserLocation();
        tomtomMap.set2DMode(); //3D and 25D available
        LatLng pune = new LatLng(18.5204,73.8567);
        LatLng amsterdam = new LatLng(52.37, 4.90);
        SimpleMarkerBalloon balloon = new SimpleMarkerBalloon("Pune");
        tomtomMap.addMarker(new MarkerBuilder(pune).markerBalloon(balloon));
        tomtomMap.centerOn(CameraPosition.builder(pune).zoom(6.0).build());
    }


}