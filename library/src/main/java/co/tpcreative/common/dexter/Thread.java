

package co.tpcreative.common.dexter;

/**
 * Abstraction around threads to execute passed runnable objects in a certain thread
 */
interface Thread {
  void execute(Runnable runnable);
  void loop();
}
