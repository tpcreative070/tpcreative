package co.tpcreative.model;
import android.util.Log;
import com.google.gson.Gson;
import java.util.List;
import co.tpcreative.common.controller.RealmController;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class TaskManager extends RealmObject {

    @PrimaryKey
    private  String key;
    private int status ;
    @Ignore
    static private TaskManager instance;
    @Ignore
    public static final String TAG = TaskManager.class.getSimpleName();

    public TaskManager(String key,int status){
        this.key = key;
        this.status = status;
    }

    public TaskManager(){
        this.key = null;
        this.status = 0;

    }

    public static TaskManager with(){
        if (instance == null){
            synchronized(TaskManager.class){
                if (instance == null){
                    instance = new TaskManager();//instance will be created at request time
                }
            }
        }
        return instance;
    }

    public synchronized void onInsert(TaskManager cTalkManager){
        try {
            if (cTalkManager.key==null){
                return;
            }
            RealmController realmController = RealmController.with();
            TaskManager mTalkManager = (TaskManager) realmController.insertObject(cTalkManager);
            Log.d(TAG,new Gson().toJson(mTalkManager));
        }
        catch (Exception e){

        }
    }

    public final synchronized boolean onDelete(String key,String value){
        try{
            RealmController realmController = RealmController.with();
            realmController.deleteItem(key,value,TaskManager.class);
            return true;
        }
        catch (Exception e){

        }
        return false;
    }

    public final synchronized TaskManager getContact(String key,int value){
        try{
            RealmController realmController = RealmController.with();
            return (TaskManager) realmController.getSearchObject(key,value,TaskManager.class);
        }
        catch (Exception e){

        }
        return null;
    }


    public final synchronized List<TaskManager> getList(){
        try{
            RealmController realmController = RealmController.with();
            return realmController.getALLObject(TaskManager.class);
        }
        catch (Exception e){

        }
        return null;
    }

}
