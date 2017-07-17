package com.markovlabs.eros;

import java.util.HashMap;
import java.util.List;

public class ListTO<T> extends HashMap<String, List<T>> {
	
	private static final long serialVersionUID = 1L;

	public ListTO(String root, List<T> list){
		super();
		put(root, list);
	}
}
