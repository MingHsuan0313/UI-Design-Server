package com.selab.uidesignserver.graph;

public class OperationNode extends ConnectorNode{
 /*
  * The nextLayerNodes[0] is inputMessage, nextLayerNodes[1] is outputMessage
  * */
	MessageNode inputMassageNode;
	MessageNode outputMassageNode;

	// input messageNode
	public void setInputMassageNode(MessageNode inputMassageNode) { this.inputMassageNode = inputMassageNode; }
	public MessageNode getInputMassageNode() { return this.inputMassageNode; }
	
	// output messageNode
	public void setOutputMassageNode(MessageNode outputMassageNode) { this.outputMassageNode = outputMassageNode; }
	public MessageNode getOutputMassageNode() { return this.outputMassageNode; }
	
}
