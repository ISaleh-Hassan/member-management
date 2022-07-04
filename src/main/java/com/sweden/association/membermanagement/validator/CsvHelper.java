package com.sweden.association.membermanagement.validator;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CsvHelper {
    // These columns represent the structure of the CSV file that the client will export from Swedbank
    // Note: We accept only the english header
    public final static String[] VALID_HEADER =
            new String[] {"Line number","Clearing number","Account number","Product","Currency","Booked date","Transaction date","Value date","Reference","Description","Amount","Booked balance"};

    public static List<String[]> readCsv(byte[] csvBytes, int skipLines) throws Exception {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(new ByteArrayInputStream(csvBytes)))
                .withSkipLines(skipLines)
                .withCSVParser(parser)
                .build();
        return csvReader.readAll();
    }

    public static List<String[]> tryReadCsvFile(MultipartFile selectedFile, int skipLines){
        try {
            return CsvHelper.readCsv(selectedFile.getBytes(), skipLines);
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong while reading the file, make sure to add the file correctly.");
        }
    }

    public static void isValidSwedbankCsvFile(List<String[]> csvRows){
        if(Objects.isNull(csvRows) || csvRows.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Csv rows shall not be null");
        }
        if(!Arrays.equals(VALID_HEADER, csvRows.get(1))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Csv header is not valid, please use the english file");
        }
    }

    public static BigDecimal validateAndConvertAmount(String strAmount){
        BigDecimal amount = new BigDecimal(strAmount);
        if(amount.intValue() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The received swish amount may not be less than zero");
        }
        return amount;
    }

    public static String validateAndGetDate(String strDate){
        final String DATE_FORMAT = "yyyy-MM-dd";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.parse(strDate);
            return strDate;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date of the transaction is not valid");
        }
    }

}
