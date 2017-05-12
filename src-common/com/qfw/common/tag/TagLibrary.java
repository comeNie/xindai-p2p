package com.qfw.common.tag;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public class TagLibrary extends AbstractTagLibrary {
	public static final String NAMESPACE = "http://www.qfw.com/jsf/fn";
	public TagLibrary() {
		super(NAMESPACE);
		try {
            Method[] methods = TagFunction.class.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (Modifier.isStatic(methods[i].getModifiers())) {
                    this.addFunction(methods[i].getName(), methods[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

	}
}
