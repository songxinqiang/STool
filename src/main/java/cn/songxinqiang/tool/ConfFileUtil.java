/**
 * <pre>
 * Copyright 2014,2016 阿信sxq(songxinqiang@vip.qq.com).
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
 * 创建时间：2015年8月30日--下午10:27:39
 * 作者：阿信sxq 使用Windows平台下的Eclipse(STS)创建<br>
 */
package cn.songxinqiang.tool;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 配置文件操作工具类，实现对配置文件读取、写入<br>
 * 要注意的是，由于忽略了注释和空行，所以即使简单的直接读入再写回，那么也会使得文件内容变化（丢失注释和空行）<br>
 * 对于有节点的文件读取，节点使用{@code [}判断，所以任何以{@code [}开头的行都会整体作为一个键节点
 *
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 * 
 * @author 阿信sxq-2015年10月30日
 *
 */
public final class ConfFileUtil {

    /**
     * 注释行的开始，这一行的内容将被丢弃
     */
    private static final String COMMENT_LINE_START = ";";
    /**
     * 配置节点开始标记符，这一行的内容被认为是一个配置的节点<br>
     * 从这一行的下一行开始到下一次出现该字符行的上一行，这之间出现的配置都被认为是属于这个配置节点的配置项
     */
    private static final String NODE_START = "[";
    /**
     * 分隔符, 等号({@code "="})
     */
    public static final String SEPARATOR_EQUAL = "=";

    /**
     * 读取配置文件的信息，以键值对的形式返回读取到的信息<br>
     * 会忽略空行和以{@linkplain #COMMENT_LINE_START}指定内容开头的注释行， 对于由
     * {@linkplain #NODE_START}表示的字符串开头的行，将整行去掉首尾空格后作为键，
     * 值也是一个键值对的形式，这些值都是这个节点之后、下一个节点之前的配置项， 配置项采用
     * 分隔符({@linkplain #SEPARATOR_EQUAL})进行分隔，分隔符前面的部分作为键后面的部分作为值，
     * 数据均采用 {@link LinkedHashMap}存储，会保留配置在文件中的记录顺序
     *
     * @author 阿信sxq--2016年2月4日
     *
     * @param file
     *            文件的完整限定名
     * @return 键值对的形式返回配置信息，值为具体的节点的配置信息
     */
    /**
     *
     *
     * @param file
     * @return
     */
    public static final Map<String, Map<String, String>> readFileRecordWithNode(String file) {
        Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
        Map<String, String> valueMap = null;

        List<String> content = FileIO.readLine(file);
        for (String line : content) {
            if (line.length() <= 1 || line.startsWith(COMMENT_LINE_START)) {
                continue;
            }
            if (line.startsWith(NODE_START)) {
                valueMap = new LinkedHashMap<String, String>();
                returnMap.put(line, valueMap);
                continue;
            }
            String[] strs = line.split(SEPARATOR_EQUAL);
            if (strs.length <= 1) {
                valueMap.put(strs[0].trim(), null);
            } else {
                valueMap.put(strs[0].trim(), strs[1].trim());
            }
        }

        return returnMap;
    }

    /**
     * 读取配置文件的信息，将信息以键值对的形式返回<br>
     * 本方法读取的配置文件不应该包含有使用{@code []}表示出来的节点，配置项采用
     * 分隔符({@linkplain #SEPARATOR_EQUAL})进行分隔,分隔符前面的部分作为键后面的部分作为值，
     * 如果配置没有对应的值，那么会在结果的键值对记录中存储{@code null}值
     *
     * @author 阿信sxq--2016年2月4日
     *
     * @param file
     *            需要读取的配置文件的完整路径
     * @return 配置文件中记录的配置信息的键值对
     */
    public static final Map<String, String> readFileRecordWithoutNode(String file) {
        Map<String, String> valueMap = new LinkedHashMap<String, String>();

        List<String> content = FileIO.readLine(file);
        for (String line : content) {
            if (line.length() <= 1 || line.startsWith(COMMENT_LINE_START)) {
                continue;
            }
            String[] strs = line.split(SEPARATOR_EQUAL);
            if (strs.length <= 1) {
                valueMap.put(strs[0].trim(), null);
            } else {
                valueMap.put(strs[0].trim(), strs[1].trim());
            }
        }

        return valueMap;
    }

    /**
     * 将配置信息写入到文件中，覆盖原文件信息，指定的文件若不存在会新建文件,会对节点不加处理，值会紧随键之后，
     * 在键和值中间加入分隔符，分隔符使用({@linkplain #SEPARATOR_EQUAL},
     * 在最后加入换行符(由{@code System.getProperty("line.separator");}确定)
     *
     * @author 阿信sxq-2015年10月30日
     *
     * @param file
     *            文件的完整限定名，若文件不存在会新建文件
     * @param map
     *            配置信息，键值对，值为配置详细的键值对
     * 
     * @see FileIO#writeFile(String, List)
     */
    public static final void writeFileRecordWithNode(String file,
            Map<String, Map<String, String>> map) {
        List<String> contentList = new LinkedList<>();
        for (Entry<String, Map<String, String>> entry : map.entrySet()) {
            contentList.add(entry.getKey());

            Map<String, String> valueMap = entry.getValue();

            for (Entry<String, String> ent : valueMap.entrySet()) {
                contentList.add(ent.getKey() + SEPARATOR_EQUAL + ent.getValue());
            }
        }
        FileIO.writeFile(file, contentList);
    }

    /**
     * 将配置信息写入到文件中，覆盖原文件信息，指定的文件若不存在会新建文件,值会紧随键之后，
     * 在键和值中间加入分隔符，分隔符使用({@linkplain #SEPARATOR_EQUAL},
     * 在最后加入换行符(由{@code System.getProperty("line.separator");}确定)<br>
     * 本方法输出的文件不包含节点
     *
     * @author 阿信sxq-2015年10月30日
     *
     * @param file
     *            输出到的目标文件，不存在的化会新建
     * @param map
     *            需要输出到目标文件的值，输出时会保留顺序
     * 
     * @see FileIO#writeFile(String, List) 输出到文件
     */
    public static final void writeFileRecordWithoutNode(String file, Map<String, String> map) {
        List<String> contentList = new LinkedList<>();
        for (Entry<String, String> entry : map.entrySet()) {
            contentList.add(entry.getKey() + SEPARATOR_EQUAL + entry.getValue());
        }
        FileIO.writeFile(file, contentList);
    }

}
