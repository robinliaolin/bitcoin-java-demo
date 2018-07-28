package com.itheima.bitcoinjavatestdemo.bean;

/**
 * ClassName:Block
 * Description:
 */
public class Block {
    private  int id;
    private  String content;
    private  String hash;
    private  int nonce;
    private  String preHash;

    public Block() {
    }

    public Block(int id, String content, String hash,int nonce,String preHash) {
        this.id = id;
        this.content = content;
        this.hash = hash;
        this.nonce=nonce;
        this.preHash=preHash;
    }

    public String getPreHash() {
        return preHash;
    }

    public void setPreHash(String preHash) {
        this.preHash = preHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

