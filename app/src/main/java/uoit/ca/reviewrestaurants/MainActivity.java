package uoit.ca.reviewrestaurants;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Initialize restaurants list and strings from array and strings files
    Resources res;
    String restaurants[];
    String rated;
    String not_rated;
    String selected_restaurant;
    int selected_restaurant_id;
    //Intializing the spinner:
    Spinner restaurant_spinner;
    //Counters for low and high restaurants
    int lowRestaurantCount=0;
    int highRestaurantCount=0;
    //Lists for restaurants and their rating, and restaurants and if they are rated or not
    List<Map<String,String>> restaurantIsRated;
    List<Map<String,String>> restaurantRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize restaurants lists and strings from array and strings files
        ArrayAdapter<String> spinner_items;
        restaurant_spinner= (Spinner) findViewById(R.id.spinner);
        restaurants=getResources().getStringArray(R.array.restaurant_array);
        rated=getString(R.string.rated);
        not_rated=getString(R.string.not_rated);
        selected_restaurant="";
        selected_restaurant_id=0;
        restaurantRating=new ArrayList<Map<String, String>>();  //Contains restaurant and its corresponding rating
        restaurantIsRated=new ArrayList<Map<String, String>>(); //Used in checking whether the restaurant is rated or not
        spinner_items = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,restaurants);

        //Initialize restaurants and their corresponding rating array:
        Map<String,String> tempMap= new HashMap<String, String>();
        for(int i=0;i<restaurants.length;i++)
        {
            tempMap.put(restaurants[i],not_rated);
            restaurantIsRated.add(tempMap);
            restaurantRating.add(tempMap);
        }

        //Initializing the spinner cont.:
        spinner_items.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurant_spinner.setAdapter(spinner_items);
    }

    //Button method will send the user to the RateRestaurant activity to input his rating
    public void rateRestaurant(View v)
    {
        selected_restaurant=restaurant_spinner.getSelectedItem().toString();
        selected_restaurant_id=(int)restaurant_spinner.getSelectedItemId();
        //TODO: Improve the functionality such that the user can edit this restaurant
        if(restaurantIsRated.get(selected_restaurant_id).containsValue(rated))
        {
            Toast.makeText(getApplicationContext(),selected_restaurant+" is already rated, rating is: "+restaurantRating.get(selected_restaurant_id).get(selected_restaurant),Toast.LENGTH_LONG).show();

        }
        else
        {
            Intent rateIntent= new Intent(this,RateRestaurant.class);
            int RATE_RESTAURANT_ID = 13;
            rateIntent.putExtra("restaurant_name",selected_restaurant);
            startActivityForResult(rateIntent, RATE_RESTAURANT_ID);
        }
    }

    //When RateRestaurant activity answers with the result (the rating of the restaurant), restaurant lists are adjusted
    public void onActivityResult(int reqCode, int resCode, Intent result)
    {
        super.onActivityResult(reqCode,resCode,result);
        if(resCode==RESULT_OK)
        {
            String rating= result.getStringExtra("restaurant_rating");
            double adjusted_rating=Double.parseDouble(rating);
            adjusted_rating=(adjusted_rating/100)*5;
            rating=String.valueOf(adjusted_rating);
            Map<String,String> tempMap = new HashMap<String, String>();
            Map<String,String> tempMap2 = new HashMap<String, String>();
            tempMap.put(selected_restaurant,rating);
            restaurantRating.set(selected_restaurant_id,tempMap);
            tempMap2.put(selected_restaurant,rated);
            restaurantIsRated.set(selected_restaurant_id,tempMap2);
        }
    }

    //Count high and low restaurants
    public void countRestaurants()
    {
        for(int i=0;i<restaurants.length;i++)
        {
            if(restaurantIsRated.get(i).containsValue(not_rated)) {
                Toast.makeText(getApplicationContext(),"Please rate all restaurants before getting summary",Toast.LENGTH_LONG).show();
                return;
            }
        }
        Map<String,String> tempMap=new HashMap<String,String>();
        double value=0.0;
        for(int i=0;i<restaurants.length;i++)
        {
            tempMap=restaurantRating.get(i);
            value=Double.valueOf(tempMap.get(restaurants[i]));
            if(value>=3.5)
            {
                highRestaurantCount++;
            }
            else
            {
                lowRestaurantCount++;
            }
        }

    }

    //Button handle to get summary activity
    public void getSummary(View v)
    {
        countRestaurants();

        if(highRestaurantCount+lowRestaurantCount!=restaurants.length)
        {
            return;
        }
        Intent summaryIntent= new Intent(this, Summary.class);
        summaryIntent.putExtra("high_restaurants",highRestaurantCount);
        summaryIntent.putExtra("low_restaurants",lowRestaurantCount);
        startActivity(summaryIntent);
    }


}
