package team.cafehub.service.blogpost;

import java.io.IOException;
import java.io.PrintWriter;

public interface BlogPostStatsService {
    void exportCafeStatsToCsv(PrintWriter writer) throws IOException;
}
