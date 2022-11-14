# Java集合（上）

## 泛型

### 为什么使用泛型

Java 泛型（generics）是 JDK 5 中引入的一个新特性, 泛型提供了编译时类型安全检测机制，该机制允许程序员在编译时检测到非法的类型。

泛型的本质是参数化类型，即给类型指定一个参数，然后在使用时再指定此参数具体的值，那样这个类型就可以在使用时决定了。这种参数类型可以用在类、接口和方法中，分别被称为泛型类、泛型接口、泛型方法。

引入泛型的主要原因之一就是因为容器类，在创建一个具体容器前其对象的类型是不确定的。

**与Object的区别**

因为我们知道有Object的存在，一切对象的父类，那么所有用泛型的地方是不是都可以替换为Object，在JDK5之前可能确实如此。

在Java SE 1.5之前，没有泛型的情况的下，通过对类型Object的引用来实现参数的“任意化”，“任意化”带来的缺点是要做显式的强制类型转换，而这种转换是要求开发者对实际参数类型可以预知的情况下进行的。对于强制类型转换错误的情况，编译器可能不提示错误，在运行的时候才出现异常，这是一个安全隐患。泛型的好处是在编译的时候检查类型安全，并且所有的强制转换都是自动和隐式的，提高代码的重用率。
Object由于它是所有类的父类，所以会强制类型转换，而T从一开始在编码时（注意是在写代码时）就限定了某种具体类型，所以它不用强制类型转换。（泛型在虚拟机中会被JVM擦除掉它的具体类型信息）

同时泛型比Object更加灵活，虽然它们都可以代表任意类型，但是泛型允许我们指定一定的范围，即对类型可以有一定程度的限制，形如`? extends T`或`? super T`

**使用泛型的好处**

* 保证了类型的安全性。

  在没有泛型之前，从集合中读取到的每一个对象都必须进行类型转换，如果不小心插入了错误的类型对象，在运行时的转换处理有可能出错。

  ```java
  public class ObjectTest1 {
      public static void main(String[] args) {
          ArrayList list = new ArrayList();
          list.add("123");
          list.add(1);
          list.add(1.0);
          System.out.println(list);//[123, 1, 1.0]
      }
  }
  ```

  编译和运行是没有问题的，因为`ArrayList`是用Object类型的数组来存储数据，但这种能运行会给我们对集合元素的处理带来很多风险，对数据进行一些处理时需要将它转为具体的类型，但是能不能转型成功我们很难把握

  而使用了泛型，在编辑阶段我们就会得到有关类型的一些反馈

  ![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-10_22-02-47.png)

* 消除类型转换。

  在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，也就是说在运行之前泛型的类型信息就已经确定下来了

* 提高了代码的重用性。

  泛型实现了参数化类型的概念，使一段代码可以应用于多种类型环境

**关于泛型能避免不必要的装箱、拆箱操作，提高程序的性能。**

在非泛型编程中，将简单类型作为Object传递时会引起装箱和拆箱操作，这两个过程都是具有很大开销的。而所谓装箱就是把基本数据类型存储在堆中，所以要把他变成引用类型；反过来所谓拆箱，就是把引用类型中的数据放到栈中，要把它变成基本数据类型。这两种操作的开销是很大的。

我觉得**泛型能避免不必要的装箱、拆箱操作，提高程序的性能**这种表达有些不妥，泛型中的类型参数只能代表引用型类型，不能是原始类型，在需要用到一些基本数据类型时也只能使用其对应的包装类，装箱和拆箱仍然会存在。与其说避免不必要的装箱、拆箱操作，倒不如说泛型避免了不必要的类型转换，因为在你使用泛型时它的类型就应该已经确定了（暂时这么说），在运行时是没有泛型这个概念的。

### 使用泛型

#### 泛型方法

**定义规则**

- 所有泛型方法声明都有一个类型参数声明部分（由尖括号分隔），该类型参数声明部分在方法返回类型之前。
- 每一个类型参数声明部分包含一个或多个类型参数，参数间用逗号隔开。一个泛型参数，也被称为一个类型变量，是用于指定一个泛型类型名称的标识符。
- 类型参数能被用来声明返回值类型，并且能作为泛型方法得到的实际参数类型的占位符。
- 泛型方法体的声明和其他方法一样。注意类型参数只能代表引用型类型，不能是原始类型（像 **int、double、char** 等）。

**java 中泛型标记符：**

- **E** - Element (在集合中使用，因为集合中存放的是元素)
- **T** - Type（Java 类）
- **K** - Key（键）
- **V** - Value（值）
- **N** - Number（数值类型）
- **？** - 表示不确定的 java 类型

```java
public class GenericsTest1 {
    // 泛型方法 printArray
    public static <E> void printArray(E[] inputArray) {
        // 输出数组元素
        for (E element : inputArray) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        // 创建不同类型数组： Integer, Double 和 Character
        Integer[] intArray = {1, 2, 3, 4, 5};
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
        Character[] charArray = {'H', 'E', 'L', 'L', 'O'};

        System.out.println("整型数组元素为:");
        printArray(intArray); // 传递一个整型数组

        System.out.println("双精度型数组元素为:");
        printArray(doubleArray); // 传递一个双精度型数组

        System.out.println("字符型数组元素为:");
        printArray(charArray); // 传递一个字符型数组
    }
}
```

运行结果

```
整型数组元素为:
1 2 3 4 5 
双精度型数组元素为:
1.1 2.2 3.3 4.4 
字符型数组元素为:
H E L L O 
```

Java中的泛型，只在编译阶段有效。在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，成功编译过后的class文件中是不包含任何泛型信息的。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_15-24-26.png)

#### 泛型类

泛型类的声明和非泛型类的声明类似，除了在类名后面添加了类型参数声明部分。

和泛型方法一样，泛型类的类型参数声明部分也包含一个或多个类型参数，参数间用逗号隔开。一个泛型参数，也被称为一个类型变量，是用于指定一个泛型类型名称的标识符。因为他们接受一个或多个参数，这些类被称为参数化的类或参数化的类型。

我们定义一个泛型类

```java
class Container<T>{
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
```

我们在创建一个泛型类后，其类型就已经确定（我们只能假装知道类型）

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_15-35-12.png)

```java
public class GenericsTest2 {
    public static void main(String[] args) {
        Container<Integer> container1 = new Container<>();
        container1.setT(15);
        System.out.println(container1.getT());//15
        Container<Double> container2 = new Container<>();
        System.out.println(container2.getT());//null
    }
}
```

如果使用的是Object来代替泛型，我们在使用这些数据时还需要进行额外的类型转换

### 擦除

```java
public class GenericsTest3 {
    public static void main(String[] args) {
        Class class1 = new ArrayList<String>().getClass();
        Class class2 = new ArrayList<Integer>().getClass();
        System.out.println(class1==class2);//true
        System.out.println(Arrays.toString(class1.getTypeParameters()));//[E]
        System.out.println(Arrays.toString(class2.getTypeParameters()));//[E]

    }
}
```

我们很容易认为`ArrayList<String>()`与`ArrayList<Integer>()`是不同的类型，当我们尝试将一个Integer类型的数字放入`ArrayList<String>()`中编译是不会通过的，但上面的结果确认为它们是相同的类型

`getTypeParameters()`返回表示该类型声明的变量通过 `GenericDeclaration`对象表示的泛型声明  `TypeVariable`对象的数组，从输出上来看它发现的只是用作参数占位符的标识符，这并非我们想要的类型信息。

所以有这样一个结论：**在泛型代码内部，无法获得任何有关泛型参数类型的信息**

Java泛型是使用擦除来实现的，它是一个折中的方案。如果泛型在Java1.0中就出现将不会使用擦除，而是使用具体化，使类型参数保持为第一类实体，这样就能在类型参数上执行基于类型的语言操作和反射操作。

擦除的动机是它使得泛化的客户端可以用非泛化的类库来使用，反之亦然，这也被称为“迁移兼容性”。

Java泛型不仅必须支持向后兼容性，即现有的代码和类文件仍旧合法，并且继续保持之前的含义，而且还要坚持迁移兼容性，使得之前的一些类库按照它们自己的节奏变为泛型，并且当某个类变为泛型时不会破坏依赖于它的代码和应用程序。通过使非泛型代码与泛型代码共存，擦除使得这种向着泛型的迁移成为可能

例如某个应用程序具有两个类库X，Y，且Y还要使用类库Z。随着泛型的出现，这个应用程序和类库的创建者可能希望将它们迁移到泛型上。但是在进行迁移时，他们有着不同的动机和限制。为了实现迁移兼容性，每个应用程序和类库都必须与其它所有的部分是否使用了泛型无关。这样，他们必须不具备探测其它类库是否使用泛型的能力，因此，某个类库使用了泛型这样的证据必须被“擦除”。擦除是否时唯一选择或者最佳选择需要时间去证明。

**擦除主要的正当理由就是从非泛化代码到泛化代码转变的过程，以及在不破坏现有的类库的情况下将泛型融入Java语言。**

但是擦除的代价是显著的，泛型不能用于显示的应用运行时类型的操作之中，因为所有关于参数类型的信息都已经丢失了。在编写泛型代码时我们只是看起来好像拥有有关参数的类型信息。例如像`List<T>`这样的类型会被擦除为`List`，普通的类型变量在未指定边界的情况下将被擦除为`Object`。

### 边界

擦除在方法体中移除了类型信息，所以在运行时的问题就是`边界`：即对象进入和离开方法的地点，这些是编译器在编译期执行类型检查并插入转型代码的地点

```java
public class GenericsTest4 {
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    public static void main(String[] args) {
        GenericsTest4 simpleHolder = new GenericsTest4();
        simpleHolder.setObject("Item");
        String s = (String) simpleHolder.getObject();
    }
}
```

`javap -c GenericsTest4`反编译

涉及到的一些字节码指令

* `aload_0`：将第一个引用类型本地变量推送至栈顶
* `aload_1`：将第二个引用类型本地变量推送至栈顶
* `getfield`：获取指定类的实例域，并将其压入栈顶
* `putfield`：为指定类的实例域赋值
* `areturn`：从当前方法返回对象引用
* `return`：从当前方法返回void
* `new`：创建一个对象，并将其引用值压入栈顶
* `dup`：赋值栈顶数值并将复制值压入栈顶
* `invokespecial`：调用超类构造方法，实例初始化方法，私有方法
* `invokevirtual`：调用实例方法
* `astore_1`：将栈顶引用型数值存入第二个本地变量
* `astore_2`：将栈顶引用型数值存入第三个本地变量
* `ldc`：将int，float或String型常量值从常量池中推送至栈顶
* `checkcast`：检验类型转换，检验未通过将抛出`ClassCastException`

`get()`方法

```
  public java.lang.Object getObject();
    Code:
       0: aload_0
       1: getfield      #2                  // Field object:Ljava/lang/Object;
       4: areturn
```

`set()`方法

```
  public void setObject(java.lang.Object);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #2                  // Field object:Ljava/lang/Object;
       5: return
```

`main()`方法

```
  public static void main(java.lang.String[]);
    Code:
       0: new           #3                  // class com/dar/consolidation/java_collections/GenericsTest4
       3: dup
       4: invokespecial #4                  // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: ldc           #5                  // String Item
      11: invokevirtual #6                  // Method setObject:(Ljava/lang/Object;)V
      14: aload_1
      15: invokevirtual #7                  // Method getObject:()Ljava/lang/Object;
      18: checkcast     #8                  // class java/lang/String
      21: astore_2
      22: return
```

`get()`和`set()`方法都是直接存储和产生值，而转型是在调用`get()`方法的时候接受检查的

使用泛型

```java
public class GenericsTest5<T> {
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public static void main(String[] args) {
        GenericsTest5<String> simpleHolder = new GenericsTest5<String>();
        simpleHolder.setObject("Item");
        String s = simpleHolder.getObject();
    }
}
```

调用`get()`方法后不需要在代码中实现转型，并且在使用`set()`方法时传递给`set()`的值在编译期会接受检查

相关字节码

```
  public T getObject();
    Code:
       0: aload_0
       1: getfield      #2                  // Field object:Ljava/lang/Object;
       4: areturn

  public void setObject(T);
    Code:
       0: aload_0
       1: aload_1
       2: putfield      #2                  // Field object:Ljava/lang/Object;
       5: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #3                  // class com/dar/consolidation/java_collections/GenericsTest5
       3: dup
       4: invokespecial #4                  // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: ldc           #5                  // String Item
      11: invokevirtual #6                  // Method setObject:(Ljava/lang/Object;)V
      14: aload_1
      15: invokevirtual #7                  // Method getObject:()Ljava/lang/Object;
      18: checkcast     #8                  // class java/lang/String
      21: astore_2
      22: return
```

两者的字节码是相同的。对`set()`的类型进行检查是不需要的，因为这将由编译器执行。而对`get()`返回的值进行转型仍旧是需要的，这与我们自己必须执行的操作是一样的（此处它将由编译器自动插入），所以我们写入和读取代码的噪声将更小。

由于所产生的`get()`和`set()`的字节码相同，所以泛型中的所有动作都发生在边界处——对传递进来的值进行额外的编译期检查，并插入对传递出去的值的转型，**边界就是发生动作的地方**

因为擦除我们失去了类型信息，所以，可以用无界泛型参数调用的方法只是那些Object可以调用的方法。但是如果将这个参数限制到某个类型子集，那么就可以用这个类型子集来调用方法。为了执行这种限制，Java重用了extends关键字

```java
public class GenericsTest6<T> {
    // 比较三个值并返回最大值
    public static <T extends Comparable<T>> T maximum(T x, T y, T z) {
        T max = x; // 假设x是初始最大值
        if (y.compareTo(max) > 0) {
            max = y; //y 更大
        }
        if (z.compareTo(max) > 0) {
            max = z; // 现在 z 更大
        }
        return max; // 返回最大对象
    }
    public static void main(String[] args) {
        System.out.printf("%d, %d 和 %d 中最大的为 %d\n",
                3, 4, 5, maximum(3, 4, 5));
        System.out.printf("%.1f, %.1f 和 %.1f 中最大的为 %.1f\n",
                6.6, 8.8, 7.7, maximum(6.6, 8.8, 7.7));
        System.out.printf("%s, %s 和 %s 中最大的为 %s\n", "pear",
                "apple", "orange", maximum("pear", "apple", "orange"));
    }
}
```

```
3, 4 和 5 中最大的为 5
6.6, 8.8 和 7.7 中最大的为 8.8
pear, apple 和 orange 中最大的为 pear
```

`T extends Comparable<T>`是得其对应的变量可以调用`compareTo()`方法

**通配符**

类型通配符一般是使用 **?** 代替具体的类型参数。例如 `List<?>` 在逻辑上是` List<String>,List<Integer> `等所有 `List<具体类型实参>` 的父类。

类型通配符上限通过形如`? extends Number`来定义，如此定义就是通配符泛型值接受Number及其下层子类类型。

类型通配符下限通过形如 `? super Number` 来定义，表示类型只能接受 Number 及其上层父类类型，如 Object 类型的实例。

```java
class Food{

}
class Fruit extends Food{

}
class Apple extends Fruit{

}
```

`<? extends T>`规定了上界，所以返回值可以不是Object，但插入时则有很多限制

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_21-08-09.png)

`set()`方法直接失效了，`get()`方法倒是可以用。这里的编译器根本不知道需要Fruit的哪个具体子类型，`? extends Fruit`意味着它可以是任何事物，编译器无法验证任何事物的类型安全。因此它不会接受任何类型的Fruit，编译器直接拒绝对参数列表中涉及通配符的方法（如`set()`）的调用。

```java
        GenericsTest5<? extends Fruit> simpleHolder = new GenericsTest5<>();
        GenericsTest5<Apple> apple = new GenericsTest5<>();
        simpleHolder = apple;
        Apple object = (Apple)simpleHolder.getObject();
```

上面这段代码放在`main()`函数中是可以运行的，`GenericsTest5<Apple>`不能向上转型为`GenericsTest5<Fruit>`，但可以将其向上转型为`GenericsTest5<? extends Fruit>`，如果调用`get()`方法，它会返回一个Fruit类型的引用，这就是在给定“任何拓展自Fruit对象”这一边界后，它所能知道的一切了。

`<? super T>`（超类型通配符）规定了下界，对于上界并不清楚，所以取变量时只能放到最根本的基类Object中，在设置变量时可选项则有很多

```java
public class GenericsTest5<T> {
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public static void main(String[] args) {
        GenericsTest5<? super Fruit> simpleHolder = new GenericsTest5<>();
        simpleHolder.setObject(new Apple());
        simpleHolder.setObject(new Fruit());
        Object object = simpleHolder.getObject();
    }
}
```

超类型边界放松了可以向方法传递的参数上所做的限制

**PECS原则**

*Producer Extends 生产者使用Extends来确定上界，往里面放东西来生产*

*Consumer Super 消费者使用Super来确定下界，往外取东西来消费*

* 频繁往外读取内容的，适合用上界Extends，即extends 可用于的返回类型限定，不能用于参数类型限定。
* 经常往里插入的，适合用下界Super，super 可用于参数类型限定，不能用于返回类型限定。
* 带有 super 超类型限定的通配符可以向泛型对象用写入，带有 extends 子类型限定的通配符可以向泛型对象读取

### 泛型带来的一些问题

**任何基本类型不能作为类型参数**

泛型不支持基本类型的自动包装机制，解决办法是使用基本类型的包装器类以及自动包装机制

```java
public class GenericsTest7{
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        for (int i : list) {
            System.out.print(i+" ");//0 1 2 3 4 
        }
    }
}
```

这种解决方案能够成功存储和读取int，有一些转换碰巧在发生的同时会对我们屏蔽掉。但是如果性能成为了问题，就需要使用专门适配基本类型的容器版本。

**一个类不能实现同一个泛型接口的两种变体**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_21-58-55.png)

编译是不会通过的，由于擦除上面的代码就意味着重复两次的实现了相同的接口

**转型和警告**

使用带有泛型类型参数的转型或`instanceof`不会有任何效果。

```java
class FixedSizeStack<T> {
    private int index = 0;
    private Object[] storage;

    public FixedSizeStack(int size) {
        this.storage = new Object[size];
    }

    public void push(T t){
        storage[index++] = t;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        return (T) storage[--index];
    }
}
class Test{
    public static final int SIZE = 10;
    public static void main(String[] args) {
        FixedSizeStack<String> stack = new FixedSizeStack<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            stack.push(i+"");
        }
        for (int i = 0; i < SIZE; i++) {
            System.out.print(stack.pop() + " ");//9 8 7 6 5 4 3 2 1 0 
        }
    }
}
```

由于擦除原因，编译器无法知道这个转型是否安全，并且pop()方法实际上并没有执行任何转型，T被擦除到第一个边界，默认是Object，因此，pop()实际上只是将Object转型为Object。如果没有`@SuppressWarnings`注解，编译器将对pop()产生“Unchecked cast”警告。

**重载**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_22-02-13.png)

由于擦除，重载的方法将产生相同的类型签名

**基类劫持了接口**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-13_22-05-06.png)

一旦基类实现了某个泛型接口，其导出类将无法重写泛型接口中的类型参数

## 迭代器Iterator

对持有事物的容器进行遍历是一种经常出现的操作，同时对List，Set之类具有很多共性的容器进行遍历希望遍历部分的代码能够得到很好的重用，没必要去写很多类似的代码。

**迭代器模式：指提供一种方法来访问聚合对象，而不用暴露这个对象的内部表示。迭代器模式又称为游标，属于对象行为型模式**

在Java中，迭代器是一个对象，它的工作是遍历并选择序列中的对象，而使用者不用关系该序列的底层结构。此外，迭代器被称为**轻量级对象**：创建它的代价小。因此，迭代器有很多限制，如`Iterator`只能单向移动

迭代器`Iterator`包含以下四个方法：

* `hasNext() :boolean` —— 如果迭代具有更多的元素，则返回`true` 即如果`next()`返回一个元素而不是抛出一个异常，则返回`true`  

* `next() :E` ——  返回迭代中的下一个元素

* `remove() :void` —— 删除当前元素，从底层集合中删除此迭代器返回的最后一个元素（可选操作）。此方法只能调用一次`next()` 。  如果底层集合在迭代过程中以任何方式进行修改而不是通过调用此方法，则迭代器的行为是未指定的。

  ```java
      default void remove() {//默认的实现抛出的一个实例UnsupportedOperationException并执行其他操作
          throw new UnsupportedOperationException("remove");
      }
  ```

* `forEachRemaining(Consumer) :void` —— JDK 8 中添加的，提供一个 lambda 表达式遍历容器元素。

  ```java
      default void forEachRemaining(Consumer<? super E> action) {//默认行为
          Objects.requireNonNull(action);//action - 要为每个元素执行的操作
          while (hasNext())
              action.accept(next());
      }
  ```

实现`Iterator`接口来完成数组的迭代遍历，使用泛型来完成多种数据类型的数组遍历

```java
class MyIterator<T> implements Iterator<T>{
    private int size;//数组大小
    private final T[] nums;//泛型数组，不能初始化，只能采用聚合的方式由外界传入
    private int count = 0;//遍历使用的下标
    MyIterator(T[] nums) {
        this.nums = nums;
        this.size = nums.length;
    }
    @Override
    public boolean hasNext() {
        return count<size;
    }
    @Override
    public T next() {
        return nums[count++];
    }

    @Override
    public void remove() {
        if(count>=size){
            Iterator.super.remove();
        } else {//删除当前下标所指元素
            for (int i = count; i < size-1; i++) {
                nums[count] = nums[count+1];
            }
            size = Math.max(size - 1, 0);//重新确定元素个数
        }
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        count=0;
        Iterator.super.forEachRemaining(action);
        count=0;
    }
}
```

```java
public class IteratorTest1{
    public static void main(String[] args) {
        String[] nums = new String[5];
        for (int i = 0; i < 5 ; i++) {
            nums[i] = i+"";
        }
        MyIterator<String> iterator = new MyIterator<>(nums);
        iterator.forEachRemaining(v-> System.out.print(v+" "));//0 1 2 3 4
        System.out.println();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("2")){
                iterator.remove();//删除2后面一个元素3
            }
            System.out.print(next + " ");//0 1 2 4
        }
        System.out.println();
        iterator.forEachRemaining(v-> System.out.print(v+" "));//0 1 2 4

    }
}
```

**ListIterator**

```java
public interface ListIterator<E> extends Iterator<E>
```

`ListIterator`是一个更强大的`Iterator`的子类型，它只能用于各种List类的访问，`ListIterator`可以双向移动，它还可以产生相对于迭代器在列表中指向的当前位置前一个和后一个元素的索引，并且可以使用set方法替换它访问过的最后一个元素。可以通过`listIterator(n)`来创建一个一开始就指向列表索引为n的元素处的`ListIterator`

**Iterable**

`Iterable`包含以下三个方法：

* `Iterator<T> iterator()`：返回类型为 `T`元素的迭代器。

* `default void forEach(Consumer<? super T> action)`：对`Iterable`的每个元素执行给定的操作，直到所有元素都被处理或动作引发异常。  除非实现类另有规定，否则按照迭代的顺序执行操作（如果指定了迭代顺序）。 动作抛出的异常被转发给呼叫者

  ```java
      default void forEach(Consumer<? super T> action) {//默认实现
          Objects.requireNonNull(action);
          for (T t : this) {
              action.accept(t);
          }
      }
  ```

* `default Spliterator<T> spliterator() `：在Iterable描述的元素上创建一个`Iterable` 。默认实现从iterable的Iterator创建一个early-binding拼接器。 Spliter继承了iterable的迭代器的fail-fast属性。

  ```java
      default Spliterator<T> spliterator() {
          return Spliterators.spliteratorUnknownSize(iterator(), 0);
      }
  ```

Iterable是迭代器的意思，作用是为集合类提供for-each循环的支持。由于使用for循环需要通过位置获取元素，而这种获取方式仅有数组支持，其他许多数据结构，比如链表，只能通过查询获取数据，这会大大的降低效率。Iterable就可以让不同的集合类自己提供遍历的最佳方式。

```
Implementing this interface allows an object to be the target of the "for-each loop" statement.
实现这个接口允许对象成为“for-each循环”语句的目标
```

如果现有一个`Iterable`类，想要添加一种或者多种在for-each语句中使用这个类的方法，应该怎么做呢？例如，我们希望可以选择向前或者向后遍历一个单词列表。如果直接继承这个类，覆盖方法`iterator()`，是无法做到选择的。

这时候可以使用适配器方法来解决问题，当已经有一个接口并需要另一个接口时，编写适配器就可以解决问题。

**适配器模式：将一个接口转换成客户希望的另一个接口，适配器模式可以使接口不兼容的类一起工作，其别名为包装器**

```java
public class IteratorTest3 {
    public static void main(String[] args) {
        ReversibleArrayList<String> list = new ReversibleArrayList<>(Arrays.asList("1 2 3 4 5 6 7 8".split(" ")));
        for (String s : list) {
            System.out.print(s + " ");//1 2 3 4 5 6 7 8
        }
        System.out.println();
        for (String s : list.reversed()) {
            System.out.print(s + " ");//8 7 6 5 4 3 2 1
        }
    }
}

class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<? extends T> c) {
        super(c);
    }

    public Iterable<T> reversed() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int current = size() - 1;

                    @Override
                    public boolean hasNext() {
                        return current > -1;
                    }

                    @Override
                    public T next() {
                        return get(current--);
                    }
                };
            }
        };
    }
}
```

**Consumer**

`Consumer`是一个函数式接口（可以采用lambda写法），它的源码如下

```java
@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);//对给定的参数执行此操作。

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    //返回一个组合的Consumer ， Consumer执行该操作，后跟after操作。 
    //如果执行任一操作会抛出异常，它将被转发到组合操作的调用者。 
    //如果执行此操作抛出一个异常， after操作将不被执行。
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

`Consumer`是一个接口，并且只要实现一个 `accept` 方法，就可以作为一个**“消费者”**输出信息。
其实，lambda 表达式、方法引用的返回值都是 **Consumer 类型**，所以，他们能够作为 `forEach` 方法的参数，并且输出一个值。

```java
public class ConsumerTest1 {
    public static void main(String[] args) {
        Student student = new Student("麻衣", 99);

        Consumer<Student> consumer = new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                student.setName("02");
                student.setScore(100);
            }
        };
        /**        使用lambda简写
         *         Consumer<Student> consumer = student1 -> {
         *             student1.setName("02");
         *             student1.setScore(100);
         *         };
         */
        consumer.accept(student);

        System.out.println("姓名：" + student.getName() + " ，分数：" + student.getScore());//姓名：02 ，分数：100
    }
}
```



## 集合概述

**数组的特点**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-07-17_11-29-38.png)

* 数组定义完成并启动后，类型确定、长度固定
* 不适合元素的个数和类型不确定的业务场景，更不适合做需要增删数据操作。
* 数组的功能也比较的单一，处理数据的能力并不是很强大。

**集合的特点**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-07-17_11-31-27.png)

* 集合的大小不固定，启动后可以动态变化，类型也可以选择不固定。
* 集合非常适合元素个数不能确定，且需要做元素的增删操作的场景。
* 同时，集合提供的种类特别的丰富，功能也是非常强大的，开发中集合用的更多。

**集合体系结构**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-07-17_11-34-49.png)

* 集合（Collection）：一组单独的元素，通常应用了某种规则。在这里，一个 List（列表）必须按特定 的顺序容纳元素，而一个Set（集）不可包含任何重复的元素。相反，“包”（Bag）的概念未在新的集合库 中实现，因为“列表”已提供了类似的功能。
*  映射（Map）：一系列“键－值”对（这已在散列表身上得到了充分的体现）。从表面看，这似乎应该成为一个“键－值”对的“集合”，但假若试图按那种方式实现它，就会发现实现过程相当笨拙。这进一步证明了应该分离成单独的概念。另一方面，可以方便地查看 Map 的某个部分。只需创建一个集合，然后用它表示那一部分即可。这样一来，Map 就可以返回自己键的一个Set、一个包含自己值的List 或者包含自己“键－值”对的一个List。和数组相似，Map 可方便扩充到多个“维”，只需简单地在一 个Map 里包含其他Map（后者又可以包含更多的Map，以此类推）。

**注意：集合都是泛型的形式，可以在编译阶段约束集合只能操作某种数据类型。集合和泛型都只能支持引用数据类型，不支持基本数据类型，所以集合中存储的元素都认为是对象。**


