package ldt.springframework.tigibusiness.services.machineLearning.sample;



/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */

public class JavaMLExample {
    public static void main(String[] args) {
//        System.out.println("JavaML Example");
//        JavaMLTest.doJavaMLTest();

        System.out.println("");
        System.out.println("");
        System.out.println("WeatherTest Example");
        try {
            WeatherTest.doWeatherTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
