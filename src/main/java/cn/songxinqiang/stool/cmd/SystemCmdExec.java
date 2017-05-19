/**
 * <pre>
 * Copyright 2014,2017 阿信sxq(songxinqiang@vip.qq.com).
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
 * 创建时间：2015年8月30日--下午10:23:14
 * 作者：宋信强(阿信sxq, songxinqiang@vip.qq.com, https://my.oschina.net/songxinqiang)
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 */
package cn.songxinqiang.stool.cmd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统命令执行工具类，提供对系统命令的执行操作<br>
 * 执行系统命令，获取输出或者不获取输出，支持linux或者windows系统，输出已经处理了乱码的情况
 *
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 * 
 * @author 阿信sxq-2015年8月30日
 *
 */
public final class SystemCmdExec {

    private static final Logger log = LoggerFactory.getLogger(SystemCmdExec.class);

    private static final Runtime run;

    static {
        run = Runtime.getRuntime();
    }

    /**
     * 执行系统命令，返回执行是否成功
     * 
     * @param cmd
     *            命令
     * @return 仅当操作成功返回true
     */
    public final boolean runCmd(String cmd) {
        log.debug("run system cmd:[" + cmd + "]");

        boolean flag = false;

        try {
            run.exec(cmd);
            flag = true;
        } catch (Exception e) {
            log.error("run system cmd error," + e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 执行系统命令返回结果字符串，返回结果输出的一个字符串列表<br>
     * 该方法会产生阻塞，等待执行结束后才会返回.<br>
     * 读取命令的输出结果时使用{@linkplain StandardCharsets#UTF_8}
     * 
     * @param cmd
     *            命令
     * @return 命令所产生的所有输出，等到输出完成后才会返回
     */
    public final List<String> runCmdForString(String cmd) {
        log.debug("run system cmd for String:[" + cmd + "]");

        List<String> strList = Collections.emptyList();

        BufferedInputStream in = null;
        BufferedReader inBr = null;
        try {
            Process p = run.exec(cmd);
            in = new BufferedInputStream(p.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            strList = inBr.lines().collect(Collectors.toList());
        } catch (Exception e) {
            log.error("run system cmd for String error," + e.getMessage());
        } finally {
            if (inBr != null) {
                try {
                    inBr.close();
                } catch (IOException e) {}
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
            inBr = null;
            in = null;
        }

        return strList;
    }

    /**
     * 执行系统命令返回结果字符串，返回结果输出的一个字符串列表<br>
     * 该方法会产生阻塞，等待执行结束后才会返回.<br>
     * 读取命令的输出结果时使用{@linkplain StandardCharsets#UTF_8}
     * 
     * @param cmd
     *            命令及参数数组
     * @return 命令所产生的所有输出，等到输出完成后才会返回
     */
    public final List<String> runCmdForString(String[] cmd) {
        log.debug("run system cmd for String:[" + Arrays.toString(cmd) + "]");

        List<String> strList = Collections.emptyList();

        BufferedInputStream in = null;
        BufferedReader inBr = null;
        try {
            Process p = run.exec(cmd);
            in = new BufferedInputStream(p.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            strList = inBr.lines().collect(Collectors.toList());
        } catch (Exception e) {
            log.error("run system cmd for String error," + e.getMessage());
        } finally {
            if (inBr != null) {
                try {
                    inBr.close();
                } catch (IOException e) {}
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
            inBr = null;
            in = null;
        }

        return strList;
    }

}
