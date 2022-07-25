package edu.neu.madcourse.team20_finalproject.game.system;

/**
 *
 */
public class Message {
    private long timestamp;
    private String content;

    public Message(long timestamp, String content) {
        this.timestamp = timestamp;
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
