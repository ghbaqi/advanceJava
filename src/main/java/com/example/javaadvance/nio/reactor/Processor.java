package com.example.javaadvance.nio.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processor {

    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(16);

    public void process(SelectionKey key) {
        SERVICE.submit(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                int read = channel.read(buffer);
                if (read < 0) {
                    channel.close();
                    key.cancel();
                    logger.info("read end channel = "+channel);
                } else if (read == 0) {
                    return;
                }
                logger.info("read msg = "+new String(buffer.array()));
            } catch (IOException e) {
                e.printStackTrace();

            }
        });
    }
}
