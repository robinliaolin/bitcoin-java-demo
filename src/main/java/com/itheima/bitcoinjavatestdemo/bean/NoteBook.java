package com.itheima.bitcoinjavatestdemo.bean;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.bitcoinjavatestdemo.utils.HashUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ClassName:NoteBook
 * Description:
 */
public class NoteBook {
    private ArrayList<Block> list = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    public ArrayList<Block> getList() {
        return list;
    }

    public void setList(ArrayList<Block> list) {
        this.list = list;
    }

    public NoteBook() {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,Block.class);
        File file = new File("a.json");
        if (file.exists()&&file.length()>0){
            try {
                list = objectMapper.readValue(file, javaType);
                System.out.println(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addFengmian(String content){
        if(list.size()>0){
            throw new RuntimeException("添加封面时账本必须没有封面！");
        }
        //SHA256的哈希值默认是64位
        String preHash = "00000000000000000000000000000000000000000000000000000000";
        int nonce = getNonce(content+preHash);

        String sha256 = HashUtils.sha256(nonce+content+preHash);

        Block block = new Block(list.size()+1,content,sha256,nonce,preHash);
        list.add(block);
        save();
    }
    public void addNote(String note){
        if(list.size()<1){
            throw new RuntimeException("添加记录时账本必须有封面！");
        }
        String preHash = list.get(list.size()-1).getHash();
        int nonce = getNonce(note+preHash);

        String sha256 = HashUtils.sha256(nonce+note+preHash);
        Block block = new Block(list.size()+1,note,sha256,nonce,preHash);
        list.add(block);
        save();
    }
    public void save(){

        try {
            objectMapper.writeValue(new File("a.json"),list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Block> showList() throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,Block.class);
        ArrayList<Block> arrayList = objectMapper.readValue(new File("a.json"), javaType);
        if(list.size()<arrayList.size()){
            list=arrayList;
        }
        for (Block s : list) {
            System.out.println(s);
        }
        return list;
    }
    public String check(){
        String errorStr="";
        //创世区块只要校验hash值，普通的区块需要检验的hash值和prehash的值
        for (int i = 0; i < list.size(); i++) {

            Block block = list.get(i);
            if (i==0) {
                String blockHash = HashUtils.sha256(block.getNonce() + block.getContent() + block.getPreHash());
                if (!block.getHash().equals(blockHash)) {
                    errorStr += "Id为" + block.getId() + "的hash值有误，请注意！,";
                }
            }
            else {
                String hash = HashUtils.sha256(block.getNonce() + block.getContent() + block.getPreHash());
                String preBlockHash = list.get(i - 1).getHash();
                String preHash = block.getPreHash();
                if (!preBlockHash.equals(preHash)) {
                        errorStr += "Id为" + block.getId() + "的区块的前个区块的哈希值有误，请注意！,";
                }
                else {
                    if (!block.getHash().equals(hash)) {
                        errorStr += "Id为" + block.getId() + "的哈希值有误，请注意！,";
                    }
                }
            }
        }
        return errorStr;
    }
    public int getNonce(String content){
        int nonce =0;
        for (int i =0;i<Integer.MAX_VALUE;i++){
            String s = HashUtils.sha256(i + content);
            if(s.startsWith("0000")){
                System.out.println("第"+i+"次挖矿成功");
                nonce=i;
                return nonce;
            }
        }
        throw new RuntimeException("挖矿失败！");
    }
}
