package com.selab.uidesignserver.config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;


public class Config {
	
	private final static Logger logger = Logger.getLogger(Config.class);
	
	// Logger
	public static String logger_config_path = "src/main/java/com/selab/uidesignserver/config/log4j.properties";
	
	// Word Net
	public static String JWNL_properties_path = "src/main/java/com/selab/uidesignserver/config/file_properties.xml";
	
	// LSA
	public static String LSA_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/LSA/wsdl_compound/";
	public static String LSA_no_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/LSA/wsdl_no_compound/";
	
	// PLSA
	public static String PLSA_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/PLSA/wsdl_compound/";
	public static String PLSA_no_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/PLSA/wsdl_no_compound/";
	public static int PLSA_iteration = 100;
	public static int PLSA_topicNum = 44;
	
	// tfidf
	public static String tfidf_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/tfidf/wsdl_compound/";
	public static String tfidf_no_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/tfidf/wsdl_no_compound/";
	
	// word2vec
	public static String word2vec_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/word2vec/wsdl_compound/";
	public static String word2vec_no_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/word2vec/wsdl_no_compound/";
	
	// google word2vec
	public static String google_word2vec_script_path = "src/main/java/com/selab/uidesignserver/res/model/google_word2vec/wordvec.py";
	public static String google_word2vec_model_path = "src/main/java/com/selab/uidesignserver/res/model/google_word2vec/";
	
	// LDA
	public static String LDA_script_path = "src/main/java/com/selab/uidesignserver/res/model/LDA/scripts/loadLDA.py";
	public static String LDA_model_path = "src/main/java/com/selab/uidesignserver/res/model/LDA/wsdl_1081/all_false_1081";
	public static String LDA_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/LDA/wsdl_compound/";
	public static String LDA_no_comp_folder = "src/main/java/com/selab/uidesignserver/res/model/LDA/wsdl_no_compound/";
	public static String LDA_importance_comp_path = "src/main/java/com/selab/uidesignserver/res/model/wsdl_lda/wsdl_compound";
	public static String LDA_importance_no_comp_path = "src/main/java/com/selab/uidesignserver/res/model/wsdl_lda/wsdl_no_compound";
	
	// word cooccurrence frequency
	public static String cooccurrence_frequency_comp_path = "src/main/java/com/selab/uidesignserver/res/model/cooccurrence_frequency/frequency_true";
	public static String cooccurrence_frequency_no_comp_path = "src/main/java/com/selab/uidesignserver/res/model/cooccurrence_frequency/frequency_false";
	
	// PMI
	public static String PMI_comp_path = "src/main/java/com/selab/uidesignserver/res/model/PMI/pmi_value_true";
	public static String PMI_no_comp_path = "src/main/java/com/selab/uidesignserver/res/model/PMI/pmi_value_false";
	
	// Word Segmemtation
	public static String word_segmentation_script_path = "src/main/java/com/selab/uidesignserver/res/model/word_segmentation/word_segmentation.py";
	
	// word_importance path = {enwiki-category-WS-trigram_phrases_release_model_471942, all_false_1081}, folder = {wiki, wsdl}
	public static String word_importance_script_path = "src/main/java/com/selab/uidesignserver/res/model/word_importance/scripts/word_importance_api.py";
	public static String word_importance_model_path = "src/main/java/com/selab/uidesignserver/res/model/LDA/enwiki_category/enwiki-category-WS-trigram_phrases_release_model_471942";
	public static String word_importance_matrix_folder = "src/main/java/com/selab/uidesignserver/res/model/word_importance/model/wiki/";
	
	// Python
	public static String python_virtualenv_cmd = "source /Users/selab/virtualenv/SDEGM/bin/activate";
	
	// Output folder
	public static String output_folder = "output/";
	
	// Resources
	public static String words_path = "src/main/java/com/selab/uidesignserver/res/custom.words";
	public static String phrases_path = "src/main/java/com/selab/uidesignserver/res/custom.phrases";
	public static String stopwords_path = "src/main/java/com/selab/uidesignserver/res/custom.stopwords";
	public static String datatype_table_folder = "src/main/java/com/selab/uidesignserver/res/dataType/";

	// test data
	public static String testdata_queries_folder = "testdata/queries/1.3/";
	public static String testdata_Wsdl_pool = "testdata/candidates/WsdlPool/";
	public static String testdata_Owls_pool = "testdata/candidates/OwlsPool/";
	public static String testdata_benchmark_file = "testdata/owls-tc4.xml";


	/** has compound word or not */
	public static boolean usePhraser = false;

	/** OWA, TopicModel */
	public static String importanceMode = "TopicModel";

	/** plsa, lda, tfidf */
	public static String importanceMode_topicModel_mode = "plsa";
	
	/** At least one, Few, Some, Half, Many, All */
	public static String importanceMode_OWA_linguesticQuantifier = "Many";
	
	/** Const, AHP, LDA, OWA */
	public static String elasticityMode = "Const";

	/** new_thresh = mean_thresh * factor */
	public static Double elasticityMode_LDA_threshFactor = 1.0;
	
	/**  word2vec, vector_combination */
	public static String wordSimilarityMode = "vector_combination";
	public static String wordSimilarityMode_word2vec_modelPath = "src/main/java/com/selab/uidesignserver/res/model/word2vec/google_news/GoogleNews-vectors-negative300";
	public static String wordSimilarityMode_vector_combination_modelPath = "src/main/java/com/selab/uidesignserver/res/model/word2vec/google_news/best/best.txt";

	/** Normal, Aggregation */
	public static String graphSimmilarityMode = "Normal";
	
	/**  importance_1, importance_*, hungarian_1, hungarian_* */
	public static String aggregationMode = "hungarian_*"; 
	
	/**  range 0.0 ~ 1.0 */
	public static double aggregationMode_thresh = 1.0;
	
	/** _table */
	public static String DataTypeNodeDistanceMode = "_table";
	
	/** constant keyword node elasticity */
	public static Double ELASTICITY_KEYWORD = 1.0;
	public static Double ELASTICITY_DATATYPE = 0.0;
	public static Double ELASTICITY_CONNECTOR = 0.0;
	
	/** part similarity weight */
	public static double PART_WEIGHT_OPERATION = 1.0;
	public static double PATR_WEIGHT_INPUT = 0.0;
	public static double PART_WEIGHT_OUTPUT = 0.0;
	
	public static void changeConfigByPropertiesFile(String property_file_path) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(property_file_path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Can't Load Properties File: " + property_file_path);
		}
		parsePropertiesToConfig(properties);
	}
	
	public static void parsePropertiesToConfig(Properties properties) {
		System.err.println("--------------- Custom Properties ---------------");
		for (Object keyObj : properties.keySet()) {
			String key = keyObj.toString();
			String value = properties.get(keyObj).toString();
			System.err.printf("%s = %s\n", key, value);
			if (key.equals("similarityCalculatorCore")) {wordSimilarityMode = value;} 
			else if (key.equals("similarityCalculatorStrategy")) {graphSimmilarityMode = value;}
			else if (key.equals("aggregationMode")) {aggregationMode = value;}
			else if (key.equals("aggregationMode_star_thresh")) {aggregationMode_thresh = Double.parseDouble(value);}
			else if (key.equals("elasticityMode")) {elasticityMode = value;}
			else if (key.equals("LDA_thresh_factor")) {elasticityMode_LDA_threshFactor = Double.parseDouble(value);}
			else if (key.equals("DataTypeNodeDistanceMode")) {DataTypeNodeDistanceMode = value;}
			else if (key.equals("importanceMode")) {importanceMode_topicModel_mode = value;}
			else if (key.equals("hasCompWord")) {usePhraser = Boolean.parseBoolean(value);}
			else if (key.equals("python_virtualenv_cmd")) {python_virtualenv_cmd = value;}
			else {
				System.err.printf("Ignore unsurpported key and value: %s = %s %n", key, value);
			}
		}
		System.err.println("--------------- Cucstom Properties ---------------");
	}
	
	public static void logConfig() {
		logger.info("--------------- log Config ---------------");
		// importance
		if ("Aggregation".equals(graphSimmilarityMode)) {
			if ("TopicModel".equals(importanceMode)) {
				logger.info("ImportanceMode: TopicModel(" + importanceMode_topicModel_mode + ")");
			} else {
				logger.info("ImportanceMode: " + importanceMode);
			}
		} else {
			logger.info("ImportanceMode: X");
		}
		// elasticity
		if ("Const".equals(elasticityMode)) {
			logger.info("ElasticityMode: Const" + String.format("(%.1f, %.1f, %.1f)", ELASTICITY_KEYWORD, ELASTICITY_DATATYPE, ELASTICITY_CONNECTOR));
		} else {
			logger.info("ElasticityMode: " + elasticityMode);
		}
		// distance
		if ("Aggregation".equals(graphSimmilarityMode)) {
			if (aggregationMode.contains("*")) {
				logger.info("DistanceMode: Aggregation(" + aggregationMode + " - " + aggregationMode_thresh + ")");
			} else {
				logger.info("DistanceMode: Aggregation(" + aggregationMode + ")");
			}
		} else {
			logger.info("DistanceMode: " + graphSimmilarityMode);
		}
		// part weight
		logger.info("PartWeight: " + String.format("(%.1f, %.1f, %.1f)", PART_WEIGHT_OPERATION, PATR_WEIGHT_INPUT, PART_WEIGHT_OUTPUT));
		logger.info("--------------- log Config ---------------");
	}
	
	
	public static void main(String args[]) {
		
	}
}
