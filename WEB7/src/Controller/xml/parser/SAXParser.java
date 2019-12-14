package Controller.xml.parser;

import Controller.VoucherManager;
import Model.*;
import Model.Enums.Food;
import Model.Enums.Transport;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * SAX parser of XML
 */

public class SAXParser implements VouchersParser {

    private static final Logger logger = LogManager.getLogger("Parser");

    /**
     * Parse XML file to collective using DOM parser
     *
     * @param fileName placeName of the file that contains vouchers stored in XML format
     * @return parsed vouchers list
     */
    @Override
    public List<Voucher> parse(String fileName) {
        logger.info("Starting SAX parsing");
        List<Voucher> vouchers = new LinkedList<>();

        try {
            File inputFile = new File(fileName);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            Handler handler = new Handler();
            saxParser.parse(inputFile, handler);
            vouchers = handler.getVouchers();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            logger.error(e);
        }

        logger.info("Finish SAX parsing");
        return vouchers;
    }

    /**
     * Tags handler
     */
    private class Handler extends DefaultHandler {

        VoucherManager voucherManager = new VoucherManager();

        private List<Voucher> vouchers = new ArrayList<>();

        private boolean parsed = true;
        private String currentElement;

        private String placeName;
        private int daysAmount;
        private int price;
        private int peopleAmount;
        private Food food;
        private Transport transport;
        private String cruiserName;
        private String guideName;
        private String mallName;
        private String mainProcedureName;
        private String hotelName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            if (qName.equals("placeName") ||
                    qName.equals("daysAmount") ||
                    qName.equals("price") ||
                    qName.equals("peopleAmount") ||
                    qName.equals("food") ||
                    qName.equals("transport") ||
                    qName.equals("cruiserName") ||
                    qName.equals("guideName") ||
                    qName.equals("mallName") ||
                    qName.equals("mainProcedureName") ||
                    qName.equals("hotelName")) {
                parsed = false;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "cruise":
                    Cruise cruise = new Cruise.Builder<>()
                            .placeName(placeName)
                            .cruiserName(cruiserName)
                            .daysAmount(daysAmount)
                            .price(price)
                            .peopleAmount(peopleAmount)
                            .food(food)
                            .transport(transport)
                            .build();

                    vouchers.add(cruise);
                    break;
                case "excursion":
                    Excursion excursion = new Excursion.Builder<>()
                            .placeName(placeName)
                            .guideName(guideName)
                            .daysAmount(daysAmount)
                            .price(price)
                            .peopleAmount(peopleAmount)
                            .food(food)
                            .transport(transport)
                            .build();

                    vouchers.add(excursion);
                    break;
                case "shopping":
                    Shopping shopping = new Shopping.Builder<>()
                            .placeName(placeName)
                            .mallName(mallName)
                            .daysAmount(daysAmount)
                            .price(price)
                            .peopleAmount(peopleAmount)
                            .food(food)
                            .transport(transport)
                            .build();

                    vouchers.add(shopping);
                    break;
                case "therapy":
                    Therapy therapy = new Therapy.Builder<>()
                            .placeName(placeName)
                            .mainProcedureName(mainProcedureName)
                            .daysAmount(daysAmount)
                            .price(price)
                            .peopleAmount(peopleAmount)
                            .food(food)
                            .transport(transport)
                            .build();

                    vouchers.add(therapy);
                    break;
                case "vacation":
                    Vacation vacation = new Vacation.Builder<>()
                            .placeName(placeName)
                            .hotelName(hotelName)
                            .daysAmount(daysAmount)
                            .price(price)
                            .peopleAmount(peopleAmount)
                            .food(food)
                            .transport(transport)
                            .build();

                    vouchers.add(vacation);
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (!parsed) {
                switch (currentElement) {
                    case "placeName":
                        placeName = value;
                        break;
                    case "price":
                        price = Integer.parseInt(value);
                        break;
                    case "daysAmount":
                        daysAmount = Integer.parseInt(value);
                        break;
                    case "peopleAmount":
                        peopleAmount = Integer.parseInt(value);
                        break;
                    case "food":
                        food = Food.valueOf(value.toUpperCase());
                        break;
                    case "transport":
                        transport = Transport.valueOf(value.toUpperCase());
                        break;
                    case "cruiserName":
                        cruiserName = value;
                        break;
                    case "guideName":
                        guideName = value;
                        break;
                    case "mallName":
                        mallName = value;
                        break;
                    case "mainProcedureName":
                        mainProcedureName = value;
                        break;
                    case "hotelName":
                        hotelName = value;
                        break;
                }

                parsed = true;
            }

        }

        public List<Voucher> getVouchers() {
            return vouchers;
        }
    }
}

