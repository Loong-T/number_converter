package nerd_is.in.number_converter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nerd on 2014/4/22 0022.
 */
public class ResultAdapter extends BaseAdapter {

    private static final String TAG = "ResultAdapter";

    private String[] mResultTitles;
    private ArrayList<String> mResults;
    private LayoutInflater mInflater;
    private ClipboardManager mClipboardManager;
    private Context mContext;
    private int mVersionCode;

    public ResultAdapter(Context context, String[] titles) {
        Log.d(TAG, "constructor");

        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mVersionCode = android.os.Build.VERSION.SDK_INT;
        if (mVersionCode >= Build.VERSION_CODES.HONEYCOMB) {
            mClipboardManager = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        else {
            mClipboardManager = (android.text.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
        }

        mResultTitles = titles;
        mResults = new ArrayList<String>(titles.length);
        String resultZero = getResString(R.string.result_zero);
        for (int i = 0; i < titles.length; ++i) {
            mResults.add(i, resultZero);
        }
    }

    private String getResString(int stringId) {
        return mContext.getString(stringId);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView:on position " + position);
        final ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView:convertView == null");
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.result_item, null);
            holder.tvItemTitle = (TextView) convertView.findViewById(R.id.item_title);
            holder.tvItemResult = (TextView) convertView.findViewById(R.id.item_result);
            holder.btnItemCopy = (Button) convertView.findViewById(R.id.item_copy_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItemTitle.setText(mResultTitles[position]);
        holder.tvItemResult.setText(mResults.get(position));
        holder.btnItemCopy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                CharSequence copyText = holder.tvItemResult.getText();
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    mClipboardManager.setText(copyText);
                } else {
                    ClipData clipData = ClipData.newPlainText(mResultTitles[position]
                            + getResString(R.string.clip_data_label), copyText);
                    ((android.content.ClipboardManager) mClipboardManager)
                            .setPrimaryClip(clipData);
                }

                Toast.makeText(mContext, mResultTitles[position] + "结果已复制",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public void setResults(ArrayList<String> mResults) {
        this.mResults = mResults;
    }

    public ArrayList<String> getResults() {
        return mResults;
    }

    static class ViewHolder {
        TextView tvItemTitle;
        TextView tvItemResult;
        TextView btnItemCopy;
    }
}
