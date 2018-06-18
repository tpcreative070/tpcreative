
package co.tpcreative.common.dexter;

import co.tpcreative.common.dexter.listener.DexterError;

final class DexterException extends IllegalStateException {

  final DexterError error;

  DexterException(String detailMessage, DexterError error) {
    super(detailMessage);
    this.error = error;
  }
}
