import java.util.concurrent.SynchronousQueue;

public class test {
    public static void main(String[] args) {

//        StringBuffer stringBuffer = new StringBuffer();
//        StringBuilder stringBuilder = new StringBuilder();
//
//        String nullStr = null;
//
//        stringBuffer.append(nullStr);
//        stringBuilder.append(nullStr);
//
//        System.out.println("stringBuffer=="+stringBuffer.length());
//        System.out.println("stringBuffer=="+stringBuffer.toString());
//        System.out.println("stringBuilder=="+stringBuilder.length());
//        System.out.println("stringBuilder=="+stringBuilder.toString());

        boolean testboolean = true;
        String testStr = "test";

        if(testboolean==true){
            System.out.println("testboolean==true");
        }else if(testboolean==true && testStr!=null){
            System.out.println("testboolean==true && testStr!=null");
        }

    }
}
