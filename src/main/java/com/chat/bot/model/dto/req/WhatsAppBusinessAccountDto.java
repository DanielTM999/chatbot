package com.chat.bot.model.dto.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class WhatsAppBusinessAccountDto {
    @JsonProperty("object")
    private String object;

    @JsonProperty("entry")
    private List<Entry> entry;

    @Data
    @ToString
    public static class Entry {
        @JsonProperty("id")
        private String id;

        @JsonProperty("changes")
        private List<Change> changes;
    }

    @Data
    @ToString
    public static class Change {
        @JsonProperty("value")
        private Value value;

        @JsonProperty("field")
        private String field;
    }

    @Data
    @ToString
    public static class Value {
        @JsonProperty("messaging_product")
        private String messagingProduct;

        @JsonProperty("metadata")
        private Metadata metadata;

        @JsonProperty("contacts")
        private List<Contact> contacts;

        @JsonProperty("messages")
        private List<Message> messages;
    }

    @Data
    @ToString
    public static class Metadata {
        @JsonProperty("display_phone_number")
        private String displayPhoneNumber;

        @JsonProperty("phone_number_id")
        private String phoneNumberId;
    }

    @Data
    @ToString
    public static class Contact {
        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("wa_id")
        private String waId;
    }

    @Data
    @ToString
    public static class Profile {
        @JsonProperty("name")
        private String name;
    }

    @Data
    @ToString
    public static class Message {
        @JsonProperty("from")
        private String from;

        @JsonProperty("id")
        private String id;

        @JsonProperty("timestamp")
        private long timestamp;

        @JsonProperty("text")
        private Text text;

        @JsonProperty("type")
        private String type;
    }

    @Data
    @ToString
    public static class Text {
        @JsonProperty("body")
        private String body;
    }
}
