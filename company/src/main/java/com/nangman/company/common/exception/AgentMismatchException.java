package com.nangman.company.common.exception;

public class AgentMismatchException extends DefaultException {
    public AgentMismatchException() {
        super(ExceptionStatus.COMPANY_AGENT_MISMATCHED);
    }
}
