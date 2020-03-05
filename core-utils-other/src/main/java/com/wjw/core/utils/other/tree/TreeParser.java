package com.wjw.core.utils.other.tree;

import com.wjw.core.utils.other.collection.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @CreateDate 2020/01/15
 * @Desc
 */
public class TreeParser<T extends TreeNode> {

  private List<T> srcTreeNodes;
  private Predicate<T> rootNodePredicate;

  public TreeParser(List<T> srcTreeNodes) {
    this.srcTreeNodes = srcTreeNodes;
  }

  public TreeParser(List<T> srcTreeNodes, Predicate<T> rootNodePredicate) {
    this.srcTreeNodes = srcTreeNodes;
    this.rootNodePredicate = rootNodePredicate;
  }

  public List<T> parseToTree() {
    if (CollectionUtils.isEmpty(this.srcTreeNodes)) {
      return Collections.emptyList();
    }
    for (T t : this.srcTreeNodes) {
      t.initChildNodes();
    }
    List<T> resultTree = new ArrayList<>();
    // find the root nodes
    List<T> rootNodes = this.srcTreeNodes.stream()
        .filter(preparePredicate())
        .collect(Collectors.toList());
    //remove root nodes
    srcTreeNodes.removeAll(rootNodes);

    for (T rootNode : rootNodes) {
      T node = packageNode(rootNode);
      resultTree.add(node);
    }
    return resultTree;
  }

  private Predicate<T> preparePredicate() {
    if (this.rootNodePredicate == null) {
      Set<String> allNodeIds = Optional.ofNullable(this.srcTreeNodes)
          .orElse(Collections.emptyList())
          .stream()
          .map(TreeNode::getNodeId)
          .collect(Collectors.toSet());
      this.rootNodePredicate = (item -> !allNodeIds.contains(item.getParentNodeId()));
    }
    return this.rootNodePredicate;
  }

  private T packageNode(T rootNode) {
    List<T> childNodes = findChildNodes(rootNode);
    if (!CollectionUtils.isEmpty(childNodes)) {
      this.srcTreeNodes.removeAll(childNodes);
      for (T childNode : childNodes) {
        T node = packageNode(childNode);
        rootNode.addChildNode(node);
      }
    }
    return rootNode;
  }

  private List<T> findChildNodes(T rootNode) {
    return this.srcTreeNodes.stream()
        .filter(item -> Objects.equals(item.getParentNodeId(), rootNode.getNodeId()))
        .collect(Collectors.toList());
  }
}
