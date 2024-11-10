package com.hotel.system.config;


/**
 * 支付宝
 * @author 言曌
 * @date 2022/5/8 2:43 下午
 */


public class AlipayConfig {

    // 网站地址，即本网站地址，比如 http://localhost:8080，需要确保网站地址能在公网访问，所以需要部署到服务器上或者通过第三方软件穿透
    public static final String DOMAIN_URL = "http://hotel.liuyanzhao.com";

    // 商户appid
    public static String APPID = "2016101300675891";

    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCLoIrqnzly9hh5fQeIzKnTwVNmhMjNeGrdwpi7WLJwonrZoTh/SZWzXuN+75LGKQrQ6crcJ22pJH7R6stWg+N/xZspiIN1mq8bB3IW4NhKrN9PEiIzohh080iXsuYTk/J35vL4FB+wEgKmomagQcKVCEK/He4Nr7+2ysFie8dQGegdui9ya/ucxwZzLdjfhAYALlnIQR1neHPrV0NInjz5hbphyAL9oyUK9v/+nLHCEDIFWagfaOp2wljkwMtxkXnzo2+l7UfczA53dGe3M7+rc/u9XB/8P9TFcq9Z6zw85C7KzdEtBN+q6Mr77VDkiNBmzR5N2LkDgUG9lUkQ5t0RAgMBAAECggEBAIh9J06y1zXJ26Lm1bakWl7lXOGaa7BvRggBccuxqLzyF6UmNFQ5Wl09JKWxC4kqFaii4erToB73zgDlxF+ODZWZVVQEXn1X+FQzsxMqOETHOIPS9hwDGGU6bHveJYp91UfgOyLGiTNUZ8L8fdWqcsu8h4LLfylh48aL7P6YgP2K43CJDt2/yPJ5pC3R+f6Lvov9AKNHaCe/nnPWt1tx2yUpZaxUFyJ4HRQYC4utOp1VH8xO1dZmAuYg5z/qlHTUOTObrg5QS5tj31vf0YbL0Qzopmrr4HyZXE7COgA5A09CmyVuW91ypHnhnXYgCLtQCQHNLs8LvXkBvQy/nu5xNQECgYEAx40aTgnnHyvOgpoImzafAUDnYw5VpCt3wSbrZfRQTCr5V787skjWPlx2tr800vdmZvphC/pt45/AVuUiyTdaTaNcT+wU+zm/TgTuZK0CzhKbKk6hJgF2RhEt4qkPn/XOMxLgAqKJEIZcLxc++xaW+6PtUhF/ywj25J7Vmq9hdaMCgYEAsx/rU9OeBnAxdJoEFztpwu0SvdJwajZ5IQsMz5MNh9wVrTm3xydQGYamAoT/PujHNxbOBRtHZKZF+3lHvdF0HK6QY1STqATPZORVPTH4VDcthSR9ztpVGCJfXKpCqUmeoxIrLiRiOh8nfyeyLQgnxp+/orE+ibU8qEBDufYLRbsCgYALZyvGo9g6NsWhPu4Y+IETkELorSwFqpo4RmITPGfOQnJnKj1eDb14aM472SdKmuqWHd6n0ZKv/DQAgy1+tBQL9bbTtljolj8xVSTOSfisD/If88hPpKu+i0LE/gYvjNtQndaBMAqHEDZ6Vmi57muIS7DHDMrxrjx1igIIydbW4wKBgQCM6Mg0Cl3ezLVq95nOONPLHrpGj1wVgcKqBspOJG1Zef2nZqc2yKtPWUgQ5VTxxSaqJItvIvV3rQGSgOa54SJJSd2V9jutyhLoWGLhoAStIzbw7TL23XRhZvDDscB88cgUmj9TrhWDiWyFr9GPcUHxE0wqjOoYZHVSGjPs/ywA7wKBgQCy3BPVF3qNHoa9OFCCmx5n78W7xS1VvaG0tUl1Yzx94rJeSb21hVCq4ZG5nIBWcT25Si9t4/mV3Vd4RWcxnqIDY9c4+UoptsiZwilo0wtg2dA6ZRKUyr745ciErHZWwjX2DFj7SKvnyGXzZ9rKifbPxg5OmwXdnM+85jX8vv5q/w==";

    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo84P1f9Gd2Q9+V4JPyrsSimhjLyi3Yl51kQfXAx2lq8xaZI51d8QmSeERGSmzvCqxaZU1P9749AVy/LO88afLWHM0UeZvXvykVCNChG6TlHuW7Oo+/3JTa6wXJL4ny+DIujWPSZX/BWrq9SF+d89YqEuI3V2SfPGvUhO+cxkq03h5oRNeX7Fjspx1yzA5bcgbwkr3NMEG1WsaxLbJB9I8t6aD3QlOsgGCyNrmwBLsj8IvwQC5UDO5gVgKxs81ZHUJwNd+nBXLHQRJxnfWIfiKMagcAYDKZMYz7aELsfjjdrqhK+ISvyKNZvl+v9G/PhVOXuDK1ZrXaSHIjC1HywMyQIDAQAB";


    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = DOMAIN_URL + "/payment/alipay/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = DOMAIN_URL + "/payment/paySuccess";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";

    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

    public static String getAPPID() {
        return APPID;
    }

    public static void setAPPID(String APPID) {
        AlipayConfig.APPID = APPID;
    }

    public static String getRsaPrivateKey() {
        return RSA_PRIVATE_KEY;
    }

    public static void setRsaPrivateKey(String rsaPrivateKey) {
        RSA_PRIVATE_KEY = rsaPrivateKey;
    }

    public static String getNotify_url() {
        return notify_url;
    }

    public static void setNotify_url(String notify_url) {
        AlipayConfig.notify_url = notify_url;
    }

    public static String getReturn_url() {
        return return_url;
    }

    public static void setReturn_url(String return_url) {
        AlipayConfig.return_url = return_url;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        AlipayConfig.URL = URL;
    }

    public static String getCHARSET() {
        return CHARSET;
    }

    public static void setCHARSET(String CHARSET) {
        AlipayConfig.CHARSET = CHARSET;
    }

    public static String getFORMAT() {
        return FORMAT;
    }

    public static void setFORMAT(String FORMAT) {
        AlipayConfig.FORMAT = FORMAT;
    }

    public static String getAlipayPublicKey() {
        return ALIPAY_PUBLIC_KEY;
    }

    public static void setAlipayPublicKey(String alipayPublicKey) {
        ALIPAY_PUBLIC_KEY = alipayPublicKey;
    }

    public static String getLog_path() {
        return log_path;
    }

    public static void setLog_path(String log_path) {
        AlipayConfig.log_path = log_path;
    }

    public static String getSIGNTYPE() {
        return SIGNTYPE;
    }

    public static void setSIGNTYPE(String SIGNTYPE) {
        AlipayConfig.SIGNTYPE = SIGNTYPE;
    }
}