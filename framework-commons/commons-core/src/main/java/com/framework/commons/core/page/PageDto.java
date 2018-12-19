package com.framework.commons.core.page;

import com.framework.commons.core.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
public class PageDto implements IDto {

    /**
     * 当前第几页
     */
    private int pageIndex = 0;
    /**
     * 每页数据条数
     */
    private int pageSize = 10;
    /**
     * 数据总数
     */
    private int dataCount = 0;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 上一页
     */
    private int prePage;
    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 默认数据
     */
    private List data = new ArrayList<>(0);

    private Map<String, Object> otherField = null;

    public PageDto(int pageIndex, int pageSize, List data) {
        this(pageIndex, pageSize, -1, data);
    }

    public PageDto(int pageIndex, int pageSize, int dataCount, List data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataCount = dataCount;
        this.setData(data);
    }

    public void setData(List data) {
        this.data = data == null ? new ArrayList<>(0) : data;
    }

    public void addField(String key, Object obj) {
        if (otherField == null) {
            otherField = new ConcurrentHashMap<>();
        }
        otherField.put(key, obj);
    }

    public void removeField(String key) {
        otherField.remove(key);
    }
}
