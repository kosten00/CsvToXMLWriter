package ru.vasilev.converter;

import com.opencsv.CSVReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class Converter {
    public void convert(String inputFile, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFile);
             FileReader reader = new FileReader(inputFile)) {

            CSVReader csvReader = new CSVReader(reader);

            List<String[]> csvEntries = csvReader.readAll();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element table = doc.createElement("table");
            doc.appendChild(table);

            for (String[] entry : csvEntries) {
                Element tableRow = doc.createElement("tr");
                table.appendChild(tableRow);

                for (String str : entry) {
                    Element tableDetails = doc.createElement("td");
                    tableRow.appendChild(tableDetails);
                    tableDetails.appendChild(doc.createTextNode(str));
                }
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            System.out.println("Done");

        } catch (FileNotFoundException | ParserConfigurationException | TransformerConfigurationException e) {
            System.out.println("File was not found!");
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
