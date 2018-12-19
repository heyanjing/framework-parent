package com.framework.commons.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

    //-------------------------------------------------------------------------
    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'};

    /**
     * @param str        原始字符串
     * @param len        截取长度
     * @param append_str 补足字符串  如...
     * @return
     */
    public static String subLeft(String str, int len, String append_str) {
        if (str == null) {
            return "";
        }
//		int len1 = len;
        String result1 = "";
        str = str.replaceAll("　", "");
        str = str.trim();

        if (str.length() > len) {
            result1 = str.substring(0, len);
        } else {
            result1 = str;
        }

//		int len2 = 0;
//		for (int i = 0; i < str.length(); i++) {
//			if (str.codePointAt(i) > 255) {
//				len2 += 2;
//			} else {
//				len2 += 1;
//			}
//		}
//		if (len2 > len1) {
//			for (int i = 0; i < len1; i++) {
//				if (str.codePointAt(i) > 255) {
//					len1 -= 1;
//				}
//			}
//			result1 = str.substring(0, len1);
//			if (append_str != null) {
//				result1 = result1 + append_str;
//			}
//		} else {
//			result1 = str.trim();
//		}
        return result1;// 返回文本字符串
    }

    /**
     * @param str   原始字符串
     * @param s_num 开始
     * @param e_num 结束
     * @return
     */
    public static String subStr(String str, int s_num, int e_num) {
        if (str == null) {
            return "";
        }
        if (str.length() > s_num && str.length() > e_num && e_num > s_num) {
            return str.substring(s_num, e_num);
        } else {
            return str;
        }
    }

    /**
     * 清除html 获取纯文本
     *
     * @param strHtml HTML文本代码
     * @return 处理后的纯文本
     */
    public static String clearHtml(String strHtml) {
        if (strHtml == null) {
            return "";
        }
        String htmlStr = strHtml;
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签
        htmlStr = htmlStr.replaceAll("&\\w*?;", "");//
        htmlStr = htmlStr.replaceAll("\\s*", "");
        // 各种样式
        return htmlStr;
    }

    public static String getDir(String str) {
        String dir = str;
        if (dir != null) {
            dir = dir.replaceAll("\\.", "/");
        }
        return dir;
    }

    /**
     * 字符串替换
     *
     * @param str
     * @return
     */
    public static String replaceAll(String str, String regex, String replacement) {
        if (str != null) {
            return str.replaceAll(regex, replacement);
        }
        return "";
    }

    /**
     * 字符串转换为大写
     *
     * @param str
     * @return
     */
    public static String toUpperCase(String str) {
        if (str != null) {
            return str.toUpperCase();
        }
        return "";
    }

    /**
     * 字符串转换为小写
     *
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        if (str != null) {
            return str.toLowerCase();
        }
        return "";
    }

    /**
     * InputStream 转为 String
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            //  e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /**
     * 将字符串编码成 Unicode 形式的字符串. 如 "黄" to "\u9EC4"
     * Converts unicodes to encoded \\uxxxx and escapes
     * special characters with a preceding slash
     *
     * @param theString   待转换成Unicode编码的字符串。
     * @param escapeSpace 是否忽略空格，为true时在空格后面是否加个反斜杠。
     * @return 返回转换后Unicode编码的字符串。
     */
    public static String toUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }

            switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace) {
                        outBuffer.append('\\');
                    }
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        // 每个unicode有16位，每四位对应的16进制从高位保存到低位
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * 从 Unicode 形式的字符串转换成对应的编码的特殊字符串。 如 "\u9EC4" to "黄".
     * Converts encoded \\uxxxx to unicode chars
     * and changes special saved chars to their original forms
     *
     * @param in  Unicode编码的字符数组。
     * @param off 转换的起始偏移量。
     * @param len 转换的字符长度。
     * @return 完成转换，返回编码前的特殊字符串。
     */
    public static String unicodeToStr(char[] in, int off, int len) {
        char aChar;
        char[] out = new char[len]; // 只短不长
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = (char) aChar;
            }
        }
        return new String(out, 0, outLen);
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param s
     * @return
     */
    public static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append("＞");// 转义大于号
                    break;
                case '<':
                    sb.append("＜");// 转义小于号
                    break;
                case '\'':
                    sb.append("＇");// 转义单引号
                    break;
                case '\"':
                    sb.append("＂");// 转义双引号
                    break;
                case '&':
                    sb.append("＆");// 转义&
                    break;
                case '\\':
                    sb.append("＼ ");// 转义斜杠＼
                    break;
                case '#':
                    sb.append("＃");// 转义＃
                    break;
//			case '(':
//				sb.append("（");// 转义小括号（
//				break;
//			case ')':
//				sb.append("）");// 转义小括号）
//				break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (ValidatorUtil.isEmpty(s)) {
            return null;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (s == null) {
            return "";
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 以某字符分割的所有单词 首字母大写
     */
    public static String toUpperCase(String str, String regex) {
        StringBuffer sb = new StringBuffer("");

        if (str == null) {
            return "";
        }
        String[] s = str.split(regex);

        if (s != null) {
            for (String temp : s) {
                sb.append(toUpperCaseFirstOne(temp));
            }
        }
        return sb.toString();
    }

    /**
     * 截取字符串最后n位
     *
     * @param str
     * @param n
     * @return
     */
    public static String subEndStr(String str, int n) {
        if (str == null || str.length() == 0 || n >= str.length()) {
            return str;
        }
        return str.substring(str.length() - n);
    }

    /**
     * 小驼峰格式化字符串
     */
    public static String fmtStr(String str, String regex) {
        return toLowerCaseFirstOne(toUpperCase(str, regex));
    }

    public static boolean containsStr(String a, String b) {
        return a.contains(b);
    }

    public static String asString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 去除字符串中的回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 生成随机字符串（数字+字母）
     *
     * @param len 字符串长度
     * @return
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";//去掉1和i ，0和o，小写l
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++) {
            //循环随机获得一个字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            result.append(nowStr);
        }
        return result.toString();
    }
}
