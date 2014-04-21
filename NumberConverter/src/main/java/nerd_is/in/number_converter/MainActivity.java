package nerd_is.in.number_converter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private int mSelectType = 0;
    private String[] mTypeStrings;
    private String[] mResultViewIds = {"result_item_1", "result_item_2", "result_item_3",
            "result_item_4", "result_item_5", "result_item_6", "result_item_7", "result_item_8",
            "result_item_9"};
    private Spinner mTypeSpinner;
    private View[] mResultViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTypeStrings = getResources().getStringArray(R.array.types_array);
        mResultViews = new View[mResultViewIds.length];
        for (int i = 0; i < mResultViewIds.length; ++i) {
            mResultViews[i] = findViewById(
                    getResources().getIdentifier(mResultViewIds[i], "id", getPackageName()));
        }



        mTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_dropdown_item, mTypeStrings);
        mTypeSpinner.setAdapter(spinnerAdapter);
        mTypeSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener(spinnerAdapter));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private final ArrayAdapter<CharSequence> spinnerAdapter;

        public MyOnItemSelectedListener(ArrayAdapter<CharSequence> spinnerAdapter) {
            this.spinnerAdapter = spinnerAdapter;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mSelectType = position;
            Toast.makeText(MainActivity.this, spinnerAdapter.getItem(position),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(MainActivity.this, "Do nothing",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
