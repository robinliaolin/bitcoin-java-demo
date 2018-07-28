package com.itheima.bitcoinjavatestdemo.bean;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * ClassName:MyClient
 * Description:
 */
public class MyClient extends WebSocketClient {

    private String name;
    private String recieveContent;

    public MyClient(URI serverURI,String name) {
        super(serverURI);
        this.name=name;
    }

    public String getRecieveContent() {
        return recieveContent;
    }

    public void setRecieveContent(String recieveContent) {
        this.recieveContent = recieveContent;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("客户端"+name+"打开了连接");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("客户端"+name+"发送了消息："+message);
        recieveContent=message;
        List<Block> blocks = JSON.parseArray(message, Block.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("a.json"),blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("客户端"+name+"关闭了连接");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("客户端"+name+"发生了错误！");
    }
}
