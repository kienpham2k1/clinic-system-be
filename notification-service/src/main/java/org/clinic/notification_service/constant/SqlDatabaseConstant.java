package org.clinic.notification_service.constant;

public class SqlDatabaseConstant {
    //TABLE NOTIFICATION
    public static final String NOTIFICATION = "tbl_notification";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_RECIPIENT_ID = "recipient_id";
    public static final String NOTIFICATION_TYPE = "type";
    public static final String NOTIFICATION__SUBJECT = "subject";
    public static final String NOTIFICATION_MESSAGE = "message";
    public static final String NOTIFICATION_STATUS = "status";
    public static final String NOTIFICATION_METADATA = "metadata";
    //TABLE INBOX_EVENT
    public static final String OUTBOX_EVENT = "tbl_outbox_event";
    public static final String OUTBOX_EVENT_ID = "outbox_event_id";
    public static final String OUTBOX_EVENT_PAYLOAD = "payload";
    public static final String OUTBOX_EVENT_STATUS = "status";
    public static final String OUTBOX_EVENT_AGGREGATE_ID = "aggregate_id";
    public static final String OUTBOX_EVENT_AGGREGATE_TYPE = "aggregate_type";
    public static final String OUTBOX_EVENT_TOPIC = "topic";
    public static final String OUTBOX_EVENT_KEY = "key";
    public static final String OUTBOX_EVENT_CREATED_AT = "created_at";
    public static final String OUTBOX_EVENT_SENT_AT = "sent_at";
    //TABLE INBOX_EVENT
    public static final String INBOX_EVENT = "tbl_inbox_event";
    public static final String INBOX_EVENT_ID = "inbox_event_id";
    public static final String INBOX_EVENT_AGGREGATE_ID = "aggregate_id";
    public static final String INBOX_EVENT_PAYLOAD = "payload";
    public static final String INBOX_EVENT_RECEIVED_AT = "received_at";
    public static final String INBOX_EVENT_PROCESSED_AT = "processed_at";
    public static final String INBOX_EVENT_STATUS = "status";
    public static final String INBOX_EVENT_RETRIES = "retries";
}
