package devs.team.net.repository.search;

import devs.team.net.domain.Recibo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Recibo entity.
 */
public interface ReciboSearchRepository extends ElasticsearchRepository<Recibo, Long> {
}
