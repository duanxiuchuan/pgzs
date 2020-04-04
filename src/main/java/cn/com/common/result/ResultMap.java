package cn.com.common.result;

import java.util.List;

/**
 * 消息封装
 *
 * @author LiDaDa
 */
public class ResultMap<T> {
    private String msg = "";
    private List<T> data;
    private int code = 0;
    private Long count;

    public ResultMap() {
    }

    public ResultMap(List<T> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public ResultMap(String msg, List<T> data, int code, Long count) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
