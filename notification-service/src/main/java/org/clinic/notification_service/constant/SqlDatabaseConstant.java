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
    //TABLE OUTBOX_MESSAGE
    public static final String OUTBOX_MESSAGE = "tbl_outbox_message";
    public static final String OUTBOX_MESSAGE_ID = "outbox_message_id";
    public static final String OUTBOX_MESSAGE_EVENT_TYPE = "event_type";
    public static final String OUTBOX_MESSAGE_PAYLOAD = "payload";
    public static final String OUTBOX_MESSAGE_STATUS = "status";
    //TABLE INBOX_MESSAGE
    public static final String INBOX_MESSAGE = "tbl_inbox_message";
    public static final String INBOX_MESSAGE_ID = "inbox_message_id";
    public static final String INBOX_MESSAGE_MESSAGE_ID = "message_id";
    public static final String INBOX_MESSAGE_PAYLOAD = "payload";
}
