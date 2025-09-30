package team.cafehub.service.cafe;

import java.io.IOException;
import java.io.PrintWriter;

public interface CafeStatsService {
    void exportCafeStatsToCsv(PrintWriter writer) throws IOException;
}
