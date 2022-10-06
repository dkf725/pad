package com.pad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.Admin;
import com.pad.entity.Role;
import com.pad.mapper.RoleMapper;
import com.pad.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    //角色列表分页显示
    @Override
    public void queryPage(Page<Role> rolePage, Role role) {
        //构造条件
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //不显示被删除的角色
        wrapper.eq(Role::getIsDeleted,1);
        //判断条件是否为空
        if (ObjectUtils.isEmpty(role)){
            //条件为空 直接分页查询
            baseMapper.selectPage(rolePage,null);
            return;
        }
        //判断单个条件是否为空
        String name = role.getName();
        String value = role.getValue();
        Integer status = role.getStatus();
        if (StringUtils.hasText(name)){
            wrapper.like(Role::getName,name);
        }
        if (StringUtils.hasText(value)){
            wrapper.like(Role::getValue,value);
        }
        if (status !=null){
            wrapper.eq(Role::getStatus,status);
        }
        //查询
        baseMapper.selectPage(rolePage,wrapper);
    }

    //查询角色对应权限id列表
    @Override
    public List<Integer> getMenuIdsByRoleId(String id) {
        return baseMapper.selectMenuIdsByRoleId(id);
    }

    //逻辑删除角色
    @Override
    public void removeRole(List<String> asList) {
        baseMapper.deleteRoleByIds(asList);
    }
}
