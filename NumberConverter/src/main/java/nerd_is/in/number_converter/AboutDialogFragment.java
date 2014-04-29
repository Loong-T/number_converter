package nerd_is.in.number_converter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Nerd on 2014/4/29 0029.
 */
public class AboutDialogFragment extends DialogFragment {
    private View mDialogView;
    private FragmentActivity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mDialogView = View.inflate(activity, R.layout.about_dialog, null);
        // this two hyperlinks DO NOT WORK!
        TextView tvAuthor = (TextView) mDialogView.findViewById(R.id.dialog_author);
        TextView tvSrc = (TextView) mDialogView.findViewById(R.id.dialog_src);
        tvAuthor.setText(Html.fromHtml(activity.getString(R.string.dialog_author)));
        tvAuthor.setMovementMethod(LinkMovementMethod.getInstance());
        tvSrc.setText(Html.fromHtml(activity.getString(R.string.dialog_src)));
        tvSrc.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setView(mDialogView)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AboutDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"talentloong@163.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "NumberConverter Feedback");
                        activity.startActivity(emailIntent);
                    }
                })
                .setTitle(R.string.about_dialog_title);

        return builder.create();
    }
}
