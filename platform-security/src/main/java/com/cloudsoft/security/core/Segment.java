package com.cloudsoft.security.core;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhujianrong
 * 分割类
 */
public class Segment {
    private String[] pathArr;

    public Segment(HttpServletRequest request) {
        if (request != null) {
            String path = request.getServletPath();
            path = path.replaceFirst("/", "");
            pathArr = path.split("/");
        }

    }

    public String getSegment(int index) {
        //从1开始
        index = index - 1;
        if (pathArr == null || pathArr.length < index) {
            return null;
        }
        return pathArr[index];
    }

    @Override
    public String toString() {
        return String.join(",", pathArr);
    }
}
