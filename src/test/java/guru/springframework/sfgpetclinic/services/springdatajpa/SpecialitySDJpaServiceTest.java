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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.BDDMockito.*;

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
        // Given
        Speciality speciality = new Speciality();

        // When
        specialitySDJpaService.delete(speciality);

        // Then
        then(specialtyRepository).should().delete(isNotNull());
        then(specialtyRepository).should().delete(and(isA(Speciality.class), any(Speciality.class)));
        then(specialtyRepository).should().delete(and(isNotNull(), any(Speciality.class)));
        then(specialtyRepository).should().delete(or(isA(Speciality.class), any(Speciality.class)));
        then(specialtyRepository).should().delete(isA(Speciality.class));
        then(specialtyRepository).should().delete(any(Speciality.class));
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void delete() {
        // When
        specialitySDJpaService.delete(new Speciality());

        // Then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // Then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtLeast() {
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void deleteByIdAtMost() {
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // Then
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNever() {
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void testDoThrow() {

        // Given
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrows() {

        // Given
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));

        // Then
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(1L));
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException()).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        then(specialtyRepository).should().delete(any());
    }
}