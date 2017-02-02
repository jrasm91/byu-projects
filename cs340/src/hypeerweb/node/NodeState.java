package hypeerweb.node;

/**
 * States for nodes while in the HyPeerWeb. 
 * Helps define how actions may be taken using the state pattern.
 * @author Adam, Jason, Ben
 */
public enum NodeState {
	NULL_STATE,
	STANDARD,
	UPPOINTING,
	DOWNPOINTING,
	HYPEERCUBECAP,
	TERMINAL;

}
