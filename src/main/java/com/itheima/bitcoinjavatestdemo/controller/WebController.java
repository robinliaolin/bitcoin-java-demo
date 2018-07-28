package com.itheima.bitcoinjavatestdemo.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.bitcoinjavatestdemo.BitcoinJavaTestDemoApplication;
import com.itheima.bitcoinjavatestdemo.bean.Block;
import com.itheima.bitcoinjavatestdemo.bean.MyClient;
import com.itheima.bitcoinjavatestdemo.bean.MyServer;
import com.itheima.bitcoinjavatestdemo.bean.NoteBook;
import com.itheima.bitcoinjavatestdemo.bean.Transance;
import com.itheima.bitcoinjavatestdemo.bean.Wallet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.PostConstruct;

/**
 * ClassName:WebController
 * Description:
 */
@Controller
public class WebController {
    private NoteBook mNoteBook = new NoteBook();
    private ObjectMapper mObjectMapper = new ObjectMapper();
    private Wallet wallet = new Wallet("张三");
    private MyServer myServer ;
    @PostConstruct
    //该注解表示当该类对象的构造函数被调用后自动调用该方法。
    public void init(){
        myServer = new MyServer((Integer.parseInt(BitcoinJavaTestDemoApplication.port)+1)+"");
        myServer.startServer();
    }
    private HashSet<String> set = new HashSet<>();
    @RequestMapping(value = "/addFengmian",method = RequestMethod.POST)
    @ResponseBody
    public String addFengmian(String content){
        mNoteBook.addFengmian(content);
        return "添加封面成功";
    }
    @RequestMapping(value = "/addNote",method = RequestMethod.POST)
    @ResponseBody
    public String addNote(String note){
        mNoteBook.addNote(note);
        return "添加记录成功";
    }
    @RequestMapping(value = "/showData",method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Block> showData() throws IOException {
        ArrayList<Block> list = mNoteBook.showList();
        return list;
    }
    @RequestMapping(value = "/checkHash",method = RequestMethod.POST)
    @ResponseBody
    public String checkHash(){
        String errorStr = mNoteBook.check();
        System.out.println("错误信息："+errorStr);
        if(errorStr.length()<1)
            return "数据是安全的";
        return errorStr.substring(0,errorStr.length()-1);
    }
    @RequestMapping(value = "/sendTransance",method = RequestMethod.POST)
    @ResponseBody
    public String sendTransance(Transance transance) throws IOException {
        System.out.println(transance);
        if(transance.verify()) {
            String transanceString = JSON.toJSONString(transance);
            mNoteBook.addNote(transanceString);
            String s = JSON.toJSONString(mNoteBook.showList());
            myServer.broadcast(s);
            return "提交成功";
        }
        else {
            throw new RuntimeException("数据有问题！");
        }
        //Todo:发送到目标节点，接收数据，并广播给所有节点。

    }

    @RequestMapping(value = "register",method = RequestMethod.GET)
    @ResponseBody
    //节点注册
    public String registerNode(String node){
        set.add(node);
        return "注册成功";
    }

    @RequestMapping(value = "conn",method = RequestMethod.GET)
    @ResponseBody
    //连接
    public String conn(){

            try {
                for (String s : set) {
                    URI uri = new URI("ws://localhost:" + s);
                    MyClient myClient = new MyClient(uri,s);
                    myClient.connect();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        return "连接成功";
    }
    @RequestMapping(value = "broadcast",method = RequestMethod.GET)
    @ResponseBody
    //广播消息
    public String broadcast(String msg){
        myServer.broadcast(msg);
        return "广播成功";
    }
    @RequestMapping(value = "/gotoSendTransance",method = RequestMethod.GET)
    public String goToSendTransance(){
        System.out.println("跳转到goToSendTransance方法");
        return "sendTransance.html";
    }
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String goIndex(){
        System.out.println("goIndex");
        return "redirect:index.html";
    }

    //同步节点
    public String sycnData() throws IOException {
//        List<Block> blocks = JSON.parseArray(dataStr, Block.class);
        String s = JSON.toJSONString(mNoteBook.showList());
        return s;
    }
}
