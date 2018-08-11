package ldt.springframework.tigibusiness.services.machineLearning.sample;


import ldt.springframework.tigibusiness.services.machineLearning.NlpManchineLearningService;
import ldt.springframework.tigibusiness.services.machineLearning.NplManchineLearningServiceImpl;
import opennlp.tools.util.Span;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */


public class JavaMLExample {
    
    // =======================================
    // =           Injection Point           =
    // =======================================
    
    public static void main(String[] args) {

        // =======================================
        // =            NLP Example              =
        // =======================================

//        NlpManchineLearningService nlpManchineLearningService = new NplManchineLearningServiceImpl();
//
//        System.out.print("Input Search: ");
//        Scanner scanner = new Scanner(System.in);
//        String sentence = scanner.nextLine();
//        System.out.println("--------------------------------------------");
//        System.out.println("");
//
//        try {
//            System.out.println("Organization Detect");
//            List<String> tokens = nlpManchineLearningService.detectOrganization(sentence);
//            for (String token : tokens){
//                System.out.println(token);
//            }
//
//
//            System.out.println("");
//            System.out.println("Person Detect");
//            tokens = nlpManchineLearningService.detectPerson(sentence);
//            for (String token : tokens){
//                System.out.println(token);
//            }
//
//
//            System.out.println("");
//            System.out.println("Verb Detect");
//            tokens = nlpManchineLearningService.detectPartOfSpeechByTag(sentence, "VB");
//            for (String token : tokens){
//                System.out.println(token);
//            }
//
//            System.out.println("");
//            System.out.println("Proper Noun Detect");
//            tokens = nlpManchineLearningService.detectPartOfSpeechByTag(sentence, "NNP");
//            for (String token : tokens){
//                System.out.println(token);
//            }
//
//            System.out.println("");
//            System.out.println("Noun Detect");
//            tokens = nlpManchineLearningService.detectPartOfSpeechByTag(sentence, "NN");
//            for (String token : tokens){
//                System.out.println(token);
//            }
//
//
//            System.out.println("");
//            System.out.println("Noun Phrase Detect");
//            List<String> phrases = nlpManchineLearningService.detectNounPhraseChunk(sentence);
//            for (String phrase : phrases){
//                System.out.println(phrase);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
