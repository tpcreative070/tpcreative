
package co.tpcreative.common.dexter.listener.multi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import co.tpcreative.common.dexter.MultiplePermissionsReport;

/**
 * Utility listener that shows a {@link Dialog} with a minimum configuration when the user rejects
 * any of the requested permissions
 */
public class DialogOnAnyDeniedMultiplePermissionsListener extends BaseMultiplePermissionsListener {

  private final Context context;
  private final String title;
  private final String message;
  private final String positiveButtonText;
  private final Drawable icon;

  private DialogOnAnyDeniedMultiplePermissionsListener(Context context, String title,
      String message, String positiveButtonText, Drawable icon) {
    this.context = context;
    this.title = title;
    this.message = message;
    this.positiveButtonText = positiveButtonText;
    this.icon = icon;
  }

  @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
    super.onPermissionsChecked(report);

    if (!report.areAllPermissionsGranted()) {
      showDialog();
    }
  }

  private void showDialog() {
    new AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setIcon(icon)
        .show();
  }

  /**
   * Builder class to configure the displayed dialog.
   * Non set fields will be initialized to an empty string.
   */
  public static class Builder {
    private final Context context;
    private String title;
    private String message;
    private String buttonText;
    private Drawable icon;

    private Builder(Context context) {
      this.context = context;
    }

    public static Builder withContext(Context context) {
      return new Builder(context);
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withTitle(@StringRes int resId) {
      this.title = context.getString(resId);
      return this;
    }

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder withMessage(@StringRes int resId) {
      this.message = context.getString(resId);
      return this;
    }

    public Builder withButtonText(String buttonText) {
      this.buttonText = buttonText;
      return this;
    }

    public Builder withButtonText(@StringRes int resId) {
      this.buttonText = context.getString(resId);
      return this;
    }

    public Builder withIcon(Drawable icon) {
      this.icon = icon;
      return this;
    }

    public Builder withIcon(@DrawableRes int resId) {
      this.icon = context.getResources().getDrawable(resId);
      return this;
    }

    public DialogOnAnyDeniedMultiplePermissionsListener build() {
      String title = this.title == null ? "" : this.title;
      String message = this.message == null ? "" : this.message;
      String buttonText = this.buttonText == null ? "" : this.buttonText;
      return new DialogOnAnyDeniedMultiplePermissionsListener(context, title, message, buttonText, icon);
    }
  }
}
