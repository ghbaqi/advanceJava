package com.example.javaadvance.nio.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 在Reactor模式中，包含如下角色
 * <p>
 * Reactor 将I/O事件发派给对应的Handler
 * Acceptor 处理客户端连接请求
 * Handlers 执行非阻塞读/写
 */
// 单线程  reactor 模式
public class NioServer {
    private static Logger logger = LoggerFactory.getLogger(NioServer.class);

    public static void main(String[] args) throws Exception {

        // 多路复用 一个 selector 管理多个 channel
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  // channel 注册到 selector，关心事件 OP_ACCEPT

        while (selector.select() > 0) {   // 获取关心到的事件集合
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {               // 处理 accept 事件
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptChannel.accept();
                    socketChannel.configureBlocking(false);
                    logger.info("accept request address = " + socketChannel.getRemoteAddress());
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {          // 处理 readable 事件
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(2048);
                    int read = socketChannel.read(buffer);
                    if (read <= 0) {
                        socketChannel.close();
                        key.cancel();
                        logger.info("invalid data ,close");
                        continue;
                    }
                    logger.info("recevice data = " + new String(buffer.array()));
                }
                keys.remove(key);
            }
        }
    }
}
