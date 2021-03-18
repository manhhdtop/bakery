package com.bakery.server.controller.admin;

import com.bakery.server.controller.admin.base.BaseController;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${admin-base-path}/user")
@RestController
public class UserController extends BaseController<User> {
}
