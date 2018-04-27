package com.example.javaadvance.nio.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 经典Reactor模式中，尽管一个线程可同时监控多个请求（Channel），
 * 但是所有读/写请求以及对新连接请求的处理都在同一个线程中处理，
 * 无法充分利用多CPU的优势，同时读/写操作也会阻塞对新连接请求的处理。
 * 因此可以引入多线程，并行处理多个读/写操作
 */
// 多线程  reactor 模式
public class NioServerM {

    private static Logger logger = LoggerFactory.getLogger(NioServerM.class);

    public static void main(String[] args) throws Exception {

        // selctor
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        // 将 channel 注册到 selector ，关心 accept 事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.selectNow() < 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptChannel.accept();
                    socketChannel.configureBlocking(false);
                    logger.info("accept request address = " + socketChannel.getRemoteAddress());
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    selectionKey.attach(new Processor());
                } else if (key.isReadable()) {
                    Processor attachment = (Processor) key.attachment();
                    attachment.process(key);
                }
            }
        }

    }

}
