# Java集合（中）

## Collection集合体系

### 概述

**单列集合UML图（部分）**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/collection.png)

#### Collection的存在

集合层次结构中的*根界面* 。 集合表示一组包含*元素*的对象。  一些集合允许重复元素，而其它集合则可能不允许；有些集合的元素是有序的，而有些是无序的。 JDK不提供此接口的任何*直接*实现：它提供了更具体的子接口的实现，如`Set`和`List`  。 Collection通常用于传递集合，并在需要最大的通用性的情况下对其进行操作。

**Collecion设计职责（个人理解）**

Collecion作为单列集合的根接口，它将单列集合的共性最大程度的抽象出来，单列集合的具体类都需要去实现该接口并根据需要去实现相应的方法（Collection中存在默认实现的方法）。这种设计思想在Java其它模块也经常出现，至少有以下好处：

* 符合面向对象设计原则的开闭原则（不修改实体的基础上扩展功能）与依赖倒置原则（针对抽象层编程）。符合Java面向接口编程的理念，我们在使用方法是可以根据需要选择相应的子类，这也是Java多态的一种体现。
* Java集合类库十分庞大，使用一个接口管理一些常用的方法，既方便开发者去使用（只需要关注对外的接口，不需要去关注其内部的各种实现）也方便开发者去维护（只需要修改子类即可，对外接口不用变）。添加方法则可以再编写一个接口去继承Collection，如`List`

 **Collection操作**

```java
    int size();//返回此集合中的元素数

    boolean isEmpty();//如果此集合不包含元素，则返回 true 

    Iterator<E> iterator();//返回此集合中的元素的迭代器

    boolean contains(Object o);//如果此集合包含指定的元素，则返回 true 

    boolean containsAll(Collection<?> c);//如果此集合包含指定 集合中的所有元素，则返回true

    Object[] toArray();//返回一个包含此集合中所有元素的数组

    <T> T[] toArray(T[] a);//返回包含此集合中所有元素的数组; 返回的数组的运行时类型是指定数组的运行时类型

    boolean add(E e);//确保此集合包含指定的元素（可选操作）

    boolean addAll(Collection<? extends E> c);//将指定集合中的所有元素添加到此集合（可选操作）

    boolean remove(Object o);//从该集合中删除指定元素的单个实例（如果存在）（可选操作）

    boolean removeAll(Collection<?> c);//删除指定集合中包含的所有此集合的元素（可选操作）

    default boolean removeIf(Predicate<? super E> filter) {//删除满足给定谓词的此集合的所有元素
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    boolean retainAll(Collection<?> c);//仅保留此集合中包含在指定集合中的元素（可选操作）

    void clear();//从此集合中删除所有元素（可选操作）

    boolean equals(Object o);//将指定的对象与此集合进行比较以获得相等性

    int hashCode();//返回此集合的哈希码值

    @Override
    default Spliterator<E> spliterator() {//创建一个Spliterator在这个集合中的元素
        return Spliterators.spliterator(this, 0);
    }

    default Stream<E> stream() {//返回以此集合作为源的顺序 Stream 
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> parallelStream() {//返回可能并行的 Stream与此集合作为其来源
        return StreamSupport.stream(spliterator(), true);
    }

```

**可选操作**

执行各种不同的添加和移除的方法在Collection接口中都是可选操作，实现类可以不为它们提供功能定义。接口是面向对象设计中的契约，它声明*无论你选择如何实现该接口，保证你可以向这个接口发送这些消息*，可选操作违背了这个原则，它声明调用某些方法将不会执行有意义的操作，而且还会抛出异常。

但在我们的使用过程中，这种异常情况好像很少出现。如果一个操作是可选的，编译器仍会严格要求我们只能调用该接口中的方法。这与动态语言不同，动态语言可以在任何对象上调用任何方法，并且可以在运行时发现莫个特定调用是否可以工作。另外，将Collection当作参数传递的大部分方法，只会从Collection中执行读取操作，而读取操作都是不可选的。

那为什么这么设计呢？

为了防止在设计中出现接口爆炸的情况，容器类库中的其它设计看起来总是在描述每个主题的各种变体，而最终往往会导致接口过剩。“未获得支持”这种操作可以实现*容器易用易学*，未获支持的操作是一种特例，等需要时再去实现。同时，为了这种方法能很好的工作：

* `UnsupportedOperationException`必须是一种罕见的事件，即对于大多数类来说所有操作都应该可以正常工作，只有在特例中才会有未获支持的操作
* 如果使用的操作是未获支持的，实现接口时我们应该会发现`UnsupportedOperationException`异常（至少在将代码交给别人前发现）

```java
public class CollectionTest1 {
    static void test(String msg, List<String> list) {
        System.out.println("--- " + msg + " ---");
        Collection<String> c = list;
        Collection<String> subList = list.subList(1, 8);
        // Copy of the sublist:
        Collection<String> c2 = new ArrayList<String>(subList);
        try {
            c.retainAll(c2);
        } catch (Exception e) {
            System.out.println("retainAll(): " + e);
        }
        try {
            c.removeAll(c2);
        } catch (Exception e) {
            System.out.println("removeAll(): " + e);
        }
        try {
            c.clear();
        } catch (Exception e) {
            System.out.println("clear(): " + e);
        }
        try {
            c.add("X");
        } catch (Exception e) {
            System.out.println("add(): " + e);
        }
        try {
            c.addAll(c2);
        } catch (Exception e) {
            System.out.println("addAll(): " + e);
        }
        try {
            c.remove("C");
        } catch (Exception e) {
            System.out.println("remove(): " + e);
        }
        // The List.set() method modifies the value but
        // doesn't change the size of the data structure:
        try {
            list.set(0, "X");
        } catch (Exception e) {
            System.out.println("List.set(): " + e);
        }
    }
    public static void main(String[] args) {
        List<String> list =
                Arrays.asList("A B C D E F G H I J K L".split(" "));
        test("Modifiable Copy", new ArrayList<String>(list));
        test("Arrays.asList()", list);
        test("unmodifiableList()",
                Collections.unmodifiableList(
                        new ArrayList<String>(list)));
    }
}
```

```
--- Modifiable Copy ---
--- Arrays.asList() ---
retainAll(): java.lang.UnsupportedOperationException
removeAll(): java.lang.UnsupportedOperationException
clear(): java.lang.UnsupportedOperationException
add(): java.lang.UnsupportedOperationException
addAll(): java.lang.UnsupportedOperationException
remove(): java.lang.UnsupportedOperationException
--- unmodifiableList() ---
retainAll(): java.lang.UnsupportedOperationException
removeAll(): java.lang.UnsupportedOperationException
clear(): java.lang.UnsupportedOperationException
add(): java.lang.UnsupportedOperationException
addAll(): java.lang.UnsupportedOperationException
remove(): java.lang.UnsupportedOperationException
List.set(): java.lang.UnsupportedOperationException
```

**`Arrays.asList`会生成一个List，它固定大小的数组，仅支持那些不会改变数组大小的操作**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-24-05.png)

这里的`ArrayList`是`Arrays`一个私有的内部类，不是我们经常使用的那个。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-29-13.png)

这个类继承自`AbstractList`并且只覆盖了部分方法，那些涉及到修改的方法基本没有覆盖，很多修改操作在`AbstractList`中的默认实现都是抛出`UnsupportedOperationException`异常。所以通过`Arrays.asList`生成的List在执行一些修改操作时会抛出异常。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-34-34.png)

*`Arrays.asList`可以将数组转换成List集合（通过适配器将数组和List关联起来），这里用到了适配器模式，即将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。*

**`Collections.unmodifiableList`会将容器包装到一个代理中，目标是产生“常量”容器对象**

#### AbstractCollection的存在

```java
public abstract class AbstractCollection<E> implements Collection<E>
```



该类提供了Collection接口的`骨架`实现，以尽量减少实现此接口所需的工作量。

为了实现一个不可修改的集合，程序员只需要扩展这个类并提供`iterator`和`size`方法的实现。  （ `iterator`方法返回的迭代器必须实现`hasNext`和`next` ） 

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-41-38.png)

要实现可修改的集合，程序员必须另外覆盖此类的`add`方法（否则将抛出`UnsupportedOperationException`  ），并且由`iterator`方法返回的迭代器必须另外实现其`remove`方法。

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-43-10.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_10-45-16.png)

**部分方法实现**

* `public boolean contains(Object o)`：如果此集合包含指定的元素，则返回 `true` 

  ```java
      public boolean contains(Object o) {
          Iterator<E> it = iterator();//通过迭代器进行遍历
          if (o==null) {//指定元素为空
              while (it.hasNext())
                  if (it.next()==null)//使用等号进行比较
                      return true;
          } else {//指定元素不为空
              while (it.hasNext())
                  if (o.equals(it.next()))//使用equals方法进行比较，默认还是使用等号进行比较
                      return true;
          }
          return false;
      }

* `public <T> T[] toArray(T[] a)`：返回包含此集合中所有元素的数组; 返回的数组的运行时类型是指定数组的运行时类型

  ```java
      @SuppressWarnings("unchecked")//告诉编译器忽略 unchecked 警告信息
      public <T> T[] toArray(T[] a) {
          // 获取数组的大小（可能与元素的个数不相同）
          int size = size();
          // 判断参数a所指向的内存区域是否足够，不够则创建具有指定组件类型和尺寸的新数组（利用反射创建数组）
          T[] r = a.length >= size ? a :
                    (T[])java.lang.reflect.Array
                    .newInstance(a.getClass().getComponentType(), size);
          Iterator<E> it = iterator();//获取迭代器
  
          for (int i = 0; i < r.length; i++) {
              if (! it.hasNext()) { // 元素比预期的少，如果重新开辟了空间直接返回会有浪费
                  if (a == r) {//没有重新开辟数组，参数a与r指向的是同一块内存
                      r[i] = null; // 赋空，相当于一个终止符
                  } else if (a.length < i) {//重新开辟了的数组，参数数组a的长度要小于元素个数
                      return Arrays.copyOf(r, i);//根据数组r重新创建一个大小合适的数组并返回，i即为数组大小
                  } else {//重新开辟了的数组，参数数组a的长度要大于等于元素个数
                      System.arraycopy(r, 0, a, 0, i);//以r为原数组拷贝元素到a，0表示开始拷贝的下标，i表示个数
                      if (a.length > i) {//数组a的长度要大于元素的个数
                          a[i] = null;//赋空，相当于一个终止符
                      }
                  }
                  return a;
              }
              r[i] = (T)it.next();//赋值，需要转型
          }
          //元素比预期的多，需要完成最后的拷贝
          return it.hasNext() ? finishToArray(r, it) : r;
      }
  
      //要分配的数组的最大容量(除非必要)。有些虚拟机在数组中保留一些头字。
      //尝试分配更大的阵列可能会导致OutOfMemoryError:请求的内存大小超过虚拟机限制 
      private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
  
      @SuppressWarnings("unchecked")
      private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
          int i = r.length;
          while (it.hasNext()) {//继续迭代
              int cap = r.length;
              if (i == cap) {//数组元素个数已经达到容量
                  int newCap = cap + (cap >> 1) + 1;//重新确定新数组大小，差不多扩容了一半
                  // 考虑溢出
                  if (newCap - MAX_ARRAY_SIZE > 0)//比预定的最大容量还要大
                      newCap = hugeCapacity(cap + 1);//尝试去分配，可能会抛出异常
                  r = Arrays.copyOf(r, newCap);//拷贝数组
              }
              r[i++] = (T)it.next();//赋值
          }
          // 如果存在过度分配则修剪
          return (i == r.length) ? r : Arrays.copyOf(r, i);
      }
  
      private static int hugeCapacity(int minCapacity) {
          if (minCapacity < 0) // 变为赋值肯定溢出了
              throw new OutOfMemoryError
                  ("Required array size too large");
          //重新确定数组大小
          return (minCapacity > MAX_ARRAY_SIZE) ?
              Integer.MAX_VALUE :
              MAX_ARRAY_SIZE;
      }
  ```

  `public static Object newInstance(Class<?> componentType, int length);`用于创建具有指定类型和尺寸的新数组（利用反射创建数组），`getClass().getComponentType()`可用于动态的获取组件类型

  具体实现中它调用了自己的一个本地方法`private static native Object newArray(Class<?> componentType, int length)`来进行数组的创建

  `private static <T> T[] finishToArray(T[] r, Iterator<?> it)`用于收尾，与字符串中`StringBuilder`扩容类似

* `public boolean retainAll(Collection<?> c)`：仅保留此集合中包含在指定集合中的元素（可选操作）。 换句话说，从该集合中删除所有不包含在指定集合中的元素

  这个实现遍历这个集合，依次检查迭代器返回的每个元素，看看它是否包含在指定的集合中。  如果没有这样包含，它将使用迭代器的`remove`方法从该集合中删除。 

  注意，此实现将抛出`UnsupportedOperationException`如果迭代由`iterator`方法返回没有实现`remove`方法，并且此collection包含指定集合中不存在的一个或多个元素，即有要删除的元素

  ```java
      public boolean retainAll(Collection<?> c) {
          Objects.requireNonNull(c);//判空
          boolean modified = false;
          Iterator<E> it = iterator();//获取迭代器
          while (it.hasNext()) {
              if (!c.contains(it.next())) {//不包含
                  it.remove();//迭代删除
                  modified = true;
              }
          }
          return modified;
      }
  ```

* `public String toString()`：返回此集合的字符串表示形式。 字符串表示由集合的元素的列表按照它们的迭代器返回的顺序包含在方括号（  `"[]"` ）中。 相邻元素由字符`", "` （逗号和空格）分隔。

  ```java
      public String toString() {
          Iterator<E> it = iterator();
          if (! it.hasNext())//空集合
              return "[]";
  
          StringBuilder sb = new StringBuilder();//拼接字符串
          sb.append('[');
          for (;;) {
              E e = it.next();//迭代遍历
              sb.append(e == this ? "(this Collection)" : e);
              if (! it.hasNext())//迭代结束
                  return sb.append(']').toString();
              sb.append(',').append(' ');//使用逗号和空格将元素隔开
          }
      }
  ```

### List系列集合

**List系列集合特点 **

* 有序：存储和取出的元素顺序一致
* 有索引：可以通过索引操作元素
* 可重复：存储的元素可以重复

```java
public interface List<E> extends Collection<E>
```

List承诺将元素维护在特定的序列，List接口在Collection的基础上添加了大量的方法，是得可以在List中间删除和插入元素

有两种常见的List：

* 基本的ArrayList：一般用于随机访问元素，但在List中间插入和删除元素是较慢
* LinkedList：它通过较低代价在List中间进行插入和删除操作，提供了优化的顺序访问。LinkedList在随机访问中相对较慢，但它的特新集较ArrayList更大

#### AbstractList的存在

```java
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> 
```

此类提供的骨干实现的`List`接口以最小化来实现该接口由一个“随机访问”数据存储备份所需的工作（如阵列）。对于顺序存取的数据（如链接列表）， `AbstractSequentialList`应优先使用此类。

要实现一个不可修改的列表，程序员只需要扩展这个类并提供`get(int)`和`size()`方法的实现。 

要实现可修改的列表，程序员必须另外覆盖`set(int,E)`方法（否则会抛出一个`UnsupportedOperationException` ）。  如果列表是可变大小，则程序员必须另外覆盖`add(int,E)`和`remove(int)`方法

*`AbstractList`用到了模板模式，即定义一个算法骨架，允许子类为一个或多个步骤提供实现。模板方法使得子类可以在不改变算法结构的情况下，重新定义算法的某些步骤。一次性实现一个算法的不变的部分，将可变的行为留给子类实现。AbstractList可以说是ArrayList的模板*

**部分方法实现**

* `protected transient int modCount = 0;`

  表示已被*结构修改*的次数。结构修改是改变列表大小的那些修改，或以其他方式扰乱它，使得正在进行的迭代可能产生不正确的结果。

  该字段由迭代器和列表迭代器实现使用，由`iterator`和`listIterator`方法返回。  如果该字段的值意外更改，迭代器（或列表迭代器）将抛出一个`ConcurrentModificationException`响应`next`  ， `remove` ， `previous` ，  `set`或`add`操作。  这提供了*故障快速*行为，而不是面对在迭代期间的并发修改的非确定性行为。 

  *子类使用此字段是可选的。*  如果一个子类希望提供故障快速迭代器（和列表迭代器），那么它只需要在其`add(int,  E)`和`remove(int)`方法（以及它覆盖的任何其他方法导致对列表的结构修改）中增加该字段。  对`add(int,  E)`或`remove(int)`单一调用必须向该字段添加不超过一个，否则迭代器（和列表迭代器）将抛出伪造的`ConcurrentModificationExceptions`  。 如果实现不希望提供故障快速迭代器，则该字段可能会被忽略。 

* ` public Iterator<E> iterator()`：以正确的顺序返回该列表中的元素的迭代器。

  此实现返回一个简单的实现Iterator接口，依托后台列表的`size()` ，  `get(int)`和`remove(int)`方法。 

  除非列表的`remove(int)`方法被覆盖，否则通过此方法返回的迭代器将抛出`UnsupportedOperationException`以响应其`remove`方法。 

  如同（protected） `modCount`字段的规范中所描述的那样，这种实现可以面对并发修改来抛出运行时异常

  ```java
      public Iterator<E> iterator() {
          return new Itr();
      }
  ```

  ```java
  private class Itr implements Iterator<E> {
          // 当前元素索引
          int cursor = 0;
  
          //记录元素索引，刚遍历的那个元素，为删除元素提供索引
          int lastRet = -1;
  
          //迭代器认为列表应该具有的modCount值。如果违反了这个期望，迭代器就检测到并发修改。
          int expectedModCount = modCount;
  
          public boolean hasNext() {//判断迭代是否还要继续
              return cursor != size();//判断当前元素索引是否有效，size()可以获取元素个数，即最大下标+1
          }
  
          public E next() {//返回当前元素，指针后移
              checkForComodification();//检测是否存在并发修改
              try {
                  int i = cursor;
                  E next = get(i);//获取元素
                  lastRet = i;//记录元素位置
                  cursor = i + 1;//指针后移
                  return next;
              } catch (IndexOutOfBoundsException e) {
                  checkForComodification();
                  throw new NoSuchElementException();
              }
          }
  
          public void remove() {
              if (lastRet < 0)//要删除元素的索引不合法
                  throw new IllegalStateException();
              checkForComodification();
  
              try {
                  AbstractList.this.remove(lastRet);//删除上一个元素（在用户看来可能是当前元素）
                  if (lastRet < cursor)
                      cursor--;//重新确定遍历指针位置
                  lastRet = -1;//重置索引为-1
                  expectedModCount = modCount;//重新确定期望值
              } catch (IndexOutOfBoundsException e) {
                  throw new ConcurrentModificationException();
              }
          }
  
          final void checkForComodification() {//检测是否存在并发修改
              if (modCount != expectedModCount)
                  throw new ConcurrentModificationException();
          }
      }
  ```

  使用迭代器，在用户看来出现某个元素，才会认为遍历到这个元素了（指针停留在这个元素上），但实际上指针已经后移，如果要删除这个元素，就需要再有一个指针去记录这个元素的索引。这也规定了如果要迭代删除所有元素，需要这么些

  ```java
          Iterator<Integer> iterator = list.iterator();
          while (iterator.hasNext()){
              iterator.next();
              iterator.remove();
          }
  ```

* `public ListIterator<E> listIterator(final int index)`：

  从列表中的指定位置开始，返回列表中的元素（按正确顺序）的列表迭代器。指定的索引表示初始调用将返回的第一个元素为`next` 。初始调用`previous`将返回指定索引减1的元素。

  此实现返回一个简单的贯彻`ListIterator`扩展了实施接口`Iterator`通过返回的接口`iterator()`方法。  该`ListIterator`实现依赖于后台列表的`get(int)` ， `set(int,  E)` ， `add(int, E)`和`remove(int)`方法。 

  请注意，此实现返回的列表迭代器将抛出一个`UnsupportedOperationException`响应其`remove`  ， `set`和`add`方法，除非列表的`remove(int)` ，  `set(int, E)`和`add(int, E)`方法被覆盖。 

  如同（protected） `modCount`字段的规范中所描述的那样，这种实现可以面对并发修改来抛出运行时异常。

  ```java
      public ListIterator<E> listIterator(final int index) {
          rangeCheckForAdd(index);//判断所有位置是否合法
  
          return new ListItr(index);
      }
  ```

  ```java
      private class ListItr extends Itr implements ListIterator<E> {
          ListItr(int index) {
              cursor = index;
          }
  
          public boolean hasPrevious() {//判断是否有前驱
              return cursor != 0;
          }
  
          public E previous() {//获取前驱元素
              checkForComodification();//检测是否存在并发修改
              try {
                  int i = cursor - 1;
                  E previous = get(i);//获取元素
                  lastRet = cursor = i;//指针前移
                  return previous;
              } catch (IndexOutOfBoundsException e) {
                  checkForComodification();
                  throw new NoSuchElementException();
              }
          }
  
          public int nextIndex() {//获取下一个元素的索引
              return cursor;
          }
  
          public int previousIndex() {//获取前驱元素索引
              return cursor-1;
          }
  
          public void set(E e) {//赋值
              if (lastRet < 0)
                  throw new IllegalStateException();
              checkForComodification();
  
              try {
                  AbstractList.this.set(lastRet, e);
                  expectedModCount = modCount;//重新确定期望值
              } catch (IndexOutOfBoundsException ex) {
                  throw new ConcurrentModificationException();
              }
          }
  
          public void add(E e) {//添加元素
              checkForComodification();
  
              try {
                  int i = cursor;
                  AbstractList.this.add(i, e);
                  lastRet = -1;//重置为-1
                  cursor = i + 1;//指针后移
                  expectedModCount = modCount;//重新确定期望值
              } catch (IndexOutOfBoundsException ex) {
                  throw new ConcurrentModificationException();
              }
          }
      }
  ```

  `ListItr`是在`Itr`的基础上进行拓展的，所以两者很相像

* `public List<E> subList(int fromIndex, int toIndex)`：返回指定的`fromIndex` （含）和`toIndex`之间的列表部分的视图。  （如果`fromIndex`和`toIndex`相等，返回的列表为空。）返回的列表由此列表支持，因此返回的列表中的非结构性更改将反映在此列表中，反之亦然。  返回的列表支持此列表支持的所有可选列表操作。

  ```java
      public List<E> subList(int fromIndex, int toIndex) {
          return (this instanceof RandomAccess ?
                  new RandomAccessSubList<>(this, fromIndex, toIndex) :
                  new SubList<>(this, fromIndex, toIndex));
      }
  ```

  ```java
  public interface RandomAccess {
  }
  ```

  List实现使用的`标记`界面，表明它们支持快速（通常为恒定时间）随机访问。此接口的主要目的是允许通用算法更改其行为，以便在应用于随机访问列表或顺序访问列表时提供良好的性能。

  当施加到顺序访问列表（如`LinkedList），`用于操纵随机接入列表（如`ArrayList）`最好算法可以产生二次行为。  鼓励通用列表算法在应用如果应用于顺序访问列表之前提供较差性能的算法，并且如果需要改变其行为以确保可接受的性能，则检查给定列表是否为`instanceof`此接口。 

  人们认识到，随机访问和顺序访问之间的区别通常是模糊的。  例如，一些`List`实现提供渐近的线性访问时间，如果它们在实践中获得巨大但是恒定的访问时间。 这样的一个`List`实现应该通常实现这个接口。 根据经验，`List`实现应实现此接口，如果对于类的典型实例，此循环： 

  ```java
    for (int i=0, n=list.size(); i < n; i++)
           list.get(i); 
  ```

  比这个循环运行得更快：

  ```java
    for (Iterator i=list.iterator(); i.hasNext(); )
           i.next(); 
  ```

  `new SubList<>(this, fromIndex, toIndex)`创建了一个`SubList`对象，`SubList`类继承自`AbstractList`，并且重写了相应的一些方法。`SubList`类通过以下三个变量来记录内存区域，元素下标，元素个数

  ```java
  private final AbstractList<E> l;
  private final int offset;
  private int size;
  ```

  在构造方法中完成初始化

  ```java
      SubList(AbstractList<E> list, int fromIndex, int toIndex) {
          if (fromIndex < 0)
              throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
          if (toIndex > list.size())
              throw new IndexOutOfBoundsException("toIndex = " + toIndex);
          if (fromIndex > toIndex)
              throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                                 ") > toIndex(" + toIndex + ")");
          l = list;
          offset = fromIndex;
          size = toIndex - fromIndex;//元素个数
          this.modCount = l.modCount;
      }
  ```

  `new RandomAccessSubList<>(this, fromIndex, toIndex)`创建了一个`RandomAccessSubList`对象，`RandomAccessSubList`类继承自`SubList`，并且实现了`RandomAccess`接口（上标记）

  ```java
  class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
      RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
          super(list, fromIndex, toIndex);
      }
  
      public List<E> subList(int fromIndex, int toIndex) {
          return new RandomAccessSubList<>(this, fromIndex, toIndex);
      }
  }
  ```

  上面这两种方式获取的对象（句柄）操作的都是同一块区域，这也再次说明了`subList`函数返回的子集合的非结构性更改将反映到原集合中

  ```java
  public class AbstractListTest2 {
      public static void main(String[] args) {
          List<Integer> list = new ArrayList<>();
          for (int i = 0; i < 10; i++) {
              list.add(i);
          }
          List<Integer> subList = list.subList(0, 5);
          System.out.println(list);//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
          subList.clear();
          System.out.println(list);//[5, 6, 7, 8, 9]
      }
  }
  ```

* `public boolean equals(Object o)`：重写了`equals`与`hashCode`方法

  ```java
      public boolean equals(Object o) {
          if (o == this)
              return true;
          if (!(o instanceof List))
              return false;
  
          ListIterator<E> e1 = listIterator();
          ListIterator<?> e2 = ((List<?>) o).listIterator();
          while (e1.hasNext() && e2.hasNext()) {//迭代遍历进行比较
              E o1 = e1.next();
              Object o2 = e2.next();
              //先判空，o1.equals(o2)会根据o1的类型调用相关的equals方法
              if (!(o1==null ? o2==null : o1.equals(o2)))
                  return false;
          }
          return !(e1.hasNext() || e2.hasNext());
      }
  
      public int hashCode() {//返回此列表的哈希码值
          int hashCode = 1;
          for (E e : this)
              hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
          return hashCode;
      }
  ```



#### ArrayList

```java
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

可调整大小的数组的实现`List`接口。实现所有可选列表操作，并允许所有元素，包括`null` 。除了实现List  `接口`之外，该类还提供了一些方法来操纵内部使用的存储列表的数组的大小。（这个类是大致相当于`Vector，`不同之处在于它是不同步的）。

该`size，isEmpty，get，set，iterator`和`listIterator`操作在固定时间内运行。  `add`操作以*摊余常数运行* ，即添加n个元素需要O(n)个时间。  所有其他操作都以线性时间运行（粗略地说）。 与`LinkedList`实施相比，常数因子较低。 

每个`ArrayList`实例都有一个*容量* 。  容量是用于存储列表中的元素的数组的大小。 它总是至少与列表大小一样大。 当元素添加到ArrayList时，其容量会自动增长。  没有规定增长政策的细节，除了添加元素具有不变的摊销时间成本。 

应用程序可以添加大量使用`ensureCapacity`操作元件的前增大`ArrayList`实例的容量。 这可能会减少增量重新分配的数量。

**此实现不同步。**  如果多个线程同时访问某个实例，并且至少有一个线程在结构上修改列表，则*必须*在外部进行同步。  （结构修改是添加或删除一个或多个元素的任何操作，或明确调整后台数组的大小;仅设置元素的值不是结构修改。）这通常是通过在一些自然地封装了列表。  如果没有这样的对象存在，列表应该使用`Collections.synchronizedList`方法“包装”。  这最好在创建时完成，以防止意外的不同步访问列表

 `iterator`和`listIterator`方法是*快速失败的*  ：如果列表在任何时间从结构上修改创建迭代器之后，以任何方式除非通过迭代器自身`remove`种或`add`方法，迭代器都将抛出一个`ConcurrentModificationException`。 因此，面对并发修改，迭代器将快速而干净地失败，而不是在未来未确定的时间冒着任意的非确定性行为。 

不过，迭代器的故障快速行为无法保证，因为一般来说，在不同步并发修改的情况下，无法做出任何硬性保证。  失败快速迭代器尽力投入`ConcurrentModificationException` 。  因此，编写依赖于此异常的程序的正确性将是错误的：迭代器*的故障快速行为应仅用于检测错误。*

##### ArrayList集合的创建

在源码中首先可以看见这四个参数

```java
	//默认初始容量    
	private static final int DEFAULT_CAPACITY = 10;

    //用于空实例的共享空数组
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //共享空数组用于默认大小的空实例.与EMPTY_ELEMENTDATA区分开来
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //数组缓冲区，数组列表的元素存储在其指向的空间中。数组列表的容量是这个数组缓冲区的长度
    //任何带有elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA的空数组列表
    //在添加第一个元素时将被扩展为DEFAULT_CAPACITY。
    transient Object[] elementData; // 非私有以简化内部类访问

    //数组列表的大小(它包含的元素的数量)
    private int size;
```

对于集合数据的存储底层使用的还是数组，集合（数组）中存储的是元素对象的地址。*`transient`关键字的主要作用就是让某些**被transient关键字修饰的成员属性变量不被序列化**。*

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-07-17_14-17-20.png)

数组需要被声明成Object类型，这样不仅可以容纳所有对象而且可以进行初始化，如果是泛型数组因为擦除是没有办法进行初始化的（运行时已经丢失了类型信息）

初始化集合最常用的就是这三种方式

```java
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>(3);
        ArrayList<Integer> list3 = new ArrayList<>(list2);
```

* `new ArrayList<>()`：构造一个空列表

  ```java
      public ArrayList() {
          this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
      }
  ```

* `new ArrayList<>(3)`：构造具有指定初始容量的空列表

  ```java
      public ArrayList(int initialCapacity) {
          if (initialCapacity > 0) {
              this.elementData = new Object[initialCapacity];
          } else if (initialCapacity == 0) {
              this.elementData = EMPTY_ELEMENTDATA;
          } else {
              throw new IllegalArgumentException("Illegal Capacity: "+
                                                 initialCapacity);
          }
      }
  ```

* `new ArrayList<>(list2)`：构造一个包含指定集合的元素的列表，按照它们由集合的迭代器返回的顺序

  ```java
      public ArrayList(Collection<? extends E> c) {
          elementData = c.toArray();
          if ((size = elementData.length) != 0) {
              // c.toArray 不一定会返回Object类型的数组
              if (elementData.getClass() != Object[].class)
                  elementData = Arrays.copyOf(elementData, size, Object[].class);
          } else {
              // 替换为空数组
              this.elementData = EMPTY_ELEMENTDATA;
          }
      }
  ```

  ![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-16_21-38-13.png)

  有些方法返回的数组类型可能会用泛型来保留实际类型



##### ArrayList集合扩容

数组的扩容参考前面的`StringBuilder`感觉都差不多，这里以`public void add(int index, E element)`为例，该方法用于在此列表中的指定位置插入指定的元素。 将当前位于该位置的元素（如果有）和任何后续元素（向其索引添加一个）移动

```java
    public void add(int index, E element) {
        rangeCheckForAdd(index);//判断索引是否合法

        ensureCapacityInternal(size + 1);  // 扩容
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);//数组拷贝
        elementData[index] = element;//插入数组（此时已经移位为插入的元素腾出位置了）
        size++;//元素个数加1
    }
```

`rangeCheckForAdd(index)`是用于判断索引是否合法的

```java
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)// 索引不合法
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
```

`ensureCapacityInternal(size + 1)`则是用于扩容

```java
	protected transient int modCount = 0	
	private static int calculateCapacity(Object[] elementData, int minCapacity) {//计算容量
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {//判断是否为空数组
            return Math.max(DEFAULT_CAPACITY, minCapacity);//初步确定扩容后的数组大小
        }
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;//modCount代表此列表被结构修改的次数（一定不能忘记加一）

        // 溢出判断
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);//真正的扩容
    }

    //要分配的最大数组大小。有些虚拟机在数组中保留一些头字。
    //尝试分配更大的数组可能会导致请求的数组大小超过虚拟机限制
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    //增加容量，以确保它可以容纳由最小容量参数指定的元素数量。
    private void grow(int minCapacity) {
        // 溢出判断
        int oldCapacity = elementData.length;//获取当前数组容量
        int newCapacity = oldCapacity + (oldCapacity >> 1);//尝试将容量扩大一半
        if (newCapacity - minCapacity < 0)//溢出了
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)//超出了预想的最大容量
            newCapacity = hugeCapacity(minCapacity);//尝试申请更大容量
        //minCapacity通常接近size，不易造成严重的空间浪费
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // 溢出了
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;//最大也只能有Integer.MAX_VALUE
    }
```

总结一下扩容机制：

* 通过`calculateCapacity`方法初步确定容量
* 在`ensureExplicitCapacity`方法中需要将modCount加一（此列表被修改次数），做一下简单的溢出判断后进行扩容
* 在`grow`方法中进行数组扩容，尝试将容量扩大一半（溢出了就放弃），如果超出了预想的最大容量，调用`hugeCapacity`尝试申请更大容量（最大也只能有Integer.MAX_VALUE），最后通过`Arrays.copyOf`执行数组拷贝

*注：size是成员变量，有默认值0，初始化时它的值就为0*

##### ArrayList集合其它常用操作

* `public E get(int index)`：返回此列表中指定位置的元素

  ```java
  	public E get(int index) {
          rangeCheck(index);//检查给定的索引是否在范围内
  
          return elementData(index);
      }    
  	@SuppressWarnings("unchecked")
      E elementData(int index) {
          return (E) elementData[index];//根据索引读取数组元素，会执行类型转换
      }
  
      private void rangeCheck(int index) {
          if (index >= size)//没有检测负数，索引为负数会在数组访问时抛出异常
              throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
      }
  ```

  如果索引为负数会在访问数组时抛出异常

  ![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_14-10-45.png)

* `public boolean remove(Object o)`：从列表中删除指定元素的第一个出现（如果存在）

  参数是Object类型，需要注意`remove(1)`会调用`public E remove(int index)`删除索引为1的元素，`remove(new Integer(1))`才会删除值为1的那个元素

  ```java
      public E remove(int index) {
          rangeCheck(index);//检测索引范围，与上面相同
  
          modCount++;//modCount代表此列表被结构修改的次数，需要加1
          E oldValue = elementData(index);
  
          int numMoved = size - index - 1;//需要移动的元素数量
          if (numMoved > 0)
              System.arraycopy(elementData, index+1, elementData, index, numMoved);//拷贝数组
          elementData[--size] = null; // 让GC完成它的工作
  
          return oldValue;
      }
  ```

  ```java
      public boolean remove(Object o) {//先找到元素索引，然后根据索引去删除
          if (o == null) {//为空
              for (int index = 0; index < size; index++)
                  if (elementData[index] == null) {//使用==比较
                      fastRemove(index);
                      return true;
                  }
          } else {//不为空
              for (int index = 0; index < size; index++)
                  if (o.equals(elementData[index])) {//比较值（在AbstractList中重写了此方法）
                      fastRemove(index);
                      return true;
                  }
          }
          return false;
      }
  
      //跳过边界检查的私有删除方法，返回删除的值。
      private void fastRemove(int index) {
          modCount++;//结构修改的次数同样需要加一
          int numMoved = size - index - 1;//需要移动元素的数量
          if (numMoved > 0)
              System.arraycopy(elementData, index+1, elementData, index, numMoved);//拷贝数组
          elementData[--size] = null; // 交给垃圾回收了
      }
  ```

  ```java
  public class ArrayListTest1 {
  
      public static void main(String[] args) {
          ArrayList<Integer> list = new ArrayList<>();
          for (int i = 10; i > 0; i--) {
              list.add(i);
          }
          list.remove(new Integer(1));
          System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2]
          list.remove(1);
          System.out.println(list);//[10, 8, 7, 6, 5, 4, 3, 2]
      }
  }
  ```

* `public void sort(Comparator<? super E> c)`：使用提供的 `Comparator`对此列表进行排序

  ```java
      @Override
      @SuppressWarnings("unchecked")
      public void sort(Comparator<? super E> c) {
          final int expectedModCount = modCount;//防止并发操作的影响
          Arrays.sort((E[]) elementData, 0, size, c);//排序
          if (modCount != expectedModCount) {
              throw new ConcurrentModificationException();
          }
          modCount++;
      }
  ```

  `Arrays.sort((E[]) elementData, 0, size, c)`先将集合中的部分元素排列好顺序。 然后再将剩余的元素用二分法插入到已排好序（二分法的使用是建立在已排好序的前提下）的元素中去。然后得到排好序的集合

  ```java
          list.sort(new Comparator<Integer>() {
              @Override
              public int compare(Integer o1, Integer o2) {//升序，降序换一下位置
                  return o1-o2;
              }
          });
  ```

* `public int indexOf(Object o)`：返回此列表中指定元素的第一次出现的索引，如果此列表不包含元素，则返回-1，寻找方式与`remove`相似

* `public boolean contains(Object o)`：如果此列表包含指定的元素，则返回 `true` ，通过`indexOf(o) >= 0`来实现的

* `public Object clone()`：返回此`ArrayList`实例的浅拷贝。 （元素本身不被复制）

  ![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_15-05-36.png)

  集合的底层使用的是Object类型进行存储的，对数组的拷贝不过是把数组中存储的引用拷贝了一份，其具体的元素内容并没有赋值

  如果数组中存储的是基本类型的数据，这种拷贝会达到深拷贝的作用（运气好）

  ```java
          ArrayList<Integer> list = new ArrayList<>();
          for (int i = 10; i > 0; i--) {
              list.add(i);
          }
          List<Integer> clone =(List<Integer>) list.clone();
          clone.add(5);
          System.out.println(clone);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 5]
          System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
          clone.set(1,100);
          System.out.println(clone);//[10, 100, 8, 7, 6, 5, 4, 3, 2, 1, 5]
          System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
  ```

  ```java
  class Target{
      String name;
  
      public Target(String name) {
          this.name = name;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      @Override
      public String toString() {
          return "Target{" +
                  "name='" + name + '\'' +
                  '}';
      }
  }
  ```

  ```java
          ArrayList<Target> list1 = new ArrayList<>();
          list1.add(new Target("123"));
          list1.add(new Target("456"));
          list1.add(new Target("789"));
          ArrayList<Target> clone1 = (ArrayList<Target>)list1.clone();
          clone1.get(0).setName("000");
          System.out.println(clone1);//[Target{name='000'}, Target{name='456'}, Target{name='789'}]
          System.out.println(list1);//[Target{name='000'}, Target{name='456'}, Target{name='789'}]
  ```

  通过上面两个例子也可以说明`public Object clone()`：返回此`ArrayList`实例的浅拷贝。`ArrayList<Target> list1 = new ArrayList<>();`中`list1`的类型不能是`List<Integer>`，在`List`中没有这个方法

#### LinkedList

```java
public class LinkedList<E> extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

`LinkedList`与`ArrayList`一样实现了基本List接口但是它执行某些操作（在List中间插入和移除）时比`ArrayList`更高效，但在随机访问操作要逊色一些。`LinkedList`还添加了使其可以作为栈，队列，双端队列的方法

* `LinkedList` 继承了 `AbstractSequentialList `类。

  `AbstractSequentialList`继承于` AbstractList` ，提供了对数据元素的链式访问而不是随机访问

* `LinkedList` 实现了 `List` 接口，可进行列表的相关操作。

* `LinkedList` 实现了 `Deque` 接口，可作为队列使用。

  `public interface Deque<E> extends Queue<E> `，支持两端元素插入和移除的线性集合

* `LinkedList` 实现了 `Cloneable` 接口，可实现克隆。

* `LinkedList` 实现了 `java.io.Serializable` 接口，即可支持序列化，能通过序列化去传输。

**以下情况使用 ArrayList :**

- 频繁访问列表中的某一个元素。
- 只需要在列表末尾进行添加和删除元素操作。

**以下情况使用 LinkedList :**

- 你需要通过循环迭代来访问列表中的某些元素。
- 需要频繁的在列表开头、中间、末尾等位置进行添加和删除元素操作。

*我们在项目中一般是不会使用到 `LinkedList` 的，需要用到 `LinkedList` 的场景几乎都可以使用 `ArrayList` 来代替，并且，性能通常会更好，就连 `LinkedList` 的作者约书亚 · 布洛克（Josh Bloch）自己都说从来不会使用 `LinkedList`*

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_15-40-29.png)

##### LinkedList集合的创建

在源码中首先可以看到这三个参数，都用`transient`修饰不允许被序列化

```java
   	//节点个数
	transient int size = 0;
    //指向第一个节点的指针
    transient Node<E> first;
    //指向最后一个节点的指针
    transient Node<E> last;
```

构造方法有两个

```java
    //无参构造
	public LinkedList() {
    }

	//按照集合的迭代器返回元素的顺序构造一个包含指定集合元素的列表
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
```

`public boolean addAll(Collection<? extends E> c)`：按照指定集合的迭代器返回的顺序将指定集合中的所有元素追加到此列表的末尾

```java
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);//将size代表插入的第一个位置
    }
```

```java
    //将指定集合中的所有元素从指定位置开始插入到此列表中。
    //将当前位于该位置的元素(如果有的话)和所有后续元素向右移动(增加它们的索引)。
    //新元素将按照指定集合的迭代器返回它们的顺序出现在列表中。
	public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);//检测索引是否合法

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)//添加的数组为空
            return false;

        Node<E> pred, succ;
        if (index == size) {//追加到链表尾部，不用移动元素
            succ = null;
            pred = last;
        } else {
            succ = node(index);//返回指定元素索引处的(非空)Node
            pred = succ.prev;//索引的前驱
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);//创建节点，这时候已经确定了前驱节点
            if (pred == null)//为空说明在头部插入
                first = newNode;//重新确定头节点
            else
                pred.next = newNode;//连接节点
            pred = newNode;//指针后移
        }

        if (succ == null) {//为空说明在尾部插入
            last = pred;//重新确定尾指针位置
        } else {//在中间插入
            pred.next = succ;// 连接索引后面的链表
            succ.prev = pred;// 连接索引前面的链表
        }

        size += numNew;//增加节点数量
        modCount++;//增加结构修改次数
        return true;
    }

```

检测索引是否合法

```java
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;//索引位于index与size之间
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
```

返回指定元素索引处的(非空)Node

```java
    Node<E> node(int index) {
        if (index < (size >> 1)) {//判断从前往后还是从后往前
            Node<E> x = first;
            for (int i = 0; i < index; i++)//从前往后（i < index所以返回结果不会为空）
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)//从后往前（i > index所以返回结构不会为空）
                x = x.prev;
            return x;
        }
    }
```

**综上：**

`LinkedList` 底层使用的是 **双向链表** 数据结构。

**存储空间**

`ArrayList` 的空间浪费主要体现在在列表的结尾会预留一定的容量空间，而 LinkedList 的空间花费则体现在它的每一个元素都需要消耗比 ArrayList 更多的空间（因为要存放直接后继和直接前驱以及数据）。 `LinkedList` 不支持高效的随机元素访问，因为它无法通过下标直接找到元素。

**插入和删除的效率**

`LinkedList` 采用链表存储，所以，如果是在头尾插入或者删除元素不受元素位置的影响（`add(E e)`、`addFirst(E e)`、`addLast(E e)`、`removeFirst()` 、 `removeLast()`），时间复杂度为 O(1)，如果是要在指定位置 `i` 插入和删除元素的话（`add(int index, E element)`，`remove(Object o)`）， 因为需要先移动到指定位置再进行插入，时间复杂度为 O(n/2)（可以从前往后也可以从后往前）。

`ArrayList` 采用数组存储，所以插入和删除元素的时间复杂度受元素位置的影响。 比如：执行`add(E e)`方法的时候， `ArrayList` 会默认在将指定的元素追加到此列表的末尾，这种情况时间复杂度就是 O(1)。但是如果要在指定位置 i 插入和删除元素的话（`add(int index, E element)`）时间复杂度就为 O(n-i)，因为在进行上述操作的时候集合中第 i 和第 i 个元素之后的(n-i)个元素都要执行向后位/向前移一位的操作。但实际使用`System.arraycopy`进行数组拷贝的效率还真不好说。

##### LinkedList其它常用操作

* `public boolean add(E e)`：将指定的元素追加到此列表的末尾

  ```java
      public boolean add(E e) {
          linkLast(e);//在尾部添加元素
          return true;
      }
  ```

  ```java
      void linkLast(E e) {
          final Node<E> l = last;
          final Node<E> newNode = new Node<>(l, e, null);//将尾指针作为新节点的前驱节点
          last = newNode;//重新确定尾指针
          if (l == null)//尾指针为空，添加的可能是第一个元素
              first = newNode;//确定头指针的位置
          else
              l.next = newNode;//从前往后连接节点
          size++;//节点个数加一
          modCount++;//结构修改次数加一
      }
  ```

  因为是双向链表，所以要对于每一个节点都要维护两个指针，直接前驱和直接后继，对于整个链表需要维护头指针和尾指针

* `public boolean removeLastOccurrence(Object o)`：删除此列表中指定元素的最后一次出现（从头到尾遍历列表时）。 如果列表不包含该元素，则它不会更改

  ```java
      public boolean removeLastOccurrence(Object o) {//肯定会使用尾指针从后往前遍历
          if (o == null) {//为空
              for (Node<E> x = last; x != null; x = x.prev) {
                  if (x.item == null) {
                      unlink(x);//删除节点
                      return true;
                  }
              }
          } else {//不为空
              for (Node<E> x = last; x != null; x = x.prev) {
                  if (o.equals(x.item)) {
                      unlink(x);//删除节点
                      return true;
                  }
              }
          }
          return false;
      }
  ```

  ```java
      //断开非空节点x的链接
      E unlink(Node<E> x) {
          final E element = x.item;//获取节点值
          final Node<E> next = x.next;//节点的后继
          final Node<E> prev = x.prev;//节点的前驱
  
          if (prev == null) {//前驱为空，删除头节点
              first = next;//重新确定头指针
          } else {
              prev.next = next;//连接节点（从前往后）
              x.prev = null;//断开节点与前驱的连接
          }
  
          if (next == null) {//后继为空，删除尾节点
              last = prev;//重新确定尾指针
          } else {
              next.prev = prev;//连接节点（从后往前）
              x.next = null;//断开节点与后继的连接
          }
  
          x.item = null;//赋空值，方便回收
          size--;//节点数减一
          modCount++;//增加结构修改次数
          return element;
      }
  ```

* `public Iterator<E> descendingIterator()`：以相反的顺序返回此链表中的元素的迭代器。 元素将从最后（尾）到第一（头）的顺序返回

  ```java
      public Iterator<E> descendingIterator() {
          return new DescendingIterator();
      }
  
      //适配器通过ListItr.previous提供迭代器
      private class DescendingIterator implements Iterator<E> {
          private final ListItr itr = new ListItr(size());
          public boolean hasNext() {
              return itr.hasPrevious();
          }
          public E next() {
              return itr.previous();
          }
          public void remove() {
              itr.remove();
          }
      }
  ```

  `ListItr`是`LinkedList`内部实现的迭代器，`private class ListItr implements ListIterator<E>`，因为`LinkedList`底层使用的是双向链表，所以存在两种遍历方式的需求，从后往前与从前往后，具体实现与`AbstractList`类似



### Set系列集合

Set 完全就是一个Collection，只是具有不同的行为（这是实例和多形性最理想的应用：用于表达不同的行为）。在这里，一个Set 只允许每个对象存在一个实例。

Set（接口） 添加到 Set 的每个元素都必须是独一无二的；Set 不会添加重复的元素。添加到 Set 里 的对象必须定义equals()，从而建立对象的唯一性。Set 拥有与 Collection 完全相同的接口。一个 Set不能保证自己可按任何特定的顺序维持自己的元素

**Set系列集合特点**

* 无序：存取顺序不一致
* 不重复：可以去除重复
* 无索引：没有带索引的方法，所以不能使用普通for循环遍历，也不能通过索引来获取元素。

#### AbstractSet的存在

```java
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E>
```

`AbstractSet`继承于`AbstractCollection` 并且实现了大部分`Set`接口。

通过扩展此类实现集合的过程与通过扩展`AbstractCollection`实现集合的过程相同，除了此类的子类中的所有方法和构造函数必须遵守`Set`接口施加的`附加`约束（例如，添加方法不能允许将一个对象的多个实例添加到集合中）。 

`AbstractSet`仅覆盖`AbstractCollection`类中的`removeAll`方法，添加了`equals`和`hashCode`的实现

* `public boolean equals(Object o)`：将指定的对象与此集合进行比较（集合间的比较）

  ```java
      public boolean equals(Object o) {
          if (o == this)//同一个引用
              return true;
  
          if (!(o instanceof Set))
              return false;
          Collection<?> c = (Collection<?>) o;
          if (c.size() != size())//集合大小不同
              return false;
          try {
              return containsAll(c);//调用AbstractCollection中的方法进行比较，这里比较的是数组元素（引用）
          } catch (ClassCastException unused)   {
              return false;
          } catch (NullPointerException unused) {
              return false;
          }
      }
  ```

* `public int hashCode()`：返回此集合的哈希码值。  集合的哈希码被定义为集合中的元素的哈希码的总和，其中`null`元素的哈希码被定义为零

  ```java
      public int hashCode() {
          int h = 0;
          Iterator<E> i = iterator();
          while (i.hasNext()) {//遍历元素
              E obj = i.next();
              if (obj != null)
                  h += obj.hashCode();
          }
          return h;
      }
  ```

* `public boolean removeAll(Collection<?> c)`：从此集合中删除指定集合中包含的所有元素（可选操作）

  ```java
      public boolean removeAll(Collection<?> c) {
          Objects.requireNonNull(c);
          boolean modified = false;
  
          if (size() > c.size()) {//指定集合的元素少
              for (Iterator<?> i = c.iterator(); i.hasNext(); )
                  modified |= remove(i.next());//调用AbstractCollection中的remove方法
          } else {//指定集合的元素多
              for (Iterator<?> i = iterator(); i.hasNext(); ) {
                  if (c.contains(i.next())) {
                      i.remove();//调用迭代器中的删除方法
                      modified = true;
                  }
              }
          }
          return modified;
      }
  ```

#### HashSet

```java
public class HashSet<E> extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
```

* `HashSet` 基于 `HashMap `来实现的，是一个不允许有重复元素的集合。
* `HashSet` 允许有 null 值。
* `HashSet` 是无序的，即不会记录插入的顺序。
* `HashSet` 不是线程安全的， 如果多个线程尝试同时修改 `HashSet`，则最终结果是不确定的。 您必须在多线程访问时显式同步对 `HashSet` 的并发访问。
* `HashSet` 实现了 `Set` 接口

##### HashSet集合的创建

```java
	//其实HashSet底层采用的是HashMap<E,Object>来实现的，value是一个固定值
    private transient HashMap<E,Object> map;

    // 与支持映射中的对象相关联的虚拟值，HashMap是<key,value>映射，既然HashSet底层用的是HashMap，
    // 那么value在哪里呢？ value是一个Object，所有的value都是它
    private static final Object PRESENT = new Object();
```

所以创建一个`HashSet`，实际上是创建了一个`HashMap`，因为`HashMap`的key值完全符合`HashSet`的要求，只要把`HashMap`中有关value的一些操作给屏蔽掉就行。所以`HashSet`的大部分操作都是基于`HashMap`来实现的。至于原因放在后面的`Map`中再说。

**构造方法**

```java
    //构造一个新的空集合; 背景HashMap实例具有默认初始容量（16）和负载因子（0.75）
    public HashSet() {
        map = new HashMap<>();
    }

    public HashSet(Collection<? extends E> c) {//构造一个包含指定集合中的元素的新集合
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }
    ////构造一个新的空集合; 背景HashMap实例具有指定的初始容量和指定的负载因子
    public HashSet(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }
    //构造一个新的空集合; 背景HashMap实例具有指定的初始容量和默认负载因子（0.75）
    public HashSet(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    //创建一个LinkedHashMap
    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }
```

##### HashSet其它常用操作

* `public boolean add(E e)`：将指定的元素添加到此集合（如果尚未存在）

  ```java
      public boolean add(E e) {
          return map.put(e, PRESENT)==null;
      }
  ```

* `public Object clone()`：返回此 `HashSet`实例的浅层副本：元素本身不被克隆

  ```java
  @SuppressWarnings("unchecked")
  public Object clone() {
      try {
          HashSet<E> newSet = (HashSet<E>) super.clone();//调用父类的Object的clone()
          newSet.map = (HashMap<E, Object>) map.clone();//调用HashMap中的clone()
          return newSet;
      } catch (CloneNotSupportedException e) {
          throw new InternalError(e);
      }
  }
  ```

* `public Iterator<E> iterator()`：返回此集合中元素的迭代器。 元素没有特定的顺序返回

  ```java
  public Iterator<E> iterator() {
       return map.keySet().iterator();
  }
  ```



#### LinkedHashSet

```java
public class LinkedHashSet<E> extends HashSet<E>
    implements Set<E>, Cloneable, java.io.Serializable 
```

**特点**

* 有序、不重复、无索引。
* 这里的有序指的是保证存储和取出的元素顺序一致
* 原理：底层数据结构是依然哈希表，只是每个元素又额外的多了一个双链表的机制记录存储的顺序

**构造函数**

```java
public LinkedHashSet(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor, true);
}

public LinkedHashSet(int initialCapacity) {
    super(initialCapacity, .75f, true);
}

public LinkedHashSet() {
    super(16, .75f, true);
}

public LinkedHashSet(Collection<? extends E> c) {
    super(Math.max(2*c.size(), 11), .75f, true);
    addAll(c);
}
```

构造函数与`HashSet`类似，不过底层通过`HashSet`中的`HashSet(int initialCapacity, float loadFactor, boolean dummy)`来创建`LinkedHashMap`

具体操作与`HashSet`类似（相同）

#### TreeSet

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_21-53-02.png)

```java
public class TreeSet<E> extends AbstractSet<E>
    implements NavigableSet<E>, Cloneable, java.io.Serializable
```

* `SortedSet`：进一步提供使其元素的排序的集合。元素使用它们的自然顺序进行排序，或者由通常在排序集创建时提供的`Comparator`进行排序。集合的迭代器将按元素升序遍历集合。还提供了几个额外的操作来利用排序。

* `NavigableSet`：`NavigableSet`表示Java集合框架中的一个可导航集。`NavigableSet`接口继承自`SortedSet`接口。它的行为类似于`SortedSet`，除了`SortedSet`的排序机制之外，我们还有其它可用的导航方法。

  例如，与`SortedSet`中定义的顺序相比，可以按升序或降序访问和遍历`NavigableSet`
  

**TreeSet集合概述和特点**

* 不重复、无索引、可排序
* 可排序：按照元素的大小默认升序（有小到大）排序。
* TreeSet集合底层是基于红黑树的数据结构实现排序的，增删改查性能都较好。
* 注意：TreeSet集合是一定要排序的，可以将元素按照指定的规则进行排序。

**TreeSet集合默认的规则**

* 对于数值类型：Integer , Double，官方默认按照大小进行升序排序。
* 对于字符串类型：默认按照首字符的编号升序排序。
* 对于自定义类型如Student对象，TreeSet无法直接排序。

**注：想要使用TreeSet存储自定义类型，需要制定排序规则**

* 让自定义的类（如学生类）实现Comparable接口重写里面的compareTo方法来定制比较规则。

* TreeSet集合有参数构造器，可以设置Comparator接口对应的比较器对象，来定制比较规则。

**注：如果TreeSet集合存储的对象有实现比较规则，集合也自带比较器，默认使用集合自带的比较器排序**

```java
   public class Book implements Comparable{ // 第一步：实现Comparable接口
   
    	private Integer bookId;
		private String bookName;
		private String bookAuthor;
		private Double price;
        
         public int compareTo(Object o) {    // 第二步：重写Comparable接口中的compareTo方法。
			Book book = (Book)o;
	         int flag = (int)(this.getPrice() - book.getPrice()); //升序
			return flag;
 		}
  }
```

```java
        TreeSet treeSet = new TreeSet(new Comparator() {   // 匿名内部类操作
            public int compare(Object o1, Object o2) {
                Book book1 = (Book)o1;
                Book book2 = (Book)o2;
                return (int)( book1.getPrice() - book2.getPrice());//升序：第一个减第二个，降序：第二个减第一个
            }

        });
```

##### TreeSet集合常用操作

```java
    // 底层使用NavigableMap
	private transient NavigableMap<E,Object> m;

    //用于填充的value值
    private static final Object PRESENT = new Object();
```

`NavigableMap `继承自 `SortedMap`，所以它的元素是有序的。在 SortedMap 基础上，支持快速搜索符合条件的最近的元素。这里条件主要是指 lower(>), floor(>=)， ceiling(<)，higher(>)。支持逆序访问。支持获取子集合，集合边界元素是否包含是可配的。

**构造方法**

```java
   TreeSet(NavigableMap<E,Object> m) {
        this.m = m;
    }

    public TreeSet() {
        this(new TreeMap<E,Object>());
    }

    public TreeSet(Comparator<? super E> comparator) {
        this(new TreeMap<>(comparator));
    }

    public TreeSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    public TreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }
```

创建一个`TreeSet`相当于创建了一个`TreeMap`，`TreeSet`的大部分基本操作都是基于`TreeMap`实现的

**`public NavigableSet<E> tailSet(E fromElement, boolean inclusive)`**

返回此集合的部分的视图，其元素小于（或等于，如果`inclusive`为真） `toElement`  。返回的集合由此集合支持，因此返回集合中的更改将反映在此集合中，反之亦然。返回的集合支持该集支持的所有可选集合操作

```java
  public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new TreeSet<>(m.tailMap(fromElement, inclusive));
    }
```

**`public E last()`**

返回此集合中当前的最后（最大）元素。

```java
    public E last() {
        return m.lastKey();
    }
```

**`public E lower(E e)`**

返回这个集合中严格小于给定元素的最大的元素，如果没有这样的元素，则返回 `null` 

```java
 public E lower(E e) {
        return m.lowerKey(e);
  }
```

**以下列举了基本所有出现的方法**

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_22-18-36.png)

![](https://dar-1305869431.cos.ap-shanghai.myqcloud.com/algorithm/Snipaste_2022-11-17_22-18-56.png)