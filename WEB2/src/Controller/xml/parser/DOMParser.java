package Controller.xml.parser;

import Controller.VoucherManager;
import Model.*;
import Model.Enums.Food;
import Model.Enums.Transport;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * DOM parser of XML
 */

public class DOMParser implements VouchersParser {

    private static final Logger logger = LogManager.getLogger("Parser");

    /**
     * Parse XML file to collective using DOM parser
     *
     * @param fileName name of the file that contains medicines stored in XML format
     * @return parsed medicines list
     */
    @Override
    public List<Voucher> parse(String fileName) {
        logger.info("Starting DOM parsing");
        List<Voucher> vouchers = new ArrayList<>();
        VoucherManager voucherManager = new VoucherManager();

        File inputFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error(e);
        }
        doc.getDocumentElement().normalize();

        NodeList cruiseNodes = doc.getElementsByTagName("cruise");
        NodeList excursionNodes = doc.getElementsByTagName("excursion");
        NodeList shoppingNodes = doc.getElementsByTagName("shopping");
        NodeList therapyNodes = doc.getElementsByTagName("therapy");
        NodeList vacationNodes = doc.getElementsByTagName("vacation");

        for (int i = 0; i < cruiseNodes.getLength(); ++i) {
            Node node = cruiseNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Cruise cruise = new Cruise.Builder<>()
                        .placeName(element.getElementsByTagName("placeName").item(0).getTextContent())
                        .cruiserName(element.getElementsByTagName("cruiserName").item(0).getTextContent())
                        .daysAmount(Integer.parseInt(element.getElementsByTagName("daysAmount").item(0).getTextContent()))
                        .price(Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent()))
                        .peopleAmount(Integer.parseInt(element.getElementsByTagName("peopleAmount").item(0).getTextContent()))
                        .food(Food.valueOf(element.getElementsByTagName("food").item(0).getTextContent().toUpperCase()))
                        .transport(Transport.valueOf(element.getElementsByTagName("transport").item(0).getTextContent().toUpperCase()))
                        .build();

                vouchers.add(cruise);
            }
        }

        for (int i = 0; i < excursionNodes.getLength(); ++i) {
            Node node = excursionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Excursion excursion = new Excursion.Builder<>()
                        .placeName(element.getElementsByTagName("placeName").item(0).getTextContent())
                        .guideName(element.getElementsByTagName("guideName").item(0).getTextContent())
                        .daysAmount(Integer.parseInt(element.getElementsByTagName("daysAmount").item(0).getTextContent()))
                        .price(Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent()))
                        .peopleAmount(Integer.parseInt(element.getElementsByTagName("peopleAmount").item(0).getTextContent()))
                        .food(Food.valueOf(element.getElementsByTagName("food").item(0).getTextContent().toUpperCase()))
                        .transport(Transport.valueOf(element.getElementsByTagName("transport").item(0).getTextContent().toUpperCase()))
                        .build();

                vouchers.add(excursion);
            }
        }

        for (int i = 0; i < shoppingNodes.getLength(); ++i) {
            Node node = shoppingNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Shopping shopping = new Shopping.Builder<>()
                        .placeName(element.getElementsByTagName("placeName").item(0).getTextContent())
                        .mallName(element.getElementsByTagName("mallName").item(0).getTextContent())
                        .daysAmount(Integer.parseInt(element.getElementsByTagName("daysAmount").item(0).getTextContent()))
                        .price(Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent()))
                        .peopleAmount(Integer.parseInt(element.getElementsByTagName("peopleAmount").item(0).getTextContent()))
                        .food(Food.valueOf(element.getElementsByTagName("food").item(0).getTextContent().toUpperCase()))
                        .transport(Transport.valueOf(element.getElementsByTagName("transport").item(0).getTextContent().toUpperCase()))
                        .build();

                vouchers.add(shopping);
            }
        }

        for (int i = 0; i < therapyNodes.getLength(); ++i) {
            Node node = therapyNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Therapy therapy = new Therapy.Builder<>()
                        .placeName(element.getElementsByTagName("placeName").item(0).getTextContent())
                        .mainProcedureName(element.getElementsByTagName("mainProcedureName").item(0).getTextContent())
                        .daysAmount(Integer.parseInt(element.getElementsByTagName("daysAmount").item(0).getTextContent()))
                        .price(Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent()))
                        .peopleAmount(Integer.parseInt(element.getElementsByTagName("peopleAmount").item(0).getTextContent()))
                        .food(Food.valueOf(element.getElementsByTagName("food").item(0).getTextContent().toUpperCase()))
                        .transport(Transport.valueOf(element.getElementsByTagName("transport").item(0).getTextContent().toUpperCase()))
                        .build();

                vouchers.add(therapy);
            }
        }

        for (int i = 0; i < vacationNodes.getLength(); ++i) {
            Node node = vacationNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Vacation vacation = new Vacation.Builder<>()
                        .placeName(element.getElementsByTagName("placeName").item(0).getTextContent())
                        .hotelName(element.getElementsByTagName("hotelName").item(0).getTextContent())
                        .daysAmount(Integer.parseInt(element.getElementsByTagName("daysAmount").item(0).getTextContent()))
                        .price(Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent()))
                        .peopleAmount(Integer.parseInt(element.getElementsByTagName("peopleAmount").item(0).getTextContent()))
                        .food(Food.valueOf(element.getElementsByTagName("food").item(0).getTextContent().toUpperCase()))
                        .transport(Transport.valueOf(element.getElementsByTagName("transport").item(0).getTextContent().toUpperCase()))
                        .build();

                vouchers.add(vacation);
            }
        }

        logger.info("Finished DOM parsing");
        return vouchers;
    }

}
