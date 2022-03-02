package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 02/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    private VetRepository repository;

    @InjectMocks
    VetSDJpaService service;

    @Test
    void deleteById() {

        service.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}