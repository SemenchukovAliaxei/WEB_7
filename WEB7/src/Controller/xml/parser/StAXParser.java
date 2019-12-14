package Controller.xml.parser;

import Model.*;
import Model.Enums.Food;
import Model.Enums.Transport;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * StAX parser of XML
 */

public class StAXParser implements VouchersParser {

    private static final Logger logger = LogManager.getLogger("Parser");

    /**
     * Parse XML file to collective using DOM parser
     *
     * @param fileName name of the file that contains vouchers stored in XML format
     * @return parsed vouchers list
     */
    @Override
    public List<Voucher> parse(String fileName) {
        logger.info("Starting StAX parsing");
        List<Voucher> vouchers = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = null;
        try {
            eventReader = factory.createXMLEventReader(new FileReader(fileName));
        } catch (XMLStreamException | FileNotFoundException e) {
            logger.error(e);
        }

        boolean parsed = true;
        String currentElement = "";

        String placeName = null;
        int daysAmount = 0;
        int price = 0;
        int peopleAmount = 0;
        Food food = null;
        Transport transport = null;
        String cruiserName = "";
        String guideName = "";
        String mallName = "";
        String mainProcedureName = "";
        String hotelName = "";


        while (eventReader.hasNext()) {
            XMLEvent event = null;
            try {
                event = eventReader.nextEvent();
            } catch (XMLStreamException e) {
                logger.error(e);
            }
            String qName = "";

            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    qName = startElement.getName().getLocalPart();
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
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String value = event.asCharacters().getData();
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
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    qName = endElement.getName().getLocalPart();
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
                    break;
            }
        }
        return vouchers;
    }
}
