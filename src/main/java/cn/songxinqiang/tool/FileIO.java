/**
 * <pre>
 * Copyright 2014,2015 阿信sxq(songxinqiang@vip.qq.com).
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
 * 创建时间：2015年8月30日--下午10:25:25
 * 作者：阿信sxq 使用Windows平台下的Eclipse(STS)创建<br>
 */
package cn.songxinqiang.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 与文件读写操作有关的工具类
 *
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 * 
 * @author 阿信sxq-2015年8月30日
 *
 */
public class FileIO {

	/**
	 * 按照行读取文件，得到的文件内容列表保留文件中的顺序
	 *
	 * @author 阿信sxq-2015年8月30日
	 *
	 * @param file
	 *            需要读取的文件
	 * @return 文件内容的列表
	 */
	public static LinkedList<String> readLine(File file) {
		LinkedList<String> list = new LinkedList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String rstr = null;
			while ((rstr = reader.readLine()) != null) {
				list.add(rstr);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {}
		}
		return list;
	}

	/**
	 * 读取文件内容，传入的字符串参数是文件的完整路径描述，将用于创建{@code File}的对象，调用本方法和调用<br>
	 * {@code FileIO.readLine(new File(file));}<br>
	 * 是一样的的
	 *
	 * @author 阿信sxq-2015年8月30日
	 *
	 * @param file
	 *            文件的路径描述
	 * @return 文件的内容
	 */
	public static LinkedList<String> readLine(String file) {
		return readLine(new File(file));
	}

	/**
	 * 按照一次一行的方式写文件<br>
	 * 会将文件中原有内容清除掉，所以需要在调用本方法传入的文件内容就会是调用后文件中的所有内容。文件写入时使用的行分隔符，使用的是和操作系统相关的分隔符
	 * ，使用{@code System.getProperty("line.separator");}获取。
	 *
	 * @author 阿信sxq-2015年8月30日
	 *
	 * @param file
	 *            要写入到的文件的路径描述
	 * @param content
	 *            文件内容
	 * @see FileWriter#write(String)
	 */
	public static void writeFile(String file, List<String> content) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			for (String line : content) {
				writer.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {}
		}
	}

	/**
	 * 读取并处理文本文件中的内容<br>
	 * 将文件中的内容按照一行一行的方式读取，然后将每一行按照{@code regex}
	 * 所指定的正则表达式的方式进行拆分，在返回的列表中会保留文本内容在原文件的顺序，相当于使用类似二维数组的方式返回结果.<br>
	 * <b>注意：</b>{@code regex}应该是一个正则表达式，这将直接应用于对
	 * {@linkplain String#split(String)}的调用
	 *
	 * @author 阿信sxq-2015年8月30日
	 *
	 * @param file
	 *            要读取的文本文件
	 * @param regex
	 *            拆分行所使用的正则表达式
	 * @return 文件中的内容
	 * @see String#split(String) 拆分字符串
	 * @see #readLine(File) 读取文件内容
	 */
	public static LinkedHashMap<Integer, LinkedList<String>> parseFile(File file, String regex) {
		LinkedHashMap<Integer, LinkedList<String>> map = new LinkedHashMap<Integer, LinkedList<String>>();
		LinkedList<String> fileStr = FileIO.readLine(file);
		for (int i = 0; i < fileStr.size(); i++) {
			LinkedList<String> list = new LinkedList<String>();
			String[] strs = fileStr.get(i).split(regex);
			for (String str : strs) {
				list.add(str);
			}
			map.put(i, list);
		}

		return map;
	}

}
