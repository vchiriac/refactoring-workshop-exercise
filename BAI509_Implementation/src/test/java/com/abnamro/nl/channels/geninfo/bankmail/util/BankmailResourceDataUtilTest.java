package com.abnamro.nl.channels.geninfo.bankmail.util;

import com.abnamro.nl.channels.geninfo.bankmail.interfaces.BankmailApplicationException;
import com.abnamro.nl.channels.geninfo.bankmail.jsons.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import static com.abnamro.nl.channels.geninfo.bankmail.asc.interfaces.BankmailConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class BankmailResourceDataUtilTest {

    @Test
    void testRetrieveBOData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(FILTERED_BOS);
        assertNotNull(result);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals("4", list.get(1));

    }

    @Test
    void testRetrieveCCAMailboxTemplatePrivateData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(CCA_MAILBOX_TEMPLATE_PRIVATE);
        assertNotNull(result);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "33", ((CCAMailboxTemplateJson) list.get(0)).ccaRole);
    }

    @Test
    void testRetrieveBOMailboxTemplateData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(BO_MAILBOX_TEMPLATE);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "YBB", ((BOMailTemplate) list.get(0)).getFallbackStrategy());
    }

    @Test
    void testRetrieveGenesysMailboxTemplateYbbData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(GENESYS_MAILBOX_TEMPLATE_YBB);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "ABN AMRO Kleinbedrijf", ((GenesysMailboxTemplateJson) list.get(0)).getDisplayName());
    }

    @Test
    void testRetrieveCCAMailboxTemplatePreferredData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(CCA_MAILBOX_TEMPLATE_PREFERRED);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "ABN AMRO Bank N.V.", ((CCAMailboxTemplateJson) list.get(0)).getDisplayNamePrefix());
    }

    @Test
    void testRetrieveFilteredCustomerGroupsData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(FILTERED_CUSTOMER_GROUPS);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "0106", list.get(5));
    }

    @Test
    void testRetrieveGenesysMailboxTemplateAscData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(GENESYS_MAILBOX_TEMPLATE_ASC);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "ABN AMRO Contact Center", ((GenesysMailboxTemplateJson) list.get(0)).getDisplayName());
    }

    @Test
    void testRetrieveServiceConceptCGCData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(SERVICE_CONCEPT_BY_CGC);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "Y", ((ServiceConceptCGC) list.get(0)).getCanSelectSubject());
    }

    @Test
    void testRetreiveServiceConceptbySegmnetData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons(SERVICE_CONCEPT_BY_SEGMENT);
        final var list = new ArrayList<>((Collection<?>)result);
        assertEquals( "Y", ((ServiceConcept) list.get(0)).getCanSelectSubject());
    }

    @Test
    void testRetreiveBadData() throws BankmailApplicationException {
        final var result = BankmailResourceDataUtil.readJsons("SOME_NOT_EXISTING_SEGMENT");
        assertNull(result);
    }
}
