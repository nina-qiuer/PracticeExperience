package com.tuniu.gt.toolkit;

public interface Serializable {
    byte[] serialize();
    void unserialize(byte[] ss);
}
