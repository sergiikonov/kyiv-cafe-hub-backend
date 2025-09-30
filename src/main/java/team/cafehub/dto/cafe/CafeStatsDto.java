package team.cafehub.dto.cafe;

import java.util.Date;

public record CafeStatsDto (
        String cafeName,
        Integer views,
        Date created,
        Date updated,
        String userEmail
) {
}
