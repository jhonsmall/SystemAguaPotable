package devs.team.net.repository.search;

import devs.team.net.domain.EscalasDelMedidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EscalasDelMedidor entity.
 */
public interface EscalasDelMedidorSearchRepository extends ElasticsearchRepository<EscalasDelMedidor, Long> {
}
