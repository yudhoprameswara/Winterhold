package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.customer.CustomerBioDTO;
import com.winterhold.Winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.customer.CustomerRowDTO(
            cus.membershipNumber,
            CONCAT(cus.firstName,' ',cus.lastName),
            cus.membershipExpireDate
            )
            FROM Customer AS cus
            WHERE CONCAT(cus.firstName,' ',cus.lastName) LIKE %:name%
            AND cus.membershipNumber LIKE %:membershipNumber%
            AND (:date IS NULL OR cus.membershipExpireDate < :date)
            """)
    Page<CustomerRowDTO> getRows(@Param("name") String name,
                                 @Param("membershipNumber") String membershipNumber,
                                 @Param("date") LocalDate date,
                                 Pageable pageable);


    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(
            cus.membershipNumber,
            CONCAT(cus.firstName,' ',cus.lastName)
            )
            FROM Customer AS cus
            WHERE cus.membershipExpireDate > GETDATE()
            """)
    public List<DropdownDTO> customerDropdown();
}
