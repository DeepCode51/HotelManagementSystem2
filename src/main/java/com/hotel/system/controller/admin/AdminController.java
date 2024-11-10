package com.hotel.system.controller.admin;

import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.Permission;
import com.hotel.system.entity.Role;
import com.hotel.system.entity.User;
import com.hotel.system.service.PermissionService;
import com.hotel.system.service.RoleService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <pre>
 *     后台首页控制器
 * </pre>
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**
     * 请求后台页面
     *
     * @param model model
     * @return 模板路径admin/admin_index
     */
    @GetMapping
    public String index(Model model) {
        User user = getLoginUser();

        Role role = roleService.findByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        return "admin/admin_index";
//        return "redirect:/admin/order";
    }


    /**
     * 获得当前用户的菜单
     *
     * @return
     */
    @GetMapping(value = "/currentMenus")
    @ResponseBody
    public JsonResult getMenu() {
        Long userId = getLoginUserId();
        List<Permission> permissions = permissionService.findPermissionTreeByUserIdAndResourceType(userId, "menu");
        return JsonResult.success("", permissions);
    }

    /**
     * 获得当前登录用户
     */
    @GetMapping(value = "/currentUser")
    @ResponseBody
    public JsonResult currentUser() {
        User user = getLoginUser();
        if (user != null) {
            return JsonResult.success("", user);
        }
        return JsonResult.error("用户未登录");
    }

    /**
     * 获得当前用户角色编码
     */
    @GetMapping(value = "/currentRole")
    @ResponseBody
    public JsonResult currentRole() {
        Role role = roleService.findByUserId(getLoginUserId());
        if (role == null) {
            return JsonResult.error("用户未登录或无角色");
        }
        return JsonResult.success("", role.getRole());
    }

}
