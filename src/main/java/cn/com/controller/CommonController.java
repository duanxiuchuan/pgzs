package cn.com.controller;

import cn.com.annotation.Log;
import cn.com.common.constant.DataConstant;
import cn.com.common.constant.RedisConstant;
import cn.com.common.message.JsonResult;
import cn.com.common.redis.RedisService;
import cn.com.entity.admin.Area;
import cn.com.entity.base.Dict;
import cn.com.service.admin.AreaService;
import cn.com.service.admin.DictService;
import cn.com.utils.FileUtil;
import cn.com.utils.FileUtils;
import cn.com.utils.JsonListUtil;
import cn.com.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 公共接口
 *
 * @author LiDaDa
 */
@Controller
@RequestMapping("/admin/common")
public class CommonController {
	@Log
    private Logger log;
	@Autowired
    private AreaService areaService;
    @Resource
    private RedisService redisService;
    @Autowired
    private DictService dictService;

    /**
     * 获取省级数据
     */
    @ResponseBody
    @RequestMapping("provinceArea")
	public String provinceArea(String id) {
        List<Map> list = new ArrayList<>();
        Map<String, Object> map;
        String areaList = redisService.get(RedisConstant.REDIS_AREA + (id == null ? "area" : id));
        if (StringUtils.isNotEmpty(areaList)) {
            return areaList;
        }
        List<Area> areas = areaService.findArea(Area.class, id);
        for (Area area : areas) {
            map = new HashMap<>();
            map.put("key", area.getName());
            map.put("value", area.getId());
            list.add(map);
        }
        areaList = JSONObject.toJSON(list).toString();
        redisService.set(RedisConstant.REDIS_AREA + (id == null ? "area" : id), areaList);
        return areaList;
    }

    /***
     * 上传多个文件
     * @param files
     * @return
     */
    @RequestMapping(value = "/fileUploads")
    @ResponseBody
    public JsonResult fileUploads(@RequestParam("file") MultipartFile[] files){
        List<Map<String,Object>> data = new ArrayList<>();
        try {
            for (MultipartFile file : files ) {
                Map<String, Object> map = parseFile(file);
                data.add(map);
            }
            return JsonResult.success("文件上传成功",data);
        }catch (IOException e){
            log.error(e.getMessage()+"文件上传出错   《----------");
        }
        return JsonResult.error("文件上传失败",null);
    }

    private Map<String,Object> parseFile(MultipartFile file)throws IOException {
        Map<String,Object> map = new HashMap<>();
        // 获取文件名
        String fileRealName = file.getOriginalFilename();
        map.put("oldName",fileRealName);

        int pointIndex = fileRealName.lastIndexOf(".");
        // 截取文件后缀
        String fileSuffix = fileRealName.substring(pointIndex);

        String tempPath = getFilePath(fileSuffix);

        //生成文件的前缀包含连字符
        String fileId = UUID.randomUUID().toString().replace("-", "");
        String savedFileName = fileId.concat(fileSuffix);
        map.put("newName",savedFileName);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), "static"+tempPath);
        if (!upload.exists()) {
            upload.mkdirs();
        }

        File savedFile = new File(upload.getAbsolutePath(), savedFileName);
        boolean isCreateSuccess = savedFile.createNewFile();
        if (isCreateSuccess) {
            file.transferTo(savedFile);  //转存文件
        }
        map.put("path",tempPath+savedFileName);
        return map;
    }

    /***
     * 获取文件路径
     * @param suffix
     * @return
     */
    private static String getFilePath(String suffix) {
        if (StringUtils.isNotEmpty(suffix)) {
            if (".png".equals(suffix) || ".jpeg".equals(suffix) || ".jpg".equals(suffix) || ".gif".equals(suffix)) {
                return FileUtils.IMAGE_PATH;
            }
            else  if (".arm".equals(suffix) || ".mp3".equals(suffix)) {
                return FileUtils.VOICE_PATH;
            }
            else  if (".mp4".equals(suffix)) {
                return FileUtils.VEDIO_PATH;
            }
        }
        return FileUtils.DEFAULT_PATH;
    }
    /**
     * 富文本编辑的上传图片
     * {
     *   "code": 0 //0表示成功，其它失败
     *   ,"msg": "" //提示信息 //一般上传失败后返回
     *   ,"data": {
     *     "src": "图片路径"
     *     ,"title": "图片名称" //可选
     *   }
     * }
     */
    @RequestMapping(value = "/richImg")
    @ResponseBody
    public Map richImg(@RequestParam("file") MultipartFile[] files){
        Map<String, Object> map = new HashMap<>();
        try {
            for (MultipartFile file : files ) {
                map = parseFile(file);
            }
            Map data = new HashMap();
            data.put("src",map.get("path"));
            data.put("title",map.get("oldName"));
            map.clear();
            map.put("code",DataConstant.CHAR_ZERO);
            map.put("msg","上传成功");
            map.put("data",data);
        }catch (IOException e){
            log.error(e.getMessage()+"文件上传出错   《----------");
        }
        return map;
    }
    /***
     * @Author: Li Sir
     * @Date: 2019/1/16 15:45
     * @Description:  文件下载
     */
    @RequestMapping("/downFile")
    public String downFile(String path, HttpServletResponse response, HttpServletRequest request) throws Exception{

                // 类加载器获取文件流
                File file = new File(path);
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("static"+path);
                // 获取文件名
                String name = file.getName();
                System.out.println(file.getAbsolutePath());
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                BufferedInputStream bis = null;
        try {
                    bis = new BufferedInputStream(in);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download  successfully!");
            }
            catch (Exception e) {
                System.out.println("Download  failed!");
            }
            finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
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
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("static" + path);
        FileUtil.copy(input, response.getOutputStream());
        return null;
    }
    @RequestMapping("/getDictListByType.do")
    @ResponseBody
    public JsonResult getDictListByType(@RequestParam(name="dictType") String dictType) {

             List<Dict> list = null;
        try {
            String dictList = redisService.get(dictType);
            log.info("根据类型获取字典列表："+dictType);
            if (StringUtils.isNotEmpty(dictList)) {
                list = JsonListUtil.jsonToList(dictList,Dict.class);
            } else {
                list = dictService.findByDicType(dictType);
                redisService.set(dictType, JSONObject.toJSON(list).toString());
            }

        } catch (Exception e){
            e.printStackTrace();
            log.error("获取字典异常："+e.getMessage());
            return JsonResult.error("error:"+e.getMessage(),null);
        }

        return JsonResult.success("success",list);
    }
}
