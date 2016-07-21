package model.actions;

import model.elements.Element;
import model.elements.Robot;

public class Action {
	Element elements = Robot.system;
	Method method;
	int value;
	Status status = Status.UNKNOWN;

	public Action(Element elements, Method method, int value, Status status) {
		this.elements = elements;
		this.method = method;
		this.value = value;
		this.status = status;
	}

	public Action(Element elements, Method method, int value) {
		this.elements = elements;
		this.method = method;
		this.value = value;
	}

	public Action(Method method, int value) {
		this.method = method;
		this.value = value;
	}

	public Action(Element elements, Method method) {
		this(elements, method, 0);
	}

	public EncodedAction encode() {
		return new EncodedAction(elements.getValue(), method.getValue(), (value >> 24) % 256, (value >> 16) % 256, (value >> 8) % 256, (value) % 256);
	}
}
