package Test2;

import java.util.ArrayList;

/*（1）定义如下方法：
        ①定义public static ArrayList<Student>  getSum(ArrayList<Student> list){...}方法:
        ②要求：遍历list集合，将list中语文成绩和数学成绩的总和大于160分的元素存入到另一个ArrayList<Student> 中并返回。
        （2）分别实例化三个Student对象，三个对象分别为："邓超" ,88,99、"baby" ,85,78、"郑凯" ,86,50;
        （3）创建一个ArrayList集合，这个集合里面存储的是Student类型，分别将上面的三个Student对象添加到集合中，
        调用方法getSum，根据返回的list集合遍历对象并调用show（）方法输出所有元素信息。

        示例如下：*/
public class Test {
    public static void main(String[] args) {
        ArrayList<Student> array = new ArrayList<Student>();
        //实例化对象
        Student s1 = new Student("邓超",88,99);
        Student s2 = new Student("baby",85,78);
        Student s3 = new Student("郑凯",86,50);
        array.add(s1);
        array.add(s2);
        array.add(s3);
        //调用方法
        ArrayList<Student> list = getSum(array);
        //遍历新集合
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            s.show();
        }
    }
    public static ArrayList<Student> getSum(ArrayList<Student> list) {
        //新建集合
        ArrayList<Student> array = new ArrayList<Student>();
        //遍历旧集合
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            if ((s.getChinese()+s.getMath()) > 160) {
                // 满足条件就添加到新集合中
                array.add(s);
            }
        }
        //返回新集合
        return array;
    }
}
