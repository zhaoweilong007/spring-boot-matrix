package com.zwl.repository;

import com.zwl.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/11 14:07
 **/
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
