
package co.tpcreative.common.dexter;
import co.tpcreative.common.dexter.listener.PermissionDeniedResponse;
import co.tpcreative.common.dexter.listener.PermissionGrantedResponse;
import co.tpcreative.common.dexter.listener.PermissionRequest;
import co.tpcreative.common.dexter.listener.multi.MultiplePermissionsListener;
import co.tpcreative.common.dexter.listener.single.PermissionListener;
import java.util.List;

/**
 * Adapter to translate calls to a {@link MultiplePermissionsListener} into @{PermissionListener}
 * methods
 */
final class MultiplePermissionsListenerToPermissionListenerAdapter
    implements MultiplePermissionsListener {

  private final PermissionListener listener;

  MultiplePermissionsListenerToPermissionListenerAdapter(PermissionListener listener) {
    this.listener = listener;
  }

  @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
    List<PermissionDeniedResponse> deniedResponses = report.getDeniedPermissionResponses();
    List<PermissionGrantedResponse> grantedResponses = report.getGrantedPermissionResponses();

    if (!deniedResponses.isEmpty()) {
      PermissionDeniedResponse response = deniedResponses.get(0);
      listener.onPermissionDenied(response);
    } else {
      PermissionGrantedResponse response = grantedResponses.get(0);
      listener.onPermissionGranted(response);
    }
  }

  @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> requests,
      PermissionToken token) {
    PermissionRequest firstRequest = requests.get(0);
    listener.onPermissionRationaleShouldBeShown(firstRequest, token);
  }
}
