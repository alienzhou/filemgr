package com.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by zhouhongxuan on 2016/6/20.
 */
public class FileManger extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
        switch (action) {
            case "check":
                try {
                    this.check(request, response);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
                break;
            case "upload":
                this.upload(request, response);
                break;
            case "download":
                this.download(request, response);
                break;
            case "progress":
                try {
                    this.progress(request, response);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                write("bad request", response);
        }
    }

    private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //为解析器提供一些缺省的配置
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建一个解析器，该解析使用factory提供的配置
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setProgressListener(new MyPreogressListener(request));//自定义监听，传入request
        String msg = "";
        try {
            List<FileItem> fileItems = sfu.parseRequest(request);
            int len = fileItems.size();
            String number = "";
            for (int i = 0; i < len; i++) {
                FileItem item = fileItems.get(i);
                if (item.isFormField() && item.getFieldName().equals("number")) {
                    number = item.getString();
                    break;
                }
            }
            if (!this.passJudge(number)) {
                msg = "密码错误";
            } else {
                String savePath = "E:/file/";
                for (int i = 0; i < len; i++) {
                    FileItem item = fileItems.get(i);
                    if (!item.isFormField()) {//不操作普通表单域：只操作input type="file"的表单
                        String fileName = item.getName();
                        String filePath = savePath + fileName;
                        File file = new File(filePath);
                        item.write(file);
                        msg = fileName + " upload success!";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "exception: error in file uploading.";
        } finally {
            write(msg, response);
        }
    }

    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String number = request.getParameter("number");
        String fn = request.getParameter("fname");
        String msg = "";
        if (!this.passJudge(number)) {
            msg = "密码错误";
        }
        try {
            String filepath = "E:/file/" + fn;
            File file = new File(filepath);
            // 取得文件名。
            String filename = file.getName();
            InputStream fileStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fileStream.available()];
            fileStream.read(buffer);
            fileStream.close();

            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            msg = "exception: error in downloading.";
        } finally {
            write(msg, response);
        }
    }

    private void progress(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        HttpSession session = request.getSession();
        String percent = session.getAttribute("percent").toString();
        write(percent, response);
    }

    private void check(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {
        String msg = "";
        String number = request.getParameter("number");
        if (!this.passJudge(number)) {
            msg = "0";
        } else {
            msg = "1";
        }
        write(msg, response);
    }

    private boolean passJudge(String number) {
        if (number == null || !number.equals("didids")) {
            return false;
        }
        return true;
    }

    private void write(String msg, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(msg);
        out.flush();
        out.close();
    }
}
