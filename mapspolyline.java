package winlab.findplaces2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

public class PolyLine_Encoder {

	private ArrayList<LatLng> points;
	private JSONObject originalObject;
	private JSONArray steps;
	private ArrayList<Double> latitudes;
	private ArrayList<Double> longitudes;
	private Polyline route;
	
	public PolyLine_Encoder(JSONObject jsonObject){
		points = new ArrayList<LatLng>();
		latitudes = new ArrayList<Double>();
		longitudes = new ArrayList<Double>();
		originalObject = jsonObject;
	}
	
	/**
	 * takes the JSONObject and returns an 
	 * ArrayList<LatLng> with all the points
	 * on route to destination.
	 * @return {@link ArrayList}
	 * @throws JSONException
	 */
	public ArrayList<LatLng> getPolyline() throws JSONException{
		JSONArray routesArray = originalObject.getJSONArray("routes");
		JSONObject route = routesArray.getJSONObject(0);
		JSONArray legs = route.getJSONArray("legs");
		 
		//run through the legs array to get all "steps" arrays
		for(int i = 0; i < legs.length(); i++){
			JSONObject leg = legs.getJSONObject(i);
			JSONArray steps = leg.getJSONArray("steps");
			for(int j = 0; j < steps.length(); j++){
				
				JSONObject step = steps.getJSONObject(j);
				
				//start location and add to arraylists
				JSONObject startLocation = step.getJSONObject("start_location");
				double lat = startLocation.getDouble("lat");
				double lng = startLocation.getDouble("lng");
				latitudes.add(lat);
				longitudes.add(lng);
				LatLng LL = new LatLng(lat, lng);
				points.add(LL);
				
				//end location and add to arraylists
				JSONObject endLocation = step.getJSONObject("end_location");
				lat = endLocation.getDouble("lat");
				lng = endLocation.getDouble("lng");
				latitudes.add(lat);
				longitudes.add(lng);
				LL = new LatLng(lat, lng);
				points.add(LL);								
			}
		}
		return points;
	}	
}
