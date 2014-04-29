package nerd_is.in.number_converter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final int DECIMAL = 0, BINARY = 1, HEX = 2,
            SHORT = 3, INT = 4, FLOAT = 5, DOUBLE = 6;
    private static final String TAG = "MainActivity";
    private int mSelectType = 0;
    private String[] mTypeStrings;
    private ResultAdapter mResultAdapter;
    private Spinner mTypeSpinner;
    private ListView mResultListView;
    private EditText mEditText;
    private EditTextWatcher mTextWatcher;
    private Button mConvertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mTextWatcher = new EditTextWatcher();
        setContentView(R.layout.activity_main);

        mTypeStrings = getResources().getStringArray(R.array.types_array);
        mTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_dropdown_item, mTypeStrings);
        mTypeSpinner.setAdapter(spinnerAdapter);
        mTypeSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener(spinnerAdapter));

        mResultListView = (ListView) findViewById(R.id.resultList);
        mResultAdapter = new ResultAdapter(this, mTypeStrings);
        mResultListView.setAdapter(mResultAdapter);

        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.addTextChangedListener(mTextWatcher);

        mConvertBtn = (Button) findViewById(R.id.convertBtn);
        mConvertBtn.setOnClickListener(new ConvertClickListener(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutDialogFragment dialogFragment = new AboutDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "about");
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
            Log.d(TAG, "onItemSelected:spinner:position " + position);
            mSelectType = position;
            mTextWatcher.setMode(mSelectType);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(MainActivity.this, "Do nothing",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public int getmSelectType() {
        return mSelectType;
    }

    public ResultAdapter getmResultAdapter() {
        return mResultAdapter;
    }

    public EditText getmEditText() {
        return mEditText;
    }
}
