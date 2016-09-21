/**
 * <pre>
 * Copyright (c) 2016, 2016 阿信sxq(songxinqiang@vip.qq.com).
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
 * 创建时间：2016年9月21日--上午10:10:11
 * 作者：宋信强(阿信sxq, songxinqiang@vip.qq.com, http://my.oschina.net/songxinqiang )
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 */
package cn.songxinqiang.tool.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 记录分页的工具类，保存分页情况的相关数据和当前页的记录数据
 *
 * @author 阿信sxq--2016年9月21日
 *
 */
public class Page<T> implements Serializable {

    /**
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private static final long serialVersionUID = -8160825312250756542L;

    /**
     * 数据
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private List<T> list;
    /**
     * 当前页码
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private int pageNumber;
    /**
     * 每页记录数
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private int pageSize;
    /**
     * 总页码数
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private int totalPage;
    /**
     * 总记录数
     * 
     * @author 阿信sxq--2016年9月21日
     */
    private int totalRow;

    /**
     * 构造器，提供所有的属性参数
     *
     * @author 阿信sxq--2016年9月21日
     *
     * @param list
     *            数据
     * @param pageNumber
     *            当前页码
     * @param pageSize
     *            每页记录数目
     * @param totalPage
     *            总页数
     * @param totalRow
     *            总记录数
     */
    public Page(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
        this.list = list;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
    }

    /**
     * 是否是首页
     *
     * @author 阿信sxq--2016年9月21日
     *
     * @return 当前页码为{@code 1}时是首页，返回true
     */
    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    /**
     * 是否是最后一页
     *
     * @author 阿信sxq--2016年9月21日
     *
     * @return 当前页码和总页数一样是是最后一页，返回true
     */
    public boolean isLastPage() {
        return pageNumber == totalPage;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @author 阿信sxq--2016年9月21日
     */
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

}
