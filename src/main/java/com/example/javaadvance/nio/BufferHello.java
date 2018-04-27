package com.example.javaadvance.nio;

/**
 * Java NIO中的Buffer用于和NIO通道进行交互。如你所知，数据是从通道读入缓冲区，从缓冲区写入到通道中的。
 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，
 * 并提供了一组方法，用来方便的访问该块内存。
 */

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer的基本用法
 * 使用Buffer读写数据一般遵循以下四个步骤：
 * <p>
 * 写入数据到Buffer
 * 调用flip()方法
 * 从Buffer中读取数据
 * 调用clear()方法或者compact()方法
 * 当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，
 * 需要通过flip()方法将Buffer从写模式切换到读模式。在读模式下，可以读取之前写入到buffer的所有数据。
 * 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：
 * 调用clear()或compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据。
 * 任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
 */

/**
 * capacity
 * 作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
 * <p>
 * position
 * 当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
 * 当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
 * <p>
 * limit
 * 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
 * 当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
 */

/**
 * Buffer的类型
 ByteBuffer
 MappedByteBuffer
 CharBuffer
 DoubleBuffer
 FloatBuffer
 IntBuffer
 LongBuffer
 ShortBuffer
 */
public class BufferHello {

    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\aaa.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 5 bytes
        ByteBuffer buf = ByteBuffer.allocate(5);

        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {

            buf.flip();  //make buffer ready for read

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // read 1 byte at a time
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
            System.out.println();
        }
        aFile.close();

    }


}
