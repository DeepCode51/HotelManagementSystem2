package com.hotel.system.controller.admin;

import com.hotel.system.controller.common.BaseController;
import com.hotel.system.util.FileUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *     后台附件控制器
 * </pre>
 *
 * @author : wpx
 * @date : 2022/04/19
 */
@Controller
@RequestMapping(value = "/admin/file")
public class AttachmentController extends BaseController {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AttachmentController.class);

    /**
     * 上传文件
     *
     * @param file file
     * @return Map
     */
    @PostMapping(value = "/upload", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>(1);
        String path = FileUtil.upload(file);
        map.put("link", path);
        return map;
    }


}
