package com.bestinet.mycare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

import static com.bestinet.mycare.constant.AppConstant.AUTOCOMPLETE_REQUEST_CODE;

public class RegEmployerActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private TextInputEditText etCompName;
    private TextInputEditText etStreet1;
    private TextInputEditText etStreet2;
    private TextInputEditText etStreet3;
    private TextInputEditText etPostcode;
    private TextInputEditText etTown;
    private TextInputEditText etDistrict;
    private TextInputEditText etState;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_employer);
        init();

    }

    private void init() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Employer Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Places.initialize(this, getString(R.string.google_maps_key));

        MaterialButton btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }

        etCompName = findViewById(R.id.etCompName);
        etStreet1 = findViewById(R.id.etStreet1);
        etStreet2 = findViewById(R.id.etStreet2);
        etStreet3 = findViewById(R.id.etStreet3);
        etPostcode = findViewById(R.id.etPostcode);
        etTown = findViewById(R.id.etTown);
        etDistrict = findViewById(R.id.etDistrict);
        etState = findViewById(R.id.etState);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnNext) {
            startActivity(new Intent(RegEmployerActivity.this, RegSummaryActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                        Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.d("Places", "Places details: " + place);
                populatePlaceDetails(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "ERROR: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Toast.makeText(this, "Search cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void populatePlaceDetails(Place place) {
        if (place != null) {
            if (place.getAddressComponents() != null) {

                String street = "";
                String route = "";
                String sublocality = "";
                String locality = "";
                String administrative_area = "";
                String postalcode = "";

                for (AddressComponent addressComponent : place.getAddressComponents().asList()) {
                    if (addressComponent.getTypes().get(0).equals("route")) {
                        route = addressComponent.getName();
                    }

                    if (addressComponent.getTypes().get(0).equals("street")) {
                        street = addressComponent.getName();
                    }

                    if (addressComponent.getTypes().get(0).equals("sublocality_level_1")) {
                        sublocality = addressComponent.getName();
                    }

                    if (addressComponent.getTypes().get(0).equals("locality")) {
                        locality = addressComponent.getName();
                    }

                    if (addressComponent.getTypes().get(0).equals("administrative_area_level_1")) {
                        administrative_area = addressComponent.getName();
                    }

                    if (addressComponent.getTypes().get(0).equals("postal_code")) {
                        postalcode = addressComponent.getName();
                    }
                }
                etCompName.setText(place.getName());
                etStreet1.setText(String.format("%s %s", street, route));
                etStreet2.setText(sublocality);
                etStreet3.setText(locality);
                etPostcode.setText(postalcode);
                etState.setText(administrative_area);
                if (place.getLatLng() != null) {
                    mMap.clear();
                    LatLng selectedLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedLatLng));
                    mMap.addMarker(new MarkerOptions()
                            .position(selectedLatLng));
                }
            }

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
