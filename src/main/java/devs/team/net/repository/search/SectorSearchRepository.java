package devs.team.net.repository.search;

import devs.team.net.domain.Sector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sector entity.
 */
public interface SectorSearchRepository extends ElasticsearchRepository<Sector, Long> {
}
