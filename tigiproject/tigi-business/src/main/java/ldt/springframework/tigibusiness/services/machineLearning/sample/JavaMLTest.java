package ldt.springframework.tigibusiness.services.machineLearning.sample;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.tools.data.ARFFHandler;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */
public class JavaMLTest {
    public static void doJavaMLTest(){
        int rangeMin = 1;
        int rangeMax = 5;
        Dataset data = new DefaultDataset();
        for (int i = 0; i < 100; i++){

            /* Create instance with 10 attributes */
            Instance instance = new SparseInstance(10);
            for (int j = 1; j <= 10; j++) {
                Random r = new Random();
                double randomValue = r.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
                /* Set the values for particular attributes */
                instance.put(j, randomValue);
            }

            data.add(instance);
        }


        try {
            /* Store the data back to another file */
            FileHandler.exportDataset(data, new File("./tigi-business/data/iris.txt"));

            /* Load the iris data set from file */
            data = ARFFHandler.loadARFF(new File("./tigi-business/data/weather.txt"));
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
