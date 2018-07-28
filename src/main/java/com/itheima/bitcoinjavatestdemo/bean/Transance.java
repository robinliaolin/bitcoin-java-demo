package com.itheima.bitcoinjavatestdemo.bean;

import com.itheima.bitcoinjavatestdemo.utils.RSAUtils;

import java.security.PublicKey;

/**
 * ClassName:Transance
 * Description:
 */
public class Transance {
    private String  signData;
    private String senderPublicKey;
    private String content;
    private String recevierPublicKey;

    public Transance(String signData, String senderPublicKey, String content, String recevierPublicKey) {
        this.signData = signData;
        this.senderPublicKey = senderPublicKey;
        this.content = content;
        this.recevierPublicKey = recevierPublicKey;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecevierPublicKey() {
        return recevierPublicKey;
    }

    public void setRecevierPublicKey(String recevierPublicKey) {
        this.recevierPublicKey = recevierPublicKey;
    }
    public boolean verify(){
        PublicKey publicKey = RSAUtils.getPublicKeyFromString("RSA",senderPublicKey);
        boolean sha256WithRSA = RSAUtils.verifyDataJS("SHA256WithRSA", publicKey, content, signData);
        return sha256WithRSA;
    }

    @Override
    public String toString() {
        return "Transance{" +
                "signData='" + signData + '\'' +
                ", senderPublicKey=" + senderPublicKey +
                ", content='" + content + '\'' +
                ", recevierPublicKey='" + recevierPublicKey + '\'' +
                '}';
    }
}
