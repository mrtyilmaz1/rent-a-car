package com.x.rentacar.DataInitializer;

import com.x.rentacar.Enums.Roles;
import com.x.rentacar.model.Address;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Address address1 = new Address();
        address1.setCountry("Türkiye"); address1.setCity("Ankara");
        address1.setDistrict("Sincan"); address1.setPostCode(42560);
        address1.setAddressLine("Aşağı mahalle, Yukarı sokak , no:3/29");

        Address address2 = new Address();
        address2.setCountry("Türkiye"); address2.setCity("İstanbul");
        address2.setDistrict("Üsküdar"); address2.setPostCode(34006);
        address2.setAddressLine("Yukarı mahalle, Aşağı sokak , no:5");


        Customer customer1 = new Customer();
        customer1.setId(1L); customer1.setEmail("asd@gmail.com");
        customer1.setFirstName("murat"); customer1.setSurname("yilmaz");
        customer1.setPassword("12345"); customer1.setAddress(address1);
        customer1.setRoles(Roles.ROLE_ADMIN);

        Customer customer2 = new Customer();
        customer2.setId(2L); customer2.setEmail("qwe@gmail.com");
        customer2.setFirstName("hasan"); customer2.setSurname("demircan");
        customer2.setPassword("54321"); customer2.setAddress(address2);
        customer2.setRoles(Roles.ROLE_USER);

        customerRepository.save(customer1);
        customerRepository.save(customer2);



    }
}
