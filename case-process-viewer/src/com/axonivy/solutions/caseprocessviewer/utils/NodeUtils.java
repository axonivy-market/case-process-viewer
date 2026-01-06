package com.axonivy.solutions.caseprocessviewer.utils;

import java.util.List;

import com.axonivy.solutions.caseprocessviewer.bo.Node;
import com.axonivy.solutions.caseprocessviewer.core.util.ProcessUtils;
import com.axonivy.solutions.caseprocessviewer.resolver.NodeFrequencyResolver;
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
//    var nodeFrequencyResolver = new NodeFrequencyResolver(nodes, processElements);
//    nodeFrequencyResolver.updateFrequencyByTasks(tasks);
//    nodes = nodeFrequencyResolver.getNodes();
    
    var pasedStatusnodeResolver = new PassedStatusNodeResolver(nodes, processElements);
    pasedStatusnodeResolver.updateNodeStatusOfCurrentIvyCase();
//    return pasedStatusnodeResolver.getNodes();
    return NodeResolver.updateNode(nodes);
  }
}
