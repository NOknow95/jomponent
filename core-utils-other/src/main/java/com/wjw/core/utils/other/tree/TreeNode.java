package com.wjw.core.utils.other.tree;

/**
 * @author wang.jianwen
 * @version 1.0
 * @CreateDate 2020/01/15
 * @Desc
 */
public interface TreeNode<T> {

  String getNodeId();

  String getParentNodeId();

  void initChildNodes();

  void addChildNode(T node);
}
