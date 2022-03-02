package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 01/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = specialitySDJpaService.findById(1L);

        assertThat(foundSpeciality).isNotNull();
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void findByIdBddTest() {

        // Given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));

        // When
        Speciality foundSpeciality = specialitySDJpaService.findById(1L);

        // Then
        assertThat(foundSpeciality).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByObjectTest() {

        Speciality speciality = new Speciality();

        specialitySDJpaService.delete(speciality);

        verify(specialtyRepository).delete(isNotNull());
        verify(specialtyRepository).delete(and(isA(Speciality.class), any(Speciality.class)));
        verify(specialtyRepository).delete(and(isNotNull(), any(Speciality.class)));
        verify(specialtyRepository).delete(or(isA(Speciality.class), any(Speciality.class)));
        verify(specialtyRepository).delete(isA(Speciality.class));
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void delete() {
        specialitySDJpaService.delete(new Speciality());

        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, times(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeast() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtMost() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, atMost(5)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNever() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, atLeastOnce()).deleteById(anyLong());
        verify(specialtyRepository, never()).deleteById(5L);
    }
}