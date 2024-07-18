package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DesignConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDesignConfigurationRepository extends JpaRepository<DesignConfiguration,Integer> {

    List<DesignConfiguration> findDesignConfigurationByDesignType(String designType);
}
