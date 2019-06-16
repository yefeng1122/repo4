package Test2;
/*1. 定义"学生"类,Student,包含以下成员:
        成员属性: name (姓名):String类型，chinese(语文):int类型，math(数学):int类型，属性使用private修饰。
        （1）生成所有属性的get/set方法，生成构造方法
        （2）成员方法：void show()方法,打印对象所有属性的值以及对象的语文和数学成绩的总和到控制台;*/
public class Student {
    private String name;
    private int chinese;
    private int math;

    public Student() {    //空参
    }
    public Student(String name,int chinese,int math) {  //有参
        this.name = name;
        this.chinese = chinese;
        this.math = math;
    }

    public String getName() {                //获取姓名
        return name;
    }

    public void setName(String name) {       // 设置姓名
        this.name = name;
    }

    public int getChinese() {                //获取语文
        return chinese;
    }

    public void setChinese(int chinese) {     //设置语文
        this.chinese = chinese;
    }

    public int getMath() {                   // 获取数学
        return math;
    }

    public void setMath(int math) {          //设置数学
        this.math = math;
    }
    //成员方法 成员方法：void show()方法,打印对象所有属性的值以及对象的语文和数学成绩的总和到控制台
    public void show() {
        System.out.println(name+"的语文成绩："+chinese+",数学成绩："+math+",总成绩："+(chinese+math));
    }
}
