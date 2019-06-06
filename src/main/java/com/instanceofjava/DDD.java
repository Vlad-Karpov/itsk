package com.instanceofjava;

public class DDD {
    int GetValue() {
        return (true ? null : 0);
    }

    public static void main(String[] args) {
        DDD obj = new DDD();
        obj.GetValue();
    }
}