package nerd_is.in.number_converter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * Created by Nerd on 2014/4/25 0025.
 */
public class EditTextWatcher implements TextWatcher {
    private static final String TAG = "EditTextWatcher";
    private static final String DECIMAL_REGEX = "^(-)?(([1-9]\\d*|0)(\\.(\\d)+)?)?$";
    private static final String BINARY_REGEX = "^([01])*$";
    private static final String HEX_REGEX = "^[\\da-fA-f]*$";
    private static final String INTEGER_REGEX = "^(\\d)*$";
    private int mode = 0;
    private Pattern mPattern;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged;mode=" + mode + ";Editable=" + s);

        String str = s.toString();
        switch (mode) {
            case MainActivity.DECIMAL:
            case MainActivity.DOUBLE:
            case MainActivity.FLOAT:
                mPattern = Pattern.compile(DECIMAL_REGEX);
                if (existsSecondDot(str) && !mPattern.matcher(s).matches()) {
                    s = s.delete(s.length() - 1, s.length());
                }
                break;
            case MainActivity.BINARY:
                mPattern = Pattern.compile(BINARY_REGEX);
                if (!mPattern.matcher(s).matches()) {
                    s = s.delete(s.length() - 1, s.length());
                }
                break;
            case MainActivity.HEX:
                mPattern = Pattern.compile(HEX_REGEX);
                if (!mPattern.matcher(s).matches()) {
                    s = s.delete(s.length() - 1, s.length());
                }
                break;
            case MainActivity.INT:
            case MainActivity.SHORT:
                mPattern = Pattern.compile(INTEGER_REGEX);
                if (!mPattern.matcher(s).matches()) {
                    s = s.delete(s.length() - 1, s.length());
                }
                break;
            default:
                break;
        }
    }

    private boolean existsSecondDot(String str) {
        return !str.endsWith(".") // while end with dot, the number is not input completely, so do not delete this dot
                || str.indexOf(".") != str.lastIndexOf("."); // the second dot appears, delete it
    }

    public void setMode(int mMode) {
        this.mode = mMode;
    }
}
