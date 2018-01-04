package com.xwb.wb.enity.request;


import java.util.HashMap;
import java.util.Map;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */

public class WBRequest {

    private String path;
    private Map<String, Object> query;
    private Map<String, Object> form;
    private Map<String, Object> header;
    private Object body;

    private WBRequest() {
        query = new HashMap<>();
        form = new HashMap<>();
        header = new HashMap<>();
    }


    public Object getBody() {
        return body;
    }

    public Map<String, Object> getForm() {
        return form;
    }

    public Map<String, Object> getQuery() {
//        query.put("appkey", "");
//        query.put("token", "");
//        query.put("sign", "");
        return query;
    }

    public String getPath() {
        return path;
    }


    public String getFullPath() {
        return "";
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public static class Builder {

        private String path;
        private Map<String, Object> query;
        private Map<String, Object> form;
        private Map<String, Object> header;
        private Object body;

        public Builder() {
            query = new HashMap<>();
            form = new HashMap<>();
            header = new HashMap<>();
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder query(String key, Object value) {
            query.put(key, value);
            return this;
        }

        public Builder form(String key, Object value) {
            form.put(key, value);
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }


        public WBRequest buid() {
            WBRequest request = new WBRequest();
            request.path = path;
            request.form = form;
            request.query = query;
            request.body = body;
            request.header = header;
            return request;
        }
    }
}
