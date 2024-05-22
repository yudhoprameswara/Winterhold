package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.author.ResponseUpsertAuthorDTO;
import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.dto.book.BookCategoryRowDTO;
import com.winterhold.Winterhold.dto.customer.CustomerBioDTO;
import com.winterhold.Winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.Winterhold.dto.customer.UpsertCustomerDTO;
import com.winterhold.Winterhold.entity.Author;
import com.winterhold.Winterhold.entity.Customer;
import com.winterhold.Winterhold.repository.CustomerRepository;
import com.winterhold.Winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public Page<CustomerRowDTO> getRows(String name, String membershipNumber,Boolean expired, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        LocalDate date = null;
        if(expired != null){
            date = LocalDate.now();
        }
        var rows = customerRepository.getRows(name,membershipNumber,date,pageable);
        return rows;
    }

    public CustomerBioDTO getBio(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        var dto = new CustomerBioDTO(
                entity.getMembershipNumber(),
                entity.getFirstName()+" "+entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress()
        );
        return dto;
    }

    public UpsertCustomerDTO findOne(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        var dto = new UpsertCustomerDTO(
                entity.getMembershipNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMembershipExpireDate()
        );
        return dto;
    }

    public void save(UpsertCustomerDTO dto){
        var entity = new Customer();
        entity.setMembershipNumber(dto.getMembershipNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
            if(dto.getMembershipExpireDate()== null) {
                entity.setMembershipExpireDate(LocalDate.now().plusYears(2));
            }
            else {
                entity.setMembershipExpireDate(dto.getMembershipExpireDate());
            }
        customerRepository.save(entity);
        }



//        var responeseEntity =authorRepository.save(entity);
//        var responseDTO = new ResponseUpsertAuthorDTO(
//                responeseEntity.getId(),
//                responeseEntity.getTitle(),
//                responeseEntity.getFirstName(),
//                responeseEntity.getLastName(),
//                responeseEntity.getBirthDate(),
//                responeseEntity.getDeceasedDate(),
//                responeseEntity.getEducation(),
//                responeseEntity.getSummary()
//        );
//        return responseDTO;


    public Long countByCustomerNumber(String customerNumber){
        return loanRepository.countByCustomer(customerNumber);
    }

    public void delete(String customerNumber){
        customerRepository.deleteById(customerNumber);
    }

    public void extend(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        var extended= entity.getMembershipExpireDate().plusYears(2);
        if(entity.getMembershipExpireDate().isBefore(LocalDate.now()) ){
            extended = LocalDate.now().plusYears(2);
        }
        entity.setMembershipExpireDate(extended);
        customerRepository.save(entity);
    }
}
