package com.mikepoirier.reactormessaging;

public class MyMessage {
    private final String id;
    private final String key;
    private final String value;

    public MyMessage(String id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyMessage myMessage = (MyMessage) o;

        if (id != null ? !id.equals(myMessage.id) : myMessage.id != null) return false;
        if (key != null ? !key.equals(myMessage.key) : myMessage.key != null) return false;
        return value != null ? value.equals(myMessage.value) : myMessage.value == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
            "id='" + id + '\'' +
            ", key='" + key + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
