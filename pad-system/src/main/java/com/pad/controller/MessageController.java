package com.pad.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.Message;
import com.pad.response.R;
import com.pad.service.MessageService;
import com.pad.service.WebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WebSocket webSocket;


    @PreAuthorize("@me.hasAuthority('system:message:list')")
    @ApiOperation("留言列表分页显示")
    @PostMapping("/list/{current}/{limit}")
    public R messageListPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "Message", value = "查询条件", required = false)
            @RequestBody(required = false) Message message
    ) {
        //创建page对象
        Page<Message> messagePage = new Page<>(current, limit);
        //查询条件封装在service中
        messageService.pageQuery(messagePage, message);
        //获取分页后的列表和总记录数
        List<Message> messageList = messagePage.getRecords();
        long total = messagePage.getTotal();
        return R.ok().data("total", total).data("messageList", messageList);
    }


    @PreAuthorize("@me.hasAuthority('system:message:edit')")
    @ApiOperation("根据id删除留言")
    @DeleteMapping("/{id}")
    public R removeMessage(
            @ApiParam(name = "id", value = "要删除的留言id", required = true)
            @PathVariable String[] id
    ) {
        //根据银行id删除留言
        messageService.deleteByIds(Arrays.asList(id));
        return R.ok().message("删除成功");
    }

    @PreAuthorize("@me.hasAuthority('system:message:update')")
    @ApiOperation("回复留言")
    @PutMapping("/update")
    public R updateMessage(
            @ApiParam(name = "message", value = "回复的留言", required = true)
            @RequestBody Message message
    ) {
        int i = messageService.updateMe(message);
        //发送websocket消息
        webSocket.sendMessage("收到新的回复");
        if (i > 0) {
            return R.ok().message("回复成功");
        } else {
            return R.error().message("回复失败");
        }

    }
}

