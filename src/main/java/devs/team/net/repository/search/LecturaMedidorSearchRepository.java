package devs.team.net.repository.search;

import devs.team.net.domain.LecturaMedidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LecturaMedidor entity.
 */
public interface LecturaMedidorSearchRepository extends ElasticsearchRepository<LecturaMedidor, Long> {
}
