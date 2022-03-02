package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 02/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @InjectMocks
    private VisitSDJpaService service;

    @Mock
    private VisitRepository visitRepository;

    @Nested
    class ReadPutDeleteVisitObjectTest {

        private Visit visit;

        @BeforeEach
        void setUp() {
            visit = new Visit();
        }

        @DisplayName("Test Find All")
        @Test
        void findAll() {

            // Given
            Set<Visit> visits = new HashSet<>();
            visits.add(visit);
            given(visitRepository.findAll()).willReturn(visits);

            // When
            Set<Visit> foundVisits = service.findAll();

            // Then
            then(visitRepository).should().findAll();
            assertThat(foundVisits)
                    .hasSize(1)
                    .containsAll(Set.of(visit));
        }

        @DisplayName("Test Find By Id")
        @Test
        void findById() {

            // Given
            given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

            // When
            Visit foundVisit = service.findById(1L);

            // Then
            then(visitRepository).should().findById(anyLong());
            assertThat(foundVisit)
                    .isNotNull()
                    .hasAllNullFieldsOrProperties()
                    .isSameAs(visit);
        }

        @DisplayName("Test Save")
        @Test
        void save() {

            // Given
            given(visitRepository.save(any(Visit.class))).willReturn(visit);

            // When
            Visit savedVisit = service.save(new Visit());

            // Then
            then(visitRepository).should().save(any(Visit.class));
            assertThat(savedVisit)
                    .isNotNull()
                    .hasAllNullFieldsOrProperties()
                    .isInstanceOf(Visit.class);
        }

        @DisplayName("Test Delete")
        @Test
        void delete() {

            // Given
            willDoNothing().given(visitRepository).delete(and(isNotNull(), isA(Visit.class)));

            // When
            service.delete(visit);

            // Then
            then(visitRepository).should().delete(any(Visit.class));
        }
    }

    @DisplayName("Test Delete By Id")
    @Test
    void deleteById() {

        // Given
        willDoNothing().given(visitRepository).deleteById(and(isNotNull(), isA(Long.class)));

        service.deleteById(1L);

        then(visitRepository).should().deleteById(anyLong());
    }
}