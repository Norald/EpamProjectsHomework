package service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.sun.org.apache.xpath.internal.operations.Or;
import integration.Order;
import integration.OrderState;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service("readFile")
public class ReadFile {

    public Set<Order> getOrders(String csvFile) {
        Set<Order> orderList = new LinkedHashSet<>();

        CSVReader reader = null;
        try {
            // Create an object of file reader class with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFile);

            // create csvParser object with
            // custom seperator semi-colon
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            // create csvReader object with parameter
            // filereader and parser
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();

            // Read all data at once
            List<String[]> allData = csvReader.readAll();

            // Print Data.
            for (String[] row : allData) {
                Order order = new Order();
                int counter = 0;
                for (String cell : row) {
                    counter++;
                    if(counter==1){
                        order.setId(Integer.parseInt(cell));
                    }else if(counter==2){
                        order.setSum(Integer.parseInt(cell));
                    }if(counter==3){
                        order.setState(cell);
                    }
                    orderList.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }
}
