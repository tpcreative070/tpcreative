

package co.tpcreative.common.dexter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.WindowManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public final class DexterActivity extends Activity
    implements ActivityCompat.OnRequestPermissionsResultCallback {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dexter.onActivityReady(this);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Dexter.onActivityDestroyed();
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Dexter.onActivityReady(this);
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    Collection<String> grantedPermissions = new LinkedList<>();
    Collection<String> deniedPermissions = new LinkedList<>();

    if (isTargetSdkUnderAndroidM()) {
      deniedPermissions.addAll(Arrays.asList(permissions));
    } else {
      for (int i = 0; i < permissions.length; i++) {
        String permission = permissions[i];
        switch (grantResults[i]) {
          case PermissionChecker.PERMISSION_DENIED:
          case PermissionChecker.PERMISSION_DENIED_APP_OP:
            deniedPermissions.add(permission);
            break;
          case PermissionChecker.PERMISSION_GRANTED:
            grantedPermissions.add(permission);
            break;
          default:
        }
      }
    }

    Dexter.onPermissionsRequested(grantedPermissions, deniedPermissions);
  }

  private boolean isTargetSdkUnderAndroidM() {
    try {
      final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
      int targetSdkVersion = info.applicationInfo.targetSdkVersion;
      return targetSdkVersion < Build.VERSION_CODES.M;
    } catch (PackageManager.NameNotFoundException ignored) {
      return false;
    }
  }
}
