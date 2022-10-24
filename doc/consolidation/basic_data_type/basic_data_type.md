# Java基本数据类型

## 主要类型

有一系列类需特别对待；可将它们想象成“基本”、“主要”或者“主”（Primitive）类型，进行程序设计时要频繁用到它们。之所以要特别对待，是由于用 new 创建对象（特别是小的、简单的变量）并不是非常有效，因为new将对象置于“堆”里。对于这些类型，Java 采纳了与 C 和 C++相同的方法。也就是说，不是用 new 创建变量，而是创建一个并非句柄的“自动”变量（句柄可以称作“引用”，甚至“指针”）。这个变量容纳了具体的值，并置于堆栈中，能够更高效地存取。 Java 决定了每种主要类型的大小。就像在大多数语言里那样，这些大小并不随着机器结构的变化而变化。这种大小的不可更改正是 Java 程序具有很强移植能力的原因之一。

**以下类型的默认值可以理解为作为成员变量时的默认值**

成员变量：

1. 成员变量是在类的范围里定义的变量；
2. 成员变量有默认初始值；
3. 未被static修饰的成员变量也叫实例变量，它存储于对象所在的堆内存中，生命周期与对象相同；
4. 被static修饰的成员变量也叫类变量，它存储于方法区中，生命周期与当前类相同。

局部变量：

1. 局部变量是在方法里定义的变量；
2. 局部变量没有默认初始值；
3. 局部变量存储于栈内存中，作用的范围结束，变量空间会自动的释放。

### void

**void是无返回值的意思**

**void的字面意思是“无类型”，void 则为“无类型指针”，void 可以指向任何类型的数据。void几乎只有“注释”和限制程序的作用，定义一个void变量没有意义。**

**void的作用在于对函数返回的限定以及对函数参数的限定**

### 布尔型

boolean 	大小：1 位	封装器类型：Boolean，只有`true`和`false`两种类型，默认为`false`

```java
public class BasicDataTypeTest {
    static boolean b1;
    public static void main(String[] args) {
        System.out.println(b1);//false
//        boolean b2;
//        System.out.println(b2);//报错：Variable 'b2' might not have been initialized
        b1 = true;
        System.out.println(b1);//true
    }
}
```

### 字符类型

大小：char 类型是一个单一的 16 位 Unicode 字符，

范围： Unicode 0 (**\u0000**) ——> Unicode 2 的 16 次方-1 (**\uffff**)

封装器类型：Character

默认值：`\u0000`转为int类型显示0

Unicode是为了解决传统的字符编码方案的局限而产生的，它为每种语言中的每个字符设定了统一并且唯一的二进制编码，以满足跨语言、跨平台进行文本转换、处理的要求。

char 数据类型可以储存任何字符；

```java
public class BasicDataTypeTest {
    static char c1;
    public static void main(String[] args) {
        System.out.println(c1);//\u0000 直接打印只会显示一个空格
        char c = '\u0000';
        System.out.println(c1==c);//true
        System.out.println((int) c1);//0
    }
}
```

### 整数型

**byte**

* 大小：byte 数据类型是8位、有符号的，以二进制补码表示的整数
* 范围：-128（-2^7）——> 127（2^7-1）
* 封装器类型：Byte
* 默认值：0
* byte 类型用在大型数组中节约空间，主要代替整数，因为 byte 变量占用的空间只有 int 类型的四分之一

**short**

* 大小：short 数据类型是 16 位、有符号的以二进制补码表示的整数
* 范围：-32768（-2^15）——> 32767（2^15 - 1）
* 封装器类型： Short
* 默认值：0
* Short 数据类型也可以像 byte 那样节省空间。一个short变量是int型变量所占空间的二分之一

**int**

* 大小：int 数据类型是32位、有符号的以二进制补码表示的整数
* 范围：-2 147 483 648（-2^31）——> 2 147 483 647（2^31 - 1）
* 封装器类型： Integer
* 默认值：0
* 一般地整型变量默认为 int 类型

**long**

* 大小：long 数据类型是 64 位、有符号的以二进制补码表示的整数；
* 范围：-9 223 372 036 854 775 808（-2^63）——> 9 223 372 036 854 775 807（2^63 -1）
* 封装器类型： Long
* 默认值：0L
* 主要使用在需要比较大整数的系统上

```java
public class BasicDataTypeTest {
    public static void main(String[] args) {
        // byte
        /**
         * 基本类型：byte 二进制位数：8
         * 包装类：java.lang.Byte
         * 最小值：Byte.MIN_VALUE=-128
         * 最大值：Byte.MAX_VALUE=127
         */
        System.out.println("基本类型：byte 二进制位数：" + Byte.SIZE);
        System.out.println("包装类：java.lang.Byte");
        System.out.println("最小值：Byte.MIN_VALUE=" + Byte.MIN_VALUE);
        System.out.println("最大值：Byte.MAX_VALUE=" + Byte.MAX_VALUE);
        System.out.println();

        // short
        /**
         * 基本类型：short 二进制位数：16
         * 包装类：java.lang.Short
         * 最小值：Short.MIN_VALUE=-32768
         * 最大值：Short.MAX_VALUE=32767
         */
        System.out.println("基本类型：short 二进制位数：" + Short.SIZE);
        System.out.println("包装类：java.lang.Short");
        System.out.println("最小值：Short.MIN_VALUE=" + Short.MIN_VALUE);
        System.out.println("最大值：Short.MAX_VALUE=" + Short.MAX_VALUE);
        System.out.println();

        // int
        /**
         * 基本类型：int 二进制位数：32
         * 包装类：java.lang.Integer
         * 最小值：Integer.MIN_VALUE=-2147483648
         * 最大值：Integer.MAX_VALUE=2147483647
         */
        System.out.println("基本类型：int 二进制位数：" + Integer.SIZE);
        System.out.println("包装类：java.lang.Integer");
        System.out.println("最小值：Integer.MIN_VALUE=" + Integer.MIN_VALUE);
        System.out.println("最大值：Integer.MAX_VALUE=" + Integer.MAX_VALUE);
        System.out.println();

        // long
        /**
         * 基本类型：long 二进制位数：64
         * 包装类：java.lang.Long
         * 最小值：Long.MIN_VALUE=-9223372036854775808
         * 最大值：Long.MAX_VALUE=9223372036854775807
         */
        System.out.println("基本类型：long 二进制位数：" + Long.SIZE);
        System.out.println("包装类：java.lang.Long");
        System.out.println("最小值：Long.MIN_VALUE=" + Long.MIN_VALUE);
        System.out.println("最大值：Long.MAX_VALUE=" + Long.MAX_VALUE);
        System.out.println();
    }
}
```



### 浮点型

**float**

* 大小：float 数据类型是单精度、32位、符合IEEE 754标准的浮点数
* 范围：IEEE754 IEEE754
* 封装器类型： Float
* 默认值：0.0f
* float 在储存大型浮点数组的时候可节省内存空间

**double**

* 大小：double 数据类型是双精度、64 位、符合 IEEE 754 标准的浮点数
* 范围：IEEE754 IEEE754
* 封装器类型：  Double
* 默认值：0.0d
* 浮点数的默认类型为 double 类型

```java
public class BasicDataTypeTest {
    public static void main(String[] args) {
        // float
        /**
         * 基本类型：float 二进制位数：32
         * 包装类：java.lang.Float
         * 最小值：Float.MIN_VALUE=1.4E-45
         * 最大值：Float.MAX_VALUE=3.4028235E38
         */
        System.out.println("基本类型：float 二进制位数：" + Float.SIZE);
        System.out.println("包装类：java.lang.Float");
        System.out.println("最小值：Float.MIN_VALUE=" + Float.MIN_VALUE);
        System.out.println("最大值：Float.MAX_VALUE=" + Float.MAX_VALUE);
        System.out.println();

        // double
        /**
         * 基本类型：double 二进制位数：64
         * 包装类：java.lang.Double
         * 最小值：Double.MIN_VALUE=4.9E-324
         * 最大值：Double.MAX_VALUE=1.7976931348623157E308
         */
        System.out.println("基本类型：double 二进制位数：" + Double.SIZE);
        System.out.println("包装类：java.lang.Double");
        System.out.println("最小值：Double.MIN_VALUE=" + Double.MIN_VALUE);
        System.out.println("最大值：Double.MAX_VALUE=" + Double.MAX_VALUE);
        System.out.println();
    }
}
```



IEEE二进制浮点数算术标准（IEEE 754）是20世纪80年代以来最广泛使用的浮点数运算标准，为许多CPU与浮点运算器所采用。这个标准定义了表示浮点数的格式（包括负零-0）与反常值（denormal number）），一些特殊数值（无穷（Inf）与非数值（NaN）），以及这些数值的“浮点数运算符”；它也指明了四种数值舍入规则和五种例外状况（包括例外发生的时机与处理方式）。

## 对主要类型的一些解释

### 关于除数与被除数为0

1.除数和被除数均为整型

```java
public class Test {
    public static void main(String[] args) {
        int a =1 ;
        int b = 0;
        System.out.println(a/b);
  }
}
```

运行结果：

```java
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at exercise.text02.text.main(text.java:9)
```

如果int/int中除数为0，会抛出异常java.lang.ArithmeticException: / by zero

程序终止运行

2.除数或被除数为浮点型

```java
public class Test {
    public static void main(String[] args) {
        double t = 1 / 0.0;
        System.out.println(t);
        if(t>1000)
            System.out.println("正数/0.0的结果很大");
        else
            System.out.println("不清楚");
        t = -1 / 0.0;
        if(t < -1000)
            System.out.println("负数/0.0的结果很小");
        else
            System.out.println("不清楚");
    }
}

```

运行结果：

```java
Infinity
正数/0.0的结果很大
负数/0.0的结果很小

```

Infinity 中译:	无穷，无限

正数/0.0 得到的结果是正无穷大，即Infenity

负数/0.0 得到的结果是负无穷大，即Infenity

3.强制转换

```java
public class Test {
    public static void main(String[] args) {
        double t = (int)(8 / 0.0);
        System.out.println(t);
    }
}
```

运行结果：

```
2.147483647E9//每个人的结果可能都不同
```

4.被除数和除数均为0.0（0.0 / 0.0）

```java
public class Test {
    public static void main(String[] args) {
        System.out.println(0.0 / 0.0);
        System.out.println(Math.sqrt(-8));
    }
}
```

运行结果：

```
NaN
NaN
```

NaN，是Not a Number的缩写。  NaN 用于处理计算中出现的错误情况，比如 0.0 除以 0.0 或者求负数的平方根。由上面的表中可以看出，对于单精度浮点数，NaN 表示为指数为 emax + 1 = 128（指数域全为 1），且尾数域不等于零的浮点数。IEEE 标准没有要求具体的尾数域，所以 NaN 实际上不是一个，而是一族。不同的实现可以自由选择尾数域的值来表达 NaN，比如 Java 中的常量 Float.NaN 的浮点数可能表达为 01111111110000000000000000000000，其中尾数域的第一位为 1，其余均为 0（不计隐藏的一位），但这取决系统的硬件架构。Java 中甚至允许程序员自己构造具有特定位模式的 NaN 值（通过 Float.intBitsToFloat() 方法）。比如，程序员可以利用这种定制的 NaN 值中的特定位模式来表达某些诊断信息。

### 最大值与最小值

#### 整数类型

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);//-2147483648
        System.out.println(Integer.MAX_VALUE);//2147483647
        System.out.println(Integer.MIN_VALUE + Integer.MAX_VALUE);//-1
    }
}
```

从结果来看最小值不是最大值的相反数，为什么呢？

数值类型全都是有符号（正负号）的，整数类型都是以二进制补码表示的整数。这也意味着最高位表示的是符号位。

**为什么使用二进制补码来表示呢？**

* 在一开始我们使用原码来表示数据，为了把生活应该有的正负概念表示出来把左边第一位腾出位置，存放符号，正用0来表示，负用1来表示，但使用“原码”储存的方式，方便了人类，却苦了计算机，（+1）和（-1）相加是0，但计算机只能算出0001+1001=1010 (-2)
* 为了解决“正负相加等于0”的问题，在“原码”的基础上，引入了“反码”，“反码”表示方式是用来处理负数的，符号位置不变，其余位置相反，这样就解决了“正负相加等于0”的问题，（+1）和（-1）相加，变成了0001+1110=1111，刚好反码表示方式中，1111象征-0，但这也遗留下来一个问题—— 有两个零存在，+0 和 -0
* 为了解决两个0的存在问题，引入了“补码”，"补码"的意思是，在"反码"的基础上加1，目标是没有（-0），处理"反码"中的"-0",当1111再补上一个1之后，变成了10000，丢掉最高位就是0000，刚好和正数的0相同，同样"正负数相加等于0"的问题，同样得到解决，3和（-3）相加，0011 + 1101 =10000，丢掉最高位，就是0000（0），

int类型能表示的最小的负数的补码是1000 0000 0000 0000 0000 0000 0000 0000 ，所以最小值为-2^31（本来应该是表示-0的）

int类型能表示的最大正数是0111 1111 1111 1111 1111 1111 1111 1111，所以最大值是2^31 - 1

**简单来说就是使用（+0）来表示0，那么（-0）就可以用来表示其它数，从而导致负数能比整数多表示一位**

#### 浮点类型

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        System.out.println(Double.MIN_VALUE);//4.9E-324
        System.out.println(Double.MAX_VALUE);//1.7976931348623157E308
        System.out.println((long) Double.MIN_VALUE);//0
        System.out.println((long) Double.MAX_VALUE);//9223372036854775807
    }
}
```

从运行结果上来看double的最小值是要大于0的

官方对于Double.MIN_VALUE的说明：A constant holding the smallest positive nonzero value of type `double`, 即保持double类型最小的正非零值的常量，大于0趋近于0

官方对于Double.MAX_VALUE的说明：A constant holding the largest positive finite value of type `double`, 即保持double型的最大正有限值的常数，趋近于正无穷

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        double d = -1.111111111111111111111;
        System.out.println(d);//-1.1111111111111112
        System.out.println(1.4-1.1);//0.2999999999999998
    }
}
```

**为什么是这个结果呢？**

浮点数是用二进制的科学计数法来表示的，在计算机上是以二进制来进行存储的，单精度浮点数占用32位，双精度浮点数占用64位。

以64位双精度浮点数为例最高位是**符号位(sign)**，**0**表示正数，**1**表示负数。接下来的11位储存的是的**指数(exponent)**，最后是52位储存的是**小数(fraction)**。

因为规格化表示，小数点左边一定为1，所以实际有53位精度，这样的表示方法一般都会失去一定的精确度，有些浮点数运算也会产生一定的误差。

#### 类型转换与越界处理

**整型、实型（常量）、字符型数据可以混合运算。运算中，不同类型的数据先转化为同一类型，然后进行运算。**

转换从低级到高级即  byte,short,char—> int —> long—> float —> double 

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        char c1='a';//定义一个char类型
        int i1 = c1;//char自动类型转换为int
        System.out.println(i1 + "  " + (char)i1);//97  a
        char c2 = 'A';//定义一个char类型
        int i2 = c2+1;//char 类型和 int 类型计算
        System.out.println(i2 + "  " + (char)i2);//66  B

        double b = 1.999;
        System.out.println((int)b);//1  小数部位直接丢弃
    }
}
```

数据类型转换必须满足如下规则：

- 不能对boolean类型进行类型转换。
- 不能把对象类型转换成不相关类的对象。
- 在把容量大的类型转换为容量小的类型时必须使用强制类型转换。
- 转换过程中可能导致溢出或损失精度
- 浮点数到整数的转换是通过舍弃小数得到，而不是四舍五入
- 自动类型转换必须满足转换前的数据类型的位数要低于转换后的数据类型
- 强制类型转换的条件是转换的数据类型必须是兼容的

**小数保留位数怎么实现？**

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        double d = 1.66666;
        /**
         * DecimalFormat转换
         */
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(d));//1.67

        /**
         * String.format转换
         */
        System.out.println(String.format("%.2f", d));//1.67

        /**
         * BigDecimal的setScale方法
         */
        BigDecimal bg = new BigDecimal(d);
        double d1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//BigDecimal.ROUND_HALF_UP为四舍五入模式
        System.out.println(d1);//1.67

        /**
         * NumberFormat的setMaximumFractionDigits方法
         */
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(d));//1.67
    }
}
```

**整数型的越界转换**

```java
public class MaxAndMinTest {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);//-2147483648
        System.out.println(Integer.MAX_VALUE);//2147483647
        System.out.println(Integer.MIN_VALUE-1);//2147483647
        System.out.println(Integer.MAX_VALUE+1);//-2147483648
    }
}
```

最小值的二进制补码表示 1000 0000 0000 0000 0000 0000 0000 0000 ，减1后为0111 1111 1111 1111 1111 1111 1111 1111是最大的正数

最大值的二进制补码表示 0111 1111 1111 1111 1111 1111 1111 1111，加1后为 1000 0000 0000 0000 0000 0000 0000 0000

### 寻求高精度运算

Java 1.1 增加了两个类，用于进行高精度的计算：BigInteger 和 BigDecimal。

尽管它们大致可以划分为 “封装器”类型，但两者都没有对应的“主类型”。这两个类都有自己特殊的“方法”，对应于我们针对主类型执行的操作。也就是说，能对int 或 float 做的 事情，对BigInteger 和BigDecimal 一样可以做。只是必须使用方法调用，不能使用运算符。此外，由于牵涉更多，所以运算速度会慢一些。我们牺牲了速度，但换来了精度。 

BigInteger 支持任意精度的整数。也就是说，我们可精确表示任意大小的整数值，同时在运算过程中不会丢失任何信息。 

BigDecimal 支持任意精度的定点数字。例如，可用它进行精确的币值计算。

* BigInteger和BigDecimal都是不可变(immutable)

  在进行每一步运算时，都会产生一个新的对象，由于创建对象会引起开销，它们不适合于大量的数学计算，应尽量用long,float,double等基本类型做科学计算或者工程计算。设计BigInteger和BigDecimal的目的是用来精确地表示大整数和小数，使用于在商业计算中使用。

* 使用String来构造BigDecimal对象

  BigDecimal有多种构造方法，应该避免使用double构造BigDecimal，因为有些数字用double根本无法精确表示，传给BigDecimal构造方法时就已经不精确了。

* equals()方法只认为0.1和0.1是相等的

  0.1和0.1是相等的返回true，而认为0.10和0.1是不等的，结果返回false。方法compareTo()则认为0.1与0.1相等，0.10与0.1也相等。所以在从数值上比较两个BigDecimal值时，应该使用compareTo()而不是 equals()。

  ```java
  public class HighPrecisionTest {
      public static void main(String[] args) {
          BigDecimal bigDecimal1 = new BigDecimal("1.1");
          BigDecimal bigDecimal2 = new BigDecimal("1.10");
          System.out.println(bigDecimal1.equals(bigDecimal2));//false
          System.out.println(bigDecimal1.compareTo(bigDecimal2));//0
      }
  }
  ```

* 任意精度的小数运算仍不能表示精确结果

  1除以9会产生无限循环的小数 .111111...。出于这个原因，在进行除法运算时，BigDecimal允许我们显式地控制舍入。

  不设置可能报错`Non-terminating decimal expansion; no exact representable decimal result.`即无穷小数扩张;没有精确可表示的小数结果。

  ```java
  public class HighPrecisionTest {
      public static void main(String[] args) {
          System.out.println(bigDecimal1.add(bigDecimal2));//2.20  1.1+1.10
          System.out.println(bigDecimal1.multiply(bigDecimal2));//1.210  1.1*1.10
          System.out.println(bigDecimal1.divide(new BigDecimal("3"),3, BigDecimal.ROUND_DOWN));//保留三位小数，直接省略多余的小数
      }
  }
  ```

* BigDecimal中divide方法

  ```java
   public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode) 
  ```

  第一个参数是除数，第二个参数代表保留几位小数，第三个代表的是使用的模式。

  ```
  BigDecimal.ROUND_DOWN:直接省略多余的小数，比如1.28如果保留1位小数，得到的就是1.2
   
  BigDecimal.ROUND_UP:直接进位，比如1.21如果保留1位小数，得到的就是1.3
   
  BigDecimal.ROUND_HALF_UP:四舍五入，2.35保留1位，变成2.4
   
  BigDecimal.ROUND_HALF_DOWN:四舍五入，2.35保留1位，变成2.3
   
  后边两种的区别就是如果保留的位数的后一位如果正好是5的时候，一个舍弃掉，一个进位
  ```

