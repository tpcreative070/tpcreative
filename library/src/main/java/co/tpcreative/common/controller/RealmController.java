package co.tpcreative.common.controller;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmController<T extends RealmObject> {

    private static RealmController instance;
    private Realm realm = Realm.getDefaultInstance() ;
    public static final String TAG = RealmController.class.getSimpleName();
    public RealmController(){

    }

    public Realm getRealm() {
        if (realm==null){
            realm = Realm.getDefaultInstance();
        }
        return realm;
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

    public T getLatestObject(final Class<T> tClass, String key){
        realm = null;
        realm = Realm.getDefaultInstance();
        try {
            T ob = realm.where(tClass).findAllAsync().sort(key, Sort.DESCENDING).where().findFirst();
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

    public T insertObject(final T protocol){
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


    public List<T> insertList(final Class<T> tClass, final List<T> newsList){
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


    public T editItem(final String key, final String value, final Class<T> tClass){
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

    public boolean deleteItem(final String key, final String value, final Class<T> tClass){
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

}
