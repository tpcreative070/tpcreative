

package co.tpcreative.common.dexter;

import android.content.Context;
import android.content.Intent;

class IntentProvider {
  public Intent get(Context context, Class<?> clazz) {
    return new Intent(context, clazz);
  }
}
