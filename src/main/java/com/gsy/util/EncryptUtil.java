package com.gsy.util;

import com.gsy.encrypt.gm.sm4.SM4;
import com.gsy.encrypt.utils.Hex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class EncryptUtil {
    /***新增***/
    /**
     * 将一个字节数组加密,压缩后返回
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptByte2Byte(byte[] data, byte[] key) {
        try {
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, data, 0, data.length);
            return gzip(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个字节数组解压，解密后返回
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptByte2Byte(byte[] data, byte[] key) {
        try {
            data = gunzip(data);
            return SM4.ecbCrypt(false, key, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个字节数组加密，压缩，编码为base64字符串返回
     *
     * @param bytes
     * @param key
     * @return
     */
    public static String encryptByte2Base64(byte[] bytes, byte[] key) {
        try {
            byte[] encrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(gzip(encrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回解码，解压缩，解密后的字节数组
     *
     * @param str
     * @param key
     * @return
     */
    public static byte[] decryptBase642Byte(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            bytes = gunzip(bytes);
            return SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个明文字符串加密后，压缩，使用base64编码为Base64字符串
     *
     * @param str
     * @param key
     * @return
     */
    public static String encryptStr2Base64(String str, byte[] key) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] ecbCrypt = SM4.ecbCrypt(true, key, bytes, 0, bytes.length);
            return Base64.getUrlEncoder().encodeToString(gzip(ecbCrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码，解压缩，解密，还原为明文字符串
     *
     * @param str
     * @param key
     * @return
     */
    public static String decryptBase642Str(String str, byte[] key) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            bytes = gunzip(bytes);
            byte[] ecbCrypt = SM4.ecbCrypt(false, key, bytes, 0, bytes.length);
            return new String(ecbCrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增白盒加/解密
     *
     * @param programSrc 白盒程序位置
     * @param text       需要操作的文本
     * @param encrypt    加密/解密
     */
    public static String opsByWhiteBox(String programSrc, String text, boolean encrypt) {
        InputStream in = null;
        try {
            String ops = encrypt ? "enc" : "dec";
            byte[] textByte = CodeUtil.decodeStringToByte(text);
            text = Hex.encodeToString(textByte);
            String[] cmd = new String[]{programSrc, ops, text};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String result = bufferedReader.readLine();
            bufferedReader.close();
            reader.close();
            in.close();
            byte[] plainByte = Hex.decode(result);
            return CodeUtil.encodeToString(plainByte);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                assert in != null;
                in.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 压缩字节数组，返回要压缩后的字节数组
     * @param bytes 未压缩的字节数组
     * @return 压缩后的字节数组
     */
    private static byte[] gzip(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return bytes;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return out.toByteArray();
    }

    private static byte[] gunzip(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] decompressed = null;
        try {
            in = new ByteArrayInputStream(bytes);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }
}
