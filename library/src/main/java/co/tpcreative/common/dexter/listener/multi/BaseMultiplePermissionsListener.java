
package co.tpcreative.common.dexter.listener.multi;
import co.tpcreative.common.dexter.MultiplePermissionsReport;
import co.tpcreative.common.dexter.PermissionToken;
import  co.tpcreative.common.dexter.listener.PermissionRequest;
import java.util.List;

/**
 * Base implementation of {@link MultiplePermissionsListener} to allow extensions to implement
 * only the required methods
 */
public class BaseMultiplePermissionsListener implements MultiplePermissionsListener {

  @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

  }

  @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
      PermissionToken token) {
    token.continuePermissionRequest();
  }
}
