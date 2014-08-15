package nerd_is.in.number_converter;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nerd on 2014/4/25 0025.
 */
public class ConvertClickListener implements View.OnClickListener {
    private MainActivity mContext;
    private int mType;
    private EditText mEditText;
    private ResultAdapter mAdapter;
    private ArrayList<String> mResult;
    private String mBinaryString;

    public ConvertClickListener(MainActivity context) {
        mContext = context;
        mEditText = context.getEditText();
        mAdapter = context.getResultAdapter();
    }

    @Override
    public void onClick(View v) {
        // hide the input method
        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

        try {
            mType = mContext.getSelectType();
            String input = mEditText.getText().toString();
            switch (mType) {
                case MainActivity.DECIMAL:
                case MainActivity.DOUBLE:
                    Double n = Double.parseDouble(input);
                    mBinaryString = Long.toBinaryString(Double.doubleToLongBits(n));
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.DECIMAL, n.toString());
                    mResult.set(MainActivity.DOUBLE, n.toString());
                    break;
                case MainActivity.BINARY:
                    mBinaryString = input;
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.BINARY, mBinaryString);
                    break;
                case MainActivity.HEX:
                    Long longBits = Long.parseLong(input, 16);
                    mBinaryString = Long.toBinaryString(longBits);
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.HEX, input);
                    break;
                case MainActivity.SHORT:
                    Short s = Short.parseShort(input);
                    mBinaryString = Integer.toBinaryString(0xff & s);
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.SHORT, s.toString());
                    break;
                case MainActivity.INT:
                    Integer i = Integer.parseInt(input);
                    mBinaryString = Integer.toBinaryString(i);
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.INT, i.toString());
                    break;
                case MainActivity.FLOAT:
                    Float f = Float.parseFloat(input);
                    mBinaryString = Integer.toBinaryString(Float.floatToRawIntBits(f));
                    mResult = Utils.binary2AllTypes(mBinaryString);
                    mResult.set(MainActivity.FLOAT, f.toString());
                    break;
            }

            mAdapter.setResults(mResult);
            mAdapter.notifyDataSetChanged();
        } catch (NumberFormatException e) {
            Toast.makeText(mContext, mContext.getString(R.string.num_parse_error), Toast.LENGTH_LONG)
                    .show();
        }
    }
}
