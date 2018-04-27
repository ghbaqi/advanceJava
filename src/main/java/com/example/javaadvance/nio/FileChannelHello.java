package com.example.javaadvance.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
 * FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
 */
public class FileChannelHello {

    // 向FileChannel写数据
    public static void main(String[] args) throws Exception {

        RandomAccessFile aFile = new RandomAccessFile("D:\\toFile.txt", "rw");
        FileChannel channel = aFile.getChannel();
        String newData = "New String to write to file..." + Math.random();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        while (buf.hasRemaining()) {
            channel.write(buf);
        }

        channel.close();


    }
}
