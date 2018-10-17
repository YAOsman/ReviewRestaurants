package uoit.ca.reviewrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        //Set Table settings
        TableLayout restaurant_table=(TableLayout) findViewById(R.id.mainTable);
        TableRow row=new TableRow(this);
        TableRow row2=new TableRow(this);
        TextView data1= new TextView(this);
        TextView data2=new TextView(this);

        //Get data from intent
        Bundle data=getIntent().getExtras();
        int highCount=(int)data.get("high_restaurants");
        int lowCount=(int)data.get("low_restaurants");

        //Display on Table layout
        //TODO: Change text font to be more readable
        data1.setText("Low Restaurants");
        data2.setText(" - "+Integer.valueOf(lowCount));
        row.addView(data1,0);
        row.addView(data2,1);
        restaurant_table.addView(row,0);
        data1= new TextView(this);
        data2=new TextView(this);
        data1.setText("High Restaurants");
        data2.setText(" - "+Integer.valueOf(highCount));
        row2.addView(data1,0);
        row2.addView(data2,1);
        restaurant_table.addView(row2,1);
    }
}
