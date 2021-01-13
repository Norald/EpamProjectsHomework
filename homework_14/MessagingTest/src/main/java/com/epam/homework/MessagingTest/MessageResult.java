package com.epam.homework.MessagingTest;

public class MessageResult {
    private int id;
    private int result;

    public MessageResult() {
    }

    public MessageResult(int id, int result) {
        this.id = id;
        this.result = result;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MessageResult{" +
                "id=" + id +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResult that = (MessageResult) o;

        if (id != that.id) return false;
        return result == that.result;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + result;
        return result1;
    }
}
