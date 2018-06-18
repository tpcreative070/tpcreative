

package co.tpcreative.common.dexter.listener;

public enum DexterError {
  /**
   * Error code used when the user tries to request permissions before all previous
   * requests has finished.
   */
  REQUEST_ONGOING,

  /**
   * Error code used when Dexter is called with no permissions.
   */
  NO_PERMISSIONS_REQUESTED
}
