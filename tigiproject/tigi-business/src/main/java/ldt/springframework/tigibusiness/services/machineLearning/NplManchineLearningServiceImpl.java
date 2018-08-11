package ldt.springframework.tigibusiness.services.machineLearning;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/9/18
 */

@Service
public class NplManchineLearningServiceImpl implements NlpManchineLearningService{


    // =======================================
    // =             Tokenizer               =
    // =======================================

    @Override
    public String[] implementTokenizerME(String sentence) throws IOException {
        InputStream inputStream = getClass()
                .getResourceAsStream("/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(inputStream);
        TokenizerME tokenizer = new TokenizerME(model);

        return tokenizer.tokenize(sentence);
    }

    public String[] implementWhitespaceTokenizer(String sentence){
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        return  tokenizer.tokenize(sentence);
    }

    public String[] implementSimpleTokenizer(String sentence) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return tokenizer.tokenize(sentence);
    }


    // =======================================
    // =      Named Entity  Recognition      =
    // =======================================

    /*
     * Detect person in sentence
     */
    @Override
    public List<String> detectPerson(String sentence) throws IOException {

        String[] tokens = this.implementSimpleTokenizer(sentence);

        InputStream inputStreamNameFinder = getClass()
                .getResourceAsStream("/models/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(
                inputStreamNameFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));

        List<String> result = new ArrayList<>();
        for (Span span : spans) {
            result.add(tokens[span.getStart()]);
        }
        return result;
    }

    /*
     * Detect organization in sentence
     */
    @Override
    public List<String> detectOrganization(String sentence) throws IOException {

        String[] tokens = this.implementSimpleTokenizer(sentence);

        InputStream inputStreamNameFinder = getClass()
                .getResourceAsStream("/models/en-ner-organization.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(
                inputStreamNameFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));

        List<String> result = new ArrayList<>();
        for (Span span : spans) {
            result.add(tokens[span.getStart()]);
        }
        return result;
    }



    // =======================================
    // =       Part-of-Speech Tagging        =
    // =======================================

    /*
     * Detect sentence structure
     */
    @Override
    public String[] detectPartOfSpeech(String sentence) throws IOException {

        String[] tokens = this.implementSimpleTokenizer(sentence);

        InputStream inputStreamPOSTagger = getClass()
                .getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);

        return posTagger.tag(tokens);
    }

    @Override
    public List<String> detectPartOfSpeechByTag(String sentence, String tag) throws IOException {

        String[] tokens = this.implementSimpleTokenizer(sentence);

        InputStream inputStreamPOSTagger = getClass()
                .getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);

        String[] tags = posTagger.tag(tokens);
        List<String> result = new ArrayList<>();
        for(int i = 0 ; i < tags.length; i++){
            if(tags[i].equals(tag))
                result.add(tokens[i]);
        }
        return result;
    }



    // =======================================
    // =           Lemmatization             =
    // =======================================

    @Override
    public String[] detectLemmatization(String sentence) throws Exception {

        // detect tokens
        String[] tokens = this.implementSimpleTokenizer(sentence);

        // detect part-of-speech
        String tags[] = this.detectPartOfSpeech(sentence);
        InputStream dictLemmatizer = getClass()
                .getResourceAsStream("/models/dicts/en-lemmatizer.dict");
        DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(
                dictLemmatizer);

        // execute lemma detect
        return lemmatizer.lemmatize(tokens, tags);
    }



    // =======================================
    // =              Chunking               =
    // =======================================

    @Override
    public String[] detectChunk(String sentence) throws Exception {

        // detect tokens
        String[] tokens = this.implementSimpleTokenizer(sentence);

        String tags[] = this.detectPartOfSpeech(sentence);

        InputStream inputStreamChunker = getClass()
                .getResourceAsStream("/models/en-chunker.bin");
        ChunkerModel chunkerModel
                = new ChunkerModel(inputStreamChunker);
        ChunkerME chunker = new ChunkerME(chunkerModel);
        return chunker.chunk(tokens, tags);
    }

    public List<String> detectNounPhraseChunk(String sentence) throws Exception{
        // detect tokens
        String[] tokens = this.implementSimpleTokenizer(sentence);

        String tags[] = this.detectPartOfSpeech(sentence);

        InputStream inputStreamChunker = getClass()
                .getResourceAsStream("/models/en-chunker.bin");
        ChunkerModel chunkerModel
                = new ChunkerModel(inputStreamChunker);
        ChunkerME chunker = new ChunkerME(chunkerModel);
        String[] chunks = chunker.chunk(tokens, tags);


        List<String> result = new ArrayList<>();
        for(int i = 0; i < chunks.length; i++){
            String nounPhrase = "";

            if(chunks[i].equals("NP")){
                for(int j = i; j < chunks.length; j++){
                    if(!chunks[j].equals("NP"))
                        break;
                    nounPhrase += tokens[i];
                }

                result.add(nounPhrase);
            }
        }

        return result;
    }
}
