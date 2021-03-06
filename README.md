# better-elk

- elk
- knife4j



# docker挂载文件的更新问题

    问题： 1. 更新了宿主机的文件内容，挂载到docker容器内的文件没有同步更新
    
    原因： docker挂载文件基于inode。vim等编辑工具保存文件时，并非直接保存，而是将一份新的临时文件覆盖了旧文件。对于inode而言，原文件并未被修改。
   
    解决方案：
            1. 换用nano等直接更新文件的编辑工具
            2. 改为挂载目录。
            3. 修改vim配置，添加：set backupcopy=yes

    参考： https://github.com/moby/moby/issues/15793


# Lombok问题： 本地debug（编译）提示不通过，指的是找到lombok依赖包：
    
    具体查看： https://github.com/DemoMeng/better-skills/blob/master/SpringBoot%E5%90%88%E9%9B%86/SpringBoot%E5%BC%80%E5%8F%91%E9%81%87%E5%88%B0%E9%97%AE%E9%A2%98.md
    
    解决办法： 
        设置idea编译虚拟机参数 ： 
            Preferences -> Complier -> 加入参数：-Djps.track.ap.dependencies=false


# swagger文档报错问题
    
    问题： @ApiModelProperty() 中的example 属性使用不规范导致接口文档报错的问题： 
    解决及相关： 
        https://xiaoym.gitee.io/knife4j/faq/swaggerResourceInvalid.html#json%E6%A0%BC%E5%BC%8F%E9%9D%9E%E6%B3%95
        https://github.com/xiaoymin/swagger-bootstrap-ui/issues/377


# idea 设置VM虚拟机参数：
    
    -Xms512m -Xmx512m -Xmn200m


    -Xms:	
        初始堆大小，等价于-XX:InitialHeapSize	
        物理内存的1/64
        默认(MinHeapFreeRatio参数可以调整)空余堆内存小于40%时，JVM就会增大堆直到-Xmx的最大限制.

    -Xmx
        最大堆大小，等价于-XX:MaxHeapSize	
        物理内存的1/4	
        默认(MaxHeapFreeRatio参数可以调整)空余堆内存大于70%时，JVM会减少堆直到 -Xms的最小限制

    -Xmn
        年轻代大小，设置了-Xmn之后-XX:NewSize和-XX:MaxNewSize都为该数值  
        默认XX:NewSize为-XX:InitialHeapSize的1/3 XX:MaxNewSize为-XX:MaxHeapSize的1/3

        注意：此处的大小是（eden+ 2 survivor space).与jmap -heap中显示的New gen是不同的。
        整个堆大小=年轻代大小 + 年老代大小 + 持久代大小.
        增大年轻代后,将会减小年老代大小.此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8

    -Xss
        每个线程的栈大小	
        1m	
        JDK5.0以后每个线程栈大小为1M，之前每个线程栈大小为256K。在相同物理内存下，减小这个值能生成更多的线程，当然操作系统对一个进程内的线程数还是有限制的，不能无限生成。
        线程栈的大小是个双刃剑，如果设置过小，可能会出现栈溢出，特别是在该线程内有递归、大的循环时出现溢出的可能性更大，如果该值设置过大，就有影响到创建栈的数量，如果是多线程的应用，就会出现内存溢出的错误。

    -XX:PermSize	
        非堆内存初始值	
        物理内存的1/64
        java8及之后就不支持了，警告如下：Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=128m; support was removed in 8.0


    -XX:MaxPermSize	
        最大非堆内存的大小	
        物理内存的1/4	
        java8及之后就不支持了，同上

    -XX:MetaspaceSize	
        元空间初始大小	
        21m	
        一旦元空间的大小达到初始大小，就会触发Full GC并会卸载没有用的类，然后该值将会向MaxMetaspaceSize扩大一点。
        如果初始化的高水位线设置过低，会频繁的触发Full GC，高水位线会被多次调整。所以为了避免频繁GC以及调整高水位线，建议将-XX:MetaspaceSize设置为较高的值。

    -XX:MaxMetaspaceSize	
        元空间最大大小	
        物理内存的总大小	
        默认情况下，元空间最大的大小是系统内存的大小，元空间一直扩大，虚拟机可能会消耗完所有的可用系统内存。一般不建议修改-XX:MaxMetaspaceSize。

    -XX:NewRatio	
        老年代（不包含永久区）和新生代（Eden+2*S）的比值	
        2	 

    -XX:SurvivorRatio	
        Eden区和Survivor区的比值	
        8	 

    -XX:MaxTenuringThreshold	
        设置Survivor区的最大分代年龄	
        15	
        具体参看JVM系列之内存分配和回收策略中对象的衰老过程

    -XX:ReservedCodeCacheSize	
        设置代码缓存的大小	
        240m	
        用来存储已编译方法生成的本地代码，如果代码缓存被占满，JVM会打印出一条警告消息，并切换到interpreted-only 模式：JIT编译器被停用，字节码将不再会被编译成机器码。因此，应用程序将继续运行，但运行速度会降低一个数量级，直到有人注意到这个问题。


