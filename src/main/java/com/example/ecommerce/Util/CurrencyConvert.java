package com.example.ecommerce.Util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CurrencyConvert {

  public static String convertToRupiah(double price){
    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    formatRp.setCurrencySymbol("Rp. ");
    formatRp.setMonetaryDecimalSeparator(',');
    formatRp.setGroupingSeparator('.');

    kursIndonesia.setDecimalFormatSymbols(formatRp);
    return kursIndonesia.format(price).replace(",00", "");
  }

}
