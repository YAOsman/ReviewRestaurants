package uoit.ca.reviewrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class RateRestaurant extends AppCompatActivity {
    SeekBar rate;
    Intent rateIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_restaurant);

        //Set up seek bar
        rate= (SeekBar) findViewById(R.id.rating);

        //Get data from intent
        rateIntent= new Intent(Intent.ACTION_PICK);
        Bundle datafromIntent = rateIntent.getExtras();
        if(datafromIntent!=null)
        {
            String restaurant_name=(String)datafromIntent.get("restaurant_name");
            TextView restaurantTitle= (TextView)findViewById(R.id.title);
            restaurantTitle.setText(restaurant_name);
        }

        final TextView progressRate=(TextView)findViewById(R.id.progress);
        double progressDouble=0.0;

        //Handle seek bar changes to display current rating
        rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress=rate.getProgress();
                progressRate.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Button handle to put result (rating) into the result intent and send back to MainActivity
    public void goBack(View v)
    {
        int value= rate.getProgress();
        String restaurantRating=String.valueOf(value);
        rateIntent.putExtra("restaurant_rating",restaurantRating);
        setResult(RESULT_OK,rateIntent);
        finish();
    }

}
