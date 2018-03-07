package first.spring.app.ajax;

public class AjaxRequestParameters {

    private long taskId;

    public AjaxRequestParameters() { }

    public AjaxRequestParameters(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
