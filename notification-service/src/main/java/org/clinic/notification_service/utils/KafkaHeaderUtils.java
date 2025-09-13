package org.clinic.notification_service.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import java.nio.ByteBuffer;

public class KafkaHeaderUtils {
    public static String getHeader(ConsumerRecord<?, ?> r, String headerKey) {
        var it = r.headers().headers(headerKey).iterator();
        return it.hasNext() ? new String(it.next().value()) : null;
    }

    public static Integer getIntHeader(ConsumerRecord<?, ?> r, String headerKey) {
        Header header = r.headers().lastHeader(headerKey);
        return (header != null) ? ByteBuffer.wrap(header.value()).getInt() : null;
    }

    public static Long getLongHeader(ConsumerRecord<?, ?> r, String headerKey) {
        Header header = r.headers().lastHeader(headerKey);
        return (header != null) ? ByteBuffer.wrap(header.value()).getLong() : null;
    }
}
