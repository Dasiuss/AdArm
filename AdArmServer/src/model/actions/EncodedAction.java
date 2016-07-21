package model.actions;

public class EncodedAction {
	private final int element;
	private final int method;
	private final int value;
	private final int value2;
	private final int value3;
	private int value4;

	public EncodedAction(int element, int method, int value, int value2, int value3, int value4) {
		this.element = element;
		this.method = method;
		this.value = value;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
	}

	public int[] getValue() {
		return new int[] { element, method, value, value2, value3, value4 };
	}
}
