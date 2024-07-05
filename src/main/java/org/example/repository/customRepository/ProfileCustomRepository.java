package org.example.repository.customRepository;

import org.example.dto.filter.ProfileFilterDTO;
import org.example.dto.response.FilterResponseDTO;
import org.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    private  final EntityManager entityManager;

    public ProfileCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FilterResponseDTO<ProfileEntity> filter(ProfileFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder();
        if (filter.getName() != null) {
            query.append(" and s.name=:name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {
            query.append(" and s.surname=:surname ");
        }
        params.put("surname", filter.getSurname());
        if (filter.getPhone() != null) {
            query.append(" and s.phone=:phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getRole() != null) {
            query.append(" and s.role=:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getCreatedDateFrom() != null) {
            query.append(" and s.createdDate between :createdDateFrom and :createdDateTo");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
            params.put("createdDateTo", filter.getCreatedDateTo());
        }
        StringBuilder selectSql = new StringBuilder("From ProfileEntity s where s.visible = true ");
        StringBuilder countSql = new StringBuilder("select count(s) From ProfileEntity s where s.visible = true ");

        selectSql.append(query);
        countSql.append(query);

        // select
        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entity : params.entrySet()) {
            selectQuery.setParameter(entity.getKey(), entity.getValue());
            countQuery.setParameter(entity.getKey(), entity.getValue());
        }
        selectQuery.setFirstResult(page * size); // offset
        selectQuery.setMaxResults(size); // limit
        List<ProfileEntity> studentEntityList = selectQuery.getResultList();
        // count
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResponseDTO<ProfileEntity>(studentEntityList, totalCount);
    }
}
