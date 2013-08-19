package tiams.test;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestJM {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TestJM.class);
    private final static String encodingChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private final static String KEY = "f(j#0";

    @Test
    public void test() {
        String params = "ip=&card=admin&bps_up=400&bps_down=800&_=" + System.currentTimeMillis()/1000;
        String en_url = encrypt_url(params, KEY);
        String de_url = decrypt_url(en_url, KEY);
        logger.info("en_url: [" + en_url + "]");
        logger.info("de_url: [" + de_url + "]");
    }
    
    private static String encrypt_url(String url, String key) {
        return base64_encode(encrypt(url, key));
    }
    
    private static String decrypt_url(String url, String key) {
        return decrypt(base64_decode(url), key);
    }

    private static String keyED(String txt, String key) {
        String encrypt_key = md5(key);
        int ctr = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < txt.length(); i++) {
            if (ctr == encrypt_key.length())
                ctr = 0;
            sb.append((char) (txt.charAt(i) ^ encrypt_key.charAt(ctr)));
            ctr++;
        }
        return sb.toString();
    }

    private static String encrypt(String txt, String key) {
        String encrypt_key = md5(String.valueOf(new Random().nextInt(100)));
        int ctr = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < txt.length(); i++) {
            if (ctr == encrypt_key.length())
                ctr = 0;
            sb.append(encrypt_key.charAt(ctr)).append((char)(txt.charAt(i) ^ encrypt_key.charAt(ctr)));
            ctr++;
        }
        return keyED(sb.toString(), key);
    }

    private static String decrypt(String txt, String key) {
        String txtKey = keyED(txt, key);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < txtKey.length(); i++) {
            char md5 = txtKey.charAt(i);
            i++;
            sb.append((char)(txtKey.charAt(i) ^ md5));
        }
        return sb.toString();
    }
    
    
    private static String md5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String base64_encode(String source) {
        char[] sourceBytes = getPaddedBytes(source);
        int numGroups = (sourceBytes.length + 2) / 3;
        char[] targetBytes = new char[4];
        char[] target = new char[4 * numGroups];

        for (int group = 0; group < numGroups; group++) {
            convert3To4(sourceBytes, group * 3, targetBytes);
            for (int i = 0; i < targetBytes.length; i++) {
                target[i + 4 * group] = encodingChar.charAt(targetBytes[i]);
            }
        }

        int numPadBytes = sourceBytes.length - source.length();

        for (int i = target.length - numPadBytes; i < target.length; i++) target[i] = '=';
        return new String(target);
    }


    private static char[] getPaddedBytes(String source) {
        char[] converted = source.toCharArray();
        int requiredLength = 3 * ((converted.length + 2) / 3);
        char[] result = new char[requiredLength];
        System.arraycopy(converted, 0, result, 0, converted.length);
        return result;
    }


    private static void convert3To4(char[] source, int sourceIndex, char[] target) {
        target[0] = (char) (source[sourceIndex] >>> 2);
        target[1] = (char) (((source[sourceIndex] & 0x03) << 4) | (source[sourceIndex + 1] >>> 4));
        target[2] = (char) (((source[sourceIndex + 1] & 0x0f) << 2) | (source[sourceIndex + 2] >>> 6));
        target[3] = (char) (source[sourceIndex + 2] & 0x3f);
    }


    private static String base64_decode(String source) {
        if (source.length() % 4 != 0)
            throw new RuntimeException("valid Base64 codes have a multiple of 4 characters");
        int numGroups = source.length() / 4;
        int numExtraBytes = source.endsWith("==") ? 2 : (source.endsWith("=") ? 1 : 0);
        byte[] targetBytes = new byte[3 * numGroups];
        byte[] sourceBytes = new byte[4];
        for (int group = 0; group < numGroups; group++) {
            for (int i = 0; i < sourceBytes.length; i++) {
                sourceBytes[i] = (byte) Math.max(0, encodingChar.indexOf(source.charAt(4 * group + i)));
            }
            convert4To3(sourceBytes, targetBytes, group * 3);
        }
        return new String(targetBytes, 0, targetBytes.length - numExtraBytes);
    }


    private static void convert4To3(byte[] source, byte[] target, int targetIndex) {
        target[targetIndex] = (byte) ((source[0] << 2) | (source[1] >>> 4));
        target[targetIndex + 1] = (byte) (((source[1] & 0x0f) << 4) | (source[2] >>> 2));
        target[targetIndex + 2] = (byte) (((source[2] & 0x03) << 6) | (source[3]));
    }
}
