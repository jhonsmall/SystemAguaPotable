package devs.team.net.repository.search;

import devs.team.net.domain.Costo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Costo entity.
 */
public interface CostoSearchRepository extends ElasticsearchRepository<Costo, Long> {
}
