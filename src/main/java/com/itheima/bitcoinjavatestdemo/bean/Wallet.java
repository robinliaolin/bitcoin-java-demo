package com.itheima.bitcoinjavatestdemo.bean;

import com.itheima.bitcoinjavatestdemo.utils.RSAUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ClassName:Wallet
 * Description:
 */
public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String name;//姓名。表示谁的钱包


    public Wallet(String name) {
        this.name=name;
        String priKeyString = name+".pri";
        String pubKeyString = name+".pub";
        File priKeyFile = new File(priKeyString);
        File pubKeyFile = new File(pubKeyString);
        if (!priKeyFile.exists()||priKeyFile.length()==0||!pubKeyFile.exists()||pubKeyFile.length()==0){
            RSAUtils.generateKeysJS("RSA",priKeyString,pubKeyString);
        }

        this.privateKey=RSAUtils.getPrivateKeyFromFile("RSA",priKeyString);
        this.publicKey = RSAUtils.getPublicKeyFromFile("RSA",pubKeyString);

    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Transance sendMoney(String recieverPubKey, String content,String signData){
        String senderPrivateKey = Base64.encode(this.getPrivateKey().getEncoded());
        String senderPublicKey = Base64.encode(publicKey.getEncoded());
        Transance transance = new Transance(signData,senderPublicKey,content,recieverPubKey);
        return transance;
    }
    public static void main(String[] args){
        Wallet wallet1 = new Wallet("a");
        Wallet wallet2 = new Wallet("b");
        PublicKey publicKey2 =wallet2.getPublicKey();
        String content="100";
        String signData = RSAUtils.getSignature("SHA256WithRSA",wallet1.getPrivateKey(),content);
        String recieverPubKey = Base64.encode(publicKey2.getEncoded());
        System.out.println("发送方的私钥："+Base64.encode(wallet1.privateKey.getEncoded()));
        System.out.println("发送方的公钥："+Base64.encode(wallet1.publicKey.getEncoded()));
        System.out.println("接收方的公钥："+recieverPubKey);
        Transance transance = wallet1.sendMoney(recieverPubKey, content,signData);
        boolean verify = transance.verify();
        System.out.println(verify);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "privateKey=" + privateKey +
                ", publicKey=" + publicKey +
                ", name='" + name + '\'' +
                '}';
    }
}
