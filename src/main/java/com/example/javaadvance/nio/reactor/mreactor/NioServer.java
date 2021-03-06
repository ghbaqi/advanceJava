package com.example.javaadvance.nio.reactor.mreactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 多 reactor 模型
 * 一个主 selector  ， 多个从 selector
 */

/**
 * 多Reactor
 * Netty中使用的Reactor模式，引入了多Reactor，也即一个主Reactor负责监控所有的连接请求，
 * 多个子Reactor负责监控并处理读/写请求，减轻了主Reactor的压力，降低了主Reactor压力太大而造成的延迟。
 * 并且每个子Reactor分别属于一个独立的线程，每个成功连接后的Channel的所有操作由同一个线程处理。
 * 这样保证了同一请求的所有状态和上下文在同一个线程中，避免了不必要的上下文切换，同时也方便了监控请求响应状态。
 */
public class NioServer {

    private static Logger logger = LoggerFactory.getLogger(NioServer.class);

    public static void main(String[] args) throws Exception {


        // 主 selector
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int coreNum = Runtime.getRuntime().availableProcessors();
        // 从 selector
        Processor[] processors = new Processor[coreNum];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor();
        }

        int index = 0;
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                keys.remove(key);
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptChannel.accept();
                    socketChannel.configureBlocking(false);
                    logger.info("accept request address = {}", socketChannel.getRemoteAddress());

                    // 随机取一个子 reactor 去处理
                    Processor processor = processors[(int) ((index++) % coreNum)];
                    processor.registerChannel(socketChannel);
                    processor.wakeUp();
                }
            }
        }


    }
}
