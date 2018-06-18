
package co.tpcreative.common.dexter;

import android.os.Handler;
import android.os.Looper;



/**
 * A thread to execute passed runnable objects on a worker thread
 */
final class WorkerThread implements Thread {

  private final Handler handler;
  private boolean wasLooperNull = false;

  WorkerThread() {
    //Handle the case where the current thread has not called Lopper.prepare()
    if (Looper.myLooper() == null) {
      wasLooperNull = true;
      Looper.prepare();
    }
    handler = new Handler();
  }

  @Override public void execute(final Runnable runnable) {
    handler.post(runnable);
  }

  @Override public void loop() {
    //Handle the case where there is an already existing Looper in the current thread.
    if (wasLooperNull) {
      Looper.loop();
    }
  }
}
