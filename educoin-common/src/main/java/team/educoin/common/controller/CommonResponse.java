package team.educoin.common.controller;

/**
 * description:
 * @author: Messi-Q
 * @create: 2019-05-27
 */
public class CommonResponse {
    private int status;
    private String message;
    private Object data;

    public CommonResponse() {
    }

    public CommonResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
