package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vivi.vue.shop.entity.ManagerEntity;
import com.vivi.vue.shop.entity.RoleEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.LoginService;
import com.vivi.vue.shop.service.ManagerService;
import com.vivi.vue.shop.service.RoleService;
import com.vivi.vue.shop.utils.JwtUtils;
import com.vivi.vue.shop.vo.LoginVO;
import com.vivi.vue.shop.vo.LoginRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wangwei
 * 2021/2/8 22:55
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    ManagerService managerService;

    @Autowired
    RoleService roleService;

    /**
     * 后台管理系统，管理员登录
     */
    @Override
    public LoginRespVO login(LoginVO loginVO) {
        // 根据用户名查询数据库
        ManagerEntity entity = managerService.getOne(new QueryWrapper<ManagerEntity>().eq("mg_name", loginVO.getUsername()));
        // 用户不存在直接返回
        if (entity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "用户不存在");
        }
        // 比对密码
        // 密码校验失败返回
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginVO.getPassword(), entity.getMgPwd())) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "账号密码错误");
        }
        // 构造用于返回的用户实体，
        LoginRespVO userInfoVO = convertManagerEntity2UserInfoVO(entity);
        // 生成令牌
        String token = JwtUtils.createToken(userInfoVO);
        userInfoVO.setToken("Bearer " + token);
        return userInfoVO;

    }

    private LoginRespVO convertManagerEntity2UserInfoVO(ManagerEntity entity) {
        if (entity == null) {
            return null;
        }
        LoginRespVO userInfoVO = new LoginRespVO();
        // 拷贝基本信息
        userInfoVO.setId(entity.getMgId());
        userInfoVO.setRid(entity.getRoleId());
        userInfoVO.setMobile(entity.getMgMobile());
        userInfoVO.setEmail(entity.getMgEmail());
        userInfoVO.setUsername(entity.getMgName());
        // 设置角色信息
        RoleEntity roleEntity = roleService.getById(entity.getRoleId());
        if (roleEntity != null) {
            userInfoVO.setRoleName(roleEntity.getRoleName());
        }
        return userInfoVO;
    }
}
