package com.example.javaadvance.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。
 * 数据会被写到sink通道，从source通道读取。
 */
public class PipeHello {

    public static void main(String[] args) throws Exception {

        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sink = pipe.sink();

        // 向管道写数据
        String data = "message1111 num = "+ Math.random();
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.clear();
        buffer.put(data.getBytes());

        buffer.flip();

        while (buffer.hasRemaining()) {
            sink.write(buffer);
        }

        // 从管道读取数据
        Pipe.SourceChannel source = pipe.source();
        buffer.flip();
        int read = source.read(buffer);
        System.out.println("read = "+read);

    }
}
