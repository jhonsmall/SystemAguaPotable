package devs.team.net.repository.search;

import devs.team.net.domain.CostoMedidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CostoMedidor entity.
 */
public interface CostoMedidorSearchRepository extends ElasticsearchRepository<CostoMedidor, Long> {
}
