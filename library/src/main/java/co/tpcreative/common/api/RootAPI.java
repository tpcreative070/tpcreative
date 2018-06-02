package co.tpcreative.common.api;
public interface RootAPI{
    String USER_REGISTER = "/task_manager/v1/register";
    String USER_LOGIN = "/task_manager/v1/login";
    String TASKS_ADD = "/task_manager/v1/tasks";
    String TASKS_GET_LIST = "/task_manager/v1/tasks";
    String TASKS_UPDATE = "/task_manager/v1/tasks/{id}";
    String TASKS_DELETE = "/task_manager/v1/tasks/{id}";
}
