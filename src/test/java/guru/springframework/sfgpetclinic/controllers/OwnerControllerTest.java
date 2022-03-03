package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 03/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildCardString() {

        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // Then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Buck%");
    }

    @Test
    void processFindFormWildCardStrin() {

        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
    }

    @Test
    void processCreationForm() {

        // Given
        Owner owner = new Owner(1L, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);

        // When
        String viewName = controller.processCreationForm(owner, bindingResult);

        // Then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationFormNoErrors() {

        // Given
        Owner owner = new Owner(5L, "Jim", "Bob");
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);

        // When
        String viewName = controller.processCreationForm(owner, bindingResult);

        // Then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}