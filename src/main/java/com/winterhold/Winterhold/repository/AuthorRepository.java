package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.author.AuthorRowDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("""
            SELECT  new  com.winterhold.Winterhold.dto.author.AuthorRowDTO(
                    aut.id,
                    CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName) AS fullName,
            	    DATEDIFF(DAY,aut.birthDate,
            	    CASE
            		    WHEN aut.deceasedDate IS NULL THEN GETDATE()
            		ELSE aut.deceasedDate
            	    END
            	    )/365,
            	    CASE
            		    WHEN aut.deceasedDate IS NULL THEN 'Alive'
            		ELSE 'Deceased'
            		END,
            		aut.education)
            FROM Author AS aut
            WHERE
            CONCAT(aut.firstName,' ',aut.lastName) LIKE %:fullName%
            """)
    public Page<AuthorRowDTO> getRow(@Param("fullName") String fullName, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(
            aut.id,
            CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName)
            )
            FROM Author AS aut
            """)
    public List<DropdownDTO> getDropdown();
}
