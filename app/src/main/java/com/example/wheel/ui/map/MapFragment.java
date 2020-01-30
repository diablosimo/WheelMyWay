package com.example.wheel.ui.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wheel.R;
import com.example.wheel.model.AccessibiliteRoute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback, PermissionsListener {
    private static final double ZOOM = 15.0;
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private LocationComponent locationComponent;


    private static final String TAG = "DirectionsActivity";
    private PermissionsManager permissionsManager;
    private Point origin;

    private NavigationMapRoute navigationMapRoute;
    private Point destination;
    // variables needed to initialize navigation
    private FloatingActionButton navButton;
    private MarkerOptions destinationOptions;

    private DatabaseReference mAccessibilityRef = FirebaseDatabase.getInstance().getReference().child("accessibiliteRoute");


    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment(MarkerOptions options) {
        this.destinationOptions = options;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mAccessibilityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot accessSnapshot : dataSnapshot.getChildren()) {
                    AccessibiliteRoute accessibiliteRoute = accessSnapshot.getValue(AccessibiliteRoute.class);
                    assert accessibiliteRoute != null;
                    accessibiliteRoute.setId(Objects.requireNonNull(accessSnapshot.getKey()));
                    //accessibiliteRoutes.add(accessibiliteRoute);
                    addAccessibilityMarker(accessibiliteRoute);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        View root = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = (MapView) root.findViewById(R.id.mapView);
        navButton = root.findViewById(R.id.btn_nav);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return root;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        if (this.destinationOptions != null) {
            this.mapboxMap.addMarker(this.destinationOptions);
            destination = Point.fromLngLat(destinationOptions.getPosition().getLongitude(), destinationOptions.getPosition().getLatitude());
            zoomToDestinationPosition(destinationOptions.getPosition());
            mapboxMap.setOnInfoWindowLongClickListener(new MapboxMap.OnInfoWindowLongClickListener() {
                @Override
                public void onInfoWindowLongClick(@NonNull Marker marker) {
                    Fragment fragment = getFragmentManager().findFragmentByTag("foundation");
                    FragmentTransaction t = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.nav_host_fragment, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
            });
            navButton.setVisibility(View.VISIBLE);
            navButton.setEnabled(true);

            navButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    origin = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
                    getRoute(origin, destination);
                }
            });

        }
        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                        mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(ZOOM).build());

                        if (destinationOptions != null) {
                            zoomToDestinationPosition(destinationOptions.getPosition());
                        }
                    }
                });


    }


    private void zoomToDestinationPosition(LatLng latLng) {
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(latLng)
                .zoom(ZOOM)
                .build());
    }

    private void addAccessibilityMarker(AccessibiliteRoute accessibiliteRoute) {
        if (accessibiliteRoute.isEst_approvue() == true) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(accessibiliteRoute.getLat(), accessibiliteRoute.getIng()));
            markerOptions.icon(IconFactory.getInstance(getContext()).fromResource(R.drawable.ic_action_location));
            this.mapboxMap.addMarker(markerOptions);
        }
    }


    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(getContext())
                .accessToken(getString(R.string.access_token))
                .origin(origin)
                .destination(destination)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    Toast.makeText(getContext(), "check the right and access token", Toast.LENGTH_SHORT).show();
                    return;
                } else if (response.body().routes().size() == 0) {
                    Toast.makeText(getContext(), "No routes found", Toast.LENGTH_SHORT).show();
                    return;
                }
                DirectionsRoute currentRoute = response.body().routes().get(0);
                if (navigationMapRoute != null)
                    navigationMapRoute.removeRoute();
                else
                    navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);
                navigationMapRoute.addRoute(currentRoute);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Log.e(TAG, "error: " + t.getMessage());
            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(
                    getContext(), loadedMapStyle).build());

            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            zoomToDestinationPosition(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude()));

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }


    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
