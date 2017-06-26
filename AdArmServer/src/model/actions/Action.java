package model.actions;

import model.elements.Element;

public class Action {
	Element elements;
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

	public Action(Element elements, Method method) {
		this(elements, method, 0);
	}

	public EncodedAction encode() {
		int d1 = (this.value >> 24) % 256;
		int d2 = (this.value >> 16) % 256;
		int d3 = (this.value >> 8) % 256;
		int d4 = (this.value) % 256;
		return new EncodedAction(elements.getCode(), method.getValue(), d1, d2, d3, d4);
	}
}
