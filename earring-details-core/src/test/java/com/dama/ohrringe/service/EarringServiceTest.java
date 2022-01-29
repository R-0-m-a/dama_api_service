/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import static com.dama.ohrringe.util.TestUtils.getEarring;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.repository.EarringRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EarringServiceTest {
  @InjectMocks EarringService earringService;
  @Mock EarringRepository earringRepository;

  @Test
  void createEarring() {
    Earring earring = getEarring("xoY42t9");
    when(earringRepository.save(earring)).thenReturn(earring);
    assertEquals(earringService.createEarring(earring), earring);
    assertNotNull(earring, "Earring should not be null");
    verify(earringRepository, times(1)).save(earring);
  }

  @Test
  void findAllEarrings() {
    List<Earring> earrings =
        List.of(getEarring("pSiYB8Sj"), getEarring("wHH0E4V"), getEarring("MxVu"));

    when(earringRepository.findAll()).thenReturn(earrings);
    assertEquals(earringService.findAllEarrings().size(), earrings.size());
    verify(earringRepository, times(1)).findAll();
  }

  @Test
  void deleteById() {
    String id = "5Kt";
    when(earringRepository.existsById(id)).thenReturn(true);
    earringService.deleteById(id);
    verify(earringRepository).existsById(id);
    verify(earringRepository).deleteById(id);

    when(earringRepository.existsById(anyString())).thenReturn(false);
    assertThrows(
        ApplicationException.class,
        () -> earringService.deleteById("WlDQeGm"),
        "ApplicationException error was expected");
  }

  @Test
  void findEarringById() {
    when(earringRepository.existsById(anyString())).thenReturn(false);
    assertThrows(
        ApplicationException.class,
        () -> earringService.findEarringById("wz7v8"),
        "ApplicationException error was expected");

    String id = "uFgVnST4";
    Earring earring = getEarring(id);
    when(earringRepository.existsById(id)).thenReturn(true);
    when(earringRepository.findById(id)).thenReturn(Optional.ofNullable(earring));

    Optional<Earring> actual = earringService.findEarringById(id);
    verify(earringRepository).existsById(id);
    verify(earringRepository).findById(id);
    assertTrue(actual.isPresent());
    assertEquals(earring, actual.get());
  }

}
