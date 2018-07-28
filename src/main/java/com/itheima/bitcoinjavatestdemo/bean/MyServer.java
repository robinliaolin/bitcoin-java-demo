package com.itheima.bitcoinjavatestdemo.bean;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * ClassName:MyServer
 * Description:
 */
public class MyServer extends WebSocketServer {
    private String port;
    private String recieveContent;
    public MyServer(String port) {
        super(new InetSocketAddress(Integer.parseInt(port)));
        this.port=port;
    }

    public String getRecieveContent() {
        return recieveContent;
    }

    public void setRecieveContent(String recieveContent) {
        this.recieveContent = recieveContent;
    }

    public void startServer(){
        new Thread(this).start();
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("服务器"+port+"打开了连接");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("服务器"+port+"关闭了连接");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("服务器"+port+"发送了："+message);
        recieveContent=message;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("服务器发生了错误！");
    }

    @Override
    public void onStart() {
        System.out.println("服务器"+port+"已正常启动！");
    }
    public String sycnList(String recieveMsg){
        return recieveMsg;
    }
}
