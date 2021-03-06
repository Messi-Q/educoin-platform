package team.educoin.common.controller;

/*
 * =============================================================
 * @Description 响应类
 * @Author Messi-q
 * @Date 19:27 2019-06-14
 * @Param 
 * @return  
 * =============================================================       
 **/
public class CommonResponse {
    private int status;
    private String message;
    private Object data;

    public CommonResponse() {
    }

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
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
