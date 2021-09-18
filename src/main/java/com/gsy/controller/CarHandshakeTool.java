package com.gsy.controller;

import com.gsy.encrypt.gm.sm3.SM3;
import com.gsy.encrypt.gm.sm4.SM4;
import com.gsy.encrypt.gm.sm9.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class CarHandshakeTool {

    private SM9Curve sm9Curve;
    private SM9 sm9;
    private String sk = "";

    public CarHandshakeTool() {
        this.sm9Curve = new SM9Curve();
        this.sm9 = new SM9(this.sm9Curve);
    }

    protected Map<String, String> send_map_first(String vin) {
        int r = (int) (Math.random() * (10000));
        Map<String, String> map = new HashMap<>();
        map.put("vinCode", vin);
        map.put("clientRandom", Integer.toString(r));
        return map;
    }

    /**
     * Client接收到的第一次握手（A(server)-》B(client)）
     *
     * @param map 握手信息：{ IDA，{IDB，rA，rB，signA(IDB，rA，rB) }，
     *            =》
     *            {"clientServiceName"：clientServiceName（IDB）,
     *            "serverServiceName"：myServiceName（IDA），
     *            "stpk"：stpkStr（RA）,
     *            "serverRandom":ra,
     *            "clientRandom":rb,
     *            "signResult“：sign（IDB+ra+rb）
     *            }
     *            clientRandom   Client随机数
     *            myServiceName  Client身份标识IDB
     *            spkStr         Client用户签名私钥
     *            cpkStr         Client密钥交换私钥
     *            empkStr        加密主公钥
     *            smpkStr        签名主公钥
     * @return 第一次握手返回情报
     * { IDB，{IDA‖rA‖signB(IDA‖rA)} }，
     * =》
     * {“clientServiceName”：“client身份标识”，
     * “serverServiceName”：“server身份标识”，
     * “serverRandom”：“server随机数ra”，
     * “SB”：“Client的SB”,
     * "signResult":"client签名结果",
     * "ctpk":"client的RB公钥"}
     */
    public Map<String, String> dealFirst(Map<String, String> map, String clientRandom, String myServiceName,
                                         String spkStr, String cpkStr,
                                         String empkStr, String smpkStr) {
        //System.out.println("ccccccc deal dealFirst");
        //结果
        Map<String, String> result = new HashMap<>();
        //身份认证
        //log.info("开始处理" + myServiceName + "向" + map.get("serverName") + "发起第一次握手之后的结果");

        //取得签名主公钥
        byte[] smpkByte = CodeUtil.decodeStringToByte(smpkStr);
        MasterPublicKey smpk = MasterPublicKey.fromByteArray(sm9Curve, smpkByte);

        //判断rb是否相等
        if (map.get("clientRandom").equals(clientRandom)) {
            //log.info("random check pass");
        } else {
            //log.info("random check fail");
            return null;
        }
        //判断IDB是否相等
        if (map.get("vinCode").equals(myServiceName)) {
            //log.info("service name check pass");
        } else {
            //log.info("service name check fail");
            return null;
        }
        //用IDA签名验签，内容为sign（IDB+ra+rb）
        String message2verify = myServiceName + map.get("serverRandom") + clientRandom;
        byte[] signResults = CodeUtil.decodeStringToByte(map.get("signResult"));
        ResultSignature signature = ResultSignature.fromByteArray(sm9Curve, signResults);
        //System.out.println("bbbbb");
        //System.out.println(smpkStr);
        //System.out.println(smpk);
        //System.out.println(map.get("serverName"));
        //System.out.println(message2verify);
        //System.out.println(signature);
        if (sm9.verify(smpk,
                map.get("serverName"),
                message2verify.getBytes(StandardCharsets.UTF_8),
                signature)) {
            //log.info("sign result check pass");
        } else {
            //log.info("sign result check fail");
            return null;
        }
        //System.out.println("bbbbb");
        //用IDB签名私钥签名，内容为sign（IDA+ra）
        String message2sign = map.get("serverName") + map.get("serverRandom");
        PrivateKey privateKey = PrivateKey.fromByteArray(sm9Curve, CodeUtil.decodeStringToByte(spkStr));
        ResultSignature resultSignature = sm9.sign(smpk, privateKey, message2sign.getBytes(StandardCharsets.UTF_8));
        result.put("vinCode", myServiceName);
        result.put("serverName", map.get("serverName"));
        result.put("serverRandom", map.get("serverRandom"));
        result.put("signResult", CodeUtil.encodeToString(resultSignature.toByteArray()));
        //log.info(myServiceName + " 认证 " + map.get("serverName") + " 成功！");
        //密钥交换
        //加密主公钥
        MasterPublicKey empk = MasterPublicKey.fromByteArray(sm9Curve, CodeUtil.decodeStringToByte(empkStr));
        //RA
        G1PublicKey stpk = G1PublicKey.fromByteArray(sm9Curve, CodeUtil.decodeStringToByte(map.get("serverTmpPublicKey")));
        //RB
        G1KeyPair ctk = sm9.keyExchangeInit(empk, map.get("serverName"));
        //密钥交换私钥
        PrivateKey cck = PrivateKey.fromByteArray(sm9Curve, CodeUtil.decodeStringToByte(cpkStr));
        ResultKeyExchange cak = null;
        try {
            /*
             * @param empk 加密主公钥
             * @param isSponsor false 发起标志
             * @param myServiceName Client的身份标识
             * @param map.get("serverServiceName") Server的身份标识
             * @param cck Client的密钥交换私钥
             * @param ctk Client的RB密钥对
             * @param stpk Server的RA的公钥
             * @param keyByteLen the byte length of key want to exchange
             * @return ResultKeyExchange(SK, SA2, SB1).
             * @throws Exception  If error occurs.
             */
            cak = sm9.keyExchange(empk,
                    false,
                    myServiceName,
                    map.get("serverName"),
                    cck, ctk, stpk, 16);
            result.put("SB", CodeUtil.encodeToString(cak.getSB1()));
            result.put("carTempKey", CodeUtil.encodeToString(ctk.getPublicKey().toByteArray()));
            String disable = map.get("serverName") + ":disable";
            this.sk = CodeUtil.encodeToString(cak.getSK());
            byte[] saBytes = SM3.getInstance().digest(cak.getSK());
            result.put("digest", CodeUtil.encodeToString(saBytes));
            //log.info(myServiceName + " 生成了与 " + map.get("serverName") + " 待用的会话密钥！");
            Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        //System.out.println("111111"+result);
        return result;
    }

    /**
     * client接收到的第二次握手
     *
     * @param map 握手信息：{ IDB，{IDA‖rA‖signB(IDA‖rA)} }
     *            =>
     *            {"serverServiceName"：myServiceName（IDA），
     *            "SA"："SA"
     *            }
     *            myServiceName  Client的身份标识IDB
     * @return 成功/失败
     */
    public boolean dealSecond(Map<String, String> map, String myServiceName) throws NoSuchAlgorithmException {
        //System.out.println("ddddddddd deal dealSecond");
        System.out.println(map);

        byte[] saBytes1 = CodeUtil.decodeStringToByte(this.sk);
        byte[] saBytes = SM3.getInstance().digest(saBytes1);
        //log.info("开始处理" + myServiceName + "向" + map.get("serverName") + "发起第二次握手之后的结果");
        //log.info("开始认证 " + map.get("serverName") + " 所传来的SA，完成最后一次密钥交换.");
        //验证SA是否相等
        if (SM9Utils.byteEqual(saBytes, saBytes)) {
            //log.info(map.get("serverName") + " 所传来的SA认证成功！");
            String disable = map.get("serverName") + ":disable";
            //混淆
            byte[] bytes = CodeUtil.decodeStringToByte(this.sk);

            //对生成的sk进行SM3摘要
            byte[] digest = SM3.getInstance().digest(bytes);

            CodeUtil.confuse(bytes);
            sk = CodeUtil.encodeToString(bytes);
            String newKey = map.get("serverName") + ":new";
            String oldKey = map.get("serverName") + ":old";
            return true;
        } else {
            //log.info("密钥交换失败，SA不同");
        }
        return false;
    }

    public String encrypt_sendMessage(String message) throws Exception {
        byte[] message_bytes = CodeUtil.decodeStringToByte(message);
        String result = CodeUtil.encodeToString(
                SM4.ecbCrypt(true, CodeUtil.decodeStringToByte(this.sk), message_bytes, 0, message.length()));
        return result;
    }
}
