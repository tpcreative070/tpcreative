
package co.tpcreative.common.dexter;

final class PermissionRationaleToken implements PermissionToken {

  private final DexterInstance dexterInstance;
  private boolean isTokenResolved = false;

  PermissionRationaleToken(DexterInstance dexterInstance) {
    this.dexterInstance = dexterInstance;
  }

  @Override public void continuePermissionRequest() {
    if (!isTokenResolved) {
      dexterInstance.onContinuePermissionRequest();
      isTokenResolved = true;
    }
  }

  @Override public void cancelPermissionRequest() {
    if (!isTokenResolved) {
      dexterInstance.onCancelPermissionRequest();
      isTokenResolved = true;
    }
  }
}
