package com.abnamro.nl.channels.geninfo.bankmail.util;

import com.abnamro.nl.channels.geninfo.bankmail.abpc.implementation.BankmailABPCMessageKeys;
import com.abnamro.nl.channels.geninfo.bankmail.asc.interfaces.BankmailConstants;
import com.abnamro.nl.channels.geninfo.bankmail.interfaces.BankmailApplicationException;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.BOMailTemplate;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.CCAMailboxTemplateJson;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.GenesysMailboxTemplateJson;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.ServiceConcept;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.ServiceConceptCGC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.abnamro.nl.channels.geninfo.bankmail.asc.interfaces.BankmailConstants.*;

public final class BankmailResourceDataUtil {

	private static final Map<String, Object> cache = new ConcurrentHashMap<>();
	private static final ClassLoader classLoader = BankmailResourceDataUtil.class.getClassLoader();

	/** The Constant CLAZZ. used for logging */
	private static final Class<BankmailResourceDataUtil> CLASS = BankmailResourceDataUtil.class;

	/**
	 * Instantiate the Logger
	 */
	private static final LogHelper LOGGER = new LogHelper(CLASS);

	private static Object getData(String key) {
		return cache.get(key);
	}

	private static void putData(String key, Object value) {
		cache.put(key, value);
	}


	private BankmailResourceDataUtil() {

	}

	private static void retreiveBOData() throws BankmailApplicationException {

		retrieveData("filteredbos.json",
				FILTERED_BOS,
				"BO",
				String.class,
				"retreiveBOData()");
	}

	private static void retriveCCAMailboxTemplatePrivateData() throws BankmailApplicationException {

		retrieveData("ccamailboxtemplateprivate.json",
				CCA_MAILBOX_TEMPLATE_PRIVATE,
				"CCAMailboxTemplate",
				CCAMailboxTemplateJson.class,
				"retriveCCAMailboxTemplatePrivateData()");
	}


	private static void retrieveBOMailboxTemplateData() throws BankmailApplicationException {

		retrieveData("bomailboxtemplate.json",
				BO_MAILBOX_TEMPLATE,
				"BOMailTemplate",
				BOMailTemplate.class,
				"retrieveBOMailboxTemplateData()");
	}

	private static void retrieveGenesysMailboxTemplateYbbData() throws BankmailApplicationException {

		retrieveData("genesysmailboxtemplateybb.json",
				GENESYS_MAILBOX_TEMPLATE_YBB,
				"GenesysMailboxTemplate",
				GenesysMailboxTemplateJson.class,
				"retrieveBOMailboxTemplateData()");
	}

	private static void retrieveCCAMailboxTemplatePreferredData() throws BankmailApplicationException {

		retrieveData("ccamailboxtemplatepreferred.json",
				CCA_MAILBOX_TEMPLATE_PREFERRED,
				"CCAMailboxTemplate",
				CCAMailboxTemplateJson.class,
				"retrieveCCAMailboxTemplatePreferredData()");
	}

	private static void retrieveFilteredCustomerGroupsData() throws BankmailApplicationException {

		retrieveData("filteredcustomergroups.json",
				FILTERED_CUSTOMER_GROUPS,
				"CGC",
				String.class,
				"retrieveFilteredCustomerGroupsData()");
	}

	private static void retrieveGenesysMailboxTemplateAscData() throws BankmailApplicationException {

		retrieveData("genesysmailboxtemplateasc.json",
				GENESYS_MAILBOX_TEMPLATE_ASC,
				"GenesysMailboxTemplate",
				GenesysMailboxTemplateJson.class,
				"retrieveGenesysMailboxTemplateAscData()");
	}

	private static void retrieveServiceConceptCGCData() throws BankmailApplicationException {

		retrieveData("serviceconceptbycgc.json",
				SERVICE_CONCEPT_BY_CGC,
				"ServiceConcept",
				ServiceConceptCGC.class,
				"retrieveServiceConceptCGCData()");
	}


	private static void retreiveServiceConceptbySegmnetData() throws BankmailApplicationException {

		retrieveData("serviceconceptbysegment.json",
				SERVICE_CONCEPT_BY_SEGMENT,
				"ServiceConcept",
				ServiceConcept.class,
				"retreiveServiceConceptbySegmnetData()");
	}


	public static Object readJsons(String key) throws BankmailApplicationException {

		switch (key) {
			case FILTERED_BOS: retreiveBOData();
				break;
			case CCA_MAILBOX_TEMPLATE_PRIVATE: retriveCCAMailboxTemplatePrivateData();
				break;
			case BO_MAILBOX_TEMPLATE:  retrieveBOMailboxTemplateData();
				break;
			case GENESYS_MAILBOX_TEMPLATE_YBB: retrieveGenesysMailboxTemplateYbbData();
				break;
			case CCA_MAILBOX_TEMPLATE_PREFERRED: retrieveCCAMailboxTemplatePreferredData();
				break;
			case FILTERED_CUSTOMER_GROUPS:  retrieveFilteredCustomerGroupsData();
				break;
			case GENESYS_MAILBOX_TEMPLATE_ASC: retrieveGenesysMailboxTemplateAscData();
				break;
			case SERVICE_CONCEPT_BY_CGC: retrieveServiceConceptCGCData();
				break;
			case SERVICE_CONCEPT_BY_SEGMENT:  retreiveServiceConceptbySegmnetData();
				break;

			default:
				break;
		}

		return getData(key);
	}

	/**
	 * This method reads the object data and converts it into JSON
	 *
	 * @param resourceFileName resource file String
	 * @param key corresponding key String
	 * @param tagName String
	 * @param clazz the class used by the converted object Class
	 * @param logMethod the method which logs
	 * @throws BankmailApplicationException May throw Bankmail ApplicationException
	 */
	private static <T> void retrieveData(final String resourceFileName, final String key, final String tagName, Class<T> clazz, String logMethod) throws BankmailApplicationException {
		if (!cache.containsKey(key)) {
			try (InputStream reader = Objects.requireNonNull(classLoader.getResourceAsStream(resourceFileName))) {
				//Read JSON file
				parseAndPut(reader, tagName, key, clazz);

			} catch ( IOException e) {
				logAndThrow(logMethod, e);
			}
		}
	}

	private static void logAndThrow(String logMethod, Exception e) throws BankmailApplicationException {
		LOGGER.error(logMethod, BankmailConstants.BANKMAIL_JSON_DATA_ISSUE, e);
		Messages msgs = new Messages();
		msgs.addMessage(new Message(BankmailABPCMessageKeys.ERROR_UNEXPECTED_EXCEPTION), MessageType.getError());
		throw new BankmailApplicationException(msgs);
	}

	private static <T> void parseAndPut(InputStream reader, final String tagName, final String key, final Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		final var  jsonObject = gson.fromJson(new BufferedReader(new InputStreamReader(reader)), JsonObject.class);
		final var jsonArray = jsonObject.getAsJsonArray(tagName);
		var list = new ArrayList<T>();
		jsonArray.forEach(elem -> list.add(gson.fromJson(elem, clazz)));
		putData(key, list);
	}
}


