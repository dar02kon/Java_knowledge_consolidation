# Java集合（下）

## Map集合体系

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Map.png)

Map集合可以将对象映射到其它对象上，即映射关系（key-value），并且它和数组与Collection一样很容易扩展到多维，很容易通过容器组合来生成强大的数据结构。Map其实就相当于一个映射表（关联数组），其基本思想是它维护的键值对关联，因此我们可以通过键来查找值

**Map集合特点：**

* Map集合的特点都是由键决定的
* Map集合的键是无序,不重复的，无索引的，值不做要求（可以重复）
* Map集合后面重复的键对应的值会覆盖前面重复键的值
* Map集合的键值对都可以为null

```java
public class MapTest1 {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(null,null);
        map.forEach((k,v)->{
            System.out.println(k+" "+v);//null null
        });
        map.put(null,666);
        map.put(1,555);
        map.put(0,999);
        map.forEach((k,v)->{
            //null 666 //覆盖了前面的值
            //0 999
            //1 555
            System.out.println(k+" "+v);
        });
    }
}
```

**Map结构的理解：**

* Map中的key：无序的，不可重复的，使用set存储所有的key，key所在的类要重写equals()和hashCode(), (以hashMap为例)。
* Map中的value:无序的，可重复的，使用Collection存储所有的value, value所在的类要重写equals()
* 一个键值对key-value,构成了一个Entry对象
* Map中的Entry是无序的，不可重复的，使用set存储所有的entry
  

### Map的存在

```java
public interface Map<K,V>
```

Java标准类库中包含了`Map`的几种实现，包括`HashMap`，`TreeMap`，`LinkedHashMap`等，它们都拥有同样的基本接口，这表现在效率，键值对的保存及呈现次序，对象的保存周期，映射表如何在多线程中工作和判定“键”等价的策略等方面。

`Map`这个接口取代了`Dictionary`类，`Dictionary`类是一个完全抽象的类，而不是一个接口。`Map`接口提供三个集合视图，它们允许将映射的内容视为一组键、值的集合或键-值映射的集合。映射的顺序定义为映射集合视图上的迭代器返回其元素的顺序。

`Map`接口与`Collection`一样，是其一系列集合的根目录，是双列集合最深层次的抽象，制定了最初的标准

以下列举了Map中的大部分接口

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-22_19-23-57.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-22_19-25-56.png)

`default void forEach(BiConsumer<? super K, ? super V> action)`用于遍历Map集合，在Map接口中提供了默认实现。`BiConsumer`接受两个输入参数并且不返回结果。 这是`Consumer`的二元专业化 ，可以说是用于保存要执行代码

```java
    default void forEach(BiConsumer<? super K, ? super V> action) {//遍历Map集合
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();//获取key值
                v = entry.getValue();//获取value值
            } catch(IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);//对给定的参数执行此操作
        }
    }
```

```java
public class MapTest2 {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(i,i);
        }
        map.forEach((k,y)->{
            System.out.println(k+" "+y);
        });
    }
}
```

 `action.accept(k, v)`会执行`System.out.println(k+" "+y);`这条语句，当然这里用到的是`HashMap`中重写后的`forEach`方法

在Map接口下还存在一个接口

```java
interface Entry<K,V>
```

映射条目(键-值对)。entrySet方法返回映射的集合视图，其元素属于这个类。获取映射条目引用的唯一方法是从这个集合视图的迭代器中获取。条目对象仅在迭代期间有效；更正式的说法是，如果迭代器在返回映射项之后修改了备份映射，则映射项的行为是未定义的，除非通过映射项上的setValue操作。

* `public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K,V>> comparingByKey()`：返回比较Map的比较器。按键位自然顺序进入。返回的比较器是可序列化的，并且在比较条目与空键时抛出NullPointerException异常。

  ```java
          public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K,V>> comparingByKey() {
              return (Comparator<Map.Entry<K, V>> & Serializable)
                  (c1, c2) -> c1.getKey().compareTo(c2.getKey());
          }
  ```

  从函数名可以得知它的主要作用应该是按照 key 自然排序，并且这是一个泛型方法，主要说明 K 的上限是 Comparable （K必须实现这个接口）不过return语句中，有这么一个&符号确实出乎意料， 盲猜这里应该得同时实现 Comparator 和Serializable接口。
  然后就是一个常见的 lambda 表达式了，重写了 Comparator 中的 compare 方法

  ```java
  public class MapTest2 {
      public static void main(String[] args) {
          Map<String,Integer> map = new HashMap<>();
          for (int i = 5; i > 0; i--) {
              map.put(i+"",i);
          }
          map.put("a",0);
          map.put("b",0);
          map.put("c",0);
          List<Map.Entry<String, Integer>> collect = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
                  Collectors.toList()
          );
          System.out.println(map);//{1=1, a=0, 2=2, b=0, 3=3, c=0, 4=4, 5=5}
          System.out.println(collect);//[1=1, 2=2, 3=3, 4=4, 5=5, a=0, b=0, c=0]
      }
  }
  ```

* `public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp)`：返回比较Map的比较器。使用给定的Comparator按值输入。如果指定的比较器也是可序列化的，则返回的比较器是可序列化的。

  ```java
          public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
              Objects.requireNonNull(cmp);
              return (Comparator<Map.Entry<K, V>> & Serializable)
                  (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
          }
  ```



### AbstractMap的存在

```java
public abstract class AbstractMap<K,V> implements Map<K,V>
```

该类提供`Map`接口的框架实现，以尽量减少实现该接口所需的工作。要实现一个不可修改的映射，程序员只需要扩展这个类并为entrySet方法提供一个实现，该方法返回映射的集合视图。通常，返回的集合将依次在`AbstractSet`之上实现。这个集合不应该支持添加或删除方法，它的迭代器也不应该支持删除方法。

要实现可修改的映射，必须额外覆盖该类的put方法(否则会抛出`UnsupportedOperationException`)， `entrySet().iterator()`返回的迭代器必须额外实现它的`remove`方法。按照map接口规范中的建议，程序员通常应该提供一个void(无参数)和映射构造函数。

一些基本的方法与`AbstractCollection`中类似

* `public boolean containsValue(Object value)`：如果此映射将一个或多个键映射到指定的值，则返回`true` 

  ```java
      public boolean containsValue(Object value) {
          Iterator<Entry<K,V>> i = entrySet().iterator();//获取set集合的迭代器，set集合存储Entry，Entry存储键值对
          if (value==null) {//为空使用==比较
              while (i.hasNext()) {
                  Entry<K,V> e = i.next();
                  if (e.getValue()==null)
                      return true;
              }
          } else {//不为空使用equals方法比较值（重写过）
              while (i.hasNext()) {
                  Entry<K,V> e = i.next();
                  if (value.equals(e.getValue()))
                      return true;
              }
          }
          return false;
      }
  ```

* `public boolean equals(Object o)`：将指定的对象与此映射进行比较以获得相等性，这是容器间的比较

  ```java
      public boolean equals(Object o) {
          if (o == this)//同一引用
              return true;
  
          if (!(o instanceof Map))
              return false;
          Map<?,?> m = (Map<?,?>) o;//转型
          if (m.size() != size())//键值对数不同
              return false;
  
          try {
              Iterator<Entry<K,V>> i = entrySet().iterator();//获取键值对的迭代器
              while (i.hasNext()) {//遍历比较
                  Entry<K,V> e = i.next();
                  K key = e.getKey();
                  V value = e.getValue();
                  //键值对需要对应相同
                  if (value == null) {//value为null使用==进行比较
                      if (!(m.get(key)==null && m.containsKey(key)))
                          return false;
                  } else {//不为null使用equals进行比较（重写后的）
                      if (!value.equals(m.get(key)))
                          return false;
                  }
              }
          } catch (ClassCastException unused) {
              return false;
          } catch (NullPointerException unused) {
              return false;
          }
  
          return true;
      }
  ```

* `public int hashCode()`：返回此Map的哈希码值，Map的哈希码被定义为`entrySet()`视图中每个条目的哈希码的总和。  这确保了`m1.equals(m2)`时`m1.hashCode()==m2.hashCode()`

  ```java
      public int hashCode() {
          int h = 0;
          Iterator<Entry<K,V>> i = entrySet().iterator();
          while (i.hasNext())
              h += i.next().hashCode();
          return h;
      }
  ```

**在`AbstractMap`接口中还存在两个类**

```java
public static class SimpleEntry<K,V> implements Entry<K,V>, java.io.Serializable
```

实现类`Entry<K,V>`方法，维护键和值的条目。value值可以使用setValue方法更改。该类简化了构建自定义映射实现的过程。例如，在`Map.entrySet().toarray`方法中可以很方便返回`SimpleEntry`实例的数组。

```java
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }
```

对key和value对应的哈希值执行异或操作（相应的位相同为0，不相同为1）

```java
public static class SimpleImmutableEntry<K,V> implements Entry<K,V>, java.io.Serializable
```

一种保持不可变键和值的条目。这个类不支持方法`setValue`。这个类在返回键-值映射的线程安全快照的方法中可能很方便。

### HashMap

#### 底层数据结构

**JDK8之前的，底层使用数组+链表组成，JDK8开始后，底层采用数组+链表+红黑树组成。**

##### 数组

数组（Array）是有序的元素序列。有限个类型相同的变量的集合

```java
//在第一次使用时初始化，并根据需要调整大小。
//分配时，长度总是2的幂。允许某些操作的长度为0，以允许当前不需要的引导机制
transient Node<K,V>[] table;
```

**为什么长度总是2的幂？**

为了能让 HashMap 存取高效，尽量较少碰撞，也就是要尽量把数据分配均匀。我们上面也讲到了过了，Hash 值的范围值-2147483648 到 2147483647，前后加起来大概 40 亿的映射空间，只要哈希函数映射得比较均匀松散，一般应用是很难出现碰撞的。但是一个 40 亿长度的数组，内存是放不下的。所以这个散列值是不能直接拿来用的。用之前还要先做对数组的长度取模运算，得到的余数才能用来要存放的位置也就是对应的数组下标。

我们首先可能会想到采用`%`取余的操作来实现，`hash%length`。但是取余运算效率并不高。同时可以发现*取余(%)操作中如果除数是 2 的幂次则等价于与其除数减一的与(&)操作，即`hash%length==hash&(length-1)`的前提是 length 是 2 的 n 次方。* 并且 *采用二进制位操作 &，相对于%能够提高运算效率*。

所以最终为了提高运算效率，使用`&`操作来代替`%`（用`hash & (n - 1)`来代替`hash % n`），HashMap 的必须是 2 的幂次方（n 代表数组长度）。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-23_20-09-17.png)

**还有一点就是在`HashMap`扩容的时候可以保证同一个桶中的元素均匀的散列到新的桶中，具体一点就是同一个桶中的元素在扩容后一半留在原先的桶中，一半放到了新的桶中。（下面说）**

**取余(%)操作中如果除数是 2 的幂次则等价于与其除数减一的与(&)操作，为什么？**

2的幂次在使用二进制表示时，第一位总是1，而剩下的都为0。如 4的二进制为`100`，8的二进制为`1000`

2的幂次-1在使用二进制表示时，所有位都是1，如4-1=3对应的二进制为`11`，8-1=7对应的二进制为`111`

`&`，与操作对应位同时为1则为1，否则为0，所以对2的幂次-1进行与操作其实就是*将最高位抛弃，低位保留*，这与取余操作原理相同

##### 链表

链表是一种物理存储单元上非连续、非顺序的存储结构，数据元素的逻辑顺序是通过链表中的指针链接次序实现的。链表由一系列结点（链表中每一个元素称为结点）组成，结点可以在运行时动态生成。每个结点包括两个部分：一个是存储数据元素的数据域，另一个是存储下一个结点地址的指针域。

```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
```

 **“拉链法”** ：将链表和数组相结合。也就是说创建一个链表数组，数组中每一格就是一个链表。若遇到哈希冲突，则将冲突的值加到链表中即可。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-23_20-24-35.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-23_20-29-32.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-23_20-30-48.png)

##### 红黑树

**二叉查找树**

*概述:*

二叉排序树（Binary Sort Tree）,又称二叉查找树（Binary Search Tree）,亦称二叉搜索树.是数据结构中的一类.在一般情况下,查询效率比链表结构要高

*定义：*

* 一棵二叉查找树是一棵二叉树,每个节点都含有一个Comparable的键（以及对应的值）
* 每个节点的键都大于左子树中任意节点的键而小于右子树中任意节点的键
* 每个节点都有两个链接,左链接、右链接，分别指向自己的左子节点和右子节点，链接也可以指向null
* 尽管链接指向的是节点,可以将每个链接看做指向了另一棵二叉树.这个思路能帮助理解二叉查找树的递归方法

*二叉查找树的特性：*

* 左子树上所有结点的值均小于或等于它的根结点的值
* 右子树上所有结点的值均大于或等于它的根结点的值
* 左、右子树也分别为二叉排序树

*二叉查找树的优点：*

二叉排序树是一种比较有用的折衷方案（采用了二分查找的思想），数组的搜索比较方便,可以直接用下标,但删除或者插入某些元素就比较麻烦；链表与之相反,删除和插入元素很快,但查找很慢。二叉排序树就既有链表的好处,也有数组的好处，在处理大批量的动态的数据是比较有用

*二叉查找树的缺点：*

顺序存储可能会浪费空间(在非完全二叉树的时候)，但是读取某个指定的节点的时候效率比较高O(0)

链式存储相对二叉树比较大的时候浪费空间较少，但是读取某个指定节点的时候效率偏低O(nlogn)

在插入新节点可能会出现一些极端的情况失去平衡，查找效率接近线性查找

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-11-23_21-02-20.png)

**红黑树**

红黑树是一种特化的AVL树平衡二叉树，都是在进行插入和删除操作时通过特定操作保持二叉查找树的平衡，从而获得较高的查找性能。 

红黑树虽然很复杂，但它的最坏情况运行时间也是非常良好的，并且在实践中是高效的： 它可以在O(log n)时间内做查找，插入和删除（n 为树中元素的数目）。

红黑树每个结点上增加一个存储位表示结点的颜色，可以是Red或Black。 通过对任何一条从根到叶子的路径上各个结点着色方式的限制，**红黑树确保没有一条路径会比其他路径长出俩倍，因而是接近平衡的。**

**性质：**
1、根节点是黑色的

2、每个节点不是红色的就是黑色的

3、红色节点的子节点一定是黑色的

4、红色节点不能相连

5、从根节点到叶节点或空子节点的每条路径，必须包含相同数目的黑色节点（即相同的黑色高度）

**红黑树的具体实现后面再说吧呜呜呜**

#### 哈希表

**什么是哈希表？**

根据设定的哈希函数H(key)和处理冲突的方法将一组关键字映射到一个有限的连续的地址集（区间）上，并以关键字在地址集中的“像”作为记录在表中的位置，这种表变被称为哈希表，这一映像过程称为哈希造表或*散列*，所得存储位置称为哈希地址或散列地址。

哈希表的查找效率非常高（拿空间换时间）

**哈希函数的构造方法**

* 直接定址法

  取关键字或者关键字的某个线性函数值为哈希地址。对于不同的关键字不会发生冲突，但实际中很少使用

* 数字分析法

  假设关键字是以r为基的数（如以10为基的十进制数），并且哈希表中可能出现的关键字都是事先知道的，则可以取关键字的若干数位组成哈希地址

* 平方取中法

  取关键字平方后的中间几位为哈希地址。取的位数由表长决定

* 折叠法

  将关键字分割成位数相同的几部分（最后一部分的位数可以不同），然后取这几部分叠加和（舍去进位）作为哈希地址。关键字位数很多，而且关键字中每一位上数字分布大致均匀时，可以采用这种方法

* 除留余数法

  取关键字被某个不大于哈希表表长m的数p除后所得余数为哈希地址，即`H(key) = key MOD p,p<=m`。它不仅可以对关键字直接取模，也可以在折叠，平方取中等运算之后取模。一般为了减少同义词（具有相同函数值的关键字）选取p为质数或不包含小于20的质因数的合数

* 随机数法

**处理冲突的方法**

不同关键字得到同一哈希地址，这种现象称为*冲突*

* 开放地址法

  `Hi=(H(key) + di) MOD m ,i=1,2,...,k(k<=m-1)`，H(key)为哈希函数；m为哈希表表长；di为增量序列

  若di=1，2，3，...，m-1，称为线性探测再散列

  若di=1^2，-1^2，2^2，-2^2，3^3，...，（+|-）k^2，（k<=m/2）称为二次探测再散列

  若di=伪随机数序列，称为伪随机探测再散列

* 再哈希法

  `Hi = RHi(key)，i=1,2,3,...,k`，RHi均是不同的哈希函数，即在同义词产生地址冲突时计算另一个哈希函数的地址，直到冲突不再发生

* 链地址法

  将所有关键字为同义词的记录存储在同一线性表中。

* 建立一个公共溢出区

  将哈希表分为基本表和溢出表两部分，凡是和基本表发生冲突的元素，一律填入溢出表。建立一个公共溢出区域，就是把冲突的都放在另一个地方，不在表里面。

#### HashMap集合的相关机制

我们应该知道`HashMap`中最基础的几个点：

1. `Java`中`HashMap`的实现的基础数据结构是数组，每一对`key`->`value`的键值对组成`Entity`类以双向链表的形式存放到这个数组中
2. 元素在数组中的位置由`key.hashCode()`的值决定，如果两个`key`的哈希值相等，即发生了哈希碰撞，则这两个`key`对应的`Entity`将以链表的形式存放在数组中
3. 调用`HashMap.get()`的时候会首先计算`key`的值，继而在数组中找到`key`对应的位置，然后遍历该位置上的链表找相应的值。
4. 为了提升整个`HashMap`的读取效率，当`HashMap`中存储的元素大小等于桶数组大小乘以负载因子的时候整个`HashMap`就要扩容，以减小哈希碰撞
5. 在`Java 8`中如果桶数组的同一个位置上的链表数量超过一个定值，则整个链表有一定概率会转为一棵红黑树。

整体来看，`HashMap`中最重要的点有四个：**初始化**，**数据寻址-`hash`方法**，**数据存储-`put`方法**，**扩容-`resize`方法**

##### 初始化

**其中用到了一些默认的初始值：**

```java
//默认的负载因子
static final float DEFAULT_LOAD_FACTOR = 0.75f;
```

负载因子决定着何时触发扩容机制。当`HashMap`中的数组元素个数超过阈值时便会触发扩容机制，阈值计算方式是`capacity * loadFactor`，默认`HashMap`中`loadFactor`是0.75。

**为什么是0.75呢？**

在注释中有这样一段话

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_14-44-01.png)

大致意思就是在理想情况下，使用随机哈希码,节点出现的频率在hash桶中遵循泊松分布，同时给出了桶中元素个数和概率的对照表。从上面的表中可以看到当桶中元素到达8个的时候，概率已经变得非常小，也就是说用0.75作为加载因子，每个碰撞位置的链表长度超过８个是几乎不可能的。

所以总得来说就是负载因子为0.75的时候，不仅空间利用率比较高，而且避免了相当多的Hash冲突，使得底层的链表或者是红黑树的高度比较低，提升了空间效率。

```java
//最大容量
static final int MAXIMUM_CAPACITY = 1 << 30;
```

```java
//默认初始化的数组大小 (哈希桶数量)-必须是2的幂
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
```

**`HashMap`的构造方法有四个：**

```java
    //构造一个空的 HashMap，默认初始容量（16）和默认负载因子（0.75）
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; 
    }
```

```java
    //构造一个空的 HashMap具有指定的初始容量和默认负载因子（0.75）
	public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
```

```java
    //构造一个空的 HashMap具有指定的初始容量和负载因子
	public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        //要调整大小的下一个大小值(容量*负载因数)。
        this.threshold = tableSizeFor(initialCapacity);
    }
    //返回给定目标容量的2次幂大小
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

```

`tableSizeFor`的作用是找到大于`cap`的最小的2的整数幂，我们假设n(注意是n，不是cap哈)对应的二进制为000001xxxxxx，其中x代表的二进制位是0是1我们不关心，

`n |= n >>> 1;`执行后`n`的值为：

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_15-09-22.png)

可以看到此时`n`的二进制最高两位已经变成了1，再接着执行第二行代码：

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_15-09-33.png)

可见`n`的二进制最高四位已经变成了1，等到执行完代码`n |= n >>> 16;`之后，`n`的二进制最低位全都变成了1，也就是`n = 2^x - 1`其中x和n的值有关，如果没有超过`MAXIMUM_CAPACITY`，最后会返回一个2的正整数次幂，因此`tableSizeFor`的作用就是保证返回一个比入参大的最小的2的正整数次幂。

```java
    //用与指定Map相同的映射构造一个新的HashMap
    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }
```

我们执行构造方法后`HashMap`的空间并没有开辟（前三种），只是将一些即将用到的参数进行初始化（如负载因子），在`HashMap`第一次执行`put`的时候才会开辟数组空间

##### 数据寻址-`hash`方法

在`HashMap`这个特殊的数据结构中，`hash`函数承担着寻址定址的作用，其性能对整个`HashMap`的性能影响巨大，那什么才是一个好的`hash`函数呢？

- 计算出来的哈希值足够散列，能够有效减少哈希碰撞
- 本身能够快速计算得出，因为`HashMap`每次调用`get`和`put`的时候都会调用`hash`方法

```java
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

最终我们需要将哈希值映射到数组下标上，即`hash & (table.length - 1)`

hash值其实是一个int类型，二进制位为32位，而HashMap的table数组初始化size为16，取余操作为`hashCode & 15 ==> hashCode & 1111` 。这将会存在一个巨大的问题，1111只会与hashCode的低四位进行与操作，也就是hashCode的高位其实并没有参与运算，会导很多hash值不同而高位有区别的数，最后算出来的索引都是一样的。

为了避免这种情况，HashMap将高16位与低16位进行异或，这样可以保证高位的数据也参与到与运算中来，以增大索引的散列程度，让数据分布得更为均匀。

*为什么用异或，不用 & 或者 | 操作，因为异或可以保证两个数值的特性，&运算使得结果向0靠近， |运算使得结果向1靠近*

##### 数据存储-`put`方法

在`Java 8`中`put`这个方法的思路分为以下几步：

1. 调用`key`的`hashCode`方法计算哈希值，并据此计算出数组下标index
2. 如果发现当前的桶数组为`null`，则调用`resize()`方法进行初始化
3. 如果没有发生哈希碰撞，则直接放到对应的桶中
4. 如果发生哈希碰撞，且节点已经存在，就替换掉相应的`value`
5. 如果发生哈希碰撞，且桶中存放的是树状结构，则挂载到树上
6. 如果碰撞后为链表，添加到链表尾，如果链表长度超过`TREEIFY_THRESHOLD`默认是8，则将链表转换为树结构
7. 数据`put`完成后，如果`HashMap`的总数超过`threshold`就要`resize`

具体代码以及注释如下：

```java
public V put(K key, V value) {
    // 调用上文已经分析过的hash方法
    return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        // 第一次put时，会调用resize进行桶数组初始化
        n = (tab = resize()).length;
    // 根据数组长度和哈希值相与来寻址
    if ((p = tab[i = (n - 1) & hash]) == null)
        // 如果没有哈希碰撞，直接放到桶中
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            // 哈希碰撞，且节点已存在，直接替换
            e = p;
        else if (p instanceof TreeNode)
            // 哈希碰撞，树结构
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 哈希碰撞，链表结构
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 链表过长，转换为树结构
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    // 如果节点已存在，则跳出循环
                    break;
                // 否则，指针后移，继续后循环
                p = e;
            }
        }
        if (e != null) {
            // 对应着上文中节点已存在，跳出循环的分支
            // 直接替换
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        // 如果超过阈值，还需要扩容
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

##### 扩容-`resize`方法

`resize`是整个`HashMap`中最复杂的一个模块，如果在`put`数据之后超过了`threshold`的值，则需要扩容，扩容意味着桶数组大小变化，我们在前文中分析过，`HashMap`寻址是通过`index =(table.length - 1) & key.hash();`来计算的，现在`table.length`发生了变化，势必会导致部分`key`的位置也发生了变化，`HashMap`是如何设计的呢？

这里就涉及到桶数组长度为2的正整数幂的第二个优势了：当桶数组长度为2的正整数幂时，如果桶发生扩容（长度翻倍），则桶中的元素大概只有一半需要切换到新的桶中，另一半留在原先的桶中就可以，并且这个概率可以看做是均等的。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_16-46-14.png)

通过这个分析可以看到如果在即将扩容的那个位上`key.hash()`的二进制值为0，则扩容后在桶中的地址不变，否则，扩容后的最高位变为了1，新的地址也可以快速计算出来`newIndex = oldCap + oldIndex;`

下面是`Java 8`中的实现：

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        // 如果oldCap > 0则对应的是扩容而不是初始化
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        // 没有超过最大值，就扩大为原先的2倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        // 如果oldCap为0， 但是oldThr不为0，则代表的是table还未进行过初始化
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) {
        // 如果到这里newThr还未计算，比如初始化时，则根据容量计算出新的阈值
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            // 遍历之前的桶数组，对其值重新散列
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    // 如果原先的桶中只有一个元素，则直接放置到新的桶中
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    // 如果原先的桶中是链表
                    Node<K,V> loHead = null, loTail = null;
                    // hiHead和hiTail代表元素在新的桶中和旧的桶中的位置不一致
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        // loHead和loTail代表元素在新的桶中和旧的桶中的位置一致
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        // 新的桶中的位置 = 旧的桶中的位置 + oldCap
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

**一些常用方法的源码解析后面再说**

### LinkedHashMap

```java
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
```

**特点：**

* 由键决定：有序、不重复、无索引。
* 这里的有序指的是保证存储和取出的元素顺序一致
* 原理：底层数据结构是依然哈希表，只是每个键值对元素又额外的多了一个双链表的机制记录存储的顺序。

```java
//链表的头节点    
transient LinkedHashMap.Entry<K,V> head;
//链表的尾节点
transient LinkedHashMap.Entry<K,V> tail;
```

HashMap和双向链表合二为一即是LinkedHashMap。所谓LinkedHashMap，其落脚点在HashMap，因此更准确地说，它是一个将所有Entry节点链入一个双向链表的HashMap。由于LinkedHashMap是HashMap的子类，所以LinkedHashMap自然会拥有HashMap的所有特性。比如，LinkedHashMap的元素存取过程基本与HashMap基本类似，只是在细节实现上稍有不同。当然，这是由LinkedHashMap本身的特性所决定的，因为它额外维护了一个双向链表用于保持迭代顺序。

不过遗憾的是，HashMap是无序的，也就是说，迭代HashMap所得到的元素顺序并不是它们最初放置到HashMap的顺序。HashMap的这一缺点往往会造成诸多不便，因为在有些场景中，我们确需要用到一个可以保持插入顺序的Map。庆幸的是，JDK为我们解决了这个问题，它为HashMap提供了一个子类 —— LinkedHashMap。虽然LinkedHashMap增加了时间和空间上的开销，但是它通过维护一个额外的双向链表保证了迭代顺序。

```java
//此链接散列映射的迭代排序方法:对于访问顺序为true，对于插入顺序为false。
final boolean accessOrder;
```

特别地，该迭代顺序可以是插入顺序，也可以是访问顺序。因此，根据链表中元素的顺序可以将LinkedHashMap分为：保持插入顺序的LinkedHashMap 和 保持访问顺序的LinkedHashMap，其中LinkedHashMap的默认实现是按插入顺序排序的。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_18-37-51.png)

内部节点的next指针用于维护`HashMap`各个桶中的`Entry`链，before、after用于维护`LinkedHashMap`的双向链表

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_18-48-48.png)

### TreeMap

```java
public class TreeMap<K,V> extends AbstractMap<K,V> implements NavigableMap<K,V>, Cloneable, java.io.Serializable
```

**特点：**

* 由键决定特性：不重复、无索引、可排序
* 可排序：按照键数据的大小默认升序（有小到大）排序。只能对键排序。
* 注意：TreeMap集合是一定要排序的，可以默认排序，也可以将键按照指定的规则进行排序
* `TreeMap`跟`TreeSet`一样底层原理是一样的。

TreeMap是红黑二叉树的典型实现

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/java_note/Snipaste_2022-12-03_19-00-08.png)

root用来存储整个树的根节点

**具体分析后面再说**
