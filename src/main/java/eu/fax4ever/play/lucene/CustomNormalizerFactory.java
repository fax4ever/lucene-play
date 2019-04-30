package eu.fax4ever.play.lucene;

import java.io.IOException;
import java.util.Collections;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.Version;

public class CustomNormalizerFactory {

	public static Analyzer createLowerCaseNormalizer() {
		LowerCaseFilterFactory lowerCaseFilterFactory = new LowerCaseFilterFactory( Collections.emptyMap() );
		return new CustomNormalizer( lowerCaseFilterFactory );
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
}
