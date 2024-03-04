package com.tawfeeq.CarAPI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link API_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class API_Fragment extends Fragment {

    Spinner SpinnerMan, SpinnerMod;
    TextView tv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public API_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment API_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static API_Fragment newInstance(String param1, String param2) {
        API_Fragment fragment = new API_Fragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_api, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        SpinnerMan = getView().findViewById(R.id.SpinnerManufacturer);
        SpinnerMod = getView().findViewById(R.id.SpinnerModel);
        tv = getView().findViewById(R.id.textView);



        //String apiUrl = "https://api.api-ninjas.com/v1/cars?limit=2&model=camry";
        //String apiKey = "YvdWR7GkBUbsdWvZLCjY1NnEgYmdHXYNwke58b9T";



        try {
            // URL and query parameters
            String baseUrl = "https://api.carsxe.com/specs";
            String apiKey = "9e6p0jzoa_4s0y9mni2_jf418c69y";
            String vin = "WBAFR7C57CC811956";

            // Encode parameters
            String query = String.format("key=%s&vin=%s", URLEncoder.encode(apiKey, "UTF-8"), URLEncoder.encode(vin, "UTF-8"));

            // Construct full URL
            URL url = new URL(baseUrl + "?" + query);

            // Create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            // Print response
            System.out.println("Response: " + response.toString());

            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error Bruh", Toast.LENGTH_SHORT).show();
        }








        if(SpinnerMan.getSelectedItem()==null) {
            String[] ManList = {"Choose Car Manufacturer", "Audi", "Abarth", "Alfa Romeo", "Aston Martin", "BMW", "Bentley", "Citroen", "Cadillac", "Cupra", "Chevrolet",
                    "Dacia", "Dodge", "Fiat", "Ford", "Ferrari", "Genesis", "GMC", "Honda", "Hyundai", "Infiniti", "Isuzu", "Jeep", "Jaguar", "Kia", "Lamborghini", "Land Rover", "Lexus",
                    "Maserati", "Mazda", "Mini", "Mitsubishi", "Mercedes", "Nissan", "Opel", "Porsche", "Peugeot", "Renault", "Subaru", "Suzuki", "Seat", "Skoda", "Toyota", "Tesla", "Volkswagen", "Volvo"};
            ArrayAdapter<String> ManAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ManList);
            ManAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            SpinnerMan.setAdapter(ManAdapter);
        }

        String [] ModelNon = {"Choose Manufacturer"};

        SpinnerMan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String item = adapterView.getSelectedItem().toString();

                // For Each Manufacturer Selected, The Models get a new String Array of Sold/Available Models of that Manufacturer!
                if(item.equals("Choose Car Manufacturer")) {

                    ArrayAdapter<String> ModAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ModelNon);
                    ModAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    SpinnerMod.setAdapter(ModAdapter);

                }else {

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Does Nothing As Nothing is Selected
            }
        });
    }
}