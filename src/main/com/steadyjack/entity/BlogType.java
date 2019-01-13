package steadyjack.entity;

import java.io.Serializable;

/**
 * title:BlogType.java
 * description: 博客类型实体
 * time:2017年1月15日 下午9:25:32
 * author:debug-steadyjack
 */
public class BlogType implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;  // 编号
    private String typeName; // 博客类型名称
    private Integer blogCount; // 数量
    private Integer orderNo; // 排序  从小到大排序显示

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBlogCount() {
        return blogCount;
    }
    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

}
