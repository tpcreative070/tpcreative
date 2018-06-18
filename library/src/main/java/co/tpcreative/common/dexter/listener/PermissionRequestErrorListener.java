

package co.tpcreative.common.dexter.listener;

/**
 * Listener to be notified when a Dexter error occurs.
 */
public interface PermissionRequestErrorListener {
  /**
   * Method called whenever Dexter fails.
   */
  void onError(DexterError error);
}
