package team.cafehub.service.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.blogpost.BlogPostStatsDto;
import team.cafehub.mapper.blogpost.BlogPostMapper;
import team.cafehub.model.blog_post.BlogPost;
import team.cafehub.repository.blogpost.BlogPostRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogPostStatsServiceImpl implements BlogPostStatsService {
    private final BlogPostRepository blogPostRepository;
    private final BlogPostMapper blogPostMapper;

    @Override
    public void exportCafeStatsToCsv(PrintWriter writer) throws IOException {
        writer.write("Назва блогу,Кількість відвідувань,Створено,Оновлено,Ким\n");

        List<BlogPost> blogPosts = blogPostRepository.findAll();

        List<BlogPostStatsDto> blogStats = blogPosts.stream()
                .map(blogPostMapper::toBlogStatsDto)
                .toList();

        for (BlogPostStatsDto stats : blogStats) {
            String userEmail = (stats.userEmail() != null) ? stats.userEmail() : "N/A";
            writer.write(String.format("\"%s\",%d,%s,%s,\"%s\"\n",
                    stats.blogName(),
                    stats.views() != null ? stats.views() : 0,
                    stats.created(),
                    stats.updated(),
                    userEmail));
        }
    }
}
