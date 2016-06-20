package com.file;

import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

public class MyPreogressListener implements ProgressListener {
    private HttpSession session;
    private DecimalFormat df = new DecimalFormat("#00.0");

    public MyPreogressListener(HttpServletRequest request) {
        session = request.getSession();
        session.setAttribute("percent", 0);
    }

    public void update(long readBytes, long contentLength, int items) {
        double per = (double) readBytes * 100 / (double) contentLength;
        String percent = df.format(per);
        System.out.println("上传进度：" + percent);
        session.setAttribute("percent", percent);
    }
}
