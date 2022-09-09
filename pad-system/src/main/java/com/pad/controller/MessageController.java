package com.pad.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.Message;
import com.pad.response.R;
import com.pad.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-02
 */
@Api(tags = "留言管理")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;


    @PreAuthorize("@me.hasAuthority('system:message:list')")
    @ApiOperation("留言列表分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R adminListPage(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "Message",value = "查询条件",required = false)
            @RequestBody(required = false) Message message
    ){
        //创建page对象
        Page<Message> messagePage = new Page<>(current, limit);
        //查询条件封装在service中
        messageService.pageQuery(messagePage,message);
        //获取分页后的列表和总记录数
        List<Message> messageList = messagePage.getRecords();
        long total = messagePage.getTotal();
        return R.ok().data("total",total).data("messageList",messageList);
    }

    @PreAuthorize("@me.hasAuthority('system:message:query')")
    @ApiOperation("根据id查询留言")
    @GetMapping("/{id}")
    public R getMessageById(
            @ApiParam(name = "id",value = "留言编号",required = true)
            @PathVariable String id
    ){
        Message message = messageService.getById(id);
        return R.ok().data("message",message);
    }

    @PreAuthorize("@me.hasAuthority('system:bank:remove')")
    @ApiOperation("根据id删除银行列表")
    @DeleteMapping("/{id}")
    public R removeMessage(
            @ApiParam(name = "id",value = "要删除的银行id",required = true)
            @PathVariable String[] id
    ){
        //根据银行id删除银行
        messageService.deleteByIds(Arrays.asList(id));
        return R.ok().message("删除成功");
    }

    @PreAuthorize("@me.hasAuthority('system:message:add')")
    @ApiOperation("添加留言")
    @PostMapping("/add")
    public R addMessage(
            @ApiParam(name = "message",value = "添加的留言",required = true)
            @RequestBody Message message
    ){
        //添加用户
        messageService.save(message);
        return R.ok().message("添加成功");
    }
}

