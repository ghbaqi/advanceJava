package com.example.javaadvance.nio.reactor.mreactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 在Processor中，同样创建了一个静态的线程池，且线程池的大小为机器核数的两倍。
 * 每个Processor实例均包含一个Selector实例。同时每次获取Processor实例时均提交一个任务到该线程池，
 * 并且该任务正常情况下一直循环处理，不会停止。而提交给该Processor的SocketChannel通过在其Selector注册事件，
 * 加入到相应的任务中。由此实现了每个子Reactor包含一个Selector对象，并由一个独立的线程处理。
 */
public class Processor {

    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private static final ExecutorService SERVICE =
            Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    private Selector selector;

    public Processor() throws IOException {
        this.selector = SelectorProvider.provider().openSelector();
        start();
    }

    public void registerChannel(SocketChannel socketChannel) throws ClosedChannelException {
        socketChannel.register(this.selector, SelectionKey.OP_READ);
    }

    private void start() {
        SERVICE.submit((Runnable) () -> {
            while (true) {
                try {
                    if (selector.select(500) <= 0) {
                        continue;
                    }

                    // 获取选中的感兴趣事件
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(2048);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            int count = socketChannel.read(buffer);
                            if (count < 0) {
                                socketChannel.close();
                                key.cancel();
                                logger.info("read end socketchannel = " + socketChannel);
                                continue;
                            } else if (count == 0) {
                                logger.info("msg is empty socketchannel = " + socketChannel);
                                continue;
                            } else {
                                logger.info("read msg = {} , socketchannel = {} ", new String(buffer.array()), socketChannel);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public void wakeUp() {
        this.selector.wakeup();
    }
}
