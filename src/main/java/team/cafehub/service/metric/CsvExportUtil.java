package team.cafehub.service.metric;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

@Component
public class CsvExportUtil {
    public String convertToCsv(List<Map<String, Object>> data) throws IOException {
        if (data == null || data.isEmpty()) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        String[] headers = data.getFirst().keySet().toArray(new String[0]);

        try (CSVPrinter csvPrinter = new CSVPrinter(stringWriter,
                CSVFormat.DEFAULT.withHeader(headers))) {
            for (Map<String, Object> row : data) {
                csvPrinter.printRecord(row.values());
            }
            csvPrinter.flush();
            return stringWriter.toString();
        }
    }
}
