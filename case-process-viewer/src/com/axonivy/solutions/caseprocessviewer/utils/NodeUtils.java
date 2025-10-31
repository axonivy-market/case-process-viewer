package com.axonivy.solutions.caseprocessviewer.utils;

import java.util.List;

import com.axonivy.solutions.caseprocessviewer.bo.Node;
import com.axonivy.solutions.caseprocessviewer.core.internal.ProcessUtils;
import com.axonivy.solutions.caseprocessviewer.resolver.NodeResolver;
import com.axonivy.solutions.caseprocessviewer.resolver.PassedStatusNodeResolver;

import ch.ivyteam.ivy.process.model.connector.SequenceFlow;
import ch.ivyteam.ivy.process.model.element.ProcessElement;

@SuppressWarnings("restriction")
public class NodeUtils {

  private NodeUtils() {}

  public static List<Node> buildNodes(List<ProcessElement> processElements) {
    List<SequenceFlow> sequenceFlows = ProcessUtils.getSequenceFlowsFrom(processElements);
    List<Node> nodes = NodeResolver.convertToNodes(processElements, sequenceFlows);
    var nodeResolver = new PassedStatusNodeResolver(nodes, processElements);
    nodeResolver.updateNodeStatusOfCurrentIvyCase();
    return nodeResolver.getNodes();
  }
}
