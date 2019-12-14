package Controller.xml.parser;

import Model.Voucher;

import java.util.List;

/**
 * Parsing interface
 */

public interface VouchersParser {
    List<Voucher> parse(String fileName);
}
