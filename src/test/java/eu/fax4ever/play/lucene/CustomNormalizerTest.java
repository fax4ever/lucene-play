package eu.fax4ever.play.lucene;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.apache.lucene.analysis.Analyzer;

public class CustomNormalizerTest {

	@Test
	public void customNormalizer_normalize() {
		Analyzer lowerCaseNormalizer = CustomNormalizerFactory.createLowerCaseNormalizer();

		String normalized = lowerCaseNormalizer.normalize( "anyName", "BlaBlaBla" ).utf8ToString();
		Assert.assertEquals( "blablabla", normalized );
	}

	@Test
	public void customNormalizerCustomAnalyzer_normalize() throws IOException {
		Analyzer lowerCaseNormalizer = CustomNormalizerFactory.createLowerCaseNormalizerCustomAnalyzer();

		String normalized = lowerCaseNormalizer.normalize( "anyName", "BlaBlaBla" ).utf8ToString();
		Assert.assertEquals( "blablabla", normalized );
	}
}
