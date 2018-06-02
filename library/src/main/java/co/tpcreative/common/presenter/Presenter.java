package co.tpcreative.common.presenter;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class Presenter<V> {

    @Nullable
    private volatile V view;
    protected CompositeSubscription subscriptions;
    protected Subscription observable;
    @CallSuper
    public void bindView(@NonNull V view) {
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    @Nullable
    protected V view() {
        return view;
    }

    @Nullable
    public void setView(V view) {
       this.view = view;
    }

    @CallSuper
    private void unbindView(@NonNull V view) {
        if (subscriptions != null) {
            if (subscriptions.isUnsubscribed()) {
                subscriptions.unsubscribe();
            }
            if (subscriptions.hasSubscriptions()) {
                subscriptions.clear();
            }
            subscriptions = null;
        }

        this.view = null;
    }

    @CallSuper
    public void unbindView() {
        unbindView(view);
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }
    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super(
                    "Please call Presenter.attachView(MvpView) before"
                            + " requesting data to the Presenter");
        }
    }

    protected void initRxJavaDelay() {
        observable = Observable.range(1, 10)
                .concatMap(i-> Observable.just(i).delay(6000, TimeUnit.MILLISECONDS))
                .doOnNext(i->{

                    /*Do something here*/

                })
                .toCompletable().subscribe();
    }


    protected void initRxJavaLoader(){
        observable = Observable.unsafeCreate(subscriber -> {
            /*Do something here*/
            subscriber.onNext(true);
            subscriber.onCompleted();
        }).observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .subscribe(response -> {

                });
    }


}

