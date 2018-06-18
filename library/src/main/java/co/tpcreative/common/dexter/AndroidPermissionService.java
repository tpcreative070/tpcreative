
package co.tpcreative.common.dexter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Wrapper class for all the static calls to the Android permission system
 */
class AndroidPermissionService {

  /**
   * @see PermissionChecker#checkSelfPermission
   */
  int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
    return PermissionChecker.checkSelfPermission(context, permission);
  }

  /**
   * @see ActivityCompat#requestPermissions
   */
  void requestPermissions(@Nullable Activity activity, @NonNull String[] permissions,
      int requestCode) {
    if (activity == null) {
      return;
    }

    ActivityCompat.requestPermissions(activity, permissions, requestCode);
  }

  /**
   * @see ActivityCompat#shouldShowRequestPermissionRationale
   */
  boolean shouldShowRequestPermissionRationale(@Nullable Activity activity,
      @NonNull String permission) {
    if (activity == null) {
      return false;
    }

    return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
  }
}
