/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.net.ConnectException;

/**
 * @author laixiangqun
 * @since 2018-8-17
 */
public class DBFailureAnalyzer extends AbstractFailureAnalyzer<ConnectException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, ConnectException cause) {
        StringBuilder description = new StringBuilder(
                String.format("Binding to target %s failed:%n", cause.getMessage()));
        return new FailureAnalysis(description.toString(),
                "Update your application's configuration", cause);
    }
}
