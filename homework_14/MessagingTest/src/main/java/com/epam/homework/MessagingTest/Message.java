package com.epam.homework.MessagingTest;

public class Message {
    private int id;
    private int valueOne;
    private int valueTwo;

    public Message() {
    }

    public Message(int valueOne, int valueTwo) {
        this.id = System.identityHashCode(this) ;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValueOne() {
        return valueOne;
    }

    public void setValueOne(int valueOne) {
        this.valueOne = valueOne;
    }

    public int getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(int valueTwo) {
        this.valueTwo = valueTwo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", valueOne=" + valueOne +
                ", valueTwo=" + valueTwo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (valueOne != message.valueOne) return false;
        return valueTwo == message.valueTwo;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + valueOne;
        result = 31 * result + valueTwo;
        return result;
    }
}
