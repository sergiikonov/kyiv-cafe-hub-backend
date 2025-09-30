package team.cafehub.service.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.cafe.CafeStatsDto;
import team.cafehub.mapper.cafe.CafeMapper;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.cafe.CafeRepository;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeStatsServiceImpl implements CafeStatsService {
    private final CafeRepository cafeRepository;
    private final CafeMapper cafeMapper;

    @Override
    public void exportCafeStatsToCsv(PrintWriter writer) {
        writer.write("Назва кав'ярні,Кількість відвідувань,Створено,Оновлено,Ким\n");

        List<Cafe> cafes = cafeRepository.findAll();

        List<CafeStatsDto> cafeStats = cafes.stream()
                .map(cafeMapper::toCafeStatsDto)
                .toList();

        for (CafeStatsDto stats : cafeStats) {
            String userEmail = (stats.userEmail() != null) ? stats.userEmail() : "N/A";
            writer.write(String.format("\"%s\",%d,%s,%s,\"%s\"\n",
                    stats.cafeName(),
                    stats.views() != null ? stats.views() : 0,
                    stats.created(),
                    stats.updated(),
                    userEmail));
        }
    }
}
