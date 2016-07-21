package model;

import static model.elements.Robot.*;

import java.util.LinkedList;
import java.util.List;

import model.actions.Action;

public abstract class Program {
	private List<Action> actions = new LinkedList<>();
	private int actionsDone = 0;

	public Program() {
		enqueue(system.attach());
		init();
		enqueue(system.wait(500));
		enqueue(system.detach());
	}

	/**
	 * this method should init all moves program should do. Use methods startPosition and next to add moves
	 */
	protected abstract void init();

	protected void startPosition() {
		actions.add(C0.move(20));
		actions.add(C1.move(0));
		actions.add(C2.move(0));
	}

	protected void startPositionFast() {
		actions.add(C0.moveFast(20));
		actions.add(C1.moveFast(0));
		actions.add(C2.moveFast(0));
	}

	protected void enqueue(Action... action) {
		for (Action a : action) {
			actions.add(a);
		}
	}

	public Action getNextAction() {
		actionsDone++;
		return actions.get(actionsDone - 1);
	}

	public float getProgress() {
		return (float) actionsDone / getProgramSize();
	}

	public int getProgramSize() {
		return actions.size();
	}

	public boolean hasNextAction() {
		return actionsDone < getProgramSize();
	}
}
