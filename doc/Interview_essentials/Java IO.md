# Java IO

## Java IO 流了解吗？ 

IO 即 Input/Output ，输⼊和输出。数据输⼊到计算机内存的过程即输⼊，反之输出到外部存储（⽐ 如数据库，⽂件，远程主机）的过程即输出。数据传输过程类似于⽔流，因此称为 IO 流。IO 流在 Java 中分为输⼊流和输出流，⽽根据数据的处理⽅式⼜分为字节流和字符流。

 Java IO 流的 40 多个类都是从如下 4 个抽象类基类中派⽣出来的。 

* InputStream / Reader : 所有的输⼊流的基类，前者是字节输⼊流，后者是字符输⼊流。 
* OutputStream / Writer : 所有输出流的基类，前者是字节输出流，后者是字符输出流。

## I/O 流为什么要分为字节流和字符流呢?

 问题本质想问：不管是⽂件读写还是⽹络发送接收，信息的最⼩存储单元都是字节，那为什么 I/O 流 操作要分为字节流操作和字符流操作呢？ 

主要有两点原因： 

* 字符流是由 Java 虚拟机将字节转换得到的，这个过程还算是比较耗时； 
* 如果我们不知道编码类型的话，使⽤字节流的过程中很容易出现乱码问题。

## BIO，NIO，AIO 有什么区别?

### BIO (Blocking I/O)

**BIO 属于同步阻塞 IO 模型** 。

同步阻塞 IO 模型中，应用程序发起 read 调用后，会一直阻塞，直到内核把数据拷贝到用户空间。

![图源：《深入拆解Tomcat & Jetty》](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6a9e704af49b4380bb686f0c96d33b81~tplv-k3u1fbpfcp-watermark.image)

在客户端连接数量不高的情况下，是没问题的。但是，当面对十万甚至百万级连接的时候，传统的 BIO 模型是无能为力的。因此，我们需要一种更高效的 I/O 处理模型来应对更高的并发量。

### NIO (Non-blocking/New I/O)

Java 中的 NIO 于 Java 1.4 中引入，对应 `java.nio` 包，提供了 `Channel` , `Selector`，`Buffer` 等抽象。NIO 中的 N 可以理解为 Non-blocking，不单纯是 New。它是支持面向缓冲的，基于通道的 I/O 操作方法。 对于高负载、高并发的（网络）应用，应使用 NIO 。

Java 中的 NIO 可以看作是 **I/O 多路复用模型**。也有很多人认为，Java 中的 NIO 属于同步非阻塞 IO 模型。

**同步非阻塞 IO 模型**。

![图源：《深入拆解Tomcat & Jetty》](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bb174e22dbe04bb79fe3fc126aed0c61~tplv-k3u1fbpfcp-watermark.image)

同步非阻塞 IO 模型中，应用程序会一直发起 read 调用，等待数据从内核空间拷贝到用户空间的这段时间里，线程依然是阻塞的，直到在内核把数据拷贝到用户空间。

相比于同步阻塞 IO 模型，同步非阻塞 IO 模型确实有了很大改进。通过轮询操作，避免了一直阻塞。

但是，这种 IO 模型同样存在问题：**应用程序不断进行 I/O 系统调用轮询数据是否已经准备好的过程是十分消耗 CPU 资源的。**

**I/O 多路复用模型**

![img](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/88ff862764024c3b8567367df11df6ab~tplv-k3u1fbpfcp-watermark.image)

IO 多路复用模型中，线程首先发起 select 调用，询问内核数据是否准备就绪，select是内核提供的系统调用，它支持一次查询多个系统调用的可用状态，当任意一个结果状态可用时就会返回，用户进程再发起一次read 调用进行数据读取。换句话说，就是NIO中N次的系统调用，借助select，只需要发起一次系统调用就够了。read 调用的过程（数据从内核空间 -> 用户空间）还是阻塞的。

但是，select有一个限制，就是存在连接数限制，针对于此，又提出了poll。其与select相比，主要是解决了连接限制。

select/epoll 虽然解决了BIO重复无效系统调用用的问题，但同时又引入了新的问题：

1. 用户空间和内核空间之间，大量的数据拷贝
2. 内核循环遍历IO状态，浪费CPU时间

换句话说，select/poll虽然减少了用户进程的发起的系统调用，但内核的工作量只增不减。在高并发的情况下，内核的性能问题依旧。所以select/poll的问题本质是：内核存在无效的循环遍历。

减少内核重复无效的循环遍历，引入了epoll，变主动为被动，基于事件驱动来实现，将就绪的fd（记录着进程使用文件的数据）提前注册保存。epoll相较于select/poll，多了两次系统调用，其中epoll_create建立与内核的连接，epoll_ctl注册事件，epoll_wait阻塞用户进程，等待IO事件。

> 目前支持 IO 多路复用的系统调用，有 select，epoll 等等。select 系统调用，目前几乎在所有的操作系统上都有支持。
>
> - **select 调用** ：内核提供的系统调用，它支持一次查询多个系统调用的可用状态。几乎所有的操作系统都支持。
> - **epoll 调用** ：linux 2.6 内核，属于 select 调用的增强版本，优化了 IO 的执行效率。

**IO 多路复用模型，通过减少无效的系统调用，减少了对 CPU 资源的消耗。**

Java 中的 NIO ，有一个非常重要的**选择器 ( Selector )** 的概念，也可以被称为 **多路复用器**。通过它，只需要一个线程便可以管理多个客户端连接。当客户端数据到了之后，才会为其服务。

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0f483f2437ce4ecdb180134270a00144~tplv-k3u1fbpfcp-watermark.image)

**BIO和NIO的区别**

* BIO以流的方式处理数据，NIO以块的方式处理数据，块IO的效率比流IO高很多。
* BIO是阻塞的，NIO是非阻塞的
* BIO基于字节流和字符流进行操作的，而NIO基于Channel（通道）和Buffer（缓冲区）进行操作的，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。selector（选择器）用于监听多个通道事件，因此使用单个线程就可以监听多个客户端通道



### AIO (Asynchronous I/O)

AIO 也就是 NIO 2。Java 7 中引入了 NIO 的改进版 NIO 2,它是异步 IO 模型。

异步 IO 是基于事件和回调机制实现的，也就是应用操作之后会直接返回，不会堵塞在那里，当后台处理完成，操作系统会通知相应的线程进行后续的操作。

![img](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3077e72a1af049559e81d18205b56fd7~tplv-k3u1fbpfcp-watermark.image)

目前来说 AIO 的应用还不是很广泛。Netty 之前也尝试使用过 AIO，不过又放弃了。这是因为，Netty 使用了 AIO 之后，在 Linux 系统上的性能并没有多少提升。

最后，来一张图，简单总结一下 Java 中的 BIO、NIO、AIO。

![img](https://images.xiaozhuanlan.com/photo/2020/33b193457c928ae02217480f994814b6.png)