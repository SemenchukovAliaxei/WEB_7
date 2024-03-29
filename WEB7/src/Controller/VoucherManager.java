package Controller;

import Controller.Comparator.VoucherDaysAmountComparator;
import Controller.Comparator.VoucherPeopleAmountComparator;
import Controller.Comparator.VoucherPlaceNameComparator;
import Controller.Comparator.VoucherPriceComparator;
import Controller.xml.XMLValidator;
import Controller.xml.parser.DOMParser;
import Controller.xml.parser.SAXParser;
import Controller.xml.parser.StAXParser;
import Model.Enums.Food;
import Model.Enums.ParsingModeEnum;
import Model.Enums.Transport;
import Model.Voucher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents linker pattern.
 */
public class VoucherManager {

    private static final Logger logger = LogManager.getLogger("Manager");

    /**
     * List of vouchers.
     */
    public LinkedList<Voucher> vouchers = new LinkedList<Voucher>();

    /**
     * Transport.
     */
    private Transport transport;

    /**
     * True - transport is set in builder.
     */
    private boolean isTransportSet = false;

    /**
     * food.
     */
    private Food food;

    /**
     * True - food is set in builder.
     */
    private boolean isFoodSet = false;

    /**
     * days amount.
     */
    private int daysAmount;

    /**
     * True - days amount is set in builder.
     */
    private boolean isdaysAmountSet = false;

    public List<Voucher> parseVouchersList(String file, ParsingModeEnum mode) {
        switch (mode) {
            case DOM:
                return (new DOMParser()).parse(file);
            case SAX:
                return (new SAXParser()).parse(file);
            case StAX:
                return (new StAXParser()).parse(file);
            default:
                return null;
        }
    }

    /**
     * Validates XML file with given schema
     * @param file XML file
     * @param schema XSD file
     * @return true if validation succeed, otherwise false
     */
    public boolean validate(String file, String schema) {
        boolean result = XMLValidator.validate(file, schema);
        if (result) {
            logger.info("Validation succeed");
        } else {
            logger.info("Validation failed");
        }
        return result;
    }

    /**
     * sorts vouchers by price.
     * @param isReversed return values in reversed way.
     * @return list of sorted values.
     */
    public List<Voucher> sortVouchersByPrice(boolean isReversed) {
        Comparator<Voucher> priceComparator = new VoucherPriceComparator();
        if (isReversed) {
            priceComparator = priceComparator.reversed();
        }
        return sortListWithComparator(priceComparator);
    }

    /**
     * sorts vouchers by days amount.
     * @param isReversed return values in reversed way.
     * @return list of sorted values.
     */
    public List<Voucher> sortVouchersByDaysAmount(boolean isReversed) {
        Comparator<Voucher> priceComparator = new VoucherDaysAmountComparator();
        if (isReversed) {
            priceComparator = priceComparator.reversed();
        }
        return sortListWithComparator(priceComparator);
    }

    /**
     * sorts vouchers by place name.
     * @param isReversed return values in reversed way.
     * @return list of sorted values.
     */
    public List<Voucher> sortVouchersByPlaceName(boolean isReversed) {
        Comparator<Voucher> priceComparator = new VoucherPlaceNameComparator();
        if (isReversed) {
            priceComparator = priceComparator.reversed();
        }
        return sortListWithComparator(priceComparator);
    }

    /**
     * sorts vouchers by people amount.
     * @param isReversed return values in reversed way.
     * @return list of sorted values.
     */
    public List<Voucher> sortVouchersByPeopleAmount(boolean isReversed) {
        Comparator<Voucher> priceComparator = new VoucherPeopleAmountComparator();
        if (isReversed) {
            priceComparator = priceComparator.reversed();
        }
        return sortListWithComparator(priceComparator);
    }

    /**
     * sorting method.
     * @param comparator comparator to use.
     * @return list of sortes values.
     */
    private List<Voucher> sortListWithComparator(Comparator<Voucher> comparator) {
        return vouchers.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * builder to look up for vouchers.
     * @return builder object.
     */
    public lookUpBuilder newLookUpBuilder() {
        return new lookUpBuilder();
    }

    /**
     * method to look up for vouchers according to set criteria.
     * @return list of vouchers.
     */
    private List<Voucher> lookUpByCriteria() {
        if(isTransportSet && isFoodSet && isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getFood() == food)
                    .filter(voucher -> voucher.getDaysAmount() == daysAmount)
                    .filter(voucher -> voucher.getTransport() == transport)
                    .collect(Collectors.toList());
        } else if(isTransportSet && isFoodSet && !isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getFood() == food)
                    .filter(voucher -> voucher.getTransport() == transport)
                    .collect(Collectors.toList());
        } else if(isTransportSet && !isFoodSet && isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getTransport() == transport)
                    .collect(Collectors.toList());
        } else if(!isTransportSet && isFoodSet && isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getFood() == food)
                    .collect(Collectors.toList());
        } else if(isTransportSet && !isFoodSet && !isdaysAmountSet){
            return vouchers.stream()
                    .filter(voucher -> voucher.getTransport() == transport)
                    .collect(Collectors.toList());
        } else if(!isTransportSet && isFoodSet && !isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getFood() == food)
                    .collect(Collectors.toList());
        } else if(!isTransportSet && !isFoodSet && isdaysAmountSet) {
            return vouchers.stream()
                    .filter(voucher -> voucher.getDaysAmount() == daysAmount)
                    .collect(Collectors.toList());
        } else {
            return new LinkedList<>();
        }
    }

    /**
     * builder to search for vouchers according to criteria.
     */
    public class lookUpBuilder {

        private lookUpBuilder() {
        }

        public lookUpBuilder setTransport(Transport transport) {
            VoucherManager.this.transport = transport;
            isTransportSet = true;
            return this;
        }

        public lookUpBuilder setDaysAmount(int daysAmount) {
            VoucherManager.this.daysAmount = daysAmount;
            isdaysAmountSet = true;
            return this;
        }

        public lookUpBuilder setFood(Food food) {
            VoucherManager.this.food = food;
            isFoodSet = true;
            return this;
        }

        public List<Voucher> build() {
            try {
                return VoucherManager.this.lookUpByCriteria();
            } finally {
                isdaysAmountSet = false;
                isFoodSet = false;
                isTransportSet = false;
            }
        }
    }
}