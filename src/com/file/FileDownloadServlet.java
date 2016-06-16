package com.file;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.*;

/**
 * Created by AlienZHOU on 2016/6/13.
 */
@WebFilter(urlPatterns = {"/download"}, filterName = "download", asyncSupported = true)
public class FileDownloadServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpServletResponse resp = (HttpServletResponse) response;
        String fn = request.getParameter("fname");
        String number = request.getParameter("number");
        if (number.isEmpty() || !number.equals("didids")) {
            return;
        }
        try {
            //String path = "/home/xiaoju/sync/" + fn;
            String path = "E:/file" + fn;
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            resp.reset();
            // 设置response的Header
            resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            resp.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(resp.getOutputStream());
            resp.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

}
