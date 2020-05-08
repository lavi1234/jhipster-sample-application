package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EnquiryNote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnquiryNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquiryNoteRepository extends JpaRepository<EnquiryNote, Long>, JpaSpecificationExecutor<EnquiryNote> {
}
