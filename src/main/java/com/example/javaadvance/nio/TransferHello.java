package com.example.javaadvance.nio;

// 通道之间的数据传输

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 在Java NIO中，如果两个通道中有一个是FileChannel，那你可以直接将数据从一个 channel 传输到另外一个channel。
 */
public class TransferHello {

    public static void main(String[] args) throws Exception {

        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        ByteBuffer buffer = ByteBuffer.allocate(50);
        toChannel.read(buffer);
        long res = toChannel.transferFrom(fromChannel, position, count);
    }
}
