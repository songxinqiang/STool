/**
 * Copyright 2014 阿信(songxinqiang@vip.qq.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.songxinqiang.tool;

/**
 * 一个分页的工具类
 * 
 * @author 宋信强--2014年9月10日--songxinqiang@vip.qq.com
 *
 */
public class SharePager {

    /**
     * 分页操作动作枚举
     * 
     * @author 宋信强--2014年9月10日--songxinqiang@vip.qq.com
     *
     */
    public enum PagerAction {
        /**
         * 首页
         */
        first,
        /**
         * 上一页
         */
        previous,
        /**
         * 下一页
         */
        next,
        /**
         * 最后一页
         */
        last,
        /**
         * 跳转到
         */
        gotoPage,
        /**
         * 无动作，指首次进行分页
         */
        nought
    };

    /**
     * 总行数
     */
    private int totalRow;
    /**
     * 每页显示的行数
     */
    private int pageSize;
    /**
     * 当前页号
     */
    private int currentPage;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页在数据库中的起始行
     */
    private int startRow;

    /**
     * 指定总记录和每页大小，会由此计算出页码数，会将当前页置为1，起始记录编号置为0
     * 
     * @param totalRow
     *            总记录数
     * @param pageSize
     *            每页大小
     */
    public SharePager(int totalRow, int pageSize) {
        this.totalRow = totalRow;
        if (pageSize < 1) {
            this.pageSize = 1;
        } else {
            this.pageSize = pageSize;
        }

        totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        currentPage = 1;
        startRow = 0;
    }

    /**
     * 跳转到首页，当前页置为1，起始记录编号置为0
     */
    public void first() {
        this.currentPage = 1;
        this.startRow = 0;
    }

    /**
     * 跳转到前一页，将当前页编号减1，会重新计算起始记录编号，若当前已是首页则不作操作
     */
    public void previous() {
        if (currentPage == 1) {
            return;
        }
        --currentPage;
        startRow = (currentPage - 1) * pageSize;
    }

    /**
     * 跳转到下一页，若当前页编号小于总页数则将当前页编号加1，重新计算起始记录编号
     */
    public void next() {
        if (currentPage < totalPage) {
            ++currentPage;
        }
        startRow = (currentPage - 1) * pageSize;
    }

    /**
     * 跳转到最后一页
     */
    public void last() {
        currentPage = totalPage;
        if (currentPage < 1) {
            currentPage = 1;
        }
        startRow = (currentPage - 1) * pageSize;
        totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

    }

    /**
     * 跳转到指定页,若指定的页码小于0则到首页，若大于总页数则到最后一页
     * 
     * @param gotoPage
     *            跳转到的页码
     */
    public void jump(int gotoPage) {
        if (gotoPage < 0) {
            first();
        } else if (gotoPage > totalPage) {
            last();
        } else {
            currentPage = gotoPage;
            startRow = (currentPage - 1) * pageSize;
        }

    }

    /**
     * 获取起始记录编号
     * 
     * @return 起始记录编号，从0开始计算
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * 获取总页数
     * 
     * @return 总页码
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 获取当前页码
     * 
     * @return 当前页码
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 获取每一页的记录数
     * 
     * @return 每一页的记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总记录数
     * 
     * @return 总行数
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * 设置总记录数
     * 
     * @param totalRow
     *            总行数
     */
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    /**
     * 设置开始记录编号
     * 
     * @param startRow
     *            当前页的开始行编号
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * 设置总页数
     * 
     * @param totalPage
     *            总页码数
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 设置当前页
     * 
     * @param currentPage
     *            当前页码
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 设置每页大小
     * 
     * @param pageSize
     *            每一页的大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
