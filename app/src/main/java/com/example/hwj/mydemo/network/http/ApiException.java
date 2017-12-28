package com.example.hwj.mydemo.network.http;

public class ApiException extends RuntimeException {

    public String getErrorMessage () {
        return errorMessage;
    }

    private String errorMessage;
    public int err_code = -1;

    private String url = "";
    private int stutusCode = -1;
    private String requestMethod = "";
    private String requestHeader = "";
    private String originalResponse = "";
    private Long responseTimestamp = 0L;

    public int getErr_code () {
        return err_code;
    }

    public void setErr_code (int err_code) {
        this.err_code = err_code;
    }

    public static final int USER_NOT_EXIST = 105;
    public static final int WRONG_PASSWORD = 106;
    //自定义状态码
    public static final int SOCKET_TIMEOUT_EXCEPTION = 100;
    public static final int CONNECT_EXCEPTION = 101;
    public static final int RUNTIME_EXCEPTION = 102;
    public static final int UNKNOWN_HOST_EXCEPTION = 103;
    public static final int UNKNOWN_SERVICE_EXCEPTION = 104;
    public static final int GET_INFOLIST_ERR = 107;

    public ApiException () {

    }

    public ApiException (int resultCode) {
        this(getApiExceptionMessage(resultCode));
        this.err_code = resultCode;
    }

    public ApiException (String detailMessage) {
        super(detailMessage);
        this.errorMessage = detailMessage;
    }

    public void setErrorMessage (String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public int getStutusCode () {
        return stutusCode;
    }

    public void setStutusCode (int stutusCode) {
        this.stutusCode = stutusCode;
    }

    public String getRequestMethod () {
        return requestMethod;
    }

    public void setRequestMethod (String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestHeader () {
        return requestHeader;
    }

    public void setRequestHeader (String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getOriginalResponse () {
        return originalResponse;
    }

    public void setOriginalResponse (String originalResponse) {
        this.originalResponse = originalResponse;
    }

    public Long getResponseTimestamp () {
        return responseTimestamp;
    }

    public void setResponseTimestamp (Long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    /**
     * @param code
     * @return
     */
    private static String getApiExceptionMessage (int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case SOCKET_TIMEOUT_EXCEPTION:
                message = "服务器响应超时";
                break;
            case CONNECT_EXCEPTION:
                message = "网络连接异常，请检查网络";
                break;
            case RUNTIME_EXCEPTION:
                message = "运行时错误";
                break;
            case UNKNOWN_HOST_EXCEPTION:
                message = "无法解析主机，请检查网络连接";
                break;
            case UNKNOWN_SERVICE_EXCEPTION:
                message = "未知的服务器错误";
                break;
            case GET_INFOLIST_ERR:
                message = "获取用户帖子信息失败";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }


}

