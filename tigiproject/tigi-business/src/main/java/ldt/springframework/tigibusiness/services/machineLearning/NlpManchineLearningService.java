package ldt.springframework.tigibusiness.services.machineLearning;


import opennlp.tools.util.Span;

import java.io.IOException;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */
public interface NlpManchineLearningService {
    String[] implementTokenizerME(String sentence) throws IOException;

    String[] implementWhitespaceTokenizer(String sentence);

    String[] implementSimpleTokenizer(String sentence);

    List<String> detectPerson(String sentence) throws IOException;

    List<String> detectOrganization(String sentence) throws IOException;

    String[] detectPartOfSpeech(String sentence) throws IOException;

    List<String> detectPartOfSpeechByTag(String sentence, String tag) throws IOException;

    String[] detectLemmatization(String sentence) throws Exception;

    String[] detectChunk(String sentence) throws Exception;

    List<String> detectNounPhraseChunk(String sentence) throws Exception;
}
