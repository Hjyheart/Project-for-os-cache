# 操作系统课程设计二说明文档
## 环境说明
- 开发语言：java 
- 产品格式：jar

## 动态分区分配模拟
### 项目需求
    假设初始态下，可用内存空间为640K，并有下列请求序列，
    请分别用首次适应算法和最佳适应算法进行内存块的分配
    和回收，并显示出每次分配和回收后的空闲分区链的情况来。
### 基本算法
- 首次适应算法
- 最佳适应算法

###### 首次适应算法

    按分区先后次序，从头查找，找到符合要求的第一个分区。
    尽可能利用存储区低地址空闲区，尽量在高地址部分保存
    较大空闲区，以便一旦有分配大空闲区要求时，容易得到满足。
    分配简单，合并相邻空闲区也比较容易。
    查找总是从表首开始，前面空闲区往往被分割的很小时，满足分
    配要求的可能性较小，查找次数较多。

###### 最佳适应算法

    在所有大于或者等于要求分配长度的空闲区中挑选一个最小的分区，
    即对该分区所要求分配的大小来说，是最合适的。分配后，所剩余
    的块会最小。
    较大的空闲分区可以被保留。
    空闲区是按大小而不是按地址顺序排列的，因此释放时，要在整个链
    表上搜索地址相邻的空闲区，合并后，又要插入到合适的位置。

### 数据结构说明
#### 内存块
```java
public class memryBlock {
    // 是否在cache中的标记
    private boolean memState;
    // 起始位置
    private int start;
    // 结束位置
    private int end;
    // 需要内存的大小
    private int lenth;
    // 内存识别标签
    private int tag;

    public memryBlock(){
        memState = false;
        start = -1;
        end = -1;
        lenth = 0;
        tag = 0;
    }

    public memryBlock(int start, int end, int lenth, int tag){
        memState = true;
        this.start = start;
        this.end = end;
        this.lenth = lenth;
        this.tag = tag;
    }

    public void setMemState(boolean state){memState = state;}

    public boolean getMemState(){return memState;}

    public void setStart(int start){this.start = start;}

    public int getStart() {return start;}

    public void setEnd(int end) {this.end = end;}

    public int getEnd() {return end;}

    public void setLenth(int lenth) {this.lenth = lenth;}

    public int getLenth() {return lenth;}

    public void setTag(int tag) {this.tag = tag;}

    public int getTag() {return tag;}
}

```
#### 首次适应
``` java
public class firstAdapter {
    // 内存区序列
    public ArrayList<memryBlock> table;
    // 内存标签
    public ArrayList<Integer> tags;

    public firstAdapter(){
        table = new ArrayList<memryBlock>();
        tags = new ArrayList<Integer>();
        memryBlock blank = new memryBlock(0, 640, 640, 0);
        blank.setMemState(false);
        tags.add(0);
        table.add(blank);
    }

    // 移入内存
    public void putIn(int lenth, int tag){
        /*...*/
    }

    // 释放某块内存
    public void releaseMem(int tag){
        /*...*/
    }
}

```
#### 最佳适应
``` java
public class bestAdapter {
    // 内存区序列
    public ArrayList<memryBlock> table;
    // 内存标签
    public ArrayList<Integer> tags;

    public bestAdapter(){
        table = new ArrayList<memryBlock>();
        tags = new ArrayList<Integer>();
        memryBlock blank = new memryBlock(0, 640, 640, 0);
        blank.setMemState(false);
        tags.add(0);
        table.add(blank);
    }

    // 移入内存
    public void putIn(int lenth, int tag){
        
    }
    // 释放内存
    public void releaseMem(int tag){
        
    }
}

```
### 操作说明
- 顶端多选按钮可以选择模式
- 中心文本框用于显示操作和操作结果
- 复位按钮可将操作初始化
- 左侧自动演示按钮可以自动演示所有操作
- 右侧单步操作按钮可以进行单步演示
- 最下方空白出将显示内存块情况

### UI展示
![demo](https://github.com/Hjyheart/os-project-cache/blob/master/Os2/demo.png?raw=true)

## 请求调页存储管理方式模拟
### 项目需求
    假设每个页面可存放10条指令，分配给一个作业的内存块为4。模拟一个作业的执行过程，
    该作业有320条指令，即它的地址空间为32页，目前所有页还没有调入内存。
### 基本算法
- FIFO
- LRU
###### FIFO
    选择建立最早的页面被置换。
    可通过链表来表示各页的建立时间先后。
    性能较差。
    较早调入的页往往是经常被访问的页，这些页在FIFO算法下被反复
    调入和调出。

###### LRU
    是局部性原理的合理近似，性能接近最佳算法。但由于需要记录页面
    使用时间的先后关系，硬件开销太大。

    硬件支持：
    一个特殊的栈：
        把被访问的页面移到栈顶，于是栈底的是最久未使用页面。
    每个页面设立移位寄存器：
        被访问时左边最高位置1，定期右移并且最高位补0，于是寄存器
        数值最小的是最久未使用页面。

### 数据结构
#### 内存块
``` java
public class bloks {
    // 内存块名称
    private int name;
    // 进入cache的时间
    private int inTime;
    // 距离上次被使用的时间
    private int useTime;

    public bloks(int name, int inTime){
        this.name = name;
        this.inTime = inTime;
        useTime = 0;
    }

    public int getInTime() {
        return inTime;
    }

    public void setInTime(int inTime) {
        this.inTime = inTime;
    }

    public int getUseTime() {
        return useTime;
    }

    public int getName() {
        return name;
    }

    public void setUseTime() {
        useTime = 0;
    }

    public void useTimeAdd(){
        useTime++;
    }

}

```
### 操作说明
- 顶部多选按钮可以选择算法
- 下方start按钮可以使程序开始运作
- 下方文本框用于显示操作结果

### UI展示
![demo2](https://github.com/Hjyheart/os-project-cache/blob/master/Os2_2/demo.png?raw=true)
