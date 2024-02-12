package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.Stack;

public class ExecutionData implements Shareable {
  Stack<Object> stack = new Stack<>();
}
