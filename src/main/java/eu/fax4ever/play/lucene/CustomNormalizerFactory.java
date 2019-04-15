package eu.fax4ever.play.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.Version;

public class CustomNormalizerFactory {

	public static Analyzer createLowerCaseNormalizer() {
		LowerCaseFilterFactory lowerCaseFilterFactory = new LowerCaseFilterFactory( Collections.emptyMap() );
		return new CustomNormalizer( lowerCaseFilterFactory );
	}

	public static Analyzer createLowerCaseNormalizerAlt() {
		LowerCaseFilterFactory lowerCaseFilterFactory = new LowerCaseFilterFactory( Collections.emptyMap() );
		return new CustomNormalizerAlt( lowerCaseFilterFactory );
	}

	public static Analyzer createLowerCaseNormalizerCustomAnalyzer() throws IOException {
		// TODO in HSearch, use the HibernateSearchResourceLoader; see org.hibernate.search.backend.lucene.analysis.impl.LuceneAnalysisComponentFactory#LuceneAnalysisComponentFactory
		ResourceLoader resourceLoader = new ClasspathResourceLoader( CustomNormalizerFactory.class );
		return CustomAnalyzer.builder( resourceLoader )
				// TODO in HSearch, use the configured Lucene version; see org.hibernate.search.backend.lucene.analysis.impl.LuceneAnalysisComponentFactory#LuceneAnalysisComponentFactory
				.withDefaultMatchVersion( Version.LATEST )
				.withTokenizer( KeywordTokenizerFactory.class )
				.addTokenFilter( LowerCaseFilterFactory.class )
				.build();
	}

	/**
	 * Taken from Hibernate Search project.
	 *
	 * Returns the first token resulting from the analysis, logging a warning if there are more than one token.
	 *
	 * @param analyzer the Lucene analyzer to use
	 * @param fieldName the name of the field: might affect the analyzer behavior
	 * @param text the value to analyze
	 * @return the first token resulting from the analysis
	 * @throws IOException
	 * @see <a href="http://google.com">https://github.com/hibernate/hibernate-search</a>
	 */
	public static String normalize(Analyzer analyzer, String fieldName, String text) throws IOException {
		try ( TokenStream stream = analyzer.tokenStream( fieldName, new StringReader( text ) ) ) {
			String firstToken = null;
			CharTermAttribute term = stream.addAttribute( CharTermAttribute.class );
			stream.reset();
			if ( stream.incrementToken() ) {
				firstToken = new String( term.buffer(), 0, term.length() );
				if ( !stream.incrementToken() ) {
					stream.end();
				}
			}
			return firstToken;
		}
	}

}
