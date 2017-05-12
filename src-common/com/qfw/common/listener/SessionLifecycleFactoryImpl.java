package com.qfw.common.listener;

import com.sun.faces.lifecycle.LifecycleFactoryImpl;

public class SessionLifecycleFactoryImpl extends LifecycleFactoryImpl {
	public static final String SESSION_LIFECYCLE = "SessionLifecycle";

	public SessionLifecycleFactoryImpl() {
		super();
		addLifecycle(SESSION_LIFECYCLE, new SessionLifecycleImpl());
	}
}