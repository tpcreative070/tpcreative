package co.tpcreative.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmController <T extends RealmObject> {
    private static RealmController instance;
    private static RealmController mInstance ;

    private Realm realm = Realm.getDefaultInstance() ;
    public static final String TAG = RealmController.class.getSimpleName();
    private RealmControllerListener realmControllerListener;
    private List<T> mList;
    private T object ;
    private T objectQuery;
    private List<T> allObject ;

    public RealmController(){

    }

    public Realm getRealm() {
        if (realm==null){
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public static RealmController withDependencies(RealmControllerListener listener){
        if (mInstance==null){
            mInstance = new RealmController();
        }
        mInstance.realmControllerListener = listener;
        return mInstance;
    }



    public static RealmController with(RealmControllerListener listener) {
        if (instance == null) {
            instance = new RealmController();
        }
        instance.realmControllerListener = listener;
        return instance;
    }

    public static RealmController with() {
        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }

    public void refresh() {
        realm.refresh();
    }

    public void clearAll(final Class<T> tClass, final int code) {
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(tClass);
                    realmControllerListener.onRealmDeleted(true, code);
                }
            });
        }
        finally {
            realm.close();
        }
    }

    public boolean clearAll(final Class<T> tClass) {
        realm = null;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.delete(tClass);
            return true;
        }
        finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void getALLObject(final Class<T> tClass, final int code) {
        realm = null;
        realm = Realm.getDefaultInstance();
        allObject = new ArrayList<>();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    allObject = realm.where(tClass).findAll();

                }
            });
        }
        finally {
            realmControllerListener.onShowRealmList(allObject, code);
            realm.close();
        }
    }

    public T getLatestObject(final Class<T> tClass, String key){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob = realm.where(tClass).findAllSorted(key,Sort.DESCENDING).where().findFirst();
            if (ob!= null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }

    public List<T> getALLObject(final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            List<T> list =  realm.where(tClass).findAll();
            if (list!=null){
                return realm.copyFromRealm(list);
            }
            return list;
        }
        finally {
            realm.close();
        }
    }

    public void mInsertObject(final T protocol, final int code){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    object = realm.copyToRealmOrUpdate(protocol);
                }
            });
        }finally {
            realmControllerListener.onRealmInserted(object, code);
            realm.close();
        }
    }

    public T mInsertObject(final T protocol){
        realm = null;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            T ob =  realm.copyToRealmOrUpdate(protocol);
            if (ob != null){
                return  realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void mInsertList(final Class<T> tClass, final List<T> newsList, final int code) {
        realm = null;
        realm = Realm.getDefaultInstance();
        mList = new ArrayList<>();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(newsList);
                    mList = realm.where(tClass).findAll();
                }
            });
        }
        finally {
            realmControllerListener.onRealmInsertedList(mList, code);
            realm.close();
        }
    }

    public List<T> mInsertList(final Class<T> tClass, final List<T> newsList){
        realm = null;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.insertOrUpdate(newsList);
            List<T> list = realm.where(tClass).findAll();
            if (list!=null){
                return realm.copyFromRealm(list);
            }
            return list;
        }
        finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void mEditItem(final String key, final String value, final T object, final Class<T> tClass, final int code){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    T object = realm.where(tClass).equalTo(key, value).findFirst();
                    T result = realm.copyToRealmOrUpdate(object);
                    realmControllerListener.onRealmUpdated(result, code);
                }
            });
        }
        finally {
            realm.close();
        }
    }

    public T mEditItem(final String key, final String value, final Class<T> tClass){
        realm = null ;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            T object = realm.where(tClass).equalTo(key,value).findFirst();
            T result = realm.copyToRealmOrUpdate(object);
            if (result!=null){
                return realm.copyFromRealm(result);
            }
            return result;
        }
        finally {
            realm.commitTransaction();
            realm.close();
        }

    }

    public void mDeleteItem(final String key, final String value, final Class<T> tClass, final int code){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<T> result = realm.where(tClass).equalTo(key, value).findAll();
                    boolean check = result.deleteAllFromRealm();
                        if (check) {
                            realmControllerListener.onRealmDeleted(true, code);
                        } else {
                            realmControllerListener.onRealmDeleted(false, code);
                        }

                }
            });
        }finally {
            realm.close();
        }
    }

    public boolean mDeleteItem(final String key, final String value, final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<T> results = realm.where(tClass).equalTo(key,value).findAll();
                    results.deleteAllFromRealm();
                }
            });
        }
        finally {
            realm.close();
            return true;
        }
    }

    public void getSearchObject(final String key , final String value , final Class<T> tClass, final int code, final HashMap<String,String> hashMap) {
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                   objectQuery = realm.where(tClass).equalTo(key, value).findFirst();
                }
            });
        }
        finally {
            realmControllerListener.onShowRealmQueryItem(objectQuery, hashMap, code);
            realm.close();
        }
    }

    public T getSearchObject(final String key , final String value , final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob =  realm.where(tClass).equalTo(key, value).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }

    public T getSearchObject(final String key , final Boolean value , final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob =  realm.where(tClass).equalTo(key, value).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }


    public T getSearchObject(final String key , final int value , final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob =  realm.where(tClass).equalTo(key, value).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }

    public List<T> getSearchNearly(final String key , final String value , final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            List<T> ob = realm
                    .where(tClass)
                    .like(key, value)
                    .findAllAsync();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }


    public T getSearchObject(final String key , final byte value , final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob =  realm.where(tClass).equalTo(key, value).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;
        }
        finally {
            realm.close();
        }
    }


    public void getListFilter(final String key, final int value, final Class<T> tClass, final int code){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    List<T> list = realm.where(tClass).greaterThan(key, value).findAll();
                    realmControllerListener.onShowRealmList(list, code);
                }
            });
        }
        finally {
            realm.close();
        }
    }

    public List<T> getListFilter(final String key, final int value, final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            List<T> list = realm.where(tClass).equalTo(key, value).findAll();
            if (list!=null){
                return realm.copyFromRealm(list);
            }
            return list;
        }
        finally {
            realm.close();
        }
    }

    public List<T> getListFilter(final String key, final boolean value, final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            List<T> list = realm.where(tClass).equalTo(key, value).findAll();
            if (list!=null){
                return realm.copyFromRealm(list);
            }
            return list;
        }
        finally {
            realm.close();
        }
    }

    public void getFirstItem(final Class<T> tClass, final int code){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    T object = realm.where(tClass).findFirst();
                    realmControllerListener.onShowRealmObject(object, code);
                }
            });
        }finally {
            realm.close();
        }
    }

    public T getFirstItem(final Class<T> tClass){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob =  realm.where(tClass).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;

        }finally {
            realm.close();
        }
    }

    public void hasObject(final Class<T> tClass, final int code) {
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    T custom = realm.where(tClass).findFirst();
                    if (custom != null) {
                        realmControllerListener.onShowRealmCheck(true, code);
                    } else {
                        realmControllerListener.onShowRealmCheck(false, code);
                    }

                }
            });
        }
        finally {
            realm.close();
        }

    }

    public T hasObject(final Class<T> tClass) {
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob = realm.where(tClass).findFirst();
            if (ob!=null){
                return realm.copyFromRealm(ob);
            }
            return ob;

        }
        finally {
            realm.close();
        }
    }

    public interface RealmControllerListener<T>  {
        void onShowRealmObject(T object, int code);
        void onShowRealmList(List<T> list, int code);
        void onShowRealmCheck(boolean checked, int code);
        void onShowRealmQueryItem(T object, HashMap<String, String> hashMap, int code);
        void onRealmUpdated(T object, int code);
        void onRealmDeleted(boolean t, int code);
        void onRealmInserted(T object, int code);
        void onRealmInsertedList(List<T> list, int code);
    }

}
