package cn.com.entity.admin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 *
 * @author LiDaDa
 */
@Data
@Accessors(chain = true)
public class DeptNode implements Serializable {
	private static final long serialVersionUID = -5020694037128150039L;
	private Long id;
    private String text;
    private Long parentId;
    private String nodeId;
    private boolean leaf = true;
    private List<DeptNode> nodes = new ArrayList<>();


    public DeptNode(Long id, String text, Long parentId, String nodeId, boolean leaf, List<DeptNode> nodes) {
        this.id = id;
        this.text = text;
        this.parentId = parentId;
        this.nodeId = nodeId;
        this.leaf = leaf;
        this.nodes = nodes;
    }


}
