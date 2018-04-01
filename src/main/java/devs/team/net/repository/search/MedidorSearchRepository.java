package devs.team.net.repository.search;

import devs.team.net.domain.Medidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medidor entity.
 */
public interface MedidorSearchRepository extends ElasticsearchRepository<Medidor, Long> {
}
