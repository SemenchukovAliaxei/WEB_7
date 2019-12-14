import Controller.VoucherManager;
import Model.*;
import Model.Enums.ParsingModeEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger("main");

    public static void main(String[] args) {
        try {
            VoucherManager voucherManager = new VoucherManager();

            voucherManager.validate("vouchers.xml", "xsd_schema.xsd");

            List<Voucher> vouchers;
            vouchers = voucherManager.parseVouchersList("vouchers.xml", ParsingModeEnum.DOM);
            for (Voucher voucher : vouchers) {
                logger.info(voucher.toString());
            }

            vouchers = voucherManager.parseVouchersList("vouchers.xml", ParsingModeEnum.SAX);
            for (Voucher voucher : vouchers) {
                logger.info(voucher.toString());
            }

            vouchers = voucherManager.parseVouchersList("vouchers.xml", ParsingModeEnum.StAX);
            for (Voucher voucher : vouchers) {
                logger.info(voucher.toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
