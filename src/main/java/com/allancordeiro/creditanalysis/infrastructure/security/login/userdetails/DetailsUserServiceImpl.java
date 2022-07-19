package com.allancordeiro.creditanalysis.infrastructure.security.login.userdetails;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.infrastructure.security.login.exceptions.UnauthorizedException;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerInputDto;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerUseCase;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetailsUserServiceImpl implements UserDetailsService {
    private final CustomerGateway customerGateway;

    public DetailsUserServiceImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        FindCustomerUseCase useCase = new FindCustomerUseCase(this.customerGateway);

        try {
            FindCustomerInputDto login = new FindCustomerInputDto(userEmail);
            Optional<FindCustomerOutputDto> customer = useCase.execute(login);
            if(customer.isEmpty()) {
                throw new UsernameNotFoundException("Invalid login attempt");
            }
            LoginCustomerInputDto input = new LoginCustomerInputDto(customer.get().email(), customer.get().password());
            return new UserDetailData(input);
        } catch (Exception ex) {
            throw new UsernameNotFoundException(ex.toString());
        }
    }
}
