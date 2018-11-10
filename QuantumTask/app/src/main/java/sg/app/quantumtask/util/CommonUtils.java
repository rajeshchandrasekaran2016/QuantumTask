package sg.app.quantumtask.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.app.quantumtask.R;


public class CommonUtils {

    private CommonUtils() {
    }

    public static void showErrorSnackBarView(Context context, View parentView, String msg) {
        try {
            if (parentView != null && context != null) {
                Snackbar snackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG);
                View custom = LayoutInflater.from(context).inflate(R.layout.view_error_snackbar, null);
                snackbar.getView().setPadding(0, 0, 0, 0);
                ((ViewGroup) snackbar.getView()).removeAllViews();
                ((ViewGroup) snackbar.getView()).addView(custom);
                TextView errorMsg = custom.findViewById(R.id.tv_snackbar_error_msg);
                errorMsg.setText(msg);
                snackbar.show();
            }
        } catch (Exception e) {
        }
    }
}



