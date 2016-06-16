package com.file;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Created by AlienZHOU on 2016/6/13.
 */
@WebServlet(asyncSupported = true, name = "upload", urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 50 * 1024 * 1024, maxFileSize = 1024 * 1024 * 1024, maxRequestSize = 1024 * 1024 * 1024)
public class FileUploadServlet extends HttpServlet {

    /**
     * 文件存储
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String number = request.getParameter("number");
        if (number.isEmpty() || !number.equals("didids")) {
            PrintWriter out = response.getWriter();
            out.println("密码错误");
            out.flush();
            out.close();
            return;
        }
        //存储路径
        //String savePath = request.getServletContext().getRealPath("/WEB-INF/uploadFile");
        //String savePath = "/home/xiaoju/sync/";
        String savePath = "E:/file";
        //获取上传的文件集合
        Collection<Part> parts = request.getParts();

        if (parts.size() == 2) {//上传单个文件，参数为2
            //通过Part对上传的文件进行操作
            Part part = request.getPart("file");
            //通过请求头解析文件名，请求头的格式：form-data; name="file"; filename="xxx.xx"
            System.out.println(part.getHeaderNames());
            String header = part.getHeader("content-disposition");
            String fileName = getFileName(header);
            //写到指定路径
            part.write(savePath + File.separator + fileName);
        } else {//上传多个文件，暂时没有用到
            int i = 0;
            for (Part part : parts) {
                i++;
                if (i == 1) {//排除第一个密码参数
                    continue;
                }
                String header = part.getHeader("content-disposition");
                String fileName = getFileName(header);
                part.write(savePath + File.separator + fileName);
            }
        }
        PrintWriter out = response.getWriter();
        out.println("上传成功");
        out.flush();
        out.close();
    }

    /**
     * 根据请求头解析出文件名
     * 请求头的格式：火狐和google浏览器下：form-data; name="file"; filename="xxx.xx"
     * IE浏览器下：form-data; name="file"; filename="E:\xxx.xx"
     * 兼容不同浏览器
     *
     * @param header 请求头
     * @return 文件名
     */
    public String getFileName(String header) {
        String[] tempArr1 = header.split(";");
        String[] tempArr2 = tempArr1[2].split("=");
        //获取文件名，兼容各种浏览器的写法
        String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
        return fileName;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
