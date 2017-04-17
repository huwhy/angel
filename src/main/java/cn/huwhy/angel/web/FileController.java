package cn.huwhy.angel.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.huwhy.angel.common.Json;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Value("${common.uploadPath}")
    private String uploadPath;

    @RequestMapping("upload")
    @ResponseBody
    public Json upload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        File file = new File(uploadPath, uploadFile.getOriginalFilename());
        uploadFile.transferTo(file);
        return Json.SUCCESS().setData(File.separator + uploadFile.getOriginalFilename());
    }

    @RequestMapping(value = {"download/{filename:.+}", "{filename:.+}"})
    public void download(@PathVariable String filename,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        File file = new File(uploadPath, filename);
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (bis != null) {
                bis.close();
            }
        }

    }

}
