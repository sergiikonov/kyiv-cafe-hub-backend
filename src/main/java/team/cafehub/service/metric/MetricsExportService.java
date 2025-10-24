package team.cafehub.service.metric;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsExportService {
    private final JdbcTemplate jdbcTemplate;
    private final CsvExportUtil csvExportUtil;

    public Map<String, String> exportAllMetrics() throws IOException {
        final LocalDateTime exportTimestamp = LocalDateTime.now();

        Map<String, String> csvFiles = new HashMap<>();

        csvFiles.put("content_performance.csv", exportContentPerformance(exportTimestamp));
        csvFiles.put("volume_and_averages.csv", exportVolumeAndAverages(exportTimestamp));
        csvFiles.put("user_contribution.csv", exportUserContribution(exportTimestamp));
        csvFiles.put("utilization_rank.csv", exportUtilizationRank(exportTimestamp));
        csvFiles.put("data_quality.csv", exportDataQuality(exportTimestamp));
        csvFiles.put("structural_health.csv", exportStructuralHealth(exportTimestamp));

        return csvFiles;
    }

    // 1. Content Performance CSV
    private String exportContentPerformance(LocalDateTime timestamp) throws IOException {
        String sql = "SELECT ? AS export_timestamp, v.* FROM content_performance_v v";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp);
        return csvExportUtil.convertToCsv(data);
    }

    // 2. Volume and Average Metrics CSV
    private String exportVolumeAndAverages(LocalDateTime timestamp) throws IOException {
        String sql = """
            SELECT
                ? AS export_timestamp,
                COUNT(CASE WHEN created > ? - INTERVAL '7 days' THEN 1 END) AS new_content_7d,
                COUNT(*) AS total_content_volume,
                AVG(views) AS avg_views,
                (SUM(CASE WHEN is_deleted = TRUE THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS 
                    deletion_rate_pct
            FROM content_performance_v
                    """;
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp, timestamp);
        return csvExportUtil.convertToCsv(data);
    }

    // 3. User Contribution CSV
    private String exportUserContribution(LocalDateTime timestamp) throws IOException {
        String sql = """
            SELECT
                ? AS export_timestamp,
                AVG(content_count) AS avg_content_per_user
            FROM (
                SELECT
                    COALESCE(COUNT(c.id), 0) + COALESCE(COUNT(b.id), 0) AS content_count
                FROM "users" u
                LEFT JOIN cafes c ON u.id = c.user_id
                LEFT JOIN blog_posts b ON u.id = b.user_id
                GROUP BY u.id
            ) AS user_contributions
                    """;
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp);
        return csvExportUtil.convertToCsv(data);
    }

    // 4. Utilization Rank CSV
    private String exportUtilizationRank(LocalDateTime timestamp) throws IOException {
        String sql = """
            SELECT
                ? AS export_timestamp,
                'tag' AS type,
                t.name AS name,
                COALESCE(COUNT(ct.cafe_id), 0) + COALESCE(COUNT(bpt.blog_post_id), 0) 
                    AS usage_count
            FROM tags t
            LEFT JOIN cafe_tags ct ON t.id = ct.tag_id
            LEFT JOIN blog_post_tags bpt ON t.id = bpt.tag_id
            GROUP BY t.name
            ORDER BY usage_count DESC
                    """;
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp);
        return csvExportUtil.convertToCsv(data);
    }

    // 5. Data Quality & Health CSV
    private String exportDataQuality(LocalDateTime timestamp) throws IOException {
        String sql = """
            SELECT
                ? AS export_timestamp,
                AVG(CASE WHEN c.address IS NOT NULL AND c.address <> '' THEN 1.0 
                    ELSE 0.0 END) * 100 AS pct_cafe_with_address,
                CAST(COUNT(i.id) AS NUMERIC) / NULLIF(COUNT(DISTINCT cp.content_id), 
                0) AS avg_images_per_content
            FROM content_performance_v cp
            LEFT JOIN cafes c ON cp.content_id = c.id AND cp.content_type = 'cafe'
            LEFT JOIN images i ON (cp.content_type = 'cafe' AND cp.content_id = i.cafe_id) 
                                      OR (cp.content_type = 'blogpost' 
                                              AND cp.content_id = i.blog_post_id)
            WHERE cp.is_deleted = FALSE
                    """;
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp);
        return csvExportUtil.convertToCsv(data);
    }

    // 6. Structural Health CSV
    private String exportStructuralHealth(LocalDateTime timestamp) throws IOException {
        String sql = """
            SELECT ? AS export_timestamp, v.* FROM (
                (
                    SELECT 'Orphaned Tag' AS violation_type, t.name AS item_name
                    FROM tags t
                    LEFT JOIN cafe_tags ct ON t.id = ct.tag_id
                    LEFT JOIN blog_post_tags bpt ON t.id = bpt.tag_id
                    WHERE ct.cafe_id IS NULL AND bpt.blog_post_id IS NULL
                )
                UNION ALL
                (
                    SELECT 'Orphaned Category' AS violation_type, c.name
                    FROM categories c
                    LEFT JOIN blog_post_categories bpc ON c.id = bpc.category_id
                    WHERE bpc.blog_post_id IS NULL
                )
                UNION ALL
                (
                    SELECT 'Cafe Missing User' AS violation_type, c.id::text AS item_name
                    FROM cafes c
                    LEFT JOIN "users" u ON c.user_id = u.id
                    WHERE u.id IS NULL
                )
                UNION ALL
                (
                    SELECT 'BlogPost Missing User' AS violation_type, b.id::text AS item_name
                    FROM blog_posts b
                    LEFT JOIN "users" u ON b.user_id = u.id
                    WHERE u.id IS NULL
                )
            ) AS v
                    """;
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, timestamp);
        return csvExportUtil.convertToCsv(data);
    }
}
