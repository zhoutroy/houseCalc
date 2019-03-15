package com.ggiyy.housecalc.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsHelper {
    /**
     * 添加跨域的头信息
     * @param request
     * @param response
     */
    public static void buildCorsResponse(HttpServletRequest request, HttpServletResponse response) {
        String requestOrigin = request.getHeader("origin");
        if (requestOrigin != null) {
            response.addHeader("Access-Control-Allow-Origin", requestOrigin);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setContentType("text/plain;charset=UTF-8");
        }
    }
}
