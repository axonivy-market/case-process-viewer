package com.axonivy.utils.caseprocessviewer.utils;

import java.util.List;

import com.axonivy.utils.caseprocessviewer.bo.Node;
import com.axonivy.utils.caseprocessviewer.core.util.ProcessUtils;
import com.axonivy.utils.caseprocessviewer.resolver.NodeResolver;
import com.axonivy.utils.caseprocessviewer.resolver.PassedStatusNodeResolver;

import ch.ivyteam.ivy.process.model.connector.SequenceFlow;
import ch.ivyteam.ivy.process.model.element.ProcessElement;

@SuppressWarnings("restriction")
public class NodeUtils {

  private NodeUtils() {}

  public static List<Node> buildNodes(List<ProcessElement> processElements) {
    List<SequenceFlow> sequenceFlows = ProcessUtils.getSequenceFlowsFrom(processElements);
    List<Node> nodes = NodeResolver.convertToNodes(processElements, sequenceFlows);
    
    var pasedStatusnodeResolver = new PassedStatusNodeResolver(nodes, processElements);
    pasedStatusnodeResolver.updateNodeStatusOfCurrentIvyCase();
    return NodeResolver.updateNode(nodes);
  }
}
