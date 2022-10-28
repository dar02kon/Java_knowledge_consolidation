# 面向对象编程

## 对象的创建与销毁

### 创建对象

Java是一门面向对象的编程语言，Java程序运行过程中无时无刻都有对象被创建出来。我们只需要通过关键字就可以构建一个对象。

我们可以随意的去创建对象，但清除对象我们基本没有去做过，不免担心会不会出现已经失去作用的对象依然大量存在充斥着内存最终造成程序“凝固”。在C++里对象的创建与释放都是我们手动来完成的，但 Java 以后，情况却发生了改观。Java 有一个特别的“垃圾收集器”，它会查找用new创建的所有对象，并辨别其中哪些不再被引用。随后，它会自动释放由那些闲置对象占据的内存，以便能由新对象使用。这意味着我们根本不必操心内存的回收问题。只需简单地创建对象，一旦不再需要它们，它们就会自动离去。（当然正因为我们将对象的销毁全权交给了“垃圾回收器”，我们更担心它的性能，以及如何调优。）

#### Java “创建”对象的方式

**使用new关键字创建对象**

```java
public class CreateByNew {
    //使用构造方法
    //我是A对象
    public static void main(String[] args) {
        A a = new A();
        a.show();
    }
}
class A{
    public A(){
        System.out.println("使用构造方法");
    }
    public void show(){
        System.out.println("创建了A对象");
    }
}
```

**反射**

使用Class类的`newInstance()`方法

`newInstance()`方法：调用类中的无参构造器，获取对应类的对象

```java
public class CreateByReflect {
    //使用构造方法
    //我是A对象
    public static void main(String[] args) {
        Class<A> aClass = A.class;
        try {
            A a = aClass.newInstance();
            a.show();
        } catch (Exception ignored) {
        }
    }
}
```

使用Constructor中的`newInstance()`方法

`getConstructor(Class... clazz)`：根据参数列表，只能获得对应的**公有参数**构造器对象

```java
try {//运行结果与上面一样
        Constructor<A> constructor = A.class.getConstructor();
        A a = constructor.newInstance();
        a.show();
} catch (Exception e) {
     e.printStackTrace();
}
```

**Clone**

通过Clone创建对象，首先要在实体类中**必须先实现Cloneable接口**并复写Object的clone方法（Object的这个方法是protected）

```java
class A implements Cloneable{
    private String s1;
    public String getS1() {
        return s1;
    }
    public void setS1(String s1) {
        this.s1 = s1;
    }
    @Override
    protected A clone() throws CloneNotSupportedException {
        return (A)super.clone();
    }
    public A(){
        System.out.println("使用构造方法");
    }
    public void show(){
        System.out.println("我是A对象");
    }
}
```

```java
public class CreateByClone {
    public static void main(String[] args) {
        A a = new A();//使用构造方法
        a.setS1("我是第一个A");
        try {
            A clone = a.clone();//我是A对象
            clone.setS1("我是第二个A");
            System.out.println(a);//com.dar.consolidation.object_oriented_programming.create_object.A@1b6d3586
            System.out.println(clone);//com.dar.consolidation.object_oriented_programming.create_object.A@4554617c
            System.out.println(a.getS1());//我是第一个A
            System.out.println(clone.getS1());//我是第二个A
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
```

这两个对象的句柄（引用）不相同，创建成功后修改属性值不会相互影响，虽然Clone是“克隆”的意思，但对象创建出来后是独立的

#### Java 创建对象的过程

以使用new来创建普通的Java对象为例子

**1、类加载检查**

当Java虚拟机遇到一条字节码new指令时，首先将去检查这个指令的参数是否能在常量池中定位到 一个类的符号引用，并且检查这个符号引用代表的类是否已被加载、解析和初始化过。如果没有，那必须先执行相应的类加载过程

**2、为新生对象分配内存**

对象所需内存的大小在类加载完成 后便可完全确定，为对象分配空间的任务实际上便等同于把一块确定大小的内存块从Java堆中划分出来。

* 指针碰撞：假设Java堆中内存是绝对规整的，所有被使用过的内存都被放在一 边，空闲的内存被放在另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲空间方向挪动一段与对象大小相等的距离

* 空闲列表：但如果Java堆中的内存并不是规整的，已被使用的内存和空闲的内存相互交错在一起，那 就没有办法简单地进行指针碰撞了，虚拟机就必须维护一个列表，记录上哪些内存块是可用的，在分 配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录

选择哪种分配方式由Java堆是否规整决定，而Java堆是否规整又由所采用的垃圾收集器是否带有空间压缩整理（Compact）的能力决定。因此，当使用Serial、ParNew等带压缩整理过程的收集器时，系统采用的分配算法是指针碰撞，既简单又高效；而当使用CMS这种基于清除 （Sweep）算法的收集器时，理论上就只能采用较为复杂的空闲列表来分配内存。

在CMS的实现里面，为了能在多数情况下分配得更快，设计了一个叫作Linear Allocation Buffer的分配缓冲区，通过空闲列表拿到一大块分配缓冲区之后，在它里面仍然可以使用指针碰撞方式来分配

对象创建在虚拟机中是非常频繁的行为，即使仅仅修改一个指针所指向的位置，在并发情况下也并不是线程安全的，可能出现正在给对象 A分配内存，指针还没来得及修改，对象B又同时使用了原来的指针来分配内存的情况。

* 对分配内存空间的动作进行同步处理，虚拟机是采用CAS配上失败重试的方式保证更新操作的原子性；
* 把内存分配的动作按照线程划分在不同的空间之中进行，即每个线程在Java堆中预先分配一小块内存，称为本地线程分配缓冲（Thread Local Allocation Buffer，TLAB），哪个线程要分配内存，就在哪个线程的本地缓冲区中分配，只有本地缓冲区用完了，分配新的缓存区时才需要同步锁定。虚拟机是否使用TLAB，可以通过-XX：+/-UseTLAB参数来设定。

**3、初始化为零值**

内存分配完成之后，虚拟机必须将分配到的内存空间（但不包括对象头）都初始化为零值，如果使用了TLAB的话，这一项工作也可以提前至TLAB分配时顺便进行。这步操作保证了对象的实例字段 在Java代码中可以不赋初始值就直接使用，使程序能访问到这些字段的数据类型所对应的零值。

**4、对对象进行必要的设置**

例如这个对象是哪个类的实例、如何才能找到类的元数据信息、对象的哈希码（实际上对象的哈希码会延后到真正调用Object::hashCode()方法时才计算）、对象的GC分代年龄等信息。这些信息存放在对象的对象头（Object Header）之中。根据虚拟机当前运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。

这时候对于JVM来说新对象已经产生了，但对于我们来说还差一步即按照我们的意图对对象进行赋值

**5、调用构造函数为对象赋值**

**这也说明了构造函数是不能创建对象的，它只是对对象进行赋值**

一般来说（由字节码流中new指令后面是否跟随invokespecial指令所决定，Java编译器会在遇到new关键字的地方同时生成这两条字节码指令，但如果直接通过其他方式产生的则不一定如此），new指令之后会接着执行 `<init>()`方法，按照程序员的意愿对对象进行初始化，这样一个真正可用的对象才算完全被构造出来。

**6、对象销毁（待完善）**

由垃圾回收器来完成

### 传递对象

```java
public class A {
    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public A(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
   public void show(){
       System.out.println("A{" +
               "name='" + name + '\'' +
               ", age=" + age +
               '}');
   }
}
```

```java
public class TransferObjectTest1 {
    /**
     * fun()函数调用前：A{name='test1', age=12}
     * fun()函数调用前com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     * fun()函数中：A{name='test2', age=21}
     * fun()函数中：com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     * fun()函数调用后：A{name='test2', age=21}
     * fun()函数调用后com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     */
    public static void main(String[] args) {
        A a = new A("test1",12);
        System.out.print("fun()函数调用前：");
        a.show();
        System.out.println("fun()函数调用前"+a);
        fun(a);
        System.out.print("fun()函数调用后：");
        a.show();
        System.out.println("fun()函数调用后"+a);
    }

    public static void fun(A a){
        a.setName("test2");
        a.setAge(21);
        System.out.print("fun()函数中：");
        a.show();
        System.out.println("fun()函数中："+a);
    }
}
```

从上面代码运行的结果中可用得出在函数体内修改对象的属性值会影响到外部对象内容，直接输出对象的句柄，打印出相同的对象地址（都是同一个对象所在的堆地址）

Java 中的所有自变量或参数传递都是通过传递句柄进行的。当我们传递“一个对象”时，实际传递的只是指向位于方法外部的那个对象的“一个句柄”。所以一旦要对那个句柄进行任何修改，便相当于修改外部对象。此外：

* 参数传递过程中会自动产生别名问题 
* 不存在本地对象，只有本地句柄 
* 句柄有自己的作用域，而对象没有
* 对象的“存在时间”在Java 里不是个问题 
* 没有语言上的支持（如常量）可防止对象被修改（以避免别名的副作用）

若需修改一个对象，同时不想改变调用者的对象，就要制作该对象的一个本地副本。若决定制作一个本地副本，只需简单地使用 clone()方法即可。Clone 是“克隆”的意思，即制作完全一模一样的副本。这个方法在基础类Object 中定义成“protected”（受保护）模式。但在希望克隆的任何衍生类中，必须将其覆盖为“public”模式。

```java
protected native Object clone() throws CloneNotSupportedException;
```

如果希望一个类能够克隆，那么： 

(1) 实现Cloneable 接口 

Object.clone()正式开始操作前，首先会检查一个类是否Cloneable，即是否具有克隆能力。

Cloneable interface （一个空接口）的实现扮演了一个标记的角色，封装到类的类型中。 两方面的原因促成了Cloneable interface 的存在。首先，可能有一个上溯造型句柄指向一个基础类型，而且不知道它是否真的能克隆那个对象。第二个原因是考虑到我们可能不愿所有对象类型都能克隆。所以Object.clone()会验证一个类是否真的是实现了Cloneable 接口。

(2) 覆盖clone() 

(3) 在自己的clone()中调用 super.clone() 

Object.clone()会检查原先的对象有多大，再为新对象腾出足够多的内存，将所有二进制位从原来的对象复制到新对象。（“按位复制”）

(4) 在自己的clone()中捕获违例

```java
public class CloneTest {
    public static void main(String[] args) {
        A a = new A("test",18);
        try {
            A clone = a.clone();
            System.out.println(a);//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(clone);//com.dar.consolidation.object_oriented_programming.transfer_object.A@4554617c
            clone.setName("clone");
            a.show();//A{name='test', age=18}
            clone.show();//A{name='clone', age=18}
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
```

通过克隆会得到两个看上去不同的对象

#### 深拷贝和浅拷贝

- **浅拷贝**：浅拷贝会在堆上创建一个新的对象（区别于引用拷贝的一点），不过，如果原对象内部的属性是引用类型的话，浅拷贝会直接复制内部对象的引用地址，也就是说拷贝对象和原对象共用同一个内部对象。
- **深拷贝** ：深拷贝会完全复制整个对象，包括这个对象所包含的内部对象。

如果一个类中包含其它类（对象中包含其它对象），通过Object.clone()克隆出的对象中那些作为属性的对象会是怎样一种存在呢？是同外部一样 “按位复制”还是仅仅是复制了句柄（引用）？

```java
public class B implements Cloneable{
    private String sex;
    private A a;

    @Override
    protected B clone() throws CloneNotSupportedException {
        return (B)super.clone();
    }
    public B(String sex, A a) {
        this.sex = sex;
        this.a = a;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
    }
}
```

```java
public class CloneTest2 {
    public static void main(String[] args) {
        A a = new A("test",12);
        B b = new B("男",a);
        try {
            B clone = b.clone();
            System.out.println(b.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(clone.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(b.getA().getName());//test
            clone.getA().setName("clone");
            System.out.println(b.getA().getName());//clone
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
```

从结果来看作为属性的对象只是简单的复制了引用

所以为了实现深拷贝（深层复制），我们需要遍历对象属性，克隆其中每个属性对象确保每个对象都被复制

对B中的`clone()`进行修改

```java
   @Override
    protected B clone() throws CloneNotSupportedException {
        B clone = (B) super.clone();
        clone.setA(clone.getA().clone());
        return clone;
    }
```

```java
public class CloneTest2 {
    public static void main(String[] args) {
        A a = new A("test",12);
        B b = new B("男",a);
        try {
            B clone = b.clone();
            System.out.println(b.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(clone.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@4554617c
            System.out.println(b.getA().getName());//test
            clone.getA().setName("clone");
            System.out.println(b.getA().getName());//test
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
```

#### 深拷贝的实现

* **通过重写clone方法来实现深拷贝(实现Cloneable接口)**

  与通过重写clone方法实现浅拷贝的基本思路一样，只需要为对象图的每一层的每一个对象都实现Clonneable接口并重写clone方法，最后在最顶层的类的重写的clone方法中调用所有的clone方法即可实现深拷贝。简单的说就是“每一层的每个对象都进行浅拷贝=深拷贝。

* **通过对象序列化实现深拷贝(实现Serializable接口)**

  对于属性数量比较多、层次比较深的类而言，每个类都要重写clone方法太过繁琐。将对象序列化为字节序列后，默认会将该对象的整个对象图进行序列化，再通过反序列即可完美地实现深拷贝。

  实体类需要实现Serializable接口

  ```java
  public class A implements Cloneable, Serializable
  ```

  ```java
  public class C implements Serializable {
      private String sex;
      private A a;
      public C(String sex, A a) {
          this.sex = sex;
          this.a = a;
      }
      public C myClone() {
          C c = null;
          try {
              // 写入字节流
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              ObjectOutputStream oos = new ObjectOutputStream(baos);
              oos.writeObject(this);
              ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
              ObjectInputStream ois = new ObjectInputStream(bais);
              c = (C) ois.readObject();
          } catch (IOException | ClassNotFoundException e) {
              e.printStackTrace();
          }
          return c;
      }
      public String getSex() {
          return sex;
      }
      public void setSex(String sex) {
          this.sex = sex;
      }
      public A getA() {
          return a;
      }
      public void setA(A a) {
          this.a = a;
      }
  }
  ```

  ```java
  public class CloneTest3 {
      public static void main(String[] args) {
          A a = new A("test",15);
          C c = new C("男",a);
          System.out.println(a);//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
          C c1 = c.myClone();
          System.out.println(c1);//com.dar.consolidation.object_oriented_programming.transfer_object.C@15aeb7ab
      }
  }
  ```

  

## 继承

在 Java 中通过 extends 关键字可以申明一个类是从另外一个类继承而来的

**继承特性：**

- 子类拥有父类非 private 的属性、方法。
- 子类可以拥有自己的属性和方法，即子类可以对父类进行扩展。
- 子类可以用自己的方式实现父类的方法。
- Java 的继承是单继承，但是可以多重继承，单继承就是一个子类只能继承一个父类，多重继承就是，例如 B 类继承 A 类，C 类继承 B 类，所以按照关系就是 B 类是 C 类的父类，A 类是 B 类的父类，这是 Java 继承区别于 C++ 继承的一个特性。
- 提高了类之间的耦合性（继承的缺点，耦合度高就会造成代码之间的联系越紧密，代码独立性越差）。
- 继承可以使用 extends 和 implements 这两个关键字来实现继承（implements 关键字可以变相的使java具有多继承的特性，使用范围为类继承接口的情况，可以同时继承多个接口），而且所有的类都是继承于 java.lang.Object，当一个类没有继承的两个关键字，则默认继承 Object（这个类在 **java.lang** 包中，所以不需要 **import**）祖先类。

通常，继承最终会以创建一系列类收场，所有类都建立在统一的接口基础上

我们所编写的代码以及逻辑很大一部分都是对实际生活进行抽象，生活中很多的“对象”都拥有共同的特性，如果每一个类中都去编写这些公共的属性会显得很累赘，那么不妨将这些公共的属性（或者行为）再进一步抽象出来放到另一个类中进行统一处理，需要这些属性或者方法的其它类只需要通过继承的方式就可以使用，不需要编写其它代码

以动物类为例

```java
public class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void eat(){
        System.out.println("animal eat");
    }
}
```

```java
public class Cat extends Animal{
    public Cat(String name, int age) {
        super(name, age);
    }
    @Override
    public void eat() {
        System.out.println(age + "岁的"+ name +"在吃鱼捏");
    }
}
```

```java
public class Dog extends Animal{
    public Dog(String name, int age) {
        super(name, age);
    }
    @Override
    public void eat() {
        System.out.println(age + "岁的"+ name+"在啃骨头捏");
    }
}
```

```java
public class TestAnimal {
    public static void main(String[] args) {
        Animal animal1 = new Cat("猫",1);
        Animal animal2 = new Dog("狗",3);
        animal1.eat();//1岁的猫在吃鱼捏
        animal2.eat();//3岁的狗在啃骨头捏
    }
}
```

**有没有办法让类不能被继承？**

使用 final 关键字声明类，把类定义定义为最终类，不能被继承， final 用于修饰方法，该方法不能被子类重写

final 含义为 "最终的"。final 可以用来修饰变量（包括类属性、对象属性、局部变量和形参）、方法（包括类方法和对象方法）和类。

![](http://rj5qpfcaf.hn-bkt.clouddn.com/java_consolidation/Snipaste_2022-10-26_17-03-45.png)

**构造器的执行顺序**

子类是不继承父类的构造器（构造方法或者构造函数）的，它只是调用（隐式或显式）。如果父类的构造器带有参数，则必须在子类的构造器中显式地通过 **super** 关键字调用父类的构造器并配以适当的参数列表。

如果父类构造器没有参数，则在子类的构造器中不需要使用 **super** 关键字调用父类构造器，系统会自动调用父类的无参构造器。

## 抽象类

Java 提供了一种机制，名为“抽象方法”。它属于一种不完整的方法，只含有一个声明，没有方法主体。

包含了抽象方法的一个类叫作“抽象类”。如果一个类里包含了一个或多个抽象方法，类就必须指定成 abstract（抽象）。

如果从一个抽象类继承，而且想生成新类型的一个对象，就必须为基础类中的所有抽象方法提供方法定义。 如果不这样做（完全可以选择不做），则衍生类也会是抽象的，而且编译器会强迫我们用abstract关键字标志那个类的“抽象”本质。 即使不包括任何abstract 方法，亦可将一个类声明成“抽象类”。如果一个类没必要拥有任何抽象方法，而且我们想禁止那个类的所有实例，就可以使用这种方式。

```java
public abstract class Animal {
    abstract void eat();
}
```

```java
public class Cat extends Animal{
    @Override
    void eat() {
        System.out.println("猫吃鱼");
    }
}
```

```java
public class Dog extends Animal {
    @Override
    void eat() {
        System.out.println("狗啃骨头");
    }
}
```

```java
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat();//猫吃鱼
        Dog dog = new Dog();
        dog.eat();//狗啃骨头
        Animal animal = new Animal() {
            @Override
            void eat() {
                System.out.println("123");
            }
        };
        animal.eat();
    }
}
```

**抽象类的约束**

- 抽象类不能被实例化，如果被实例化，就会报错，编译无法通过。只有抽象类的非抽象子类可以创建对象。
- 抽象类中不一定包含抽象方法，但是有抽象方法的类必定是抽象类。
- 抽象类中的抽象方法只是声明，不包含方法体，就是不给出方法的具体实现也就是方法的具体功能。
- 构造方法，类方法（用 static 修饰的方法）不能声明为抽象方法。
- 抽象类的子类必须给出抽象类中的抽象方法的具体实现，除非该子类也是抽象类。

## 接口

“interface”（接口）关键字使抽象的概念更深入了一层。我们可将其想象为一个“纯”抽象类。它允许创建者规定一个类的基本形式：方法名、自变量列表以及返回类型，但不规定方法主体。接口也包含了基本数据类型的数据成员，但它们都默认为static和final。接口只提供一种形式，并不提供实施的细节。

```java
public interface Animal {
    void eat();
    void play();
}
```

```java
public class Cat implements Animal{
    @Override
    public void eat() {
        System.out.println("猫在吃");
    }

    @Override
    public void play() {
        System.out.println("猫在玩");
    }
}
```

```java
public class Dog implements Animal{
    @Override
    public void eat() {
        System.out.println("狗在吃");
    }

    @Override
    public void play() {
        System.out.println("狗在玩");
    }
}
```

```java
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat();//猫在吃
        cat.play();//猫在玩
        Dog dog = new Dog();
        dog.eat();//狗在吃
        dog.play();//狗在玩
        Animal animal = new Animal() {
            @Override
            public void eat() {
                System.out.println("吃");
            }

            @Override
            public void play() {
                System.out.println("玩");
            }
        };
        animal.eat();//吃
        animal.play();//玩
    }
}
```

接口并不是类，编写接口的方式和类很相似，但是它们属于不同的概念。类描述对象的属性和方法。接口则包含类要实现的方法。

除非实现接口的类是抽象类，否则该类要定义接口中的所有方法。

接口无法被实例化，但是可以被实现。一个实现接口的类，必须实现接口内所描述的所有方法，否则就必须声明为抽象类。另外，在 Java 中，接口类型可用来声明一个变量，他们可以成为一个空指针，或是被绑定在一个以此接口实现的对象。

**Java 的“多重继承”**

由于接口根本没有具体的实施细节——也就 是说，没有与存储空间与“接口”关联在一起——所以没有任何办法可以防止多个接口合并到一起。

在一个衍生类中，我们并不一定要拥有一个抽象或具体（没有抽象方法）的基础类。如果确实想从一个非接口继承，那么只能从一个继承。剩余的所有基本元素都必须是“接口”。我们将所有接口名置于 implements 关键字的后面，并用逗号分隔它们。可根据需要使用多个接口，而且每个接口都会成为一个独立的类型，可对其进行上溯造型。

同时，利用继承可方便地为一个接口添加新的方法声明，也可以将几个接口合并成一个新接口

## 面向对象设计原则

* 开闭原则：对扩展开放，对修改关闭

  通过“抽象约束、封装变化”来实现开闭原则，即通过接口或者抽象类为软件实体**定义一个相对稳定的抽象层**，而将相同的可变因素封装在相同的具体实现类中。

* 里氏替换原则：不要破坏继承体系

  所有引用基类的地方必须能透明地使用其子类对象即一个可以接受基类对象的地方必然可以接受一个子类对象

* 依赖倒置原则：面向抽象层编程

  不应针对具体类编程，每个类尽量提供接口或抽象类，或者两者都具备。变量的声明类型尽量是接口或者是抽象类。任何类都不应该从具体类派生。使用继承时尽量遵循里氏替换原则。

* 单一职责原则：实现类要职责单一

  一个类只负责一项职责，一个方法只负责处理一项事情。

* 合成-聚合复用原则：优先使用组合或者聚合关系复用，少用继承关系复用

  合成复用原则是通过将已有的对象纳入新对象中，作为新对象的成员对象来实现的，新对象可以调用已有对象的功能，从而达到复用。

* 迪米特法则：降低耦合度

  “只与你的直接朋友交谈，不跟“陌生人”说话”。即如果两个软件实体无须直接通信，那么就不应当发生直接的相互调用，可以**通过第三方转发该调用。**

  • 从依赖者的角度来说，只依赖应该依赖的对象；
  • 从被依赖者的角度说，只暴露应该暴露的方法。

  在类的划分上，应该创建弱耦合的类。类与类之间的耦合越弱，就越有利于实现可复用的目标。
  在类的结构设计上，尽量降低类成员的访问权限。
  在类的设计上，优先考虑将一个类设置成不变类。
  在对其他类的引用上，将引用其他对象的次数降到最低。
  不暴露类的属性成员，而应该提供相应的访问器（set和get方法）。
  谨慎使用序列化（Serializable）功能.

* 接口隔离原则：设计接口的时候要精简单一

  **它和单一职责原则差不多，一个接口只服务于一个子模块或业务逻辑。只是单一职责是侧重于约束类和方法。而借口隔离侧重约束接口**

  接口尽量小，但是要有限度。一个接口只服务于一个子模块或业务逻辑。
  为依赖接口的类定制服务。只提供调用者需要的方法，屏蔽不需要的方法。
  了解环境，拒绝盲从。每个项目或产品都有选定的环境因素，环境不同，接口拆分的标准就不同，深入了解业务逻辑。
  提高内聚，减少对外交互。使接口用最少的方法去完成最多的事情。

