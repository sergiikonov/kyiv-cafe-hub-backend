package team.cafehub.dto.blogpost;

import java.util.Date;

public record BlogPostStatsDto(
        String blogName,
        Integer views,
        Date created,
        Date updated,
        String userEmail
) {
}
