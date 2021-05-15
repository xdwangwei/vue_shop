package com.vivi.vue.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.ManagerDao;
import com.vivi.vue.shop.entity.ManagerEntity;
import com.vivi.vue.shop.entity.RoleEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.ManagerService;
import com.vivi.vue.shop.service.RoleService;
import com.vivi.vue.shop.utils.Constant;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.PageQueryVO;
import com.vivi.vue.shop.vo.UserAddVO;
import com.vivi.vue.shop.vo.UserListPageVO;
import com.vivi.vue.shop.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("managerService")
public class ManagerServiceImpl extends ServiceImpl<ManagerDao, ManagerEntity> implements ManagerService {

    @Autowired
    RoleService roleService;

    @Override
    public UserListPageVO queryPage(PageQueryVO pageQueryVO) {

        UserListPageVO resp = new UserListPageVO();

        // 构建分页查询参数
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PAGE, pageQueryVO.getPagenum().toString());
        params.put(Constant.LIMIT, pageQueryVO.getPagesize().toString());
        // 分页查询
        QueryWrapper<ManagerEntity> wrapper = new QueryWrapper<>();
        // 判断请求参数，可以传id或name
        String query = pageQueryVO.getQuery();
        if (!StringUtils.isEmpty(query)) {
            wrapper.like("mg_name", query);
        }
        IPage<ManagerEntity> page = this.page(
                new Query<ManagerEntity>().getPage(params),
                wrapper
        );
        // 封装返回结果
        resp.setPagenum((int) page.getCurrent());
        resp.setTotalpage((int) page.getTotal());
        List<UserVO> collect = page.getRecords().stream().map(member -> convertMemberEntity2User(member)).collect(Collectors.toList());
        resp.setUsers(collect);
        return resp;
    }

    @Transactional
    @Override
    public UserVO save(UserAddVO userAddVO) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setMgEmail(userAddVO.getEmail());
        managerEntity.setMgMobile(userAddVO.getMobile());
        managerEntity.setMgName(userAddVO.getUsername());
        managerEntity.setMgPwd(new BCryptPasswordEncoder().encode(userAddVO.getPassword()));
        managerEntity.setRoleId(1);
        managerEntity.setMgState(1);
        managerEntity.setMgTime(new Date());
        // 保存
        this.save(managerEntity);
        return convertMemberEntity2User(managerEntity);
    }

    @Transactional
    @Override
    public UserVO updateStatus(Integer uid, boolean type) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setMgId(uid);
        managerEntity.setMgState(type ? 1 : 0);
        boolean r = this.updateById(managerEntity);
        if (!r) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "用户不存在");
        }
        return convertMemberEntity2User(this.getById(uid));
    }

    @Override
    public UserVO getOne(Integer uid) {
        ManagerEntity byId = this.getById(uid);
        if (byId == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "用户不存在");
        }
        return convertMemberEntity2User(byId);
    }

    @Override
    public UserVO updateInfo(Integer uid, String mobile, String email) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setMgId(uid);
        managerEntity.setMgEmail(email);
        managerEntity.setMgMobile(mobile);
        boolean r = this.updateById(managerEntity);
        if (!r) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "用户不存在");
        }
        return convertMemberEntity2User(this.getById(uid));
    }

    @Transactional
    @Override
    public void deleteOne(Integer uid) {
        this.removeById(uid);
        return;
    }

    @Override
    public UserVO assignRole(Integer uid, JSONObject jsonObject) {
        String ridStr = String.valueOf(jsonObject.get("rid"));
        if(StringUtils.isEmpty(ridStr)) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求体中 'rid' 字段必传");
        }
        Integer rid = 0;
        try {
            rid = Integer.parseInt(ridStr);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求体中 'rid' 字段为有效的整数");
        }
        RoleEntity roleEntity = roleService.getById(rid);
        if (roleEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定角色不存在");
        }
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setMgId(uid);
        managerEntity.setRoleId(rid);
        boolean r = this.updateById(managerEntity);
        if (!r) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定用户不存在");
        }
        ManagerEntity member = this.getById(uid);
        UserVO user = new UserVO();
        // 拷贝基本信息
        user.setId(member.getMgId());
        user.setRid(member.getRoleId());
        user.setUsername(member.getMgName());
        user.setMobile(member.getMgMobile());
        user.setEmail(member.getMgEmail());
        user.setCreateTime(member.getMgTime());
        user.setMg_state(member.getMgState() == 0 ? false : true);
        // 设置角色信息
        user.setRoleName(roleEntity.getRoleName());
        return user;
    }

    /**
     * 对象转换封装
     * @param member
     * @return
     */
    private UserVO convertMemberEntity2User(ManagerEntity member) {
        if (member == null) {
            return null;
        }
        UserVO user = new UserVO();
        // 拷贝基本信息
        user.setId(member.getMgId());
        user.setRid(member.getRoleId());
        user.setUsername(member.getMgName());
        user.setMobile(member.getMgMobile());
        user.setEmail(member.getMgEmail());
        user.setCreateTime(member.getMgTime());
        user.setMg_state(member.getMgState() == 0 ? false : true);
        // 设置角色信息
        RoleEntity roleEntity = roleService.getById(member.getRoleId());
        if (roleEntity != null) {
            user.setRoleName(roleEntity.getRoleName());
        }
        return user;
    }

}