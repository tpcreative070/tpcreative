
package co.tpcreative.common.dexter;

import co.tpcreative.common.dexter.listener.PermissionRequestErrorListener;
import co.tpcreative.common.dexter.listener.multi.MultiplePermissionsListener;
import co.tpcreative.common.dexter.listener.single.PermissionListener;
import java.util.Collection;

public interface DexterBuilder {

  DexterBuilder onSameThread();

  DexterBuilder withErrorListener(PermissionRequestErrorListener errorListener);

  void check();

  interface Permission {
    SinglePermissionListener withPermission(String permission);

    MultiPermissionListener withPermissions(String... permissions);

    MultiPermissionListener withPermissions(Collection<String> permissions);
  }

  interface SinglePermissionListener {
    DexterBuilder withListener(PermissionListener listener);
  }

  interface MultiPermissionListener {
    DexterBuilder withListener(MultiplePermissionsListener listener);
  }
}