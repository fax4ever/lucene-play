package eu.fax4ever.play.lucene;

import java.util.Collections;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.util.TokenFilterFactory;

public class CustomNormalizer extends Analyzer {

	private static final KeywordTokenizerFactory KEYWORD_TOKENIZER_FACTORY = new KeywordTokenizerFactory( Collections.emptyMap() );
	private final TokenFilterFactory filterFactory;

	public CustomNormalizer(TokenFilterFactory filterFactory) {
		this.filterFactory = filterFactory;
	}

	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tk = KEYWORD_TOKENIZER_FACTORY.create();

		// after that try .normalize
		TokenStream ts = filterFactory.create( tk );

		return new TokenStreamComponents( tk, ts );
	}

}
