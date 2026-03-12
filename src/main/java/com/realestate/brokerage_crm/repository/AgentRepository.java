package com.realestate.brokerage_crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    boolean existsByEmail(String email);

    Optional<Agent> findByEmail(String email);

    @Query("""
        select a from Agent a
        join a.village v
        join v.sector s
        join s.district d
        join d.province p
        where (:code is not null and p.code = :code)
           or (:name is not null and lower(p.name) = lower(:name))
        """)
    List<Agent> findByProvinceCodeOrName(@Param("code") String code, @Param("name") String name);
}
