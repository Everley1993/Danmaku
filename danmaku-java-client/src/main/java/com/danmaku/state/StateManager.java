package com.danmaku.state;

import java.util.ArrayList;
import java.util.List;

import com.danmaku.common.Lockable;

public class StateManager extends Lockable {

	private List<OnStateChangedListener> listeners;

	public static enum State {
		STATE_STOP, STATE_RUNNING, STATE_PAUSE
	}

	private State state;

	public StateManager() {
		listeners = new ArrayList<OnStateChangedListener>();
		this.state = State.STATE_STOP;
	}

	public State getState() {
		return this.state;
	}

	public boolean checkState(State targetState) {
		return this.state == targetState;
	}

	public void setState(State newState) {
		State oldState = this.state;
		this.state = newState;
		notifyListeners(oldState, newState);
	}

	public void addOnStateChangedListener(OnStateChangedListener l) {
		listeners.add(l);
	}

	public void removeOnStateChangedListener(OnStateChangedListener l) {
		listeners.remove(l);
	}

	public void notifyListeners(State oldState, State newState) {
		for (OnStateChangedListener l : listeners) {
			l.OnStateChanged(oldState, newState);
		}
	}

	public interface OnStateChangedListener {
		/*
		 * 由于StateManager是可以被锁住的
		 * 所以尽量不要在这个函数中调用StateManager::lock()，避免造成死锁
		 */
		public void OnStateChanged(State oldState, State newState);
	}
}
