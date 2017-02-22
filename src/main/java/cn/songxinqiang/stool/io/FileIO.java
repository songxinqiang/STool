/**
 * <pre>
 * Copyright (c) 2014, 2017 阿信sxq(songxinqiang@vip.qq.com).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 */
/*
 * 创建时间：2017年2月22日--下午4:03:19
 * 作者：宋信强(阿信sxq, songxinqiang@vip.qq.com, https://my.oschina.net/songxinqiang)
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 */
package cn.songxinqiang.stool.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 与文件读写操作有关的工具类
 *
 * @author 阿信sxq
 *
 */
public class FileIO {

    private static final Logger log = LoggerFactory.getLogger(FileIO.class);

    /**
     * 行分隔符，针对不同的操作系统在类加载时进行读取系统属性值确定
     */
    private static final String LINE_SEPARATOR;

    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
    }

    /**
     * 使用{@code UTF-8}的编码读取文集爱你中的所有行，读取出错返回{@code null}
     *
     * @param file
     *            需要读取的文件
     * @return 文件内容的列表
     * 
     * @see Files#readAllLines(java.nio.file.Path)
     */
    public static final List<String> readLine(File file) {
        List<String> list = null;
        try {
            list = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 读取文件内容，传入的字符串参数是文件的完整路径描述，将用于创建{@code File}的对象，调用本方法和调用
     * {@code #readLine(new File(file));}是一样的的
     *
     * @param file
     *            文件的路径描述
     * @return 文件的内容
     * 
     * @see #readLine(File)
     */
    public static final List<String> readLine(String file) {
        return readLine(new File(file));
    }

    /**
     * 按照一次一行的方式写文件<br>
     * 会将文件中原有内容清除掉，所以需要在调用本方法传入的文件内容就会是调用后文件中的所有内容。
     * 文件写入时使用的行分隔符，使用的是和操作系统相关的分隔符,使用
     * {@code System.getProperty("line.separator");}获取。
     *
     * @param file
     *            要写入到的文件的路径描述
     * @param content
     *            文件内容
     * @see FileWriter#write(String)
     */
    public static final void writeFile(String file, List<String> content) {
        log.info("write file:%s, content:%s", file,
                Arrays.toString(content.toArray(new String[content.size()])));
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            for (String line : content) {
                writer.write(line + LINE_SEPARATOR);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 向文件中写入文本内容，会冲掉原本文件中的所有内容，如果文件不存在则会创建该文件
     *
     * @param file
     *            写入的目标文件
     * @param content
     *            需要写入到文件中的内容
     * @see FileWriter#write(String)
     */
    public static final void writeFile(String file, String content) {
        log.info("write file:%s, content:%s", file, content);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 读取并处理文本文件中的内容<br>
     * 将文件中的内容按照一行一行的方式读取，然后将每一行按照{@code regex}
     * 所指定的正则表达式的方式进行拆分，在返回的列表中会保留文本内容在原文件的顺序，相当于使用类似二维数组的方式返回结果.<br>
     * <b>注意：</b>{@code regex}应该是一个正则表达式，这将直接应用于对
     * {@linkplain String#split(String)}的调用
     *
     * @param file
     *            要读取的文本文件
     * @param regex
     *            拆分行所使用的正则表达式
     * @return 文件中的内容
     * @see String#split(String) 拆分字符串
     * @see #readLine(File) 读取文件内容
     */
    public static final LinkedHashMap<Integer, List<String>> parseFile(File file, String regex) {
        log.info("read file:%s, with:%s", file.getAbsolutePath(), regex);
        LinkedHashMap<Integer, List<String>> map = new LinkedHashMap<>();
        Iterator<String> iterator = FileIO.readLine(file).iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            String[] strs = iterator.next().split(regex);
            map.put(i, Arrays.asList(strs));
        }

        return map;
    }

}
