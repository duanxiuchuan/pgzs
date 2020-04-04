package cn.com.controller;

import cn.com.annotation.Log;
import cn.com.common.file.FileItem;
import cn.com.service.admin.FileService;
import cn.com.utils.FileUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/admin/file")
public class FileSystemContorller {

    @Log
    private Logger logger;

    @Autowired
    private FileService fileService;

    /**
     * execl 导入导出
     */
    @GetMapping("download")
    public ModelAndView index(HttpServletResponse response, String id) throws IOException {
        String path = id;
        response.setContentType("text/html; charset = UTF-8");
        FileItem fileItem = fileService.loadFileItemByPath(path);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileItem.getName(), "UTF-8"));
        fileItem.copy(response.getOutputStream());
        if (fileItem.isTemp()) {
            fileItem.delete();
        }
        return null;
    }

    @GetMapping("downloadTemplate")
    public ModelAndView dowloadTemplate(HttpServletResponse response, String path) throws IOException {
        response.setContentType("text/html; charset = UTF-8");
        int start1 = path.lastIndexOf("\\");
        int start2 = path.lastIndexOf("/");
        if (start2 > start1) {
            start1 = start2;
        }
        String file = path.substring(start1 + 1);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file, "UTF-8"));
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("excelTemplates/" + path);
        FileUtil.copy(input, response.getOutputStream());
        return null;
    }

    @GetMapping("simpleUpload")
    public String simpleUploadPage(HttpServletRequest request, String uploadUrl, String templatePath, String fileType) throws IOException {
        request.setAttribute("uploadUrl", uploadUrl);
        request.setAttribute("templatePath", templatePath);
        request.setAttribute("fileType", fileType);
        return "admin/common/simpleUpload";
    }
}
