/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.repository.EarringDetailRepository;
import com.dama.ohrringe.repository.EarringRepository;
import com.dama.ohrringe.util.TestUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EarringDetailServiceTest {
  @InjectMocks EarringDetailService service;
  @Mock EarringDetailRepository earringDetailRepository;
  @Mock EarringRepository earringRepository;

  @Test
  void createEarringDetail() {
    EarringDetail earringDetail = TestUtils.getEarringDetail("3eLTpd8");
    Mockito.when(earringDetailRepository.save(earringDetail)).thenReturn(earringDetail);

    EarringDetail actual = service.createEarringDetail(earringDetail);

    Assertions.assertNotNull(actual);
    Assertions.assertEquals(earringDetail, actual);
    verify(earringDetailRepository).save(earringDetail);
  }

  @Test
  void findAllEarringDetails() {
    List<EarringDetail> expected = List.of(TestUtils.getEarringDetail("hN11d"));
    Mockito.when(earringDetailRepository.findAll()).thenReturn(expected);

    List<EarringDetail> actual = service.findAllEarringDetails();

    Assertions.assertNotNull(actual);
    Assertions.assertFalse(actual.isEmpty());
    Assertions.assertEquals(expected, actual);
    verify(earringDetailRepository).findAll();
  }

  @Test
  void deleteById() {
    String id = "LVsl";

    when(earringDetailRepository.existsById(id)).thenReturn(true);
    service.deleteById(id);
    verify(earringDetailRepository).existsById(id);
    verify(earringDetailRepository).deleteById(id);

    when(earringDetailRepository.existsById(anyString())).thenReturn(false);
    assertThrows(
        ApplicationException.class,
        () -> service.deleteById("0ybfZ11"),
        "ApplicationException error was expected");

    when(earringDetailRepository.existsById(id)).thenReturn(true);
    Earring earring = TestUtils.getEarring("jB49301");
    when(earringRepository.findByEarringDetails_Id(id)).thenReturn(Optional.of(List.of(earring)));
    assertThrows(
        ApplicationException.class,
        () -> service.deleteById(id),
        "ApplicationException error was expected");
  }

  @Test
  void findEarringDetailById() {
    assertThrows(
        ApplicationException.class,
        () -> service.findEarringDetailById("31s0zTN"),
        "ApplicationException error was expected");

    String id = "uFgVnST4";
    EarringDetail expected = TestUtils.getEarringDetail("H7h");
    when(earringDetailRepository.findById(id)).thenReturn(Optional.ofNullable(expected));

    EarringDetail actual = service.findEarringDetailById(id);
    verify(earringDetailRepository).findById(id);
    assertNotNull(actual);
    assertEquals(expected, actual);
  }
}
