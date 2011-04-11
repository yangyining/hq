package org.hyperic.hq.operation.rabbit.shared;

import org.hyperic.hq.operation.Envelope;
import org.hyperic.hq.operation.annotation.OperationDispatcher;
import org.hyperic.hq.operation.rabbit.util.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Helena Edelson
 */
public class EnvelopeTests {

    @Test
    public void test() {
        Envelope envelope = new Envelope(Constants.OPERATION_NAME_METRICS_REPORT, "test content", "test.response.exchange", OperationDispatcher.class);
        assertEquals("hq-agent.metrics.report", envelope.getOperationName());
        assertEquals("test context", envelope.getContent());
        assertEquals("test.response.exchange", envelope.getReplyTo());
    }
}