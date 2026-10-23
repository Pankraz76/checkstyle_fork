///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * Contains the common implementation of a loader, for loading a configuration
 * from an XML file.
 *
 * <p>
 * The error handling policy can be described as being austere, dead set,
 * disciplinary, dour, draconian, exacting, firm, forbidding, grim, hard, hard-
 * boiled, harsh, harsh, in line, iron-fisted, no-nonsense, oppressive,
 * persnickety, picky, prudish, punctilious, puritanical, rigid, rigorous,
 * scrupulous, set, severe, square, stern, stickler, straight, strait-laced,
 * stringent, stuffy, stuffy, tough, unpermissive, unsparing and uptight.
 * </p>
 *
 * @noinspection ThisEscapedInObjectConstruction
 * @noinspectionreason ThisEscapedInObjectConstruction - only reference is used and not
 *      accessed until initialized
 */
public class XmlLoader
    extends DefaultHandler {

    /** Maps public id to resolve to resource name for the DTD. */
    private final Map<String, String> publicIdToResourceNameMap;
    /** Parser to read XML files. **/
    private final XMLReader parser;

    /**
     * Creates a new instance.
     *
     * @param publicIdToResourceNameMap maps public IDs to DTD resource names
     * @throws SAXException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     */
    protected XmlLoader(Map<String, String> publicIdToResourceNameMap)
            throws SAXException, ParserConfigurationException {
        this.publicIdToResourceNameMap =
                UnmodifiableCollectionUtil.copyOfMap(publicIdToResourceNameMap);
        parser = createXmlReader(this);
    }

    /**
     * Parses the specified input source.
     *
     * @param inputSource the input source to parse.
     * @throws IOException if an error occurs
     * @throws SAXException in an error occurs
     */
    public void parseInputSource(InputSource inputSource)
            throws IOException, SAXException {
        parser.parse(inputSource);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        InputSource inputSource = null;
        if (publicId != null) {
            final String dtdResourceName = publicIdToResourceNameMap.get(publicId);

            if (dtdResourceName != null) {
                final ClassLoader loader = getClass().getClassLoader();
                final InputStream dtdIs = loader.getResourceAsStream(dtdResourceName);
                inputSource = new InputSource(dtdIs);
            }
        }
        return inputSource;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw exception;
    }

    /**
     * Helper method to create {@code XMLReader}.
     *
     * @param handler the content handler
     * @return new XMLReader instance
     * @throws ParserConfigurationException if a parser cannot be created
     * @throws SAXException for SAX errors
     */
    private static XMLReader createXmlReader(DefaultHandler handler)
            throws SAXException, ParserConfigurationException {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        LoadExternalDtdFeatureProvider.setFeaturesBySystemProperty(factory);
        factory.setValidating(true);
        final XMLReader xmlReader = factory.newSAXParser().getXMLReader();
        LoadExternalDtdFeatureProvider.setAccessExternalDtdBySystemProperty(xmlReader);
        xmlReader.setContentHandler(handler);
        xmlReader.setEntityResolver(handler);
        xmlReader.setErrorHandler(handler);
        return xmlReader;
    }

    /**
     * Used for setting specific for secure java installations features to SAXParserFactory.
     * Pulled out as a separate class in order to suppress Pitest mutations.
     */
    public static final class LoadExternalDtdFeatureProvider {

        /** System property name to enable external DTD load. */
        public static final String ENABLE_EXTERNAL_DTD_LOAD = "checkstyle.enableExternalDtdLoad";

        /** Feature that enables loading external DTD when loading XML files. */
        public static final String LOAD_EXTERNAL_DTD =
                "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        /** Feature that enables including external general entities in XML files. */
        public static final String EXTERNAL_GENERAL_ENTITIES =
                "http://xml.org/sax/features/external-general-entities";
        /** Feature that enables including external parameter entities in XML files. */
        public static final String EXTERNAL_PARAMETER_ENTITIES =
                "http://xml.org/sax/features/external-parameter-entities";

        /**
         * Value of the {@link XMLConstants#ACCESS_EXTERNAL_DTD} property that restricts retrieval
         * of external DTDs to the local {@code file} protocol only, blocking network protocols
         * such as {@code http}, {@code https} and {@code ftp}.
         */
        private static final String LOCAL_FILE_ACCESS_ONLY = "file";

        /**
         * Value of the {@link XMLConstants#ACCESS_EXTERNAL_DTD} property that permits retrieval
         * of external DTDs over any protocol.
         */
        private static final String ALL_EXTERNAL_ACCESS = "all";

        /** Stop instances being created. **/
        private LoadExternalDtdFeatureProvider() {
        }

        /**
         * Configures SAXParserFactory with features required
         * to use external DTD file loading, this is not activated by default to no allow
         * usage of schema files that checkstyle do not know
         * it is even security problem to allow files from outside.
         *
         * @param factory factory to be configured with special features
         * @throws SAXException if an error occurs
         * @throws ParserConfigurationException if an error occurs
         */
        public static void setFeaturesBySystemProperty(SAXParserFactory factory)
                throws SAXException, ParserConfigurationException {

            final boolean enableExternalDtdLoad = isExternalDtdLoadEnabled();

            factory.setFeature(LOAD_EXTERNAL_DTD, enableExternalDtdLoad);
            factory.setFeature(EXTERNAL_GENERAL_ENTITIES, enableExternalDtdLoad);
            factory.setFeature(EXTERNAL_PARAMETER_ENTITIES, enableExternalDtdLoad);
        }

        /**
         * Checks whether external DTD loading is enabled via the
         * {@link #ENABLE_EXTERNAL_DTD_LOAD} system property.
         *
         * @return {@code true} if external DTD loading is explicitly enabled
         */
        private static boolean isExternalDtdLoadEnabled() {
            return Boolean.parseBoolean(
                System.getProperty(ENABLE_EXTERNAL_DTD_LOAD, Boolean.FALSE.toString()));
        }

        /**
         * Restricts retrieval of external DTDs over network protocols unless external DTD
         * loading is explicitly enabled via the {@link #ENABLE_EXTERNAL_DTD_LOAD} system
         * property. A validating parser fetches the DTD referenced in a {@code DOCTYPE}
         * declaration even when {@link #LOAD_EXTERNAL_DTD} is disabled, so this restriction is
         * required to prevent a crafted configuration, suppression or import-control file from
         * making Checkstyle issue requests to arbitrary URLs (SSRF) or hang the build on an
         * unresponsive host. Local {@code file} DTDs remain allowed, since the import-control
         * loader supports them and they cannot be used to reach remote hosts. Configurations
         * that reference the bundled Checkstyle DTD by its public id are unaffected, since that
         * DTD is supplied by the entity resolver without any external access.
         *
         * @param xmlReader reader to be configured with the access restriction
         * @throws SAXException if the property is not supported by the parser
         */
        public static void setAccessExternalDtdBySystemProperty(XMLReader xmlReader)
                throws SAXException {
            final String accessExternalDtd;
            if (isExternalDtdLoadEnabled()) {
                accessExternalDtd = ALL_EXTERNAL_ACCESS;
            }
            else {
                accessExternalDtd = LOCAL_FILE_ACCESS_ONLY;
            }
            xmlReader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, accessExternalDtd);
        }

    }

}
