package com.hotel.system.controller.admin;

import com.hotel.system.common.constant.CommonConstant;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.User;
import com.hotel.system.service.UserService;
import com.hotel.system.util.Md5Util;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 后台用户管理控制器
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin/user")
public class ProfileController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    private UserService userService;

    /**
     * 获取用户信息并跳转
     *
     * @return 模板路径admin/admin_profile
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        //1.用户信息
        User user = getLoginUser();
        model.addAttribute("user", user);
        return "admin/admin_profile";
    }


    /**
     * 处理修改用户资料的请求
     *
     * @param user user
     * @return JsonResult
     */
    @PostMapping(value = "/profile/save")
    @ResponseBody
    public JsonResult saveProfile(@ModelAttribute User user) {
        User loginUser = getLoginUser();

        User saveUser = userService.get(loginUser.getId());
        saveUser.setId(loginUser.getId());
        saveUser.setUserName(user.getUserName());
        saveUser.setUserDisplayName(user.getUserDisplayName());
        saveUser.setUserAvatar(user.getUserAvatar());
        saveUser.setUserDesc(user.getUserDesc());
        saveUser.setIdCard(user.getIdCard());
        userService.insertOrUpdate(saveUser);
        return JsonResult.success("资料修改成功，请重新登录");
    }


    /**
     * 处理修改密码的请求
     *
     * @param beforePass 旧密码
     * @param newPass    新密码
     * @return JsonResult
     */
    @PostMapping(value = "/changePass")
    @ResponseBody
    public JsonResult changePass(@ModelAttribute("beforePass") String beforePass,
                                 @ModelAttribute("newPass") String newPass) {

        // 1.密码长度是否合法
        if (newPass.length() > 20 || newPass.length() < 6) {
            return JsonResult.error("用户密码长度为6-20位!");
        }

        // 2.比较密码
        User loginUser = getLoginUser();
        User user = userService.get(loginUser.getId());
        if (user != null && Objects.equals(user.getUserPass(), Md5Util.toMd5(beforePass, CommonConstant.PASSWORD_SALT, 10))) {
            userService.updatePassword(user.getId(), newPass);
        } else {
            return JsonResult.error("旧密码错误");
        }
        return JsonResult.success("密码重置成功");
    }


}
