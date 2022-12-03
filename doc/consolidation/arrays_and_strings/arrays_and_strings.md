# 数组与字符串

## 数组

### 基础概念

数组代表一系列对象或者基本数据类型，所有相同的类型都封装到一起——采用一个统一的标识符名称。数组的定义和使用是通过方括号索引运算符进行的（[]）。为定义一个数组，只需在类型名后简单地跟随一对空方括号即可： 

int[] a;（推荐使用）

 也可以将方括号置于标识符后面，获得完全一致的结果： 

int a[];

这个时候我们仅仅拥有指向数组的一个句柄，而且尚未给数组分配任何空间。为了给数组创建相应的存储空间，必须编写一个初始化表达式。在没有初始化前数组是不允许使用的。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-30_20-40-07.png)

```java
int[] a = { 1, 2, 3, 4, 5 };//清楚数组元素
```

```java
int[] a = new int[20];//仅仅知道数组大小
```

**数组一旦初始化完成即拥有了相应的内存，那它的大小就不能改变**

所有数组都有一个本质成员（无论它们是对象数组还是基本类型数组），可对其进行查询——但不是改变，从而获知数组内包含了多少个元素。这个成员就是 length。数组的下标是从0开始，[0,length-1]，如果越界访问就会抛出异常。

若操作的是一个非基本类型对象的数组，那么无论如何都要使用new。因为我们创建的是一个句柄数组。如果使用句柄的话，那么 Java 堆中将会划分出一块内存来作为句柄池，局部变量表中的reference 存储的就是对象的句柄地址，而句柄中包含了对象实例数据与对象类型数据各自的具体地址信息。

如果我们将一个数组直接赋值给另外一个数组，实际上发生时的仅仅是句柄传递（引用传递），总的来说数组的存储内存还是只有那一块，两个句柄操作的是同一块内存。

```java
public class ArrayTest1 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);//12345
        }
        System.out.println();
        int[] b;
//        System.out.println(b);
        b = a;
        b[0] = 6;
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);//62345
        }
        System.out.println();
        System.out.println(a[0]);//6
    }
}
```

有两方面的问题将数组与其他集合类型区分开来：**效率和类型。**

对于Java 来说，为保存和访问一系列对象 （实际是对象的句柄）数组，最有效的方法莫过于数组。集合类只能容纳对象句柄。但对一个数组，却既可令其直接容纳基本类型的数据，亦可容纳指向对象的句柄。创建和访问一个基本数据类型数组比起访问一个封装数据的集合效率会高出许多。 当然，假如准备一种基本数据类型，同时又想要集合的灵活性（在需要的时候可自动扩展，腾出更多的空间），就不宜使用数组，必须使用由封装的数据构成的一个集合。

数组实际代表一个简单的线性序列，它使得元素的访问速度非常快，但我们却要为这种速度付出代价：创建一个数组对象时，它的大小是固定的，而且不可在那个数组对象的“存在时间”内发生改变。可创建特定大小的一个数组，然后假如用光了存储空间，就再创建一个新数组，将所有句柄从旧数组移到新数组。

创建一 个数组时，可令其容纳一种特定的类型。这意味着可进行编译期类型检查，预防自己设置了错误的类型，或者错误指定了准备提取的类型。

Java中允许创建一个大小为0的数组，我们没有办法通过下标去访问或修改，但可以通过length知道数组大小

当需要返回一个空数组时会用到

```java
public class ArrayTest2 {
    public static void main(String[] args) {
        int[] a = new int[0];
        System.out.println(Arrays.toString(a));//[]
        System.out.println(a.length);//0
    }
}
```

### 内存分配

数组变量属引用类型，数组也可以看成是对象，数组中的每个元素相当于该对象的成员变量。数组本身就是对象，Java中对象是在堆中的，因此数组无论保存原始类型还是其他对象类型,**数组对象本身是在堆中的。**

对于数组对象来说，必须初始化，也就是为该数组对象分配一块连续的内存空间，连续内存空间的长度就是数组对象的长度。
对于数组变量来说，不需要进行初始化，只需让其指向一个有效的数组对象就可以。
实际上，所有引用类型的变量，其变量本身不需要任何初始化，需要初始化的是它所引用的对象。

```java
int[] a = {1,2,3,4,5};
int[] b = a;
```

所有的局部变量都保存在栈内存（Java 虚拟机栈）中，不管是基本类型还是引用类型，局部变量都保存在各自的栈帧中的局部变量表。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-30_21-39-21.png)

**多维数组在内存中也是一维数组，只不过一维数组元素保存的是另一个一维数组的引用。**

### 使用数组

#### 读取数据

```java
public class ArrayTest3 {
    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < 10 ; i++) {
            a[i] = i;
        }
        for (int i = 0; i < a.length; i++) {//0 1 2 3 4 5 6 7 8 9 
            System.out.print(a[i] + " ");
        }
        System.out.println();
        for (int i : a) {//0 1 2 3 4 5 6 7 8 9 
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(a));//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
```

直接输出数组对象会同其它没有重写`toString()`方法的对象一样得到一串和地址有关的字符串

**for循环遍历**

通过下标的方式访问数组元素，需要注意越界处理

**增强for循环**

可以依次处理数组中的每一个元素，不用指定下标。

```java
for(variable : collection)
    statement...
```

collection这一集合表达式必须是一个数组或者实现Iterable接口的类对象（如ArrayList）

对于数组而言，使用增强for循环后反编译class文件发现它还是使用简单的for循环进行遍历

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-31_09-29-03.png)

**Arrays.toString()**

调用这个方法会返回一个包含数组元素的字符串，这些元素被放置中括号内，用逗号隔开。对于空数组它会打印一个`[]`

源码分析

```java
    public static String toString(int[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)//空数组
            return "[]";

        StringBuilder b = new StringBuilder();//StringBuilder 为非线程安全的
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

```

#### 拷贝数组

在Java中允许将一个数组变量赋值给另一个数组变量，这时两个变量指向同一个数组（使用同一块内存空间）

如果想要一个数组的所有值都拷贝到另一个数组可以使用`Arrays.copyOf()`方法

```java
 public static int[] copyOf(int[] original, int newLength) 
```

第二个参数为新数组的长度，可以通过这个方法来变更数组大小，超过原数组长度的部分都会被赋默认值

```java
public class ArrayTest4 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] copy = Arrays.copyOf(a, 10);
        a[0] = 0;
        System.out.println(Arrays.toString(copy));//[1, 2, 3, 4, 5, 6, 0, 0, 0, 0]
    }
}
```

如果长度小数原始数组，则超过的部分会被丢弃

```java
public class ArrayTest4 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] copy = Arrays.copyOf(a, 2);
        a[0] = 0;
        System.out.println(Arrays.toString(copy));//[1, 2]
    }
}
```

源码分析

```java
  public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
```

它会创建一个大小为newLength的数组（我们传入的值），然后通过`System.arraycopy()`来使用原始数组对新数组进行赋值，通过`Math.min(original.length, newLength)`来限制下标，防止越界

```java
 public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);
```

* src：源数组
* srcPos：从源数组开始复制的下标
* dest：目标数组
* destPos：目标数组开始的下标
* length：复制多少个元素
* **src和dest必须是同类型或者可以进行转换类型的数组** 

native关键字说明其修饰的方法是一个原生态方法，方法对应的实现不是在当前文件，而是在用其他语言（如C和C++）实现的文件中。Java语言本身不能对操作系统底层进行访问和操作，但是可以通过JNI接口调用其他语言来实现对底层的访问。

JNI是Java本机接口（Java Native Interface），是一个本机编程接口，它是Java软件开发工具箱（Java Software Development Kit，SDK）的一部分。JNI允许Java代码使用以其他语言编写的代码和代码库。Invocation API（JNI的一部分）可以用来将Java虚拟机（JVM）嵌入到本机应用程序中，从而允许程序员从本机代码内部调用Java代码。

不过，对Java外部的调用通常不能移植到其他平台，在applet中还可能引发安全异常。实现本地代码将使您的Java应用程序无法通过100%纯Java测试。但是，如果必须执行本地调用，则要考虑几个准则：

* 1.将所有本地方法都封装到一个类中，这个类调用单个的DLL。对每一种目标操作系统平台，都可以用特定于适当平台的版本的DLL。这样可以将本地代码的影响减少到最小，并有助于将以后所需要的移植问题考虑在内。
* 2.本地方法尽量简单。尽量使本地方法对第三方（包括Microsoft）运行时DLL的依赖减少到最小。使本地方法尽量独立，以将加载DLL和应用程序所需的开销减少到最小。如果需要运行时DLL，必须随应用程序一起提供。

JNI的书写步骤如下：

* a.编写带有native声明的方法的Java类
* b.使用javac命令编译编写的Java类
* c.使用java -jni 来生成后缀名为.h的头文件
* d.使用其他语言（C、C++）实现本地方法
* e.将本地方法编写的文件生成动态链接库
  

### 常用API

#### 二分查找

```java
public static int binarySearch(int[] a, int key)//以int类型为例
```

用二分查找算法在给定数组中搜索给定值的对象(Byte,Int,double等)。数组在调用前必须排序好的。如果查找值包含在数组中，则返回搜索键的索引；否则返回 (-(*插入点*) - 1)。

```java
public class ArrayTest5 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7};
        System.out.println(Arrays.binarySearch(a, 9));//-8
        System.out.println(Arrays.binarySearch(a,3));//2
    }
}
```

源码分析

```java
    public static int binarySearch(int[] a, int key) {
        return binarySearch0(a, 0, a.length, key);
    }
```

```java
    //二分查找算法 但没有范围检查。
    private static int binarySearch0(int[] a, int fromIndex, int toIndex,
                                     int key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;//无符号右移一位，相当于除以2
            int midVal = a[mid];

            if (midVal < key)
                low = mid + 1;//左指针右移
            else if (midVal > key)
                high = mid - 1;//右指针左移
            else
                return mid; // 找到目标值
        }
        return -(low + 1);  // 没有找到目标值
    }
```

**因为使用的是普通的二分查找算法，所以数组在传入前必须按升序排序**

#### 数组比较

```java
public static boolean equals(int[] a, int[] a2)//以int类型为例
```

如果两个数组包含相同数量的元素，并且两个数组中的所有相应元素对都是相等的，则认为这两个数组是相等的。换句话说，如果两个数组以相同顺序包含相同的元素，则两个数组是相等的。

```java
public class ArraysEqualsTest {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7};
        int[] b = {1,2,3,4};
        int[] c = {1,2,3,4,5,6,7};
        System.out.println(Arrays.equals(a, b));//false
        System.out.println(Arrays.equals(a, c));//true
    }
}
```

源码分析

```java
    public static boolean equals(int[] a, int[] a2) {
        if (a==a2)//判断是否是同一个句柄（引用）
            return true;
        if (a==null || a2==null)//判断是否为空
            return false;

        int length = a.length;
        if (a2.length != length)//判断数组大小是否相同
            return false;

        for (int i=0; i<length; i++)//遍历判断对应元素是否相同
            if (a[i] != a2[i])
                return false;

        return true;
    }
```

#### 数组初始化

```java
 public static void fill(int[] a, int val)//以int类型为例
```

将val赋值给数组a中的每一个元素

```java
public class ArraysFillTest {
    public static void main(String[] args) {
        int[] a = new int[10];
        Arrays.fill(a,6);
        System.out.println(Arrays.toString(a));//[6, 6, 6, 6, 6, 6, 6, 6, 6, 6]
    }
}
```

源码分析

```java
   public static void fill(int[] a, int val) {//循环赋值而已
        for (int i = 0, len = a.length; i < len; i++)
            a[i] = val;
    }
```

#### 数组排序（待完善）

```java
public static void sort(int[] a)//以int类型为例
```

对指定数组根据其元素的自然顺序进行升序排列

```java
public class ArraysSortTest {
    public static void main(String[] args) {
        int[] a = {7,6,5,4,3,2,1};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));//[1, 2, 3, 4, 5, 6, 7]
    }
}
```

源码分析（等我有能力再来好好分析这个算法）

```java
   public static void sort(int[] a) {
        DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
    }
```

```java
static void sort(int[] a, int left, int right,
                     int[] work, int workBase, int workLen) {
        // Use Quicksort on small arrays
        if (right - left < QUICKSORT_THRESHOLD) {
            sort(a, left, right, true);//见下一个代码块
            return;
        }

        /*
         * Index run[i] is the start of i-th run
         * (ascending or descending sequence).
         */
        int[] run = new int[MAX_RUN_COUNT + 1];
        int count = 0; run[0] = left;

        // Check if the array is nearly sorted
        for (int k = left; k < right; run[count] = k) {
            if (a[k] < a[k + 1]) { // ascending
                while (++k <= right && a[k - 1] <= a[k]);
            } else if (a[k] > a[k + 1]) { // descending
                while (++k <= right && a[k - 1] >= a[k]);
                for (int lo = run[count] - 1, hi = k; ++lo < --hi; ) {
                    int t = a[lo]; a[lo] = a[hi]; a[hi] = t;
                }
            } else { // equal
                for (int m = MAX_RUN_LENGTH; ++k <= right && a[k - 1] == a[k]; ) {
                    if (--m == 0) {
                        sort(a, left, right, true);
                        return;
                    }
                }
            }

            /*
             * The array is not highly structured,
             * use Quicksort instead of merge sort.
             */
            if (++count == MAX_RUN_COUNT) {
                sort(a, left, right, true);
                return;
            }
        }

        // Check special cases
        // Implementation note: variable "right" is increased by 1.
        if (run[count] == right++) { // The last run contains one element
            run[++count] = right;
        } else if (count == 1) { // The array is already sorted
            return;
        }

        // Determine alternation base for merge
        byte odd = 0;
        for (int n = 1; (n <<= 1) < count; odd ^= 1);

        // Use or create temporary array b for merging
        int[] b;                 // temp array; alternates with a
        int ao, bo;              // array offsets from 'left'
        int blen = right - left; // space needed for b
        if (work == null || workLen < blen || workBase + blen > work.length) {
            work = new int[blen];
            workBase = 0;
        }
        if (odd == 0) {
            System.arraycopy(a, left, work, workBase, blen);
            b = a;
            bo = 0;
            a = work;
            ao = workBase - left;
        } else {
            b = work;
            ao = 0;
            bo = workBase - left;
        }

        // Merging
        for (int last; count > 1; count = last) {
            for (int k = (last = 0) + 2; k <= count; k += 2) {
                int hi = run[k], mi = run[k - 1];
                for (int i = run[k - 2], p = i, q = mi; i < hi; ++i) {
                    if (q >= hi || p < mi && a[p + ao] <= a[q + ao]) {
                        b[i + bo] = a[p++ + ao];
                    } else {
                        b[i + bo] = a[q++ + ao];
                    }
                }
                run[++last] = hi;
            }
            if ((count & 1) != 0) {
                for (int i = right, lo = run[count - 1]; --i >= lo;
                    b[i + bo] = a[i + ao]
                );
                run[++last] = right;
            }
            int[] t = a; a = b; b = t;
            int o = ao; ao = bo; bo = o;
        }
    }

```

```java
    private static void sort(int[] a, int left, int right, boolean leftmost) {
        int length = right - left + 1;

        // Use insertion sort on tiny arrays
        if (length < INSERTION_SORT_THRESHOLD) {
            if (leftmost) {
                /*
                 * Traditional (without sentinel) insertion sort,
                 * optimized for server VM, is used in case of
                 * the leftmost part.
                 */
                for (int i = left, j = i; i < right; j = ++i) {
                    int ai = a[i + 1];
                    while (ai < a[j]) {
                        a[j + 1] = a[j];
                        if (j-- == left) {
                            break;
                        }
                    }
                    a[j + 1] = ai;
                }
            } else {
                /*
                 * Skip the longest ascending sequence.
                 */
                do {
                    if (left >= right) {
                        return;
                    }
                } while (a[++left] >= a[left - 1]);

                /*
                 * Every element from adjoining part plays the role
                 * of sentinel, therefore this allows us to avoid the
                 * left range check on each iteration. Moreover, we use
                 * the more optimized algorithm, so called pair insertion
                 * sort, which is faster (in the context of Quicksort)
                 * than traditional implementation of insertion sort.
                 */
                for (int k = left; ++left <= right; k = ++left) {
                    int a1 = a[k], a2 = a[left];

                    if (a1 < a2) {
                        a2 = a1; a1 = a[left];
                    }
                    while (a1 < a[--k]) {
                        a[k + 2] = a[k];
                    }
                    a[++k + 1] = a1;

                    while (a2 < a[--k]) {
                        a[k + 1] = a[k];
                    }
                    a[k + 1] = a2;
                }
                int last = a[right];

                while (last < a[--right]) {
                    a[right + 1] = a[right];
                }
                a[right + 1] = last;
            }
            return;
        }

        // Inexpensive approximation of length / 7
        int seventh = (length >> 3) + (length >> 6) + 1;

        /*
         * Sort five evenly spaced elements around (and including) the
         * center element in the range. These elements will be used for
         * pivot selection as described below. The choice for spacing
         * these elements was empirically determined to work well on
         * a wide variety of inputs.
         */
        int e3 = (left + right) >>> 1; // The midpoint
        int e2 = e3 - seventh;
        int e1 = e2 - seventh;
        int e4 = e3 + seventh;
        int e5 = e4 + seventh;

        // Sort these elements using insertion sort
        if (a[e2] < a[e1]) { int t = a[e2]; a[e2] = a[e1]; a[e1] = t; }

        if (a[e3] < a[e2]) { int t = a[e3]; a[e3] = a[e2]; a[e2] = t;
            if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
        }
        if (a[e4] < a[e3]) { int t = a[e4]; a[e4] = a[e3]; a[e3] = t;
            if (t < a[e2]) { a[e3] = a[e2]; a[e2] = t;
                if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
            }
        }
        if (a[e5] < a[e4]) { int t = a[e5]; a[e5] = a[e4]; a[e4] = t;
            if (t < a[e3]) { a[e4] = a[e3]; a[e3] = t;
                if (t < a[e2]) { a[e3] = a[e2]; a[e2] = t;
                    if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
                }
            }
        }

        // Pointers
        int less  = left;  // The index of the first element of center part
        int great = right; // The index before the first element of right part

        if (a[e1] != a[e2] && a[e2] != a[e3] && a[e3] != a[e4] && a[e4] != a[e5]) {
            /*
             * Use the second and fourth of the five sorted elements as pivots.
             * These values are inexpensive approximations of the first and
             * second terciles of the array. Note that pivot1 <= pivot2.
             */
            int pivot1 = a[e2];
            int pivot2 = a[e4];

            /*
             * The first and the last elements to be sorted are moved to the
             * locations formerly occupied by the pivots. When partitioning
             * is complete, the pivots are swapped back into their final
             * positions, and excluded from subsequent sorting.
             */
            a[e2] = a[left];
            a[e4] = a[right];

            /*
             * Skip elements, which are less or greater than pivot values.
             */
            while (a[++less] < pivot1);
            while (a[--great] > pivot2);

            /*
             * Partitioning:
             *
             *   left part           center part                   right part
             * +--------------------------------------------------------------+
             * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
             * +--------------------------------------------------------------+
             *               ^                          ^       ^
             *               |                          |       |
             *              less                        k     great
             *
             * Invariants:
             *
             *              all in (left, less)   < pivot1
             *    pivot1 <= all in [less, k)     <= pivot2
             *              all in (great, right) > pivot2
             *
             * Pointer k is the first index of ?-part.
             */
            outer:
            for (int k = less - 1; ++k <= great; ) {
                int ak = a[k];
                if (ak < pivot1) { // Move a[k] to left part
                    a[k] = a[less];
                    /*
                     * Here and below we use "a[i] = b; i++;" instead
                     * of "a[i++] = b;" due to performance issue.
                     */
                    a[less] = ak;
                    ++less;
                } else if (ak > pivot2) { // Move a[k] to right part
                    while (a[great] > pivot2) {
                        if (great-- == k) {
                            break outer;
                        }
                    }
                    if (a[great] < pivot1) { // a[great] <= pivot2
                        a[k] = a[less];
                        a[less] = a[great];
                        ++less;
                    } else { // pivot1 <= a[great] <= pivot2
                        a[k] = a[great];
                    }
                    /*
                     * Here and below we use "a[i] = b; i--;" instead
                     * of "a[i--] = b;" due to performance issue.
                     */
                    a[great] = ak;
                    --great;
                }
            }

            // Swap pivots into their final positions
            a[left]  = a[less  - 1]; a[less  - 1] = pivot1;
            a[right] = a[great + 1]; a[great + 1] = pivot2;

            // Sort left and right parts recursively, excluding known pivots
            sort(a, left, less - 2, leftmost);
            sort(a, great + 2, right, false);

            /*
             * If center part is too large (comprises > 4/7 of the array),
             * swap internal pivot values to ends.
             */
            if (less < e1 && e5 < great) {
                /*
                 * Skip elements, which are equal to pivot values.
                 */
                while (a[less] == pivot1) {
                    ++less;
                }

                while (a[great] == pivot2) {
                    --great;
                }

                /*
                 * Partitioning:
                 *
                 *   left part         center part                  right part
                 * +----------------------------------------------------------+
                 * | == pivot1 |  pivot1 < && < pivot2  |    ?    | == pivot2 |
                 * +----------------------------------------------------------+
                 *              ^                        ^       ^
                 *              |                        |       |
                 *             less                      k     great
                 *
                 * Invariants:
                 *
                 *              all in (*,  less) == pivot1
                 *     pivot1 < all in [less,  k)  < pivot2
                 *              all in (great, *) == pivot2
                 *
                 * Pointer k is the first index of ?-part.
                 */
                outer:
                for (int k = less - 1; ++k <= great; ) {
                    int ak = a[k];
                    if (ak == pivot1) { // Move a[k] to left part
                        a[k] = a[less];
                        a[less] = ak;
                        ++less;
                    } else if (ak == pivot2) { // Move a[k] to right part
                        while (a[great] == pivot2) {
                            if (great-- == k) {
                                break outer;
                            }
                        }
                        if (a[great] == pivot1) { // a[great] < pivot2
                            a[k] = a[less];
                            /*
                             * Even though a[great] equals to pivot1, the
                             * assignment a[less] = pivot1 may be incorrect,
                             * if a[great] and pivot1 are floating-point zeros
                             * of different signs. Therefore in float and
                             * double sorting methods we have to use more
                             * accurate assignment a[less] = a[great].
                             */
                            a[less] = pivot1;
                            ++less;
                        } else { // pivot1 < a[great] < pivot2
                            a[k] = a[great];
                        }
                        a[great] = ak;
                        --great;
                    }
                }
            }

            // Sort center part recursively
            sort(a, less, great, false);

        } else { // Partitioning with one pivot
            /*
             * Use the third of the five sorted elements as pivot.
             * This value is inexpensive approximation of the median.
             */
            int pivot = a[e3];

            /*
             * Partitioning degenerates to the traditional 3-way
             * (or "Dutch National Flag") schema:
             *
             *   left part    center part              right part
             * +-------------------------------------------------+
             * |  < pivot  |   == pivot   |     ?    |  > pivot  |
             * +-------------------------------------------------+
             *              ^              ^        ^
             *              |              |        |
             *             less            k      great
             *
             * Invariants:
             *
             *   all in (left, less)   < pivot
             *   all in [less, k)     == pivot
             *   all in (great, right) > pivot
             *
             * Pointer k is the first index of ?-part.
             */
            for (int k = less; k <= great; ++k) {
                if (a[k] == pivot) {
                    continue;
                }
                int ak = a[k];
                if (ak < pivot) { // Move a[k] to left part
                    a[k] = a[less];
                    a[less] = ak;
                    ++less;
                } else { // a[k] > pivot - Move a[k] to right part
                    while (a[great] > pivot) {
                        --great;
                    }
                    if (a[great] < pivot) { // a[great] <= pivot
                        a[k] = a[less];
                        a[less] = a[great];
                        ++less;
                    } else { // a[great] == pivot
                        /*
                         * Even though a[great] equals to pivot, the
                         * assignment a[k] = pivot may be incorrect,
                         * if a[great] and pivot are floating-point
                         * zeros of different signs. Therefore in float
                         * and double sorting methods we have to use
                         * more accurate assignment a[k] = a[great].
                         */
                        a[k] = pivot;
                    }
                    a[great] = ak;
                    --great;
                }
            }

            /*
             * Sort left and right parts recursively.
             * All elements from center part are equal
             * and, therefore, already sorted.
             */
            sort(a, left, less - 1, leftmost);
            sort(a, great + 1, right, false);
        }
    }
```

## 字符串

### 基础概念

Java字符串就是Unicode字符序列（JDK8），Java没有内置的字符串类型，而是在标准Java类库中提供了一个预定义类String，每一个用双引号（英文）括起来的字符串都是String的一个实例。

String类是不可变类，一个String对象被创建以后，包含在这个对象中的字符序列是不可变的，只到这个对象被销毁。

我们经常会看见这样一段代码，给我们一种String会扩展的错觉：

```java
public class StringTest1 {
    public static void main(String[] args) {
        String s = "123";
        s = s+"456";
        System.out.println(s);//123456
    }
}
```

上面的代码实现还有一种可能：后面的s与之前的s指向的不是同一块内存，`s+"456"`会创建一个新的字符串对象，然后将这个对象的句柄赋值给了s。可以通过以下的代码来验证我们的想法

```java
public class StringTest1 {
    public static void main(String[] args) {
        String s = "123";
        String s1 = s;
        s = s+"";
        System.out.println(System.identityHashCode(s));//460141958
        System.out.println(System.identityHashCode(s1));//1163157884
        System.out.println(s1==s);//false
    }
}
```

两个字符串其实是相同的，但它们的hashcode不同，可以理解为它们的内存地址不同

注：这里不能使用String类型的`hashCode()`，因为String类重写了这个方法（放在下面介绍）

那如果一个对象覆盖了`hashCode`方法，我们仍然想获得它的内存地址计算的Hash值，应该怎么办呢？`java.lang.System`类提供了一个本地方法（本地方法在前面拷贝数组里有说过）：
```java
public static native int identityHashCode(Object x);//以内存来计算的HashCode的方式
```

StringBuffer对象则代表一个字符序列可变的字符串，当一个StringBuffer被创建以后，可以提供StringBuffer提供的一些API来改变这个字符串，当我们觉得“可以”的时候，可以通过`toString()`方法将它转成一个String对象

StringBuilder类是JDK1.5新增的一个类，与StringBuffer基本相同，它也代表一个可变字符串对象，不同的是StringBuffer是线程安全的，而StringBuilder没有实现线程安全功能，所以性能高。

### 内存分配

首先一开始我们就说 Java字符串就是Unicode字符序列，既然是Unicode字符那自然而然就能想到字符串是使用字符数组进行存储的，在JDK源码上有这样一段注释：

```java
Strings are constant; their values cannot be changed after they are created. String buffers support mutable strings. Because String objects are immutable they can be shared.
```

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-31_21-11-54.png)

String确实是使用字符数组来进行存储的（至少JDK8是这样的，Java9字符串采用byte[]数组再加一个encoding-flag字段来保存字符），而且类和属性还用final来修饰以达到不可变。

新版的 String 其实支持两个编码方案： Latin-1 和 UTF-16。如果字符串中包含的汉字没有超过 Latin-1 可表示范围内的字符，那就会使用 Latin-1 作为编码方案。Latin-1 编码方案下，`byte` 占一个字节(8 位)，`char` 占用 2 个字节（16），`byte` 相较 `char` 节省一半的内存空间。JDK 官方就说了绝大部分字符串对象只包含 Latin-1 可表示的字符。如果字符串中包含的汉字超过 Latin-1 可表示范围内的字符，`byte` 和 `char` 所占用的空间是一样的。（官方的介绍：https://openjdk.java.net/jeps/254 ）

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-31_21-20-57.png)

String 为什么不能被修改？

* 安全性：当你在调用其他方法时，比如调用一些系统级操作指令之前，可能会有一系列校验，如果是可变类的话，可能在你校验过后，它的内部的值又被改变了，这样有可能会引起严重的系统崩溃问题，所以迫使 String 设计为 final 类的一个重要原因就是出于安全考虑；

* 高性能：String 不可变之后就保证的 hash 值的唯一性，这样它就更加高效，并且更适合做 HashMap 的 key- value 缓存；

* 节约内存：String 的不可变性是它实现字符串常量池的基础，字符串常量池指的是字符串在创建时，先去“常量池”查找是否有此字符串的引用，如果有，则不会开辟新空间创建字符串，而是直接返回常量池中的引用，这样就能更加节省空间。

  通常情况下 String 创建有两种方式，直接赋值的方式，如` String str="Java"`；另一种是 new 形式的创建，如 `String str = new String("Java")`。

  当代码中使用第一种方式创建字符串对象时，JVM 首先会检查该对象是否在字符串常量池中，如果在，就返回该对象引用，否则会在堆中创建对应的字符串对象并将该字符串对象的引用保存到字符串常量池中。这种方式可以**减少同一个值的字符串对象的重复创建，节约内存**。

  `String str = new String("Java") `这种方式，如果字符串常量池中不存在字符串对象"Java"的引用，那么会在堆中创建 2 个字符串对象"Java"，首先堆中会先创建一个未初始化的字符串对象，然后在堆中创建字符串对象"Java"并在字符串常量池中保存对应的引用，然后调用构造方法对一开始创建的字符串进行赋值；如果字符串常量池中已存在字符串对象"Java"的引用，则只会在堆中创建 1 个字符串对象"Java"。

#### new String("Java")详细过程

部分字节码指令：

* 0xbb	new	创建一个对象，将其引用值压入压入栈顶
* 0x59    dup     复制栈顶数值并将复制值压入栈顶
* 0x12    ldc       将int，float或String型常量值从常量池中推送至栈顶
* 0xb7    invokespecial      调用超类构造方法实例初始化方法，私有方法
* 0x4c    astore_1    将栈顶引用型数值存入第二个本地变量
* 0x4d    astore_2    将栈顶引用型数值存入第三个本地变量
* 0xb1    return       从当前方法返回void

字符串常量池不存在"Java"引用，编译后的部分字节码如下：

```java
public class StringTest2 {
    public static void main(String[] args) {
        String s = new String("Java");
    }
}
```

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-31_22-09-13.png)

- `0: new`，在堆上创建一个String对象，并将它的引用压入操作数栈，注意这时的对象还只是一个空壳，并没有调用类的构造方法进行初始化
- `3: dup`，复制栈顶元素，也就是复制了上面的对象引用，并将复制后的对象引用压入栈顶。这里之所以要进行复制，是因为之后要执行的构造方法会从操作数栈弹出需要的参数和这个对象引用本身（这个引用起到的作用就是构造方法中的`this`指针），如果不进行复制，在弹出后会无法得到初始化后的对象引用
- `4: ldc`，在堆上创建字符串对象，驻留到字符串常量池，并将字符串的引用压入操作数栈（具体在下面解释）
- `6: invokespecial`，执行String的构造方法，这一步执行完成后得到一个完整对象
- `9: astore_1`，弹出栈顶元素，并将栈顶引用类型值保存到第二个本地变量中，也就是保存到变量`s`中
- `10: return`，执行`void`函数返回

注：本地变量（局部变量）的赋值好像都是从第二个开始，比我们想象的多一个，不要忘记在主函数中会传入一个变量`args`

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_09-56-37.png)

字符串常量池不存在"Java"引用编译后的部分字节码如下：

```java
public class StringTest2 {
    public static void main(String[] args) {
        String s1 = "Java";
        String s = new String("Java");
    }
}
```

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-10-31_22-21-06.png)

- `0: ldc`，查找后面索引为`#2`对应的项，`#2`表示常量在常量池中的位置。在这个过程中，会触发**lazy resolve**，在resolve过程如果发现`StringTable`已经有了内容匹配的String引用，则直接返回这个引用，反之如果`StringTable`里没有内容匹配的String对象的引用，则会在堆里创建一个对应内容的String对象，然后在`StringTable`驻留这个对象引用，并返回这个引用，之后再压入操作数栈中
- `2: astore_1`，弹出栈顶元素，并将栈顶引用类型值保存到第二个本地变量中，也就是保存到变量`s1`中
- `3: new`，在堆上创建一个String对象，并将它的引用压入操作数栈，这时的对象还是一个空壳，并没有调用类的构造方法进行初始化
- `6: dup`，复制栈顶元素，也就是复制了上面的对象引用，并将复制后的对象引用压入栈顶。
- `7: ldc`，查找后面索引为`#2`对应的项，`#2`表示常量在常量池中的位置。很明显`StringTable`已经有了内容匹配的String引用，直接返回这个引用即可无需再创建对象
- `9: invokespecial`，执行String的构造方法，为前面的空壳对象赋值
- `12: astore_2`，弹出栈顶元素，并将栈顶引用类型值保存到第三个本地变量中，也就是保存到变量`s`中
- `13: return`，执行`void`函数返回

#### String.intern()方法

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_15-02-44.png)

`String.intern()` 是一个 native（本地）方法，其作用是将指定的字符串对象的引用保存在字符串常量池中，可以简单分为两种情况：

- 如果字符串常量池中保存了对应的字符串对象的引用，就直接返回该引用。
- 如果字符串常量池中没有保存了对应的字符串对象的引用，那就在常量池中创建一个指向该字符串对象的引用并返回。

```java
public class StringTest5 {
    public static void main(String[] args) {
        String s = "123";
        System.out.println(System.identityHashCode(s));//460141958
        String intern = s.intern();
        System.out.println(System.identityHashCode(intern));//460141958
        String s1 = "123";
        System.out.println(System.identityHashCode(s1));//460141958

        String s2 = new String("456");
        System.out.println(System.identityHashCode(s2));//1163157884
        String s3 = "456";
        System.out.println(System.identityHashCode(s3));//1956725890
        String intern2 = s2.intern();
        System.out.println(System.identityHashCode(intern2));//1956725890
        String s4 = new String("456");
        System.out.println(System.identityHashCode(s4));//356573597
    }
}
```

前面三个句柄s，intern，s1都指向同一块内存，s2指向的内存与s3以及intern2都不同，s4指向了另一块内存

对于前三个句柄：

* `String s = "123"`：JVM发现"123"对应的字符串对象不在字符串常量池中，如是在堆中创建"123"字符串对象并将该字符串对象的引用保存到字符串常量池中
* `String intern = s.intern();`：此时，JVM发现字符串常量池中已经保存了"123"字符串对象的引用，直接返回该引用
* `String s1 = "123";`同样也在字符串常量池中找到了对应的引用

所以它们指向同一块内存。

对于后四个句柄：

* `String s2 = new String("456");`：在字符串常量池中没有发现"456"字符串对象的引用，则会在堆中创建两个对象。首先在堆中创建一个空壳对象，然后在堆中创建字符串对象"456"，并在字符串常量池中保存对应的引用，然后调用构造方法对一开始创建的空壳对象进行赋值（注意：**s2获取的是指向那个空壳对象的引用，它的引用不在字符串常量池中**）
* `String s3 = "456";`：由于前面已经在字符串常量池中保存了字符串对象"456"对应的引用，直接返回即可，不用创建对象
* `String intern2 = s2.intern();`：同样在字符串常量池中已经存在对应的引用，直接返回即可，不需要再添加引用
* `String s4 = new String("456");`：在字符串常量池中发现了"456"字符串对象的引用，则只会在堆中创建一个对象，返回该对象的引用

所以s3与s2相同，都是从字符串常量池中获取的引用，s2，s4都是指向堆中不同对象的引用

去掉输出语句后的反编译字节码（部分）

```shell
 Code:
      stack=3, locals=8, args_size=1
         0: ldc           #2                  // String 123
         2: astore_1
         3: aload_1
         4: invokevirtual #3                  // Method java/lang/String.intern:()Ljava/lang/String;
         7: astore_2
         8: ldc           #2                  // String 123
        10: astore_3
        11: new           #4                  // class java/lang/String
        14: dup
        15: ldc           #5                  // String 456
        17: invokespecial #6                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
        20: astore        4
        22: ldc           #5                  // String 456
        24: astore        5
        26: aload         4
        28: invokevirtual #3                  // Method java/lang/String.intern:()Ljava/lang/String;
        31: astore        6
        33: new           #4                  // class java/lang/String
        36: dup
        37: ldc           #5                  // String 456
        39: invokespecial #6                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
        42: astore        7
        44: return
```

#### "1"+"2"+"3"

在前面有过这样一段代码

```java
String s = "123";
String s1 = s;
s = s+"";
```

测试我们发现s与s1指向的不是同一块区域，通过`+`号来拼接字符串其实是重新创建了一个字符串对象，那么对于`String s = "1"+"2"+"3";`它会创建几个对象呢？

**只创建了一个对象**

我们来看看字节码文件和反编译后的字节码文件

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_10-14-50.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_10-16-50.png)

源代码中字符串拼接的操作，在编译完成后会消失，直接呈现为一个拼接后的完整字符串，是因为在编译期间，应用了编译器优化中一种被称为**常量折叠**(Constant Folding)的技术。

常量折叠会将**编译期常量**的加减乘除的运算过程在编译过程中折叠。编译器通过语法分析，会将常量表达式计算求值，并用求出的值来替换表达式，而不必等到运行期间再进行运算处理，从而在运行期间节省处理器资源。

```java
public class StringTest3 {//源码文件
    public static void main(String[] args) {
        final String h1 = "dar";
        final int num = 3;
        String h2 = "dar";
        String s1 = h1 + "02";
        String s2 = h2 + "02";
        String s3 = "12"+num;
        System.out.println((s1 == "dar02"));//true
        System.out.println((s2 == "dar02"));//false
        System.out.println(s3);//123
    }
}
```

```java
public class StringTest3 {//反编译后的class文件
    public StringTest3() {
    }

    public static void main(String[] args) {
        String h1 = "dar";
        int num = true;
        String h2 = "dar";
        String s1 = "dar02";
        String s2 = h2 + "02";
        String s3 = "123";
        System.out.println(s1 == "dar02");
        System.out.println(s2 == "dar02");
        System.out.println(s3);
    }
}
```

编译期常量，即 compile-time constant。其看似是一个静态，并不一定是由 static 修饰（static 一般只是用于强调只有一份），但强制要求使用 final 进行修饰。编译期常量完整要求是：

* declared final；被声明为 final（所有编译期常量都满足的条件）；
* primitive or String；基本类型或者字符串类型（满足其一即可）；
* initialized within declaration；声明时便已初始化（必要条件）；
* initialized with constant expression；使用常量表达式进行初始化（对于第三条的补充，说明初始化方式）；

什么是常量表达式”这个问题，这个可以参考 Oracle 的官方文档：[在15.28 小节那里](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html )

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_10-27-23.png)

列举一些：

- 基本类型以及 String 类型的字面量(new 出来的、变量引用的都不能算)；
- 基本类型以及 String 类型的强制类型转换；
- 使用 + 等一元运算符进行加法运算/拼接运算得到的值；

字面量（literals）：是用于表达源代码中一个固定值的表示法，在Java中创建一个对象时需要使用`new`关键字，但是给一个基本类型变量赋值时不需要使用`new`关键字，这种方式就可以被称为字面量。

Java中字面量主要包括了以下类型的字面量：

```java
//整数型字面量：
long l=1L;
int i=1;

//浮点类型字面量：
float f=11.1f;
double d=11.1;

//字符和字符串类型字面量：
char c='h';
String s="Hydra";

//布尔类型字面量：
boolean b=true;
```

那么对于` String h2 = "dar"; String s2 = h2 + "02";`会重新创建一个字符串对象，那它是怎么创建的呢？

```java
public class StringTest4 {//源文件
    public static void main(String[] args) {
        String s1 = "1";
        String s2 = "2";
        String s3 = "3";
        String s4 = s1+s2+s3;
    }
}
```

```java
public class StringTest4 {//反编译后的字节码文件
    public StringTest4() {
    }

    public static void main(String[] args) {
        String s1 = "1";
        String s2 = "2";
        String s3 = "3";
        (new StringBuilder()).append(s1).append(s2).append(s3).toString();
    }
}
```

编译器还是忍不住出手了，使用StringBuilder()来进行字符串拼接

#### StringBuilder拼接字符串详细过程

```java
public class StringBuilderTest1 {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.length());//0
        stringBuilder.append("dar").append("02");
        System.out.println(stringBuilder);//dar02
    }
}
```

StringBuilder继承AbstractStringBuilder，在构造函数中会先调用父类的构造函数，当我们使用`new StringBuilder()`来创建一个StringBuilder对象时，它会通过调用父类的构造函数即`super(16)`创建一个默认容量为16字节的数组

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_10-55-32.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_11-01-12.png)

当我们使用`stringBuilder.length()`获取长度时返回的不是数组的长度而是数组的元素个数

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_11-02-36.png)

在父类中使用变量count来进行计数

当我们使用`stringBuilder.append("dar")`会在字符串的“末尾”拼接上"dar"，怎么实现的呢

它是直接通过`super.append(str);`调用父类`AbstractStringBuilder`的`append()`方法

```java
//将指定的字符串追加到此字符序列。
//字符串参数的字符按顺序被追加，按参数的长度增加序列的长度。    
public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);//增加容量
        str.getChars(0, len, value, count);//追加字符
        count += len;//增加计数
        return this;
 }
```

先来看` ensureCapacityInternal(count + len);`部分的代码

```java
    private void ensureCapacityInternal(int minimumCapacity) {
        // 大于0说明之前的数组容量不够了
        if (minimumCapacity - value.length > 0) {
            value = Arrays.copyOf(value, newCapacity(minimumCapacity));//通过Arrays.copyOf来拷贝数组
        }
    }
```

`newCapacity(minimumCapacity)`用于计算新数组的长度后作为参数传入

```java
//要分配的数组的最大值(除非必要)。有些虚拟机在数组中保留一些头字。
//尝试分配更大的阵列可能会导致OutOfMemoryError:请求的内存大小超过虚拟机限制
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

//返回至少与给定的最小容量相等的容量。如果足够，返回之前长度两倍 + 2的容量。
//将不会返回大于MAX_ARRAY_SIZE的容量，除非给定的最小容量大于该值。
private int newCapacity(int minCapacity) {
       int newCapacity = (value.length << 1) + 2;//原数组的长度*2+2
       if (newCapacity - minCapacity < 0) {//尝试扩容后仍然小于需求的最小容量
           newCapacity = minCapacity;//需求最小容量
       }
       return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
           ? hugeCapacity(minCapacity)//超过设置的最大容量
           : newCapacity;
   }
//返回较大容量或抛出异常
private int hugeCapacity(int minCapacity) {
      if (Integer.MAX_VALUE - minCapacity < 0) { // 容量不够，溢出，抛出异常
           throw new OutOfMemoryError();
      }
      return (minCapacity > MAX_ARRAY_SIZE)//返回尽可能大的容量
          ? minCapacity : MAX_ARRAY_SIZE;
}
```

再来`str.getChars(0, len, value, count);`部分的代码

```java
//将此字符串中的字符复制到目标字符数组中
//srcBegin：要复制的字符串中第一个字符的索引
//srcEnd：要复制的字符串中最后一个字符后的索引
//dst：目标数组
//dstBegin：目标数组中的起始偏移量（下标开始）
public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
    //参数合法校验
    if (srcBegin < 0) 
        throw new StringIndexOutOfBoundsException(srcBegin);
    }
    if (srcEnd > value.length) {
        throw new StringIndexOutOfBoundsException(srcEnd);
    }
    if (srcBegin > srcEnd) {
        throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
    }
    //使用原始数组对新数组进行赋值（拷贝数组那里有说）
    System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
 }
```

整理一下：

StringBuilder与String一样都是使用字符数组来存储字符串中的字符，String会根据字符串的大小来创建等长的数组并且通过final来限制不允许修改。StringBuilder是`AbstractStringBuilder`的一个子类，在`AbstractStringBuilder`用于存储字符串的数组没有使用final修饰，并且我们通过`length()`获取的是记录数组元素数量（通过变量count来记录），数组真正的长度不是透明的，追加字符串是，需求的存储容量小于数组容量则不需要扩容，直接复制字符串到数组即可；超出容量于String一样需要重新分配空间，`AbstractStringBuilder`会尽可能的考虑分配多一点的空间来避免下次分配（除非它要的实在太多了），**所以 StringBuilder.append() 的性能比字符串的 += 性能要高**。

同时我们在阅读StringBuilder源码的时候，并没有发现`synchronized`之类与线程安全有关的代码，而在StringBuffer中能到处都是`synchronized`，这也验证了前面所说的：`StringBuilder` 为非线程安全的，而 `StringBuffer` 是线程安全的。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_14-40-37.png)

#### 一些问题

**JVM 常量池中存储的是对象还是引用呢？**

来自RednaxelaFX的回答：

```
如果您说的确实是runtime constant pool（而不是interned string pool / StringTable之类的其他东西）的话，其中的引用类型常量（例如CONSTANT_String、CONSTANT_Class、CONSTANT_MethodHandle、CONSTANT_MethodType之类）都存的是引用，实际的对象还是存在Java heap上的。
```

**JDK 1.7 为什么要将字符串常量池移动到堆中?**

主要是因为永久代（方法区实现）的 GC 回收效率太低，只有在整堆收集 (Full GC)的时候才会被执行 GC。Java 程序中通常会有大量的被创建的字符串等待回收，将字符串常量池放到堆中，能够更高效及时地回收字符串内存。

*运行时常量池、方法区、字符串常量池这些都是不随虚拟机实现而改变的逻辑概念，是公共且抽象的，Metaspace、Heap 是与具体某种虚拟机实现相关的物理概念，是私有且具体的。*

《深入理解 Java 虚拟机（第 3 版）》样例代码&勘误：[2.2.6运行时常量池与字符串常量池应该不是同一个池](https://github.com/fenixsoft/jvm_book/issues/112#top)

### 高效使用字符串

#### 1、不要直接+=拼接字符串

理由上面已经提到过，并且使用一些集成开发工具（如IDEA），在使用`+`来拼接字符串时会给出一些提醒

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_14-49-05.png)

同样，如果确定字符串基本不变也没有必要使用`StringBuilder` 或者`StringBuffer`，String肯定是最理想的实现方式，同样IDEA也会给出提醒

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_14-51-52.png)

#### 2.善用 intern 方法 

`intern()` 是一个高效的本地方法，它的定义中说的是，当调用 `intern` 方法时，如果字符串常量池中已经包含此字符串，则直接返回此字符串的引用，如果不包含此字符串，先将字符串添加到常量池中，再返回此对象的引用。

如果在项目中可能出现大量重复字符串（如国家，城市等），就可以考虑使用`intern()` 来减少对象的新建

Twitter 工程师曾分享过一个 `String.intern()` 的使用示例，Twitter 每次发布消息状态的时候，都会产生一个地址信息，以当时 Twitter 用户的规模预估，服务器需要 32G 的内存来存储地址信息。

```java
public class Location {
    private String city;
    private String region;
    private String countryCode;
    private double longitude;
    private double latitude;
}
```

考虑到其中有很多用户在地址信息上是有重合的，比如，国家、省份、城市等，这时就可以将这部分信息单独列出一个类，以减少重复，代码如下：

```java
public class SharedLocation {

  private String city;
  private String region;
  private String countryCode;
}

public class Location {

  private SharedLocation sharedLocation;
  double longitude;
  double latitude;
}
```

通过优化，数据存储大小减到了 20G 左右。但对于内存存储这个数据来说，依然很大，怎么办呢？

Twitter 工程师使用 `String.intern()` 使重复性非常高的地址信息存储大小从 20G 降到几百兆，从而优化了 String 对象的存储。

实现的核心代码如下：

```java
SharedLocation sharedLocation = new SharedLocation();
sharedLocation.setCity(messageInfo.getCity().intern());    
sharedLocation.setCountryCode(messageInfo.getRegion().intern());
sharedLocation.setRegion(messageInfo.getCountryCode().intern());
```

#### 3.慎重使用 Split 方法

 `Split` 方法大多数情况下使用的是正则表达式，这种分割方式本身没有什么问题，但是由于正则表达式的性能是非常不稳定的，使用不恰当会引起回溯问题，很可能导致 CPU 居高不下。

**Java 正则表达式使用的引擎实现是 NFA（Non deterministic Finite Automaton，不确定型有穷自动机）自动机，这种正则表达式引擎在进行字符匹配时会发生回溯（backtracking），而一旦发生回溯，那其消耗的时间就会变得很长，有可能是几分钟，也有可能是几个小时，时间长短取决于回溯的次数和复杂度。**

所以应该慎重使用 `Split()` 方法，我们可以用 `String.indexOf() `方法代替` Split() `方法完成字符串的分割。如果实在无法满足需求，就在使用 `Split() `方法时，对回溯问题加以重视就可以了。

### 常用API

#### 查找子串

```java
public int indexOf(String str)//以这个为例
```

返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1

```java
public class StringApiTest1 {
    public static void main(String[] args) {
        String s = "abcdefg";
        int index = s.indexOf("de");
        System.out.println(index);//3
    }
}
```

源码分析

```java
 public int indexOf(String str) {
        return indexOf(str, 0);
 }
```

```java
public int indexOf(String str, int fromIndex) {
        return indexOf(value, 0, value.length,
                str.value, 0, str.value.length, fromIndex);
}
```

```java
 /* *
  * @param   source       源字符串的数组.
  * @param   sourceOffset  源字符串的偏移量.
  * @param   sourceCount   源字符串的长度.
  * @param   target       子串的数组.
  * @param   targetOffset 子串偏移量.
  * @param   targetCount  子串的长度
  * @param   fromIndex    开始索引的位置
  * @return 返回某个子串在当前字符串的起始位置索引
  */
static int indexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {
        if (fromIndex >= sourceCount) {//开始索引的位置>=源字符串的长度
            //如果子串长度为0，则返回的索引为源字符串的长度的位置
            //如果子串不为0，则说明没有找到 返回-1
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {//若起始索引位置小于，变更为0
            fromIndex = 0;
        }
        if (targetCount == 0) {//子串为空串
            return fromIndex;//直接返回开始索引
        }

        char first = target[targetOffset];//子串第一个字符
        //最大位置索引
        //例如：在 123456 中寻找 789，都从第一个字符开始，当123456遍历到4还不满足就不需要遍历了，
        //因为后面字符的个数都不够
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            //在源字符串中找到子串第一个字符
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            //找到第一个后继续遍历
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                //需要对应位置字符相同循环才会继续
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    //找到完整子串
                    return i - sourceOffset;//减去偏移量
                }
            }
        }
        return -1;//没找到
    }
```

寻找子串的算法很普通，相当于暴力解法，对于比较短的字符串比较这种求法基本能满足，所以JDK中没有用到KMP算法，可能也是出于各种性能开销的考虑，因为KMP和Boyer-Moore算法都需要预先计算处理来获得辅助数组，需要一定的时间和空间，这可能在短字符串查找中相比较原始实现耗费更大的代价。而且一般大字符串查找时，开发者也会使用其它特定的数据结构，查找起来更简单。

#### 字符串比较

比较字符串内容是否相同

```java
public boolean equals(Object anObject)
```

将字符串与指定的对象比较，String 类中重写了 equals() 方法用于比较两个字符串的内容是否相等。

```java
public class StringApiTest2 {
    public static void main(String[] args) {
        String s = "1234";
        String s2 = new String("1234");
        System.out.println(s.equals(s2));//true
    }
}
```

源码分析

```java
 public boolean equals(Object anObject) {
        if (this == anObject) {//如果是同一引用直接返回true
            return true;
        }
        if (anObject instanceof String) {//判断左边对象是否为其右边类的实例，即是否为String类的实例
            String anotherString = (String)anObject;
            int n = value.length;//获取本身字符串的长度
            if (n == anotherString.value.length) {//判断两个字符串的长度是否相同
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {//依次比较对应位置字符是否相同
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
```

比较字符串大小

```java
public int compareTo(String anotherString) 
```

按字典顺序比较两个字符串。比较基于字符串中每个字符的Unicode值。此String对象所表示的字符序列将与参数字符串所表示的字符序列进行字典顺序比较。如果此String对象在字典顺序上位于参数字符串之前，则结果为负整数。如果此String对象按照字典顺序在参数字符串之后，则结果为正整数。如果字符串相等，则结果为零。

```java
public class StringApiTest2 {
    public static void main(String[] args) {
        String s = "1234";
        String s2 = new String("1234");
        System.out.println(s.compareTo("12345"));//-1
        System.out.println(s.compareTo(s2));//0
    }
}
```

源码分析

```java
public int compareTo(String anotherString) {
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);//取最短长度作为边界
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        while (k < lim) {//从左往右挨个比较
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;//返回ASCII码之差
            }
            k++;
        }
        return len1 - len2;//回两个字符串的长度差值
    }
```

#### 替换字符串（待完善）

```java
public String replaceAll(String regex, String replacement)//以这个为例
```

使用给定的参数 replacement 替换字符串所有匹配给定的正则表达式的子字符串

```java
public class StringApiTest3 {
    public static void main(String[] args) {
        String s = "123456";
        String s1 = s.replaceAll("12", "00");
        System.out.println(s1);//003456
    }
}
```

源码分析（设计到了正则表达式的原理）

最后调用的是`Matcher`类中的`replaceAll()`方法，使用`StringBuffer`来进行字符串的拼接

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/arrays_string/Snipaste_2022-11-01_17-12-32.png)

#### 字符串拼接

```java
public StringBuilder insert(int offset, String str)//以这个为例 append()在前面已经提到过
```

将 `str` 参数的字符串插入此序列中的指定位置

```java
public class StringBuilderApiTest1 {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("123");
        s.insert(0,"456");
        System.out.println(s);//456123
    }
}
```

源码分析

```java
    @Override
    public StringBuilder insert(int offset, String str) {
        super.insert(offset, str);
        return this;
    }
```

```java
    public AbstractStringBuilder insert(int offset, String str) {
        if ((offset < 0) || (offset > length()))//判断插入位置是否合法
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null)//对空值的处理（可能内部需要吧）
            str = "null";
        int len = str.length();//获取字符串长度
        ensureCapacityInternal(count + len);//扩容，在“StringBuilder拼接字符串详细过程”中已经说过了
        //用源数组对新数组进行初始化，需要为插入的字符串腾出空间，在“拷贝数组”中已经说过了
        System.arraycopy(value, offset, value, offset + len, count - offset);
        str.getChars(value, offset);//添加插入字符串到数组中
        count += len;
        return this;
    }
```

```java
  void getChars(char dst[], int dstBegin) {
      //同样是使用源数组对新数组进行赋值（注意这个方法实在String类中，源数组对应的是插入的字符串）
      System.arraycopy(value, 0, dst, dstBegin, value.length);
  }
```

