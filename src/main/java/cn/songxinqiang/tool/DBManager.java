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
 * 创建时间：2015年10月29日--下午6:02:58
 * 作者：阿信sxq(songxinqiang@vip.qq.com)
 */
package cn.songxinqiang.tool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory;

/**
 * 数据库连接池管理工具类，负责提供连接和回收连接<br>
 * 会读取类路径下的dbcp.properties文件，需要将数据库连接配置以标准的properties文件格式给出<br>
 * 使用tomcat-dbcp连接池，所以配置文件的配置信息也要符合连接池规范
 *
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 * 
 * @author 阿信sxq-2015年10月29日
 *
 */
public final class DBManager {

    private static final String CONF_FILE = "dbcp.properties";

    private static DataSource dataSource;

    static {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(DBManager.class.getClassLoader().getResourceAsStream(CONF_FILE));
            dataSource = BasicDataSourceFactory.createDataSource(dbProperties);

            Connection conn = getConn();
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DBManager() {}

    /**
     * 获取一个连接，用后要归还
     *
     * @author 阿信sxq-2015年10月29日
     *
     * @return
     *         数据库连接
     */
    public static final Connection getConn() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 归还连接
     *
     * @author 阿信sxq-2015年10月29日
     *
     * @param conn
     *            需要关闭的数据库连接
     */
    public static void closeConn(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
